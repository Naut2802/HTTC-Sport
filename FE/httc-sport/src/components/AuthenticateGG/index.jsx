import { Box, CircularProgress, Typography } from '@mui/material';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { handleLogInWithGGAPI } from '~/apis';
import { setToken } from '~/services/localStorageService';

export default function AuthenticateGG() {
    const navigate = useNavigate();
    const [isLoggedin, setIsLoggedin] = useState(false);

    useEffect(() => {
        console.log(window.location.href);

        const authCodeRegex = /code=([^&]+)/;
        const isMatch = window.location.href.match(authCodeRegex);

        if (isMatch) {
            const authCode = isMatch[1];
            console.log(authCode);

            async function fetchData() {
                try {
                    const res = await handleLogInWithGGAPI(authCode);
                    console.log(res.data);
                    setToken(res.data.result?.accessToken);
                    localStorage.setItem('userId', res.data.result?.userId);
                    setIsLoggedin(true);
                } catch (error) {
                    console.error('Error during fetchData:', error);
                }
            }
            fetchData();
        }
    }, []);

    useEffect(() => {
        if (isLoggedin) {
            navigate('/');
            toast.success('Đăng nhập thành công với Google');
        }
    }, [isLoggedin, navigate]);

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
