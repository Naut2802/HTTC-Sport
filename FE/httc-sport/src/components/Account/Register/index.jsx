import React from 'react';
import { Box, Button, TextField, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';
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
                                    <ValidationTextField
                                        label="Tài Khoản"
                                        variant="outlined"
                                        // id="validation-outlined-input"
                                        className="my-2 "
                                        autoComplete="username"
                                        {...register('username')}
                                    />
                                </Typography>
                                <Typography component="div" className="w-100">
                                    <ValidationTextField
                                        label="Mật Khẩu"
                                        variant="outlined"
                                        // id="validation-outlined-input"
                                        type="password"
                                        className="my-2 "
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
                                <Typography
                                    component="div"
                                    className="d-flex w-100 justify-content-between align-items-center my-2"
                                >
                                    <div className="d-flex align-items-center"></div>
                                    <Button type="submit" size="large" variant="contained" className="text-capitalize">
                                        Đăng Ký
                                    </Button>
                                </Typography>
                            </Typography>
                            <Typography component="div" className="card-footer text-center">
                                <Typography component={Link} to="/login" variant="subtitle2">
                                    Bạn Đã Có Tài Khoản? Đăng Nhập Ngay!
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
