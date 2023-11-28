import { Client } from '@stomp/stompjs';
import { WebSocket } from 'ws';
Object.assign(global, { WebSocket });

let stompClient = null;

const client = new Client({
    brokerURL: 'ws://localhost:9000/ws',
    onConnect: () => {
      client.subscribe('/topic/communityPostCreate', message =>
        console.log(`Received: ${message.body}`)
      );
      client.publish({ destination: '/topic/communityPostCreate', body: 'First Message' });
    },
  });
  
  client.activate();

// const connect = (onConnectedCallback) => {
//     const socket = new SockJS('http://localhost:9000/ws-communitypost');
//     stompClient = Stomp.over(socket);

//     stompClient.connect({}, frame => {
//         console.log('Connected: ' + frame);
//         if (onConnectedCallback) onConnectedCallback();
//     }, error => {
//         console.log('STOMP error: ' + error);
//     });
// };

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
};

const sendMessage = (endpoint, message) => {
    stompClient.send(endpoint, {}, JSON.stringify(message));
};

const subscribeToTopic = (topic, callback) => {
    stompClient.subscribe(topic, message => {
        callback(JSON.parse(message.body));
    });
};

export { client, disconnect, sendMessage, subscribeToTopic };
