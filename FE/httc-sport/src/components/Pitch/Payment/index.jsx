import { Box, Button, Grid, TextField, Typography } from '@mui/material';
import { DatePicker } from '@mui/x-date-pickers';
import { toast } from 'react-toastify';
import { handleCreatePaymentLink } from '~/apis';

import logo from '~/components/Images/logo.png';

function formatCurrency(amount) {
    return amount.toLocaleString('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
}

export default function Payment({ resData, resPayment, resDate }) {
    const handlePayPercent = async () => {
        const id = resPayment.id;
        const deposit = 0.35;
        const res = await handleCreatePaymentLink(id, deposit);
        if (res.data.result.success) {
            window.location.href = res.data.result.checkoutUrl;
        } else {
            toast.warning('Có lỗi xảy ra !!!');
        }
    };

    const handlePayAll = async () => {
        const id = resPayment.id;
        const deposit = 1;
        const res = await handleCreatePaymentLink(id, deposit);
        if (res.data.result.success) {
            window.location.href = res.data.result.checkoutUrl;
        } else {
            toast.warning('Có lỗi xảy ra !!!');
        }
    };

    return (
        <div className="my-3 container">
            <div className="d-flex justify-content-center mb-4">
                <img src={logo} alt="" style={{ width: 120 }} />
            </div>

            <Box className="card" component="form" noValidate>
                <Typography className="card-header text-center fs-3" variant="h6" component="div">
                    Xác Nhận Thông Tin Đặt Sân
                </Typography>
                <Typography component="div" className="card-body ">
                    <Box className="card" noValidate>
                        <Typography component="div" className="card-body">
                            <Grid container spacing={1}>
                                <Grid item xs={4}>
                                    <Typography component={'span'}>
                                        <TextField label="Họ" variant="outlined" fullWidth value={resData.lastName} />
                                    </Typography>
                                </Grid>
                                <Grid item xs={4}>
                                    <Typography component={'span'}>
                                        <TextField label="Tên" variant="outlined" fullWidth value={resData.firstName} />
                                    </Typography>
                                </Grid>
                                <Grid item xs={4}>
                                    <Typography component={'span'}>
                                        <TextField
                                            label="Loại Sân"
                                            variant="outlined"
                                            fullWidth
                                            value={resData.typePitch === 5 ? 'Sân 5' : 'Sân 7'}
                                        />
                                    </Typography>
                                </Grid>
                                <Grid item xs={6}>
                                    <Typography component={'span'}>
                                        <TextField
                                            label="Số điện thoại"
                                            variant="outlined"
                                            fullWidth
                                            value={resData.phoneNumber}
                                        />
                                    </Typography>
                                </Grid>
                                <Grid item xs={6}>
                                    <Typography component={'span'}>
                                        <TextField
                                            label="Email"
                                            variant="outlined"
                                            fullWidth
                                            type="email"
                                            value={resData.email}
                                        />
                                    </Typography>
                                </Grid>
                                <Grid item xs={4}>
                                    <Typography component={'span'}>
                                        <DatePicker
                                            disableOpenPicker
                                            format="DD/MM/YYYY"
                                            sx={{ width: '100%' }}
                                            label="Ngày đá"
                                            value={resDate}
                                        />
                                    </Typography>
                                </Grid>
                                <Grid item xs={4}>
                                    <Typography component={'span'}>
                                        <TextField label="Giờ bắt đầu" variant="outlined" fullWidth value={resData.startTime} />
                                    </Typography>
                                </Grid>
                                <Grid item xs={4}>
                                    <Typography component={'span'}>
                                        <TextField
                                            label="Tổng thời gian"
                                            variant="outlined"
                                            fullWidth
                                            value={
                                                resData.rentTime === 60 ? '1 Giờ' : resData.rentTime === 90 ? '1.5 Giờ' : '2 Giờ'
                                            }
                                        />
                                    </Typography>
                                </Grid>
                                <Grid item xs={12}>
                                    <Typography component={'span'}>
                                        <TextField
                                            label="Ghi chú"
                                            variant="outlined"
                                            fullWidth
                                            value={resData.note === '' ? '(không có)' : resData.note}
                                        />
                                    </Typography>
                                </Grid>
                                <Grid item xs={12}>
                                    <Typography component={'span'}>
                                        <TextField
                                            label="Tổng tiền"
                                            variant="outlined"
                                            fullWidth
                                            value={formatCurrency(resPayment.total)}
                                        />
                                    </Typography>
                                </Grid>
                            </Grid>
                        </Typography>
                    </Box>
                </Typography>
                <Typography component="div" className="text-end card-footer">
                    <Button
                        sx={{
                            '&:hover': {
                                color: 'white',
                                backgroundColor: 'darkseagreen',
                            },
                        }}
                        variant="outlined"
                        color="success"
                        className="text-capitalize"
                        onClick={handlePayPercent}
                    >
                        Thanh Toán 35%
                    </Button>
                    <Button
                        sx={{
                            '&:hover': {
                                color: 'white',
                                backgroundColor: 'darkseagreen',
                            },
                        }}
                        variant="outlined"
                        color="success"
                        className="text-capitalize ms-2"
                        onClick={handlePayAll}
                    >
                        Thanh Toán Toàn Bộ
                    </Button>
                </Typography>
            </Box>
        </div>
    );
}
