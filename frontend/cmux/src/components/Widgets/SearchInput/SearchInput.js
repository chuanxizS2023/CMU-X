import React, {useState} from "react";
import "./SearchInput.css";
import SearchIcon from "@material-ui/icons/Search";
import { searchPosts } from "../../../apis/communitypostAPIs/postAPI";
import SearchResultsPopup from "./SearchPopup";


function SearchInput({ placeholder }) {
  const [isFocus, setIsFocus] = React.useState(false);
  const [searchText, setSearchText] = React.useState('');
  const [isPopupOpen, setIsPopupOpen] = useState(false);
  const [results, setResults] = useState([]);

  const handleSearch = async () => {
    try{
      const res = await searchPosts(searchText);
      setResults(res);
      setIsPopupOpen(true);
    }
    catch(err){
      console.log(err);
    }
  }

  return (
    <div
      className={isFocus ? "widgetsSearch widgetsSearchFocus" : "widgetsSearch"}
    >
      <SearchIcon
        className={
          isFocus
            ? "widgetsSearchIcon widgetsSearchIconFocus"
            : "widgetsSearchIcon"
        }
        onClick={() => handleSearch(searchText)}
      />

      <input
        className="widgetsSearchInput"
        type="text"
        placeholder={placeholder}
        onFocus={() => setIsFocus(true)}
        onBlur={() => setIsFocus(false)}
        onChange={(e) => setSearchText(e.target.value)}
      />
      <SearchResultsPopup
        open={isPopupOpen}
        onClose={() => setIsPopupOpen(false)}
        searchResults={results}
      />
    </div>
  );
}

export default SearchInput;
