import React from 'react';
import { Outlet } from 'react-router-dom';
import NavigationBar from './NavigationBar';

function MainLayout() {
  return (
    <div className="layout-container">
      <NavigationBar />
      <main className="main-content">
        <Outlet /> {/* Child pages like PetsPage or ChatPage will be rendered here */}
      </main>
    </div>
  );
}

export default MainLayout;