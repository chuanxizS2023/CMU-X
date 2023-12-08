import React, { useContext }from "react";
import "./Sidebar.css";
import SidebarItem from "./SidebarItem/SidebarItem";
import {
  HomeIcon,
  MessagesIcon,
  ListIcon,
  UserIcon,
  ExploreIcon,
  SetTweetIcon,
  NotificationsIcon,
  BookmarkIcon,
  MoreIcon,
} from "../icons/index";
// import TwitterIcon from "@material-ui/icons/Twitter";
import MoreHorizIcon from "@material-ui/icons/MoreHoriz";
import { Avatar} from "@material-ui/core";
import { Link, useLocation } from "react-router-dom";
import MoreMenu from "../MoreMenu/MoreMenu";
import logo from "../../assets/cmux_logo_no_bg.png";
import {AuthContext} from '../../components/AuthProvider';
import LogoutIcon from "../icons/LogoutIcon";

function Sidebar() {
  const [location] = React.useState(useLocation().pathname);
  const [moreActive, setMoreActive] = React.useState(false);
  const { signOut } = useContext(AuthContext);

  const handleLogout = () => {
    signOut();
  };
  return (
    <div className="sidebar">
      <img 
        src={logo} 
        style={{ width: '60px', height: '50px',  paddingLeft: '10px'}} 
      />
      <Link to="/home" style={{ textDecoration: "none" }}>
        <SidebarItem
          text="Home"
          Icon={HomeIcon}
          active={location === "/home" && true}
        />
      </Link>
      {/* <Link to="/explore" style={{ textDecoration: "none" }}>
        <SidebarItem
          text="Explore"
          Icon={ExploreIcon}
          active={location === "/explore" && true}
        />
      </Link>
      <Link to="/Notifications" style={{ textDecoration: "none" }}>
        <SidebarItem
          text="Notifications"
          Icon={NotificationsIcon}
          active={location === "/Notifications" && true}
        />
      </Link> */}
      <Link to="/Messages" style={{ textDecoration: "none" }}>
        <SidebarItem
          text="Messages"
          Icon={MessagesIcon}
          active={location === "/Messages" && true}
        />
      </Link>
      {/* <Link to="/Bookmarks" style={{ textDecoration: "none" }}>
        <SidebarItem
          text="Bookmarks"
          Icon={BookmarkIcon}
          active={location === "/Bookmarks" && true}
        />
      </Link>
      <Link to="/Lists" style={{ textDecoration: "none" }}>
        <SidebarItem
          text="Lists"
          Icon={ListIcon}
          active={location === "/Lists" && true}
        />
      </Link> */}
      <Link to="/Profile" style={{ textDecoration: "none" }}>
        <SidebarItem
          text="Profile"
          Icon={UserIcon}
          active={location === "/Profile" && true}
        />
      </Link>
      <div className="logoutButton" onClick={handleLogout}>
      <SidebarItem
          text="Logout"
          Icon={LogoutIcon}
      />
      </div>
      <div
        onClick={() => setMoreActive(!moreActive)}
        className="moreMenuButton"
      >
        <SidebarItem text="More" Icon={MoreIcon} />
        <MoreMenu active={moreActive} />
        {moreActive && <div className="closeMoreMenuPanel" />}
      </div> */}
      {/* <div className="tweetButton">
        <SetTweetIcon className="setTweetIcon" />
        <span>Tweet</span>
      </div> */}
      <div className="profileCard">
        <div className="profileCardImage">
          <Avatar src="https://avatars2.githubusercontent.com/u/38807255?s=460&u=deb087d587be7f6a4000e4e710ec4d1daa6fde84&v=4" />
        </div>
        <div className="profileCardNameCol">
          <div className="profileCardNameColName">
            <span>Test User</span>
          </div>
          <div className="profileCardNameColuserName">
            <span>@testuser</span>
          </div>
        </div>
        <div className="profileCardIcon">
          <MoreHorizIcon />
        </div>
      </div>
    </div>
  );
}

export default Sidebar;
