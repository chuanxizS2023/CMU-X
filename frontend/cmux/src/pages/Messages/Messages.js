import { Avatar } from "@material-ui/core";
import React from "react";
import { useSelector } from "react-redux";
import { useLocation } from "react-router";
import BottomSidebar from "../../components/BottomSidebar/BottomSidebar";
import Chat from "../../components/Chat/Chat";
import DrawerBar from "../../components/DrawerBar/DrawerBar";
import HomeBox from "../../components/HomeBox/HomeBox";
import { UsersIcon } from "../../components/icons";
import LastChat from "../../components/LastChat/LastChat";
import NotSelectedMessage from "../../components/NotSelectedMessage/NotSelectedMessage";
import SearchInput from "../../components/Widgets/SearchInput/SearchInput";
import "./Messages.css";

const Messages = () => {
  const [showGroupUserSelection, setShowUserList] = React.useState(false);
  const [isDrawerBar, setIsDrawerBar] = React.useState(false);
  const { messages } = useSelector((state) => state.messages);
  const { users } = useSelector((state) => state.users);
  let path = useLocation().pathname;
  document.title = "Messages / Twitter";

  const handleUsersIconClick = () => {
    setShowUserList(true);
  };

  return (
    <HomeBox>
      <div className={`messages ${path !== "/Messages" && "messagesNone"}`}>
        {isDrawerBar && (
          <div
            onClick={() => setIsDrawerBar(false)}
            className="drawerBarPanel"
          />
        )}
        <DrawerBar active={isDrawerBar} />
        <div className="messagesHeader">
          <div onClick={() => setIsDrawerBar(true)}>
            <Avatar src="" />
          </div>
          <span>Messages</span>
          <UsersIcon onClick={handleUsersIconClick} />
        </div>
        {/* <div className="messagesSearchInput">
          <SearchInput placeholder="Search for people to talk" />
        </div>
        <div className="lastMessages">
          {messages.map((message) => {
            let user = users.find(
              (user) => user.username === message.fromto.split("-")[1]
            );
            return (
              <LastChat
                username={user.username}
                userimage={user.userimage}
                lastMessageTime={message.messages.slice(-1)[0].time}
                lastMessage={message.messages.slice(-1)[0].message}
              />
            );
          })}
        </div> */}


        {!showGroupUserSelection && (
          <><div className="messagesSearchInput">
            <SearchInput placeholder="Search for people to talk" />
          </div><div className="lastMessages">
              {messages.map((message) => {
                let user = users.find(
                  (user) => user.username === message.fromto.split("-")[1]
                );
                return (
                  <LastChat
                    username={user.username}
                    userimage={user.userimage}
                    lastMessageTime={message.messages.slice(-1)[0].time}
                    lastMessage={message.messages.slice(-1)[0].message} />
                );
              })}
            </div></>
        )}

        <BottomSidebar />
      </div>

      {path === "/Messages" ? (
        <NotSelectedMessage />
      ) : (
        <Chat messages={messages} users={users} />
      )}
    </HomeBox>
  );
};

export default Messages;
