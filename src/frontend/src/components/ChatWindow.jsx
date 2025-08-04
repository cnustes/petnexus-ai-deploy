import React, { useState } from 'react';
import ReactMarkdown from 'react-markdown';
import axios from 'axios';

const API_URL = `${import.meta.env.VITE_API_URL}/api/chat`;

function ChatWindow() {
  const [messages, setMessages] = useState([
    { sender: 'bot', text: 'Welcome to PetNexus AI! How can I assist you?' }
  ]);
  const [input, setInput] = useState('');

  const handleSend = async () => {
    if (input.trim() === '') return;

    // --- CHANGE IS HERE ---
    // Get the token from localStorage
    const token = localStorage.getItem('jwt_token');

    // Check if the token exists
    if (!token) {
      setMessages(prev => [...prev, { sender: 'bot', text: 'Authentication error. Please log in again.' }]);
      return;
    }
    // --------------------

    const userMessage = { sender: 'user', text: input };
    setMessages(prevMessages => [...prevMessages, userMessage]);

    const messageToSend = input;
    setInput('');

    try {
      const headers = { 'Authorization': `Bearer ${token}` }; // Use the token from storage
      const response = await axios.post(API_URL, { message: messageToSend }, { headers });
      const botResponse = { sender: 'bot', text: response.data.reply };
      setMessages(prevMessages => [...prevMessages, botResponse]);
    } catch (error) {
      console.error("Error calling the API:", error);
      const errorResponse = { sender: 'bot', text: 'Sorry, I am having trouble connecting to the server.' };
      setMessages(prevMessages => [...prevMessages, errorResponse]);
    }
  };

  return (
      <div className="chat-window">
        <div className="chat-header">
          {/* ... (código del header) ... */}
        </div>

        <div className="messages-area">
          {messages.map((message, index) => (
            <div key={index} className={`message ${message.sender}`}>
              {/* --- 2. CHANGE IS HERE --- */}
              {/* Replace <p> with <ReactMarkdown> */}
              <ReactMarkdown>{message.text}</ReactMarkdown>
              {/* ------------------------- */}
            </div>
          ))}
        </div>

        <div className="input-area">
          {/* ... (código del input y botón) ... */}
        </div>
      </div>
    );
  }

  export default ChatWindow;