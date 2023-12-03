import React from "react";
import { Avatar } from "@material-ui/core";
import "./FromMessage.css";

const FromMessage = ({ message, userimage, timestamp }) => {
  const formatDate = (date) => {
    const d = new Date(date);
    const hours = d.getHours().toString().padStart(2, '0');
    const minutes = d.getMinutes().toString().padStart(2, '0');
    const day = d.getDate().toString().padStart(2, '0');
    const month = (d.getMonth() + 1).toString().padStart(2, '0');
    const year = d.getFullYear();
    return `${month}/${day}/${year} ${hours}:${minutes}`;
  };

  return (
    <div className="fromMessageContainer">
      <div className="messageTimestamp">{formatDate(timestamp)}</div>
      <div className="fromMessage">
        <div className="avatarContainer">
          <Avatar src={userimage} />
        </div>
        <div className="messageContent">
          <span>{message}</span>
        </div>
      </div>
    </div>
  );
};

export default FromMessage;
