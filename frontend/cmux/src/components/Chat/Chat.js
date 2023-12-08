import React, { useState, useEffect, useContext } from 'react';
import { chatSocketClient } from "../../ChatSocketClient";
import ChatInputs from '../ChatInputs/ChatInputs';
import { AuthContext } from '../../components/AuthProvider';
import MyMessage from '../MyMessage/MyMessage';
import FromMessage from '../FromMessage/FromMessage';
import { UsersIcon } from '../../components/icons';
import { useFetchWithTokenRefresh } from '../../utils/ApiUtilsDynamic';
import { useHistory } from 'react-router-dom';
import NotSelectedMessage from '../NotSelectedMessage/NotSelectedMessage';
import './Chat.css';


const Chat = ({ chat }) => {
  const [messageHistory, setMessageHistory] = useState([]);
  const { userId } = useContext(AuthContext);
  const [isUserListOpen, setIsUserListOpen] = useState(false);
  const [userList, setUserList] = useState([]);
  const [newUserId, setNewUserId] = useState("");
  const history = useHistory();

  const addMessageToHistory = async (newMessage) => {
    const senderInfo = await fetchSender(newMessage.senderId);
    const messageWithSender = { ...newMessage, sender: senderInfo };
    setMessageHistory(prevHistory => [...prevHistory, messageWithSender]);
  };

  useEffect(() => {
    if (!chat) {
      history.push('/Messages');
      return <NotSelectedMessage />;
    }

    console.log('Chat changed:', chat);

    const establishConnection = async () => {
      console.log('Establishing websocket connection ...');
      await chatSocketClient.ensureConnection();
      chatSocketClient.subscribeToTopic(`/topic/chat.${chat.chatId}`, async (message) => {
        console.log('Received message:', message);
        const senderInfo = await fetchSender(message.senderId);
        const messageWithSender = { ...message, sender: senderInfo };
        setMessageHistory(prevHistory => [...prevHistory, messageWithSender]);
      });
    };

    establishConnection();

    return () => {
      chatSocketClient.unsubscribeFromTopic(`/topic/chat.${chat.chatId}`);
      // chatSocketClient.disconnect();
    };
  }, [chat]);


  const fetchWithTokenRefresh = useFetchWithTokenRefresh();

  const fetchMessages = async () => {
    try {
      const url = `${process.env.REACT_APP_URL}api/chats/history/${chat.chatId}`;
      const response = await fetchWithTokenRefresh(url, { method: 'GET' });
      if (response.ok) {
        let data = await response.json();

        const messagesWithSender = await Promise.all(data.map(async (message) => {
          const sender = await fetchSender(message.senderId);
          return { ...message, sender };
        }));

        setMessageHistory(messagesWithSender);
        console.log('Chat history:', messagesWithSender);
      } else {
        console.error('Failed to fetch chat history');
      }
    } catch (error) {
      console.error('Error fetching chat history:', error);
    }
  };


  const fetchSender = async (userId) => {
    try {
      const url = `${process.env.REACT_APP_URL}user/${userId}`;
      const response = await fetchWithTokenRefresh(url, { method: 'GET' });
      if (response.ok) {
        const data = await response.json();
        return data;
      } else {
        throw new Error('Failed to fetch user');
      }
    } catch (error) {
      console.error('Error fetching user:', error);
      return null;
    }
  }

  const fetchUsers = async (userIds) => {
    try {
      const url = `${process.env.REACT_APP_URL}user/users`;
      const response = await fetchWithTokenRefresh(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(userIds)
      });
      if (response.ok) {
        const users = await response.json();
        return users;
      } else {
        console.error('Failed to fetch users');
        return [];
      }
    } catch (error) {
      console.error('Error fetching users:', error);
      return [];
    }
  };

  const fetchGroupUsers = async () => {
    try {
      const url = `${process.env.REACT_APP_URL}api/chats/groupusers/${chat.chatId}`;
      const response = await fetchWithTokenRefresh(url, { method: 'GET' });
      if (response.ok) {
        const data = await response.json();
        console.log('Group users:', data);
        return data;
      } else {
        console.error('Failed to fetch group users');
        return [];
      }
    } catch (error) {
      console.error('Error fetching group users:', error);
      return [];
    }
  };

  const handleAddGroupUser = async () => {
    try {
      const payload = [parseInt(newUserId)];

      if (isNaN(payload[0])) {
        console.error('Invalid user ID');
        return;
      }

      console.log('Adding user to group:', payload);
      const response = await fetchWithTokenRefresh(`${process.env.REACT_APP_URL}api/chats/groupusers/${chat.chatId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
      });

      if (response.ok) {
        loadUserList();
        setNewUserId("");
      } else {
        console.error('Failed to add user to group');
      }
    } catch (error) {
      console.error('Error adding user to group:', error);
    }
  };


  const handleDeleteGroupUser = async (userIdToDelete) => {
    try {
      console.log('Deleting user from group:', userIdToDelete);
      const response = await fetchWithTokenRefresh(`${process.env.REACT_APP_URL}api/chats/groupusers/${chat.chatId}/${userIdToDelete}`, {
        method: 'DELETE'
      });

      if (response.ok) {
        loadUserList();
      } else {
        console.error('Failed to delete user from group');
      }
    } catch (error) {
      console.error('Error deleting user from group:', error);
    }
  };

  const handleUserListOpen = () => {
    console.log('Opening user list');
    setIsUserListOpen(true);
  };

  const handleUserListClose = () => {
    setIsUserListOpen(false);
  };

  const loadUserList = async () => {
    try {
      const userIds = await fetchGroupUsers(chat.chatId);
      const users = await fetchUsers(userIds);
      setUserList(users);
    } catch (error) {
      console.error('Error loading user list:', error);
    }
  };

  useEffect(() => {
    console.log('isUserListOpen changed:', isUserListOpen);
    if (isUserListOpen) {
      console.log('Loading user list ...');
      loadUserList();
    }
  }, [isUserListOpen]);


  useEffect(() => {
    fetchMessages();
    console.log('messageHistory changed:', messageHistory);
  }, [chat]);


  if (!chat) {
    return <div className="chat">No chat selected</div>;
  }

  return (
    <div>
      (
      <div className="chat">
        <div className="chatHeader">
          <span>{chat.chatName}</span>
          {chat.chatType === 'GROUP' && <UsersIcon onClick={handleUserListOpen} />}
        </div>
        {isUserListOpen ? (
          <div className="userList">
            <h3>Group User List</h3>
            <ul>
              {userList.map((user) => (
                <li key={user.id}>
                  {user.username}
                  <button onClick={() => handleDeleteGroupUser(user.id)}>Delete</button>
                </li>
              ))}
            </ul>
            <h3>Add User</h3>
            <input
              type="text"
              placeholder="Enter User ID"
              value={newUserId}
              onChange={(e) => setNewUserId(e.target.value)}
            />
            <button onClick={handleAddGroupUser}>Add User</button>
            <button onClick={handleUserListClose}>Close</button>
          </div>
        ) : (<div className="chatRoom">
          <div className="chatMessages">
            {messageHistory.map((message) => {
              const isMyMessage = message.senderId == userId;

              return isMyMessage ? (
                <MyMessage message={message} />
              ) : (
                <FromMessage message={message} />
              );
            })}
          </div>
          <ChatInputs addMessageToHistory={addMessageToHistory} />
        </div>)}
      </div>
      )
    </div>
  );
};

export default Chat;
