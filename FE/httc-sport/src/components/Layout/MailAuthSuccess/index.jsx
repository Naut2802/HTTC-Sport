import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

function MailAuthSuccess() {
    const navigate = useNavigate();

    useEffect(() => {
        toast.success('Bạn đã xác thực Email thành công !!!');
        navigate('/dang-nhap');
    }, [navigate]);

    return <></>;
}

export default MailAuthSuccess;
