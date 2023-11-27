import React from 'react';

const Header = ({ title }) => {
  const styles = {
    header: {
      backgroundColor: '#fff',
      padding: '10px',
      borderBottom: '1px solid #ccc',
      textAlign: 'center',
    },
  };

  return (
    <div style={styles.header}>
      <h1>{title}</h1>
      {/* Include any icons or additional navigation elements here */}
    </div>
  );
};

export default Header;
