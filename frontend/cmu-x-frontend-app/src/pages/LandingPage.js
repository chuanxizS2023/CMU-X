import React from 'react';
import { AppBar, Toolbar, Typography, Button, CssBaseline, Container, Box } from '@mui/material';
import { useNavigate } from 'react-router-dom';


const LandingPage = () => {
    const navigate = useNavigate();
    const handleSubmit = (event) => {
        event.preventDefault();
        navigate('/login', { replace: true }); // Redirect to a different route after login
    };
    // Navigation Bar Component
    const NavBar = () => (
        <AppBar position="static" style={ { background: 'linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)', boxShadow: '0 3px 5px 2px rgba(255, 105, 135, .3)' } }>
            <Toolbar>
                <Typography variant="h6" style={ { flexGrow: 1, fontFamily: 'Arial, sans-serif' } }>
                    CMUX
                </Typography>
                <Button color="inherit" onClick={ handleSubmit } style={ { borderRadius: 20, border: 0, color: 'white', height: 40, padding: '0 30px', boxShadow: '0 3px 5px 2px rgba(255, 105, 135, .3)' } }>
                    Login
                </Button>
            </Toolbar>
        </AppBar>
    );

    // Footer with enhanced look
    const Footer = () => (
        <Box sx={ { bgcolor: 'background.paper', p: 6 } } component="footer" style={ { background: '#f3f3f3' } }>
            <Typography variant="h6" align="center" gutterBottom style={ { fontFamily: 'Arial, sans-serif' } }>
                CMUX
            </Typography>
            <Typography variant="subtitle1" align="center" color="text.secondary" component="p">
                © 2023 Team 6, SDA.
            </Typography>
        </Box>
    );

    // Main content of the Landing Page
    return (
        <>
            <CssBaseline />
            <NavBar />
            <Box style={ { display: 'flex', flexDirection: 'column', minHeight: '100vh' } }>
                <Container component="main" maxWidth="md" style={ { flexGrow: 1 } }>
                    <Box sx={ { my: 3, textAlign: 'center' } }>
                        <Typography variant="h2" component="h1" gutterBottom>
                            Welcome to CMUX
                        </Typography>
                        <Typography variant="h5">
                            See what’s happening in the CMU right now
                        </Typography>
                        <Button variant="contained" onClick={ handleSubmit }
                        color="primary" sx={ { mt: 1 } }>
                            Join CMUx today
                        </Button>
                    </Box>
                </Container>
                <Footer />
            </Box>
        </>
    );
};

export default LandingPage;
