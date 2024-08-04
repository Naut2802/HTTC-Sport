import { Box, Button, TextField, Typography } from '@mui/material';

export default function FormUpdateUser() {
    return (
        <div className="row">
            <div className="col-3"></div>
            <div className="col-6">
                <Box className="card" component="form" noValidate>
                    <Typography className="card-header text-center fs-3" variant="h6" component="div">
                        Quản Lý Thông Tin Khách Hàng
                    </Typography>
                    <Box className="card-body">
                        <TextField
                            label="Tên Tài Khoản"
                            variant="outlined"
                            id="username"
                            defaultValue=""
                            className="my-2 w-100"
                        />
                        <div className="row">
                            <div className="col-6">
                                <TextField
                                    label="Email"
                                    variant="outlined"
                                    id="email"
                                    defaultValue=""
                                    className="my-2 w-100"
                                    type="email"
                                />
                            </div>
                            <div className="col-6">
                                <TextField
                                    label="Số Điện Thoại"
                                    variant="outlined"
                                    id="soDT"
                                    defaultValue=""
                                    className="my-2 w-100"
                                />
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-6">
                                <TextField
                                    label="Mật Khẩu Cũ"
                                    variant="outlined"
                                    id="oldPassword"
                                    defaultValue=""
                                    className="my-2 w-100"
                                    type="password"
                                />
                            </div>
                            <div className="col-6">
                                <TextField
                                    label="Mật Khẩu Mới"
                                    variant="outlined"
                                    id="newPassword"
                                    defaultValue=""
                                    className="my-2 w-100"
                                    type="password"
                                />
                            </div>
                        </div>
                    </Box>
                    <Box className="d-flex w-100 align-items-center my-2 justify-content-end card-footer">
                        <Button variant="outlined" color="success" className="text-capitalize mx-2">
                            Cập Nhật
                        </Button>
                    </Box>
                </Box>
            </div>
        </div>
    );
}
