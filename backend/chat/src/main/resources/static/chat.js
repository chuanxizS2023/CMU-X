let stompClient;
let username;
let chatType = "public";

function updateChatTypeSelection() {
    chatType = document.getElementById('chatType').value;
    if (chatType === 'private') {
        document.getElementById('receiverInput').style.display = 'block';
        document.getElementById('groupIdInput').style.display = 'none';
    } else {
        document.getElementById('groupIdInput').style.display = 'block';
        document.getElementById('receiverInput').style.display = 'none';
    }
}

function joinChat() {
    username = document.getElementById('usernameInput').value;
    const groupId = document.getElementById('groupIdInput').value;
    const receiver = document.getElementById('receiverInput').value;

    if (!username) {
        alert("Username is required!");
        return;
    }

    if (chatType === 'public' && !groupId) {
        alert("GroupId is required for public chat!");
        return;
    }

    if (chatType === 'private' && !receiver) {
        alert("Recipient's username is required for private chat!");
        return;
    }

    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        document.getElementById('chatRoom').style.display = 'block';

        if (chatType === 'public') {
            document.getElementById('publicChat').style.display = 'block';
            stompClient.subscribe(`/topic/public.${groupId}`, function (chatMessage) {
                const message = JSON.parse(chatMessage.body);
                document.getElementById('publicChatArea').value += `${message.sender} : ${message.content}\n`;
            });
        } else {
            document.getElementById('privateChat').style.display = 'block';
            stompClient.subscribe(`/topic/private.${username}.${receiver}`, function (chatMessage) {
                const message = JSON.parse(chatMessage.body);
                document.getElementById('privateChatArea').value += `${message.sender} : ${message.content}\n`;
            });
        }

        stompClient.send("/app/chat/addUser", {}, JSON.stringify({ 'sender': username, 'content': '' }));
    });
}

function sendPublicMessage() {
    const groupId = document.getElementById('groupIdInput').value;
    const messageInput = document.getElementById('publicMsgInput').value;
    stompClient.send("/app/chat/sendPublicMessage", {}, JSON.stringify({ 'sender': username, 'content': messageInput, 'groupId': groupId, 'chatType': 'public' }));
    document.getElementById('publicMsgInput').value = '';
}

function sendPrivateMessage() {
    const receiver = document.getElementById('receiverInput').value;
    const messageInput = document.getElementById('privateMsgInput').value;
    stompClient.send(`/app/chat/sendPrivateMessage`, {}, JSON.stringify({ 'sender': username, 'content': messageInput, 'receiver': receiver, 'chatType': 'private' }));
    document.getElementById('privateMsgInput').value = '';
}
