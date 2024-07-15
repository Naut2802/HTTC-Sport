import { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

function useQuery() {
    return new URLSearchParams(useLocation().search);
}

export default function FPVerifySuccess() {
    const navigate = useNavigate();
    const query = useQuery();
    const token = query.get('token');
    console.log(token);
    localStorage.setItem('token', token);

    useEffect(() => {
        toast.success('Bạn đã xác thực Email thành công !!!');
        navigate('/forgot-password/reset-password');
    }, [navigate]);

    return <></>;
}
