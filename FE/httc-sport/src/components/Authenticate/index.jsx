import { Box, CircularProgress, Typography } from '@mui/material';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { setToken } from '~/services/localStorageService';

export default function Authenticate() {
    const navigate = useNavigate();
    const [isLoggedin, setIsLoggedin] = useState(false);

    useEffect(() => {
        console.log(window.location.href);

        const authCodeRegex = /code=([^&]+)/;
        const isMatch = window.location.href.match(authCodeRegex);

        if (isMatch) {
            const authCode = isMatch[1];
            console.log(authCode);

            fetch(`http://localhost:8082/api/auth/outbound/google/authenticate?code=${authCode}`, {
                method: 'POST',
            })
                .then((response) => {
                    console.log(response);
                    return response.json();
                })
                .then((data) => {
                    console.log(data);

                    setToken(data.result?.token);
                    setIsLoggedin(true);
                });
        }
    }, []);

    // useEffect(() => {
    //     if (isLoggedin) {
    //         navigate('/');
    //     }
    // }, [isLoggedin, navigate]);

    return (
        <>
            <Box
                sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    gap: '30px',
                    justifyContent: 'center',
                    alignItems: 'center',
                    height: '100vh',
                }}
            >
                <CircularProgress></CircularProgress>
                <Typography>Authenticating...</Typography>
            </Box>
        </>
    );
}
