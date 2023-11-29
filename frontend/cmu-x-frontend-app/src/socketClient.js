import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const socket = new SockJS('http://localhost:9000/ws-communitypost');
const client = new Client({
  webSocketFactory: () => socket,
  connectHeaders: {
    login: 'guest',
    passcode: 'guest'
  },
  reconnectDelay: 500,
  heartbeatIncoming: 4000,
  heartbeatOutgoing: 4000
});

client.activate(); 

client.onConnect = function (frame) {
  console.log('Connected: ' + frame);
};

client.onStompError = function (frame) {
  console.log('Broker reported error: ' + frame.headers['message']);
  console.log('Additional details: ' + frame.body);
};

const isClientConnected = () => client.active;

const ensureConnection = async () => {
  if (!isClientConnected()) {
    try {
      await client.activate();
    } catch (error) {
      console.error('Failed to establish STOMP connection:', error);
    }
  }
};

const disconnect = () => {
  if (client !== null) {
    client.deactivate();
  }
  console.log("Disconnected");
};

const sendMessage = async (endpoint, message) => {
  await ensureConnection();

  if (!isClientConnected()) {
    console.error("Failed to send message: STOMP client is not connected.");
    return;
  }

  const messageStr = typeof message === 'object' ? JSON.stringify(message) : message;

  client.publish({
    destination: endpoint,
    body: messageStr,
    skipContentLengthHeader: false
  });

  console.log(`Message sent to ${endpoint}: ${messageStr}`);
};

const subscribeToTopic = async (topic, callback) => {
  await ensureConnection();

  if (!isClientConnected()) {
    console.error("Failed to subscribe: STOMP client is not connected.");
    return null;
  }

  return client.subscribe(topic, message => {
    console.log(`Message received from topic ${topic}: ${message.body}`);
    callback(JSON.parse(message.body));
  });
};

export { client, disconnect, sendMessage, subscribeToTopic };
