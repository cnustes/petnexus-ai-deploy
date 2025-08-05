import React, { useState } from 'react';
import axios from 'axios';
import ReactMarkdown from 'react-markdown';
import { ClipLoader } from 'react-spinners';

const API_BASE_URL = import.meta.env.VITE_API_URL;

const API_URL = `${API_BASE_URL}/api/chat`;

function ChatWindow() {
  const [messages, setMessages] = useState([
    { sender: 'bot', text: 'Welcome to PetNexus AI! How can I assist you?' }
  ]);
  const [input, setInput] = useState('');

  const handleSend = async () => {
    if (input.trim() === '') return;

    const token = localStorage.getItem('jwt_token');
    if (!token) {
      setMessages(prev => [...prev, { sender: 'bot', text: 'Authentication error. Please log in again.' }]);
      return;
    }

    const userMessage = { sender: 'user', text: input };

    // We use a unique ID to find and replace this message later.
    const loadingMessageId = Date.now();
    const loadingMessage = { sender: 'bot', text: 'loading', id: loadingMessageId };
    setMessages(prevMessages => [...prevMessages, userMessage, loadingMessage]);

    const messageToSend = input;
    setInput('');

    try {
      const headers = { 'Authorization': `Bearer ${token}` };
      const response = await axios.post(API_URL, { message: messageToSend }, { headers });
      const botResponse = { sender: 'bot', text: response.data.reply };

      setMessages(prev => prev.map(msg =>
        msg.id === loadingMessageId ? botResponse : msg
      ));

    } catch (error) {
      console.error("Error calling the API:", error);
      const errorResponse = { sender: 'bot', text: 'Sorry, I am having trouble connecting to the server.' };

     setMessages(prev => prev.map(msg =>
        msg.id === loadingMessageId ? errorResponse : msg
      ));
    }
  };

  return (
    <div className="chat-window">
      <div className="chat-header">
        <img src="/logo.png" alt="PetNexus AI Logo" />
        <h1>PetNexus AI</h1>
      </div>

      <div className="messages-area">
        {messages.map((message, index) => (
          <div key={index} className={`message ${message.sender}`}>
            {}
            {message.text === 'loading' ? (
              <ClipLoader color="#fff" size={20} />
            ) : (
              <ReactMarkdown>{message.text}</ReactMarkdown>
            )}
          </div>
        ))}
      </div>

      <div className="input-area">
        <input
          type="text"
          placeholder="Ask something..."
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyPress={(e) => e.key === 'Enter' && handleSend()}
        />
        <button onClick={handleSend}>Send</button>
      </div>
    </div>
  );
}

export default ChatWindow;