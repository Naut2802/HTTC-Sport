import { Box, Button, Grid, TextField, Typography } from '@mui/material';
import { format } from 'date-fns';
import { toast } from 'react-toastify';
import { handleCreatePaymentLink } from '~/apis';

import logo from '~/components/Images/logo.png';

function formatCurrency(amount) {
    return amount.toLocaleString('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
}

function formatDate(dateString) {
    const date = new Date(dateString);
    return format(date, 'dd/MM/yyyy');
}

// { resData, resPayment, resDate }
export default function RePayment({ rentInfo }) {
    console.log(rentInfo);

    const handlePayPercent = async () => {
        const id = rentInfo.id;
        const deposit = 0.35;
        const res = await handleCreatePaymentLink(id, deposit);
        console.log(res.data.result.data);
        if (res.data.result.data) {
            window.location.href = res.data.result.data.checkoutUrl;
        } else {
            toast.warning(res.data.result.desc);
        }
    };

    const handlePayAll = async () => {
        const id = rentInfo.id;
        const deposit = 1;
        const res = await handleCreatePaymentLink(id, deposit);
        console.log(res.data.result.data);
        if (res.data.result.data) {
            window.location.href = res.data.result.data.checkoutUrl;
        } else {
            toast.warning(res.data.result.desc);
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
                                        <TextField label="Họ" variant="outlined" fullWidth value={rentInfo.lastName} />
                                    </Typography>
                                </Grid>
                                <Grid item xs={4}>
                                    <Typography component={'span'}>
                                        <TextField label="Tên" variant="outlined" fullWidth value={rentInfo.firstName} />
                                    </Typography>
                                </Grid>
                                <Grid item xs={4}>
                                    <Typography component={'span'}>
                                        <TextField
                                            label="Loại Sân"
                                            variant="outlined"
                                            fullWidth
                                            value={rentInfo.typePitch === 5 ? 'Sân 5' : 'Sân 7'}
                                        />
                                    </Typography>
                                </Grid>
                                <Grid item xs={6}>
                                    <Typography component={'span'}>
                                        <TextField
                                            label="Số điện thoại"
                                            variant="outlined"
                                            fullWidth
                                            value={rentInfo.phoneNumber}
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
                                            value={rentInfo.email}
                                        />
                                    </Typography>
                                </Grid>
                                <Grid item xs={4}>
                                    <Typography component={'span'}>
                                        <TextField
                                            label="Ngày đá"
                                            variant="outlined"
                                            fullWidth
                                            value={formatDate(rentInfo.rentedAt)}
                                        />
                                    </Typography>
                                </Grid>
                                <Grid item xs={4}>
                                    <Typography component={'span'}>
                                        <TextField label="Giờ bắt đầu" variant="outlined" fullWidth value={rentInfo.startTime} />
                                    </Typography>
                                </Grid>
                                <Grid item xs={4}>
                                    <Typography component={'span'}>
                                        <TextField label="Giờ kết thúc" variant="outlined" fullWidth value={rentInfo.endTime} />
                                    </Typography>
                                </Grid>
                                <Grid item xs={12}>
                                    <Typography component={'span'}>
                                        <TextField
                                            label="Ghi chú"
                                            variant="outlined"
                                            fullWidth
                                            value={rentInfo.note === '' ? '(không có)' : rentInfo.note}
                                        />
                                    </Typography>
                                </Grid>
                                <Grid item xs={12}>
                                    <Typography component={'span'}>
                                        <TextField
                                            label="Tổng tiền"
                                            variant="outlined"
                                            fullWidth
                                            value={formatCurrency(rentInfo.total)}
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
