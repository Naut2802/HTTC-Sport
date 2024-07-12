/* eslint-disable no-restricted-globals */
import { Avatar, Box, Button, Checkbox, FormControlLabel, TextField, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';
import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

import { handleGetMyInfoAPI, handleLogInAPI } from '~/apis';
import fb from '~/components/Images/facebook-logo.png';
import gg from '~/components/Images/google-logo.jpg';
import logo from '~/components/Images/logo.png';
import { OAuthConfigGoogle } from '~/utils/constants';

function DangNhap() {
    const [rememberAccount, setRememberAccount] = useState(false);
    const navigate = useNavigate();
    const { register, handleSubmit } = useForm();

    const submitLogin = async (data) => {
        const res = await handleLogInAPI(data);
        localStorage.setItem('accessToken', res.data.result.accessToken);
        localStorage.setItem('userId', res.data.result.userId);

        // const res2 = await handleGetMyInfoAPI();
        // const roleInfo = res2.data.result.roles[0].roleName;
        // if (roleInfo === 'ADMIN') {
        //     navigate('/page-admin');
        // } else {
        //     navigate('/home');
        // }
        navigate('/trang-chu');
        toast.success('Đăng nhập thành công');
    };

    const handleLoginWithGG = () => {
        const callbackUrl = OAuthConfigGoogle.redirectUri;
        const authUrl = OAuthConfigGoogle.authUri;
        const googleClientId = OAuthConfigGoogle.clientId;

        const targetUrl = `${authUrl}?redirect_uri=${encodeURIComponent(
            callbackUrl,
        )}&response_type=code&client_id=${googleClientId}&scope=openid%20email%20profile`;

        console.log(targetUrl);

        window.location.href = targetUrl;
    };

    const handleLoginWithFB = () => {
        console.log('login with google');
    };

    const handleCheckboxChange = (event) => {
        setRememberAccount(event.target.checked);
    };

    const ValidationTextField = styled(TextField)({
        width: '100%',
        '& input:valid + fieldset': {
            borderColor: '#E0E3E7',
            borderWidth: 1,
        },
        '& input:invalid + fieldset': {
            borderColor: 'red',
            borderWidth: 1,
        },
        '& input:valid:focus + fieldset': {
            borderLeftWidth: 4,
            padding: '4px !important', // override inline-style
        },
    });

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
                            component="form"
                            onSubmit={handleSubmit(submitLogin)}
                            noValidate
                            sx={{
                                top: '10%',
                            }}
                        >
                            <Typography className="card-header text-center fs-3" variant="h6" component="div">
                                Đăng Nhập
                            </Typography>
                            <Typography component="div" className="card-body">
                                <ValidationTextField
                                    label="Tài Khoản"
                                    variant="outlined"
                                    id="validation-outlined-input"
                                    className="my-2 "
                                    autoComplete="username"
                                    {...register('username')}
                                />
                                <ValidationTextField
                                    label="Mật Khẩu"
                                    variant="outlined"
                                    id="validation-outlined-input"
                                    type="password"
                                    autoComplete="current-password"
                                    className="my-2 "
                                    {...register('password')}
                                />
                                <FormControlLabel
                                    control={<Checkbox value="a" checked={rememberAccount} onChange={handleCheckboxChange} />}
                                    label="Ghi Nhớ Tài Khoản"
                                />
                                <Typography component="div" className="d-flex justify-content-end">
                                    <Button type="submit" size="large" variant="contained" className="text-capitalize">
                                        Đăng Nhập
                                    </Button>
                                </Typography>
                            </Typography>
                            <Typography component="div" className="card-footer text-center my-2">
                                <Button
                                    sx={{
                                        width: '100%',
                                        mt: 1,
                                        border: 1,
                                        borderRadius: '8px',
                                        '&:hover': {
                                            color: 'white',
                                            backgroundColor: 'navy',
                                        },
                                    }}
                                    onClick={handleLoginWithGG}
                                >
                                    <Avatar sx={{ width: 24, height: 24, mr: 1 }} alt="Avatar 1" src={gg} />
                                    Đăng nhập với tài khoản Google
                                </Button>

                                <Button
                                    sx={{
                                        width: '100%',
                                        my: 1,
                                        border: 1,
                                        borderRadius: '8px',
                                        '&:hover': {
                                            color: 'white',
                                            backgroundColor: 'navy',
                                        },
                                    }}
                                    onClick={handleLoginWithFB}
                                >
                                    <Avatar sx={{ width: 24, height: 24, mr: 1 }} alt="Avatar 2" src={fb} />
                                    Đăng nhập với tài khoản Facebook
                                </Button>
                                <Typography component={Link} to="/quen-mat-khau" variant="subtitle2">
                                    Quên Mật Khẩu ?
                                </Typography>
                                <br />
                                <Typography component={Link} to="/dang-ky" variant="subtitle2">
                                    Bạn Chưa Có Tài Khoản? Đăng Ký Tại Đây!
                                </Typography>
                            </Typography>
                        </Box>
                    </div>
                    <div className="col-sm-1 col-md-2 col-lg-3"></div>
                </div>
            </div>
        </>
    );
}

export default DangNhap;
