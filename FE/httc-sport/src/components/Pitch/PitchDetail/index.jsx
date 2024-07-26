import StarRateIcon from '@mui/icons-material/StarRate';
import StorefrontSharpIcon from '@mui/icons-material/StorefrontSharp';
import WifiSharpIcon from '@mui/icons-material/WifiSharp';
import { Avatar, Box, Breadcrumbs, Button, Card, CardContent, Input, Modal, Typography } from '@mui/material';
import * as React from 'react';
import { Link } from 'react-router-dom';

import logo from '~/components/Images/logo.png';
import FreeTime from './FreeTime';
import HotTime from './HotTime';
import ThumbSlider from './ThumbSlider';

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
};
export default function PitchDetail() {
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    return (
        <div className="my-3 container">
            <div className="d-flex justify-content-center">
                <img src={logo} alt="" style={{ width: 120 }} />
            </div>
            <Typography className="fs-3 fw-bold mt-3 mb-2">Chi Tiết Sân</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
                <Typography className="text-decoration-none text-dark fs-6" variant="h6" noWrap component={Link} to="/">
                    Trang Chủ
                </Typography>
                <Typography className="text-decoration-none text-dark fs-6" variant="h6" noWrap component={Link} to="/san-bong">
                    Danh Sách Sân
                </Typography>
                <Typography className="text-decoration-none text-dark fs-6" variant="h6" noWrap component="span">
                    Chi Tiết Sân
                </Typography>
            </Breadcrumbs>
            <div className="row border rounded-2">
                <div className="col-5 my-2">
                    <ThumbSlider />
                </div>
                <div className="col-7 my-4 h-75">
                    <Card sx={{ display: 'flex', flexDirection: 'column', height: '100%' }}>
                        <CardContent>
                            <div className="row">
                                <div className="col-8">
                                    <Typography
                                        gutterBottom
                                        variant="h5"
                                        component={Link}
                                        to="/san-bong"
                                        className="text-decoration-none text-dark"
                                    >
                                        <span className="fw-bold fs-2">Sân Quỳnh Như</span>
                                    </Typography>
                                </div>
                                <div className="col-4">
                                    <Button onClick={handleOpen} variant="text" className="text-capitalize float-end">
                                        Xem Sân Trống
                                    </Button>
                                    <Modal
                                        open={open}
                                        onClose={handleClose}
                                        aria-labelledby="modal-modal-title"
                                        aria-describedby="modal-modal-description"
                                    >
                                        <Box sx={style}>
                                            <Typography
                                                id="modal-modal-title"
                                                variant="h6"
                                                component="h2"
                                                sx={{ textAlign: 'center' }}
                                            >
                                                Thời gian hoạt động sân
                                            </Typography>
                                            <Box sx={{ mt: 2 }}>
                                                <FreeTime />
                                            </Box>
                                        </Box>
                                    </Modal>
                                </div>
                            </div>
                            <Typography variant="subtitle1" color="text.secondary">
                                <span className="fw-bold fs-5">Loại Sân :</span> Sân 5
                            </Typography>
                            <Typography variant="subtitle1" color="text.secondary">
                                <span className="fw-bold fs-5">Đánh Giá :</span> 5
                                <StarRateIcon sx={{ width: 15, marginBottom: -2, color: '#FFC107' }} />
                            </Typography>
                            <Typography variant="subtitle1" color="text.secondary">
                                <span className="fw-bold fs-5">Giá : </span>
                                <span className="fw-bold text-danger fs-5">250.000 </span> /Giờ
                            </Typography>
                            <Typography variant="subtitle1" color="text.secondary">
                                <span className="fw-bold fs-5">Địa Chỉ :</span> 202 Hoàng Văn Thụ, P. 9, Quận Phú Nhuận - Hồ Chí
                                Minh
                            </Typography>
                            <Typography variant="subtitle1" color="text.secondary">
                                <span className="fs-6">
                                    Căn Tin
                                    <StorefrontSharpIcon sx={{ marginLeft: 1, marginRight: 1 }} />
                                </span>
                                <span className="fs-6 border-start mx-2">
                                    <WifiSharpIcon sx={{ marginRight: 1, marginLeft: 2 }} />
                                    Wifi
                                </span>
                            </Typography>
                        </CardContent>
                        <div className="row">
                            <div className="col-8">
                                <HotTime />
                            </div>
                            <div className="col-4 d-flex justify-content-end align-items-end">
                                <Button variant="contained" className="text-capitalize">
                                    Đặt Sân
                                </Button>
                            </div>
                        </div>
                    </Card>
                </div>
            </div>
            <div className="row">
                <div className="col-6"></div>
                <div className="col-6 border rounded-2 my-2 w-50">
                    <Typography variant="h6" className="my-2 text-center">
                        Đánh Giá
                    </Typography>
                    <div className="row">
                        <div className="col-6 my-2 d-flex align-items-center">
                            <Avatar alt="Remy Sharp" src="https://mui.com/static/images/avatar/1.jpg" />
                            <span className="mx-2">Hoàng Lê</span>
                        </div>
                        <div className="col-6 d-flex justify-content-end align-items-center">
                            <Typography component="div">
                                5 <StarRateIcon sx={{ width: 15, marginBottom: -2, color: '#FFC107' }} />
                            </Typography>
                        </div>
                    </div>
                    <div className="my-2 d-flex align-items-center">
                        <Input type="text" placeholder="Đánh Giá" className="w-100" value="abc" disabled />
                    </div>
                </div>
            </div>
        </div>
    );
}
