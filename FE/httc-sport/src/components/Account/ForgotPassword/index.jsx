import { Box, Button, TextField, Typography } from '@mui/material';
import React from 'react';
import { useForm } from 'react-hook-form';
import { Link } from 'react-router-dom';
import { toast } from 'react-toastify';
import { handleCheckMailForgotPassword } from '~/apis';

import logo from '~/components/Images/logo.png';

export default function ForgotPassword() {
    const { register, handleSubmit } = useForm();

    const submitCheckMail = async (data) => {
        const email = data.email;
        const res = await handleCheckMailForgotPassword(email);
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
                            onSubmit={handleSubmit(submitCheckMail)}
                            sx={{
                                top: '10%',
                            }}
                        >
                            <Typography className="card-header text-center fs-3" variant="h6" component="div">
                                Quên Mật Khẩu
                            </Typography>
                            <Typography component="div" className="card-body ">
                                <Typography component="span" variant="h6" color={'red'}>
                                    Nhập chính xác email đã đăng ký tài khoản trên hệ thống.
                                </Typography>
                                <Typography component="div" className="w-100 mt-2">
                                    <Typography component="div" className="w-100">
                                        <TextField
                                            sx={{ width: '100%' }}
                                            label="Nhập Email"
                                            variant="outlined"
                                            className="my-2 "
                                            type="email"
                                            autoComplete="email"
                                            {...register('email')}
                                        />
                                    </Typography>
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
                                    color="success"
                                    className="text-capitalize"
                                >
                                    Xác Nhận
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
