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
      
      const res = await searchPosts(searchText);
      let user_res = [];
      // if searchText contains only numbers, search by userId
      const isNumericSearch = /^\d+$/.test(searchText);
      if (isNumericSearch) {
        const params = { userId: searchText };
        const query = new URLSearchParams(params).toString();
        user_res = await fetchUser(query);
      }
      else {
        const params = { username: searchText };
        const query = new URLSearchParams(params).toString();
        user_res = await fetchUserByName(query);
      }
      if (res.length === 0 && user_res.length === 0) {
        setIsPopupOpen(false);
        return;
      }
      console.log(user_res);
      // Iterate over user_res array to update each user object
      for (let i = 0; i < user_res.length; i++) {
        const user = user_res[i];
        // Use the actual user ID from the user object
        const followerParams = { userId: user.userId };
        const subscriptionParams = { userId: user.userId };
        const followerQuery = new URLSearchParams(followerParams).toString();
        const subscriptionQuery = new URLSearchParams(subscriptionParams).toString();
      
        const user_followers = await fetchFollowers(followerQuery);
        const user_subscriptions = await fetchSubscriptions(subscriptionQuery);
      
        const has_subscription_params = { userId: userId, otherUserId: user.userId };
        const has_subscription_query = new URLSearchParams(has_subscription_params).toString();
      
        const user_has_subscription = await fetchHasSubscription(has_subscription_query);
      
        // Update the user object
        user.followers = user_followers;
        user.following = user_subscriptions;
        user.isFollowing = user_has_subscription;
        user.isSelf = userId === user.userId;
        console.log(user);
      }
  
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
