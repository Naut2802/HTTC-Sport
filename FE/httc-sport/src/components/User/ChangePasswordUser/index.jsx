import { Box, Button, TextField, Typography } from '@mui/material';
import React from 'react';
import logo from '~/components/Images/logo.png';

function DoiMatKhau() {
    return (
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
                        noValidate
                        sx={{
                            top: '10%',
                        }}
                    >
                        <Typography className="card-header text-center fs-3" variant="h6" component="div">
                            Đổi Mật Khẩu
                        </Typography>
                        <Typography component="div" className="card-body text-center">
                            <Typography component="div" className="w-100">
                                <TextField label="Tài Khoản" variant="outlined" className="my-2 w-100" type="text" />
                                <div className="row">
                                    <div className="col-6">
                                        <TextField
                                            label="Nhập Mật Khẩu Mới"
                                            variant="outlined"
                                            className="my-2 w-100"
                                            type="password"
                                            autoComplete="newPassword"
                                        />
                                    </div>
                                    <div className="col-6">
                                        <TextField
                                            label="Xác Nhận Mật Khẩu"
                                            variant="outlined"
                                            className="my-2 w-100"
                                            type="password"
                                            autoComplete="confirmationPassword"
                                        />
                                    </div>
                                </div>
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
    );
}

export default DoiMatKhau;
