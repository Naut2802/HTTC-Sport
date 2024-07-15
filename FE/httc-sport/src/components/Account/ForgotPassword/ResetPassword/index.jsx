import { Box, Button, TextField, Typography } from '@mui/material';
import React from 'react';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { handleResetPasswordUser } from '~/apis';

import logo from '~/components/Images/logo.png';

export default function ResetPassword() {
    const { register, handleSubmit } = useForm();
    const navigate = useNavigate();

    const submitChangePassword = async (data) => {
        const token = localStorage.getItem('token');
        const res = await handleResetPasswordUser(token, data);
        console.log(res);
        toast.success('Bạn đã thay đổi mật khẩu thành công. Vui lòng đăng nhập lại!');
        navigate('/trang-chu');
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
                            onSubmit={handleSubmit(submitChangePassword)}
                            noValidate
                            sx={{
                                top: '10%',
                            }}
                        >
                            <Typography className="card-header text-center fs-3" variant="h6" component="div">
                                Quên Mật Khẩu
                            </Typography>
                            <Typography component="div" className="card-body text-center">
                                <Typography component="div" className="w-100">
                                    <TextField
                                        label="Nhập Mật Khẩu Mới"
                                        variant="outlined"
                                        className="my-2 w-100"
                                        type="password"
                                        autoComplete="newPassword"
                                        {...register('newPassword')}
                                    />
                                    <TextField
                                        label="Xác Nhận Mật Khẩu"
                                        variant="outlined"
                                        className="my-2 w-100"
                                        type="password"
                                        autoComplete="confirmationPassword"
                                        {...register('confirmationPassword')}
                                    />
                                </Typography>
                            </Typography>
                            <Typography component="div" className="card-footer d-flex justify-content-end">
                                <Button
                                    sx={{
                                        '&:hover': {
                                            color: 'white',
                                            backgroundColor: 'green',
                                        },
                                    }}
                                    type="submit"
                                    color="success"
                                    variant="outlined"
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
