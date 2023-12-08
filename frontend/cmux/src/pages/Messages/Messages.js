import React, { useState, useEffect, useContext } from "react";
import { Avatar } from "@material-ui/core";
import { useLocation, useHistory } from "react-router-dom";
import BottomSidebar from "../../components/BottomSidebar/BottomSidebar";
import Chat from "../../components/Chat/Chat";
import DrawerBar from "../../components/DrawerBar/DrawerBar";
import HomeBox from "../../components/HomeBox/HomeBox";
import AddIcon from "@material-ui/icons/Add";
import ChatList from "../../components/ChatList/ChatList";
import NotSelectedMessage from "../../components/NotSelectedMessage/NotSelectedMessage";
import SearchInput from "../../components/Widgets/SearchInput/SearchInput";
import { AuthContext } from '../../components/AuthProvider';
import "./Messages.css";
import { useFetchWithTokenRefresh } from '../../utils/ApiUtilsDynamic';


const Messages = () => {
  const [isDrawerBar, setIsDrawerBar] = useState(false);
  const { userId } = useContext(AuthContext);
  let path = useLocation().pathname;
  const [chats, setChats] = useState([]);
  const pathSegments = path.split('/');
  const currentChatId = pathSegments.length > 2 ? pathSegments[2] : null;
  const currentChat = chats.length > 0 ? chats.find(chat => chat.chatId === currentChatId) : null;
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [newGroupName, setNewGroupName] = useState("");

  const fetchWithTokenRefresh = useFetchWithTokenRefresh();

  const fetchReceiverName = async (userId) => {
    try {
      const url = `${process.env.REACT_APP_URL}user/${userId}`;
      const response = await fetchWithTokenRefresh(url, { method: 'GET' });
      if (response.ok) {
        const data = await response.json();
        return data.username;
      } else {
        throw new Error('Failed to fetch user');
      }
    } catch (error) {
      console.error('Error fetching user:', error);
      return null;
    }
  };


  const loadChatList = async () => {
    try {
      const response = await fetchWithTokenRefresh(`${process.env.REACT_APP_URL}api/chats/chatlist/${userId}`, {
        method: 'GET'
      });
      if (response.ok) {
        const data = await response.json();
        console.log('Chat list data:', data);
        const formattedChatsPromises = data.map(async chat => ({
          ...chat,
          chatName: await parseChatName(chat),
          lastMessageTime: formatDate(chat.lastMessageTime)
        }));
        const formattedChats = await Promise.all(formattedChatsPromises);
        console.log('formattedChats:', formattedChats);
        setChats(formattedChats);
      } else {
        console.error('Failed to fetch chat list');
      }
    } catch (error) {
      console.error('Error fetching chat list:', error);
    }
  };

  const createGroupChat = async (chatName) => {
    try {
      const payload = {
        chatName: chatName,
        userId: userId,
      };
      const response = await fetchWithTokenRefresh(`${process.env.REACT_APP_URL}api/chats/group`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
      });

      if (response.ok) {
        const newChat = await response.json();
        setChats([...chats, newChat]);
      } else {
        console.error('Failed to create group chat');
      }
    } catch (error) {
      console.error('Error creating group chat:', error);
    }
  };


  useEffect(() => {
    loadChatList();
  }, [userId]);

  const parseChatName = async (chat) => {
    console.log('parseChatName:', chat.chatName)
    if (chat.chatType === "PRIVATE") {
      const receiverId = chat.chatName.split("-").filter(id => id !== userId).join("");
      console.log('receiverId:', receiverId);
      const receiverName = await fetchReceiverName(receiverId);
      console.log('receiverName:', receiverName);
      return receiverName || 'Unknown User';
    } else {
      return chat.chatName;
    }
  };

  const formatDate = (lastMessageTime) => {
    const date = new Date(lastMessageTime);
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    return `${month}/${day} ${hours}:${minutes}`;
  };

  const history = useHistory();

  const handleAddIconClick = () => {
    handleDialogOpen();
  };

  const handleDialogOpen = () => {
    setIsDialogOpen(true);
  };

  const handleDialogClose = () => {
    setIsDialogOpen(false);
    setNewGroupName("");
  };

  const handleGroupNameChange = (event) => {
    setNewGroupName(event.target.value);
  };

  const handleCreateGroupChat = async () => {
    await createGroupChat(newGroupName);
    handleDialogClose();
  };

  return (
    <HomeBox>
      <div className={`messages ${path !== "/messages" && "messagesNone"}`}>
        {isDrawerBar && (
          <div onClick={() => setIsDrawerBar(false)} className="drawerBarPanel" />
        )}
        <DrawerBar active={isDrawerBar} />
        <div className="messagesHeader">
          <div onClick={() => setIsDrawerBar(true)}><Avatar src="" /></div>
          <span>Messages</span>
          <AddIcon onClick={handleAddIconClick} />
        </div>
        <div className="messagesSearchInput">
          <SearchInput placeholder="Search for people to talk" />
        </div>
        <div className="lastMessages">
          {chats.map(chat => (
            <ChatList
              chatId={chat.chatId}
              chatName={chat.chatName}
              lastMessage={chat.lastMessage}
              lastMessageTime={chat.lastMessageTime}
            />
          ))}
        </div>
        <BottomSidebar />
      </div>
      {isDialogOpen ? (
        <div className="groupDialog">
          <input
            type="text"
            placeholder="Enter group name"
            value={newGroupName}
            onChange={handleGroupNameChange}
          />
          <button onClick={handleCreateGroupChat}>Confirm</button>
          <button onClick={handleDialogClose}>Cancel</button>
        </div>
      ) : (
        (!currentChatId) ? (
          <NotSelectedMessage />
        ) : (
          <Chat chat={currentChat} />
        )
      )}
    </HomeBox>
  );
};

export default Messages;
