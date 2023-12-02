import { Avatar } from "@material-ui/core";
import React from "react";
import { Link } from "react-router-dom";
import "./LastChat.css";

const LastChat = ({
  username,
  userimage,
  lastMessage,
  lastMessageTime,
}) => {
  return (
    <Link className="lastChat" to={`/Messages/mucahitsahin6-${username}`}>
      <div>
        <Avatar src={userimage} />
      </div>
      <div>
        <div>
          <span>
            {username}
          </span>
          <span>
            {lastMessageTime}
          </span>
        </div>
        <span>{lastMessage}</span>
      </div>
    </Link>
  );
};

export default LastChat;
