import { Box, TextField, Typography } from '@mui/material';

export default function Details() {
    return (
        <Box className="card" component="form" noValidate>
            <Typography component="div" className="card-body">
                <div className="row">
                    <div className="col-6">
                        <TextField label="Tài Khoản" variant="outlined" id="taiKhoan" defaultValue="" className="my-2" />
                    </div>
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
                        <TextField label="Họ Tên" variant="outlined" id="hoTen" defaultValue="" className="my-2" />
                    </div>
                    <div className="col-6">
                        <TextField label="Số Điện Thoại" variant="outlined" id="sdt" defaultValue="" className="my-2" />
                    </div>
                    <div className="col-6">
                        <TextField
                            label="Ngày Đá"
                            variant="outlined"
                            id="ngayDa"
                            defaultValue=""
                            className="my-2 w-100"
                            type="date"
                        />
                    </div>
                    <div className="col-6">
                        <TextField
                            label="Thời Gian Bắt Đầu"
                            variant="outlined"
                            id="tgBd"
                            defaultValue="08:00"
                            className="my-2"
                            type="time"
                        />
                    </div>
                    <div className="col-6">
                        <TextField
                            label="Thời Gian Kết Thúc"
                            variant="outlined"
                            id="tgKt"
                            defaultValue="08:00"
                            className="my-2"
                            type="time"
                        />
                    </div>
                    <div className="col-6">
                        <TextField label="Đã Thanh Toán" variant="outlined" id="tienCoc" defaultValue="" className="my-2" />
                    </div>
                    <div className="col-6">
                        <TextField
                            label="Tiền Còn Lại"
                            variant="outlined"
                            id="tienChuaTT"
                            defaultValue=""
                            className="my-2 w-100"
                        />
                    </div>
                    <TextField label="Tổng Tiền" variant="outlined" id="tongTien" defaultValue="" className="my-2" />
                    <TextField label="Ghi Chú" variant="outlined" id="ghiChu" defaultValue="" className="my-2" />
                </div>
            </Typography>
        </Box>
    );
}
