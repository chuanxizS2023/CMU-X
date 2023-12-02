import { Avatar } from "@material-ui/core";
import React from "react";
import { useHistory, useLocation } from "react-router-dom";
import ChatInputs from "../ChatInputs/ChatInputs";
import FromMessage from "../FromMessage/FromMessage";
import BackIcon from "@material-ui/icons/KeyboardBackspace";
import MyMessage from "../MyMessage/MyMessage";
import "./Chat.css";
import Loading from "../Loading/Loading";

const Chat = ({ messages, users }) => {
  const username = "mucahitsahin6";
  //let { id } = useParams();
  var id = useLocation().pathname;
  let history = useHistory();
  const [user, setUser] = React.useState({});
  const [messagesData, setMessagesData] = React.useState();

  const [loading, setLoading] = React.useState(true);
  setTimeout(() => {
    setLoading(false);
  }, 2000);

  React.useEffect(() => {
    if (id) {
      let userid = id.split("-")[1];
      let messageid = id.split("/")[2];
      setUser(users.find((user) => user.username === userid));
      setMessagesData(
        messages.find((message) => message.fromto === messageid).messages
      );
    }
  }, [id]);

  return (
    <div className="chat">
      {loading ? (
        <Loading />
      ) : (
        <>
          <div className="chatHeader">
            <div onClick={() => history.goBack()}>
              <BackIcon />
            </div>
            <Avatar src={user && user.userimage} />
            <div>
              <span>{user && user.username}</span>
            </div>
          </div>
          <div className="chatRoom">
            <div className="chatMessages">
              {messagesData &&
                messagesData.map((message) =>
                  message.from === username ? (
                    <MyMessage message={message.message} />
                  ) : (
                    <FromMessage
                      message={message.message}
                      userimage={user.userimage}
                    />
                  )
                )}
            </div>
            <ChatInputs />
          </div>
        </>
      )}
    </div>
  );
};

export default Chat;
