import { Alert, AlertTitle, Box, Button, Typography } from '@mui/material';
import { useLocation } from 'react-router-dom';
import { toast } from 'react-toastify';
import { handleReSendVerifyMail } from '~/apis';

import logo from '~/components/Images/logo.png';

function useQuery() {
    return new URLSearchParams(useLocation().search);
}

function MailAuthError() {
    const query = useQuery();
    const token = query.get('token');
    console.log(token);

    const handleReSendVerify = async () => {
        await handleReSendVerifyMail(token);
        toast.info('Hệ thống đã gửi lại mail xác thực mới, vui lòng kiểm tra!');
    };

    return (
        <>
            <div className="my-3 container">
                <div className="d-flex justify-content-center">
                    <img src={logo} alt="" style={{ width: 120 }} />
                </div>
                <div className="row">
                    <div className="col-sm-1 col-md-2 col-lg-3"></div>
                    <div className="col-sm-10 col-md-8 col-lg-6">
                        <Box
                            className="card"
                            noValidate
                            sx={{
                                top: '10%',
                            }}
                        >
                            <Typography className="card-header text-center fs-3" variant="h6" component="div">
                                Thông báo xác thực email
                            </Typography>
                            <Typography component="div" className="card-body p-0">
                                <Typography>
                                    <Alert sx={{ fontSize: '16px' }} variant="filled" severity="error">
                                        <AlertTitle sx={{ fontSize: '22px' }}>Xác thực email không thành công</AlertTitle>
                                        Việc xác thực tài khoản email của bạn đã thất bại hoặc quá hạn, vui lòng gửi lại yêu cầu
                                        xác thực cho hệ thống.
                                    </Alert>
                                </Typography>
                            </Typography>
                            <Typography component="div" className="card-footer text-center">
                                <Button
                                    color="error"
                                    sx={{
                                        width: '50%',
                                        border: 1,
                                        borderRadius: '8px',
                                        '&:hover': {
                                            color: 'white',
                                            backgroundColor: 'darkred',
                                        },
                                    }}
                                    onClick={handleReSendVerify}
                                >
                                    Gửi yêu cầu xác thực
                                </Button>
                            </Typography>
                        </Box>
                    </div>

                    <div className="col-sm-1 col-md-2 col-lg-3"></div>
                </div>
            </div>
        </>
    );
}

export default MailAuthError;
