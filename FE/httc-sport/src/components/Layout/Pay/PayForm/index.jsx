import { styled } from '@mui/material/styles';
import { Box, Button, TextField, Typography } from '@mui/material';

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
        <div className="row mt-5">
            <div className="col-3"></div>
            <div className="col-6">
                <Box className="card" component="form" noValidate>
                    <Typography className="card-header text-center fs-3" variant="h6" component="div">
                        Thanh Toán Sân
                    </Typography>
                    <Typography component="div" className="card-body ">
                        <Box className="card" component="form" noValidate>
                            <Typography component="div" className="card-body">
                                <div className="row">
                                    <div className="col-6">
                                        <ValidationTextField
                                            label="Tổng Tiền"
                                            variant="outlined"
                                            id="tongTien"
                                            defaultValue=""
                                            className="my-2"
                                        />
                                    </div>
                                    <div className="col-6">
                                        <ValidationTextField
                                            label="Tiền Cọc"
                                            variant="outlined"
                                            id="tienCoc"
                                            defaultValue=""
                                            className="my-2 w-100"
                                        />
                                    </div>
                                </div>
                                <Typography className="text-center text-danger" variant="h6" component="div">
                                    * LƯU Ý: Nội dung chuyển khoản
                                </Typography>
                                <Typography className="text-center text-dark" variant="h6" component="div">
                                    Điền Họ Tên và Số Điện Thoại ở phần nội dung chuyển khoản
                                </Typography>
                            </Typography>
                        </Box>
                    </Typography>
                    <Typography component="div" className="d-flex w-100 justify-content-between card-footer">
                        <Button variant="outlined" className="text-capitalize">
                            Thanh Toán Toàn Bộ
                        </Button>
                        <Button variant="outlined" className="text-capitalize">
                            Thanh Toán Qua Ví
                        </Button>
                        <Button variant="outlined" className="text-capitalize">
                            Thanh Toán Tiền Cọc
                        </Button>
                    </Typography>
                </Box>
            </div>
            <div className="col-3"></div>
        </div>
    );
}

export default FormDatSan;
