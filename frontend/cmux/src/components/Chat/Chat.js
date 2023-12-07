import React, { useState, useEffect, useContext } from 'react';
import { Client } from '@stomp/stompjs';
import ChatInputs from '../ChatInputs/ChatInputs';
import { AuthContext } from '../../components/AuthProvider';
import MyMessage from '../MyMessage/MyMessage';
import FromMessage from '../FromMessage/FromMessage';
import { UsersIcon } from '../../components/icons';
import { useFetchWithTokenRefresh } from '../../utils/ApiUtilsDynamic';
import './Chat.css';


const Chat = ({ chat }) => {
  const [messageHistory, setMessageHistory] = useState([]);
  const { userId } = useContext(AuthContext);
  const refreshToken = localStorage.getItem('refreshToken');

  useEffect(() => {
    const stompClient = new Client({
      brokerURL: `ws://${process.env.REACT_APP_URL}ws-chat/websocket`,
      beforeConnect: () => {
        stompClient.connectHeaders = {
          'Authorization': `Bearer ${refreshToken}`
        };
      },
      onConnect: () => {
        stompClient.subscribe(`/topic/chat.${chat.chatId}`, (message) => {
          const newMessage = JSON.parse(message.body);
          setMessageHistory(prevHistory => [...prevHistory, newMessage]);
        });
      },
    });

    stompClient.activate();

    return () => {
      stompClient.deactivate();
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

  useEffect(() => {
    fetchMessages();
    console.log('messageHistory changed:', messageHistory);
  }, [chat]);


  if (!chat) {
    return <div className="chat">No chat selected</div>;
  }

  return (
    <div className="chat">
      <div className="chatHeader">
        <span>{chat.chatName}</span>
        {chat.chatType === 'GROUP' && <UsersIcon />}
      </div>
      <div className="chatRoom">
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
        <ChatInputs />
      </div>
    </div>
  );
};

export default Chat;
