// src/pages/ChatPage.jsx
import React from 'react';
import ChatWindow from '../components/ChatWindow';

function ChatPage() {
  return (
    <div className="page-container"> {/* The exact same centering container */}
      <ChatWindow />
    </div>
  );
}

export default ChatPage;