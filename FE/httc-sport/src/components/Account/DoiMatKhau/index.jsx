import { Box, Button, TextField, Typography } from '@mui/material';
import React from 'react';

import { Link } from 'react-router-dom';
import logo from '../../Images/logo.png';

function DoiMatKhau() {
    return (
        <>
            <div className="my-3 container">
                <div className="d-flex justify-content-center">
                    <img src={logo} alt="" style={{ width: 120 }} />
                </div>
                <div className="row">
                    <div className="col-4"></div>
                    <div className="col-4">
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
                                <Typography component="span" variant="h10">
                                    Nhập mật khẩu mới và xác nhận lại mật khẩu.
                                </Typography>
                                <Typography component="div" className="w-100">
                                    <TextField
                                        label="Nhập Mật Khẩu Mới"
                                        variant="outlined"
                                        id="doi-mk"
                                        defaultValue=""
                                        className="my-2 w-100"
                                        type="password"
                                    />
                                    <TextField
                                        label="Xác Nhận Mật Khẩu"
                                        variant="outlined"
                                        id="xac-nhan-mk"
                                        defaultValue=""
                                        className="my-2 w-100"
                                        type="password"
                                    />
                                </Typography>
                            </Typography>
                            <Typography
                                component="div"
                                className="d-flex w-100 justify-content-between align-items-center my-2 card-footer"
                            >
                                <div className="d-flex align-items-center"></div>
                                <Typography component={Link} to="/dang-nhap">
                                    <Button variant="outlined" color="success" className="text-capitalize">
                                        Xác Nhận
                                    </Button>
                                </Typography>
                            </Typography>
                        </Box>
                    </div>
                    <div className="col-4"></div>
                </div>
            </div>
        </>
    );
}

export default DoiMatKhau;
