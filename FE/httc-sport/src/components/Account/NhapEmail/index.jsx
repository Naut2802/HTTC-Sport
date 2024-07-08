import { Box, Button, TextField, Typography } from '@mui/material';
import React from 'react';

import { Link } from 'react-router-dom';
import logo from '../../Images/logo.png';

function NhapEmail() {
    return (
        <>
            <div className="my-3 container">
                <div className="d-flex justify-content-center">
                    <img src={logo} alt="" style={{ width: 120 }} />
                </div>
                <div className="row">
                    <div className="col-3"></div>
                    <div className="col-4">
                        <Box
                            className="card"
                            component="form"
                            noValidate
                            sx={{
                                width: '150%',
                                top: '10%',
                            }}
                        >
                            <Typography className="card-header text-center fs-3" variant="h6" component="div">
                                Nhập Email
                            </Typography>
                            <Typography component="div" className="card-body ">
                                <Typography component="span" variant="h10">
                                    Nhập Email đã đăng ký theo tài khoản trên hệ thống và hệ thống sẽ kiểm tra thông
                                    tin, nếu thông tin chính xác sẽ đến bước đổi mật khẩu.
                                </Typography>
                                <TextField
                                    label="Email"
                                    variant="outlined"
                                    id="validation-outlined-input"
                                    defaultValue=""
                                    className="my-2 w-100"
                                    type="email"
                                />
                            </Typography>
                            <Typography
                                component="div"
                                className="d-flex w-100 justify-content-between align-items-center my-2 card-footer"
                            >
                                <div className="d-flex align-items-center">
                                    <Typography component={Link} to="/quen-mat-khau-1">
                                        <Button variant="outlined" className="text-capitalize">
                                            Quay Lại
                                        </Button>
                                    </Typography>
                                </div>
                                <Typography component={Link} to="/quen-mat-khau-2">
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

export default NhapEmail;
