import { Box, Breadcrumbs, Button, TextField, Typography } from '@mui/material';
import { Link } from 'react-router-dom';
import logo from '~/components/Images/logo.png';

export default function PitchRent() {
    return (
        <div className="my-3 container">
            <div className="d-flex justify-content-center">
                <img src={logo} alt="" style={{ width: 120 }} />
            </div>
            <Typography className="fs-3 fw-bold mt-3 mb-2">Đặt Sân</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/trang-chu">
                    Trang Chủ
                </Typography>
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/san">
                    Sân
                </Typography>
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/dat-san">
                    Đặt Sân
                </Typography>
            </Breadcrumbs>
            <hr />
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
                                            <TextField label="Họ" variant="outlined" id="ho" defaultValue="" className="my-2" />
                                        </div>
                                        <div className="col-6">
                                            <TextField
                                                label="Tên"
                                                variant="outlined"
                                                id="ten"
                                                defaultValue=""
                                                className="my-2 w-100"
                                            />
                                        </div>
                                        <div className="col-6">
                                            <TextField
                                                label="Email"
                                                variant="outlined"
                                                id="hoTen"
                                                defaultValue=""
                                                className="my-2"
                                                type="email"
                                            />
                                        </div>
                                        <div className="col-6">
                                            <TextField
                                                label="Số Điện Thoại"
                                                variant="outlined"
                                                id="sdt"
                                                defaultValue=""
                                                className="my-2"
                                            />
                                        </div>
                                        <div>
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
                                                label="Thời Gian Nhận Sân"
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
                                        <div className="col-12">
                                            <TextField
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
        </div>
    );
}
