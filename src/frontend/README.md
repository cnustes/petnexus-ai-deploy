# PetNexus AI - Frontend

This directory contains the source code for the PetNexus AI user interface, a Single-Page Application (SPA) built with React.

This application provides views for user registration, login, and a real-time chat interface that communicates with the Spring Boot backend API.

---

## Tech Stack

- **Framework:** [React.js](https://reactjs.org/)
- **Build Tool:** [Vite](https://vitejs.dev/)
- **Routing:** [React Router](https://reactrouter.com/)
- **HTTP Client:** [Axios](https://axios-http.com/)
- **Styling:** Plain CSS3

---

## Getting Started

Follow these instructions to get the frontend running locally for development.

### Prerequisites

- [Node.js](https://nodejs.org/) (version 20.x or higher)

### Installation & Setup

1.  **Navigate to the frontend directory:**
    From the root of the monorepo, run:
    ```bash
    cd frontend
    ```

2.  **Install dependencies:**
    ```bash
    npm install
    ```

3.  **Set up environment variables:**
    * Create a new file in the `frontend` directory named `.env`.
    * Add the following line to the file. This points the frontend to your local backend server.
    ```env
    VITE_API_URL=http://localhost:8080
    ```

---

## Available Scripts

In the `frontend` directory, you can run the following commands:

### `npm run dev`

Runs the app in development mode. Open [http://localhost:5173](http://localhost:5173) to view it in your browser. The page will reload when you make changes.

### `npm run build`

Builds the app for production to the `dist` folder. It correctly bundles React in production mode and optimizes the build for the best performance.

### `npm run preview`

Runs a local server to preview the production build from the `dist` folder.

---

## Project Structure

-   **`/public`**: Contains static assets like the `logo.png` and `favicon.png`.
-   **`/src/components`**: Contains reusable React components (`ChatWindow`, `ProtectedRoute`).
-   **`/src/pages`**: Contains top-level components that represent a full page (`LoginPage`, `ChatPage`, `RegisterPage`).
-   **`/src/App.jsx`**: The main application component that handles routing.
-   **`/src/main.jsx`**: The entry point of the React application.