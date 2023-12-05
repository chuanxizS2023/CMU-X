import React, { useState } from "react";
import "./SearchInput.css";
import SearchIcon from "@material-ui/icons/Search";
import { searchPosts } from "../../../apis/communitypostAPIs/postAPI";
import SearchResultsPopup from "./SearchPopup";
import { useFetchWithTokenRefresh } from '../../../utils/ApiUtils';
import axios from 'axios';
import { useContext } from 'react';
import { AuthContext } from '../../AuthProvider';
import { Alarm } from "@material-ui/icons";


function SearchInput({ placeholder }) {
  const baseUrl = 'http://localhost:8082';
  const [ isFocus, setIsFocus ] = React.useState(false);
  const [ searchText, setSearchText ] = React.useState('');
  const [ isPopupOpen, setIsPopupOpen ] = useState(false);
  const [ results, setResults ] = useState([]);
  const [ user_results, setUserResults ] = useState([]);
  const user_id_url = baseUrl + '/subscriptions/user-id';
  const user_name_url = baseUrl + '/subscriptions/user-name';
  const follower_number_url = baseUrl + '/followers/count';
  const subscription_number__url = baseUrl + '/subscriptions/count';
  const hasSubscription_url = baseUrl + '/subscriptions/has';
  // Mock userId, should be replaced by the real userId
  // const userId = useContext(AuthContext).userId;
  const userId = '1';
  //========================================
  const fetchUser = async (query) => {
    try {
      const response = await axios.get(`${user_id_url}?${query}`);
      return response.data;
    } catch (error) {
      console.error("Error during search:", error);
      return [];
    }
  };

  const fetchUserByName = async (query) => {
    try {
      const response = await axios.get(`${user_name_url}?${query}`);
      return response.data;
    } catch (error) {
      console.error("Error during search:", error);
      return [];
    }
  };

  const fetchFollowers = async (query) => {
    try {
      const response = await axios.get(`${follower_number_url}?${query}`);
      return response.data;
    } catch (error) {
      console.error("Error during search:", error);
      return 0;
    }
  };

  const fetchSubscriptions = async (query) => {
    try {
      const response = await axios.get(`${subscription_number__url}?${query}`);
      return response.data;
    } catch (error) {
      console.error("Error during search:", error);
      return 0;
    }
  };

  const fetchHasSubscription = async (query) => {
    try {
      const response = await axios.get(`${hasSubscription_url}?${query}`);
      return response.data;
    } catch (error) {
      console.error("Error during search:", error);
      return false;
    }
  };

  const handleSearch = async () => {
    try {
      if (searchText === '') {
        setIsPopupOpen(false);
        return;
      }
      const params = { userId: searchText };
      const query = new URLSearchParams(params).toString();
      const res = await searchPosts(searchText);
      let user_res = [];
      // if searchText contains only numbers, search by userId
      const isNumericSearch = /^\d+$/.test(searchText);
      if (isNumericSearch) {
        user_res = await fetchUser(query);
      }
      else {
        user_res = await fetchUserByName(query);
      }
      if (res.length === 0 && user_res.length === 0) {
        setIsPopupOpen(false);
        return;
      }

      const has_subscription_params = { userId: userId, otherUserId: searchText };
      const has_subscription_query = new URLSearchParams(has_subscription_params).toString();

      const user_followers = await fetchFollowers(query);
      const user_subscriptions = await fetchSubscriptions(query);
      const user_has_subscription = await fetchHasSubscription(has_subscription_query);
      user_res.followers = user_followers;
      user_res.following = user_subscriptions;
      user_res.isFollowing = user_has_subscription;
      user_res.isSelf = userId === searchText;
      console.log(user_res);
      setResults(res);
      setUserResults(user_res);
      setIsPopupOpen(true);
      return;
    }
    catch (err) {
      console.log(err);
    }
  }

  return (
    <div
      className={ isFocus ? "widgetsSearch widgetsSearchFocus" : "widgetsSearch" }
    >
      <SearchIcon
        className={
          isFocus
            ? "widgetsSearchIcon widgetsSearchIconFocus"
            : "widgetsSearchIcon"
        }
        style={ { cursor: "pointer" } }
        onClick={ () => handleSearch(searchText) }
      />

      <input
        className="widgetsSearchInput"
        type="text"
        placeholder={ placeholder }
        onFocus={ () => setIsFocus(true) }
        onBlur={ () => setIsFocus(false) }
        onChange={ (e) => setSearchText(e.target.value) }
      />
      <SearchResultsPopup
        open={ isPopupOpen }
        onClose={ () => setIsPopupOpen(false) }
        searchResults={ results }
        userSearchResults={ user_results }
      />
    </div>
  );
}

export default SearchInput;
