import { Box, TextField, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';

export default function Details() {
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
            padding: '4px !important',
        },
    });

    return (
        <Box className="card" component="form" noValidate>
            <Typography component="div" className="card-body">
                <div className="row">
                    <div className="col-6">
                        <ValidationTextField
                            label="Tài Khoản"
                            variant="outlined"
                            id="taiKhoan"
                            defaultValue=""
                            className="my-2"
                        />
                    </div>
                    <div className="col-6">
                        <ValidationTextField
                            label="Email"
                            variant="outlined"
                            id="email"
                            defaultValue=""
                            className="my-2 w-100"
                            type="email"
                        />
                    </div>
                    <div className="col-6">
                        <ValidationTextField label="Họ Tên" variant="outlined" id="hoTen" defaultValue="" className="my-2" />
                    </div>
                    <div className="col-6">
                        <ValidationTextField label="Số Điện Thoại" variant="outlined" id="sdt" defaultValue="" className="my-2" />
                    </div>
                    <div className="col-6">
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
                            label="Thời Gian Bắt Đầu"
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
                    <div className="col-6">
                        <ValidationTextField
                            label="Đã Thanh Toán"
                            variant="outlined"
                            id="tienCoc"
                            defaultValue=""
                            className="my-2"
                        />
                    </div>
                    <div className="col-6">
                        <ValidationTextField
                            label="Tiền Còn Lại"
                            variant="outlined"
                            id="tienChuaTT"
                            defaultValue=""
                            className="my-2 w-100"
                        />
                    </div>
                    <ValidationTextField label="Tổng Tiền" variant="outlined" id="tongTien" defaultValue="" className="my-2" />
                    <ValidationTextField label="Ghi Chú" variant="outlined" id="ghiChu" defaultValue="" className="my-2" />
                </div>
            </Typography>
        </Box>
    );
}
