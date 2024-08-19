import { Box, CircularProgress, Typography } from '@mui/material';
import { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { handleConfirmTransaction } from '~/apis';

function useQuery() {
    return new URLSearchParams(useLocation().search);
}

export default function PaymentSuccessTransaction() {
    const navigate = useNavigate();
    const query = useQuery();
    const code = query.get('code');
    const id = query.get('id');
    const status = query.get('status');

    console.log(code);
    console.log(id);
    console.log(status);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const res = await handleConfirmTransaction(code, id, status);
                toast.success(res.data.result.message);
                navigate('/');
            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, [code, id, status, navigate]);

    return (
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
            <Typography>Loading...</Typography>
        </Box>
    );
}
