import { Box, Breadcrumbs, Button, Card, CardContent, TextField, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';
import { Link } from 'react-router-dom';

import logo from '~/components/Images/logo.png';

import MucVip from './MucVip';

function ThongTinUser() {
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
        <div className="my-3 container">
            <div className="d-flex justify-content-center">
                <img src={logo} alt="" style={{ width: 120 }} />
            </div>
            <Typography className="fs-3 fw-bold mt-3 mb-2">Danh Sách Sân</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
                <Typography className="text-decoration-none text-dark fs-6" variant="h6" noWrap component={Link} to="/">
                    Trang Chủ
                </Typography>
                <Typography
                    className="text-decoration-none text-dark fs-6"
                    variant="h6"
                    noWrap
                    component={Link}
                    to="/san"
                >
                    Thông Tin Cá Nhân
                </Typography>
            </Breadcrumbs>
            <hr />
            <div className="row">
                <Card sx={{ display: 'flex', maxWidth: 'auto', width: '100%' }}>
                    <div className="col-6 border rounded-2 my-2">
                        <Box sx={{ display: 'flex', flexDirection: 'column', height: '100%' }}>
                            <CardContent>
                                <Typography
                                    className="text-decoration-none text-center text-dark fw-bold"
                                    variant="h6"
                                    noWrap
                                >
                                    Thông Tin Cá Nhân
                                </Typography>

                                <hr />
                                <div className="d-flex">
                                    <Typography component="div" className="w-50">
                                        <ValidationTextField
                                            label="Tài Khoản"
                                            variant="outlined"
                                            id="validation-outlined-input"
                                            defaultValue=""
                                            className="my-2 "
                                        />
                                    </Typography>
                                    <Typography component="div" className="w-50 ms-1">
                                        <TextField
                                            label="   Mật Khẩu"
                                            variant="outlined"
                                            id="matkhau"
                                            defaultValue=""
                                            className="my-2 w-100"
                                            type="password"
                                        />
                                    </Typography>
                                </div>
                                <div className="d-flex">
                                    <Typography component="div" className="w-50">
                                        <ValidationTextField
                                            label="Họ"
                                            variant="outlined"
                                            id="validation-outlined-input"
                                            defaultValue=""
                                            className="my-2 "
                                        />
                                    </Typography>
                                    <Typography component="div" className="w-50 ms-1">
                                        <ValidationTextField
                                            label="Tên"
                                            variant="outlined"
                                            id="validation-outlined-input"
                                            defaultValue=""
                                            className="my-2 "
                                        />
                                    </Typography>
                                </div>
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
                                <Typography component="div" className="w-100">
                                    <ValidationTextField
                                        label="Số Điện Thoại"
                                        variant="outlined"
                                        id="validation-outlined-input"
                                        defaultValue=""
                                        className="my-2 "
                                    />
                                </Typography>
                                <hr />
                                <Typography
                                    component="div"
                                    className="d-flex w-100 justify-content-between align-items-center my-2"
                                >
                                    <div className="d-flex align-items-center">
                                        <Button variant="outlined" className="text-capitalize">
                                            Đổi Mật Khẩu
                                        </Button>
                                    </div>
                                    <Button variant="outlined" color="success" className="text-capitalize">
                                        Cập Nhật
                                    </Button>
                                </Typography>
                            </CardContent>
                        </Box>
                    </div>
                    <div className="col-6 d-flex justify-content-center ">
                        <Typography component="div" className="w-100 mx-2 my-2 ">
                            <MucVip />
                        </Typography>
                    </div>
                </Card>
            </div>
        </div>
    );
}

export default ThongTinUser;
