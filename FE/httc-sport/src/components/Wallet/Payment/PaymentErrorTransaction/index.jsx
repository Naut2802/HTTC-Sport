import { Box, CircularProgress, Typography } from '@mui/material';
import { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { handleConfirmTransaction } from '~/apis';

function useQuery() {
    return new URLSearchParams(useLocation().search);
}

export default function PaymentErrorTransaction() {
    const navigate = useNavigate();
    const query = useQuery();
    const code = query.get('code');
    const orderCode = query.get('orderCode');
    const status = query.get('status');

    console.log(orderCode);
    console.log(status);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const res = await handleConfirmTransaction(code, orderCode, status);
                console.log(res.data);

                toast.error(res.data.result.message);
                navigate('/');
            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, [code, orderCode, status, navigate]);

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
