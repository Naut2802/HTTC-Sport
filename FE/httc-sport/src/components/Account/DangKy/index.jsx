import { Box, Button, TextField, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';
import React from 'react';

import { Link } from 'react-router-dom';
import logo from '../../Images/logo.png';

function DangKy() {
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
                                Đăng Ký
                            </Typography>
                            <Typography component="div" className="card-body ">
                                <Typography component="div" className="w-100">
                                    <ValidationTextField
                                        label="Tài Khoản"
                                        variant="outlined"
                                        id="validation-outlined-input"
                                        defaultValue=""
                                        className="my-2 "
                                    />
                                </Typography>
                                <Typography component="div" className="w-100">
                                    <ValidationTextField
                                        label="Mật Khẩu"
                                        variant="outlined"
                                        id="validation-outlined-input"
                                        defaultValue=""
                                        className="my-2 "
                                    />
                                </Typography>
                                <Typography component="div" className="w-100">
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
                                    className="d-flex w-100 justify-content-between align-items-center my-2"
                                >
                                    <div className="d-flex align-items-center"></div>
                                    <Button variant="contained" className="text-capitalize">
                                        Đăng Ký
                                    </Button>
                                </Typography>
                            </Typography>
                            <Typography component="div" className="card-footer text-center">
                                <Typography component={Link} to="/dang-nhap">
                                    Bạn Đã Có Tài Khoản? Đăng Nhập Ngay!
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

export default DangKy;
