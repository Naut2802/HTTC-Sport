import { Avatar, Box, Button, Checkbox, FormControlLabel, TextField, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';
import React, { useState } from 'react';

import { Link } from 'react-router-dom';
import fb from '../../Images/facebook-logo.png';
import gg from '../../Images/google-logo.jpg';
import logo from '../../Images/logo.png';

function DangNhap() {
    const [rememberAccount, setRememberAccount] = useState(false);

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
                                Đăng Nhập
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
                                <Typography component="div">
                                    <FormControlLabel
                                        control={
                                            <Checkbox
                                                value="a"
                                                checked={rememberAccount}
                                                onChange={handleCheckboxChange}
                                            />
                                        }
                                        label="Ghi Nhớ Tài Khoản"
                                    />
                                </Typography>
                                <Typography
                                    component="div"
                                    className="d-flex w-100 justify-content-between align-items-center my-2"
                                >
                                    <div className="d-flex align-items-center">
                                        <Typography component={Link} to="/google" className="text-center">
                                            <Avatar alt="Avatar 1" src={gg} />
                                        </Typography>
                                        <Typography component={Link} to="/facebook" className="text-center">
                                            <Avatar alt="Avatar 2" src={fb} className="mx-2" />
                                        </Typography>
                                    </div>

                                    <Typography component={Link} to="/trang-chu" className="text-center">
                                        <Button variant="contained" className="text-capitalize">
                                            Đăng Nhập
                                        </Button>
                                    </Typography>
                                </Typography>
                            </Typography>
                            <Typography component="div" className="card-footer text-center my-2">
                                <Typography component={Link} to="/quen-mat-khau" variant="">
                                    Quên Mật Khẩu ?
                                </Typography>
                                <br />
                                <Typography component={Link} to="/dang-ky" variant="">
                                    Bạn Chưa Có Tài Khoản? Đăng Ký Tại Đây!
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

export default DangNhap;
