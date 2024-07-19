import { styled } from '@mui/material/styles';
import { Box, Button, TextField, Typography } from '@mui/material';
import { Link } from 'react-router-dom';

function FormDatSan() {
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
                        Đặt Sân
                    </Typography>
                    <Typography component="div" className="card-body ">
                        <Box className="card" component="form" noValidate>
                            <Typography component="div" className="card-body">
                                <div className="row">
                                    <div className="col-6">
                                        <ValidationTextField
                                            label="Họ"
                                            variant="outlined"
                                            id="ho"
                                            defaultValue=""
                                            className="my-2"
                                        />
                                    </div>
                                    <div className="col-6">
                                        <ValidationTextField
                                            label="Tên"
                                            variant="outlined"
                                            id="ten"
                                            defaultValue=""
                                            className="my-2 w-100"
                                        />
                                    </div>
                                    <div className="col-6">
                                        <ValidationTextField
                                            label="Email"
                                            variant="outlined"
                                            id="hoTen"
                                            defaultValue=""
                                            className="my-2"
                                            type="email"
                                        />
                                    </div>
                                    <div className="col-6">
                                        <ValidationTextField
                                            label="Số Điện Thoại"
                                            variant="outlined"
                                            id="sdt"
                                            defaultValue=""
                                            className="my-2"
                                        />
                                    </div>
                                    <div>
                                        <ValidationTextField
                                            label="Ngày Đá"
                                            variant="outlined"
                                            id="ngayDa"
                                            defaultValue=""
                                            className="my-2 w-100"
                                            type="date"
                                        />
                                    </div>
                                    <div className="col-6">
                                        <ValidationTextField
                                            label="Thời Gian Nhận Sân"
                                            variant="outlined"
                                            id="tgBd"
                                            defaultValue="08:00"
                                            className="my-2"
                                            type="time"
                                        />
                                    </div>
                                    <div className="col-6">
                                        <ValidationTextField
                                            label="Thời Gian Kết Thúc"
                                            variant="outlined"
                                            id="tgKt"
                                            defaultValue="08:00"
                                            className="my-2"
                                            type="time"
                                        />
                                    </div>
                                    <div className="col-12">
                                        <ValidationTextField
                                            label="Ghi Chú"
                                            variant="outlined"
                                            id="ghiChu"
                                            defaultValue=""
                                            className="my-2"
                                        />
                                    </div>
                                </div>
                            </Typography>
                        </Box>
                    </Typography>
                    <Typography
                        component="div"
                        className="d-flex w-100 justify-content-between align-items-center my-2 card-footer"
                    >
                        <div className="d-flex align-items-center"></div>
                        <Typography component={Link} to="/thanh-toan">
                            <Button variant="outlined" className="text-capitalize">
                                Đặt Sân
                            </Button>
                        </Typography>
                    </Typography>
                </Box>
            </div>
            <div className="col-3"></div>
        </div>
    );
}

export default FormDatSan;
