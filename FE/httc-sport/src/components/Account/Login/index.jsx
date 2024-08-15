/* eslint-disable no-restricted-globals */
import { Avatar, Box, Button, Checkbox, FormControlLabel, Grid, TextField, Typography } from '@mui/material';
import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

import { handleLogInAPI } from '~/apis';
import gg from '~/components/Images/google-logo.jpg';
import logo from '~/components/Images/logo.png';
import Header from '~/components/Layout/Header';
import { OAuthConfigGoogle } from '~/utils/constants';

export default function Login() {
    const [rememberAccount, setRememberAccount] = useState(false);
    const { register, handleSubmit } = useForm();
    const navigate = useNavigate();

    const submitLogin = async (data) => {
        const res = await handleLogInAPI(data);
        localStorage.setItem('accessToken', res.data.result.accessToken);
        localStorage.setItem('userId', res.data.result.userId);
        console.log(res.data);
        const checkRole = res.data.result.roles[0].roleName;
        localStorage.setItem('role', checkRole);
        if (checkRole === 'ADMIN') {
            navigate('/admin/trang-chu');
        } else {
            navigate('/');
        }

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

    const handleCheckboxChange = (event) => {
        setRememberAccount(event.target.checked);
    };

    return (
        <>
            <div className="mt-2 container px-5">
                <div className="d-flex justify-content-center">
                    <Link to="/">
                        <img src={logo} alt="#" style={{ width: 120 }} />
                    </Link>
                </div>
                <Grid sx={{ my: 4 }} container spacing={2}>
                    <Grid item xs={3}></Grid>
                    <Grid item xs={6}>
                        <Box className="card" component="form" onSubmit={handleSubmit(submitLogin)}>
                            <Typography className="card-header text-center fs-3" variant="h6" component="div">
                                Đăng Nhập
                            </Typography>
                            <Typography component="div" className="card-body">
                                <TextField
                                    label="Tài Khoản"
                                    variant="outlined"
                                    className="my-2 w-100"
                                    autoComplete="username"
                                    {...register('username')}
                                />
                                <TextField
                                    label="Mật Khẩu"
                                    variant="outlined"
                                    type="password"
                                    className="my-2 w-100"
                                    autoComplete="current-password"
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
                                <Typography sx={{ fontSize: '15px' }} variant="subtitle2">
                                    <a href="/forgot-password">Quên Mật Khẩu ?</a>
                                </Typography>
                                <Typography sx={{ fontSize: '15px' }} variant="subtitle2">
                                    <a href="/register">Bạn Chưa Có Tài Khoản? Đăng Ký Tại Đây!</a>
                                </Typography>
                            </Typography>
                        </Box>
                    </Grid>
                    <Grid item xs={3}></Grid>
                </Grid>
            </div>
        </>
    );
}
