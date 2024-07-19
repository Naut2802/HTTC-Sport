import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

export default function FPVerifyError() {
    const navigate = useNavigate();

    useEffect(() => {
        toast.error('Xác thực email không thành công. Vui lòng thử lại !!!');
        navigate('/login');
    }, [navigate]);

    return <></>;
}
