import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

export default function MailAuthSuccess() {
    const navigate = useNavigate();

    useEffect(() => {
        toast.success('Bạn đã xác thực Email thành công !!!');
        navigate('/login');
    }, [navigate]);

    return <></>;
}
