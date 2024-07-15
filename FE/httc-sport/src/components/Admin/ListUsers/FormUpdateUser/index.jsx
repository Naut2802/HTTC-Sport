import { Box, Button, TextField, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';

export default function FormUpdateUser() {
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
        <div className="row">
            <div className="col-3"></div>
            <div className="col-6">
                <Box className="card" component="form" noValidate>
                    <Typography className="card-header text-center fs-3" variant="h6" component="div">
                        Quản Lý Thông Tin Khách Hàng
                    </Typography>
                    <Typography component="div" className="card-body ">
                        <Box className="card" component="form" noValidate>
                            <Typography component="div" className="card-body">
                                <ValidationTextField
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
                                        <ValidationTextField
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
                                            type="email"
                                        />
                                    </div>
                                    <div className="col-6">
                                        <TextField
                                            label="Mật Khẩu Mới"
                                            variant="outlined"
                                            id="newPassword"
                                            defaultValue=""
                                            className="my-2 w-100"
                                            type="email"
                                        />
                                    </div>
                                </div>
                            </Typography>
                        </Box>
                    </Typography>
                    <Typography component="div" className="d-flex w-100 align-items-center my-2 justify-content-end card-footer">
                        <Button variant="outlined" color="success" className="text-capitalize mx-2 ">
                            Cập Nhật
                        </Button>
                    </Typography>
                </Box>
            </div>
        </div>
    );
}
