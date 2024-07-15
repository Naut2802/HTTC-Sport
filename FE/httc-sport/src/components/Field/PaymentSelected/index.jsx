import { Box, Breadcrumbs, Button, TextField, Typography } from '@mui/material';
import { Link } from 'react-router-dom';

import logo from '~/components/Images/logo.png';
export default function PaymentSelected() {
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
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/dat-san">
                    Thanh Toán
                </Typography>
            </Breadcrumbs>
            <hr />
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
                                            <TextField
                                                label="Tổng Tiền"
                                                variant="outlined"
                                                id="tongTien"
                                                defaultValue=""
                                                className="my-2"
                                            />
                                        </div>
                                        <div className="col-6">
                                            <TextField
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
        </div>
    );
}
