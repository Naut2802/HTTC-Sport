import { Box, Button, TextField, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';
import React from 'react';

import { Link } from 'react-router-dom';
import logo from '../../Images/logo.png';

function QuenMatKhau() {
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
                                Quên Mật Khẩu
                            </Typography>
                            <Typography component="div" className="card-body ">
                                <Typography component="span" variant="h10">
                                    Nhập chính xác username đã đăng ký trên hệ thống và hệ thống sẽ kiểm tra thông tin,
                                    nếu thông tin đúng sẽ đến bước tiếp theo
                                </Typography>
                                <Typography component="div" className="w-100">
                                    <Typography component="div" className="w-100">
                                        <ValidationTextField
                                            label="Nhập Tài Khoản"
                                            variant="outlined"
                                            id="validation-outlined-input"
                                            defaultValue=""
                                            className="my-2 "
                                        />
                                    </Typography>
                                </Typography>
                            </Typography>
                            <Typography
                                component="div"
                                className="d-flex w-100 justify-content-between align-items-center my-2 card-footer"
                            >
                                <div className="d-flex align-items-center">
                                    <Typography component={Link} to="/dang-nhap">
                                        <Button variant="outlined" className="text-capitalize">
                                            Quay Lại
                                        </Button>
                                    </Typography>
                                </div>
                                <Typography component={Link} to="/quen-mat-khau-1">
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

export default QuenMatKhau;
