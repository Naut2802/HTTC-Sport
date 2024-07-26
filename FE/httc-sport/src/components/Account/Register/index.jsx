import { Box, Button, TextField, Typography } from '@mui/material';
import React from 'react';
import { useForm } from 'react-hook-form';
import { Link } from 'react-router-dom';
import { toast } from 'react-toastify';

import { handleSignUpAPI } from '~/apis';
import logo from '~/components/Images/logo.png';

export default function Register() {
    const { register, handleSubmit } = useForm();

    const submitSignUp = async (data) => {
        const res = await handleSignUpAPI(data);
        console.log(res);
        toast.success(res.data.message);
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
                            component="form"
                            onSubmit={handleSubmit(submitSignUp)}
                            noValidate
                            sx={{
                                top: '10%',
                            }}
                        >
                            <Typography className="card-header text-center fs-3" variant="h6" component="div">
                                Đăng Ký
                            </Typography>
                            <Typography component="div" className="card-body ">
                                <Typography component="div" className="w-100">
                                    <TextField
                                        label="Tài Khoản"
                                        variant="outlined"
                                        className="my-2 w-100"
                                        autoComplete="username"
                                        {...register('username')}
                                    />
                                </Typography>
                                <Typography component="div" className="w-100">
                                    <TextField
                                        label="Mật Khẩu"
                                        variant="outlined"
                                        type="password"
                                        className="my-2 w-100"
                                        autoComplete="current-password"
                                        {...register('password')}
                                    />
                                </Typography>
                                <Typography component="div" className="w-100">
                                    <TextField
                                        label="Email"
                                        variant="outlined"
                                        className="my-2 w-100"
                                        type="email"
                                        autoComplete="email"
                                        {...register('email')}
                                    />
                                </Typography>
                            </Typography>
                            <Typography component="div" className="d-flex justify-content-between card-footer">
                                <Button
                                    sx={{
                                        '&:hover': {
                                            color: 'white',
                                            backgroundColor: 'blue',
                                        },
                                    }}
                                    component={Link}
                                    to="/trang-chu"
                                    variant="outlined"
                                    size="large"
                                    className="text-capitalize"
                                >
                                    Quay Về
                                </Button>
                                <Button
                                    sx={{
                                        '&:hover': {
                                            color: 'white',
                                            backgroundColor: 'green',
                                        },
                                    }}
                                    type="submit"
                                    variant="outlined"
                                    size="large"
                                    color="success"
                                    className="text-capitalize"
                                >
                                    Đăng Ký
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
