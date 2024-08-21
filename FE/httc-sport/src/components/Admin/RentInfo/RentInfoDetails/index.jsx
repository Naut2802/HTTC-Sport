import { Box, Button, FormControl, Grid, InputLabel, MenuItem, Select, TextField, Typography } from '@mui/material';
import { format } from 'date-fns';
import { useState } from 'react';
import { toast } from 'react-toastify';

import { handleExchangeRentInfoToBill, handlePayRemaining, handleUpdateRentInfo } from '~/apis';

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

export default function RentInfoDetails({ rentInfo, onRentInfoUpdate, closePopup }) {
    const [time, setTime] = useState(0);

    const handleChangeTime = (event) => {
        setTime(event.target.value);
    };

    const handleUpdateTimeRentInfo = async () => {
        try {
            const id = rentInfo.id;
            const rentTime = time;
            console.log(id, rentTime);
            const res = await handleUpdateRentInfo(id, rentTime);
            console.log(res.data);
            onRentInfoUpdate();
            closePopup();
            toast.success('Đã thêm giờ cho thông tin đặt sân đã chọn!');
        } catch (error) {}
    };

    const handleChangeRentInfoToBill = async () => {
        try {
            const res = await handleExchangeRentInfoToBill(rentInfo.id);
            toast.success(res.data.message);
            onRentInfoUpdate();
            closePopup();
        } catch (error) {
            toast.error('Có lỗi xảy ra!!!');
        }
    };

    const handleCreatePayRemainingLink = async () => {
        const id = rentInfo.id;
        const paymentMethod = rentInfo.paymentMethod;
        const res = await handlePayRemaining(id, paymentMethod);
        console.log(res);
        if (res.data.result.paymentLink) {
            window.location.href = res.data.result.paymentLink.checkoutUrl;
        } else {
            toast.warning(res.data.result.desc);
        }
    };

    return (
        <Box className="card">
            <Box className="card-body">
                <Grid container spacing={2}>
                    <Grid item xs={6}>
                        <Box sx={{ width: '100%' }}>
                            <Box className="card" component="form">
                                <Typography className="card-header text-center" variant="h6" component="div" sx={{ mb: 2 }}>
                                    Thông Tin
                                </Typography>
                                <Box className="card-body">
                                    <Grid container spacing={2}>
                                        <Grid item xs={6}>
                                            <Typography component={'span'}>
                                                <TextField
                                                    fullWidth
                                                    label="Tên Sân"
                                                    variant="outlined"
                                                    value={rentInfo.pitchName}
                                                />
                                            </Typography>
                                        </Grid>
                                        <Grid item xs={6}>
                                            <Typography component={'span'}>
                                                <TextField
                                                    fullWidth
                                                    label="Loại Sân"
                                                    variant="outlined"
                                                    value={
                                                        rentInfo.typePitch === 5
                                                            ? 'Sân 5'
                                                            : rentInfo.typePitch === 7
                                                            ? 'Sân 7'
                                                            : 'Sân 11'
                                                    }
                                                />
                                            </Typography>
                                        </Grid>
                                        <Grid item xs={12}>
                                            <Typography component={'span'}>
                                                <TextField
                                                    fullWidth
                                                    label="Email"
                                                    variant="outlined"
                                                    type="email"
                                                    value={rentInfo.email}
                                                />
                                            </Typography>
                                        </Grid>
                                        <Grid item xs={12}>
                                            <Typography component={'span'}>
                                                <TextField
                                                    fullWidth
                                                    label="Số Điện Thoại"
                                                    variant="outlined"
                                                    value={rentInfo.phoneNumber}
                                                />
                                            </Typography>
                                        </Grid>
                                        <Grid item xs={12}>
                                            <Typography component={'span'}>
                                                <TextField
                                                    label="Ngày đá"
                                                    variant="outlined"
                                                    fullWidth
                                                    value={formatDate(rentInfo.rentedAt)}
                                                />
                                            </Typography>
                                        </Grid>
                                        <Grid item xs={6}>
                                            <Typography component={'span'}>
                                                <TextField
                                                    fullWidth
                                                    label="Thời Gian Bắt Đầu"
                                                    variant="outlined"
                                                    value={rentInfo.startTime}
                                                />
                                            </Typography>
                                        </Grid>
                                        <Grid item xs={6}>
                                            <Typography component={'span'}>
                                                <TextField
                                                    fullWidth
                                                    label="Thời Gian Kết Thúc"
                                                    variant="outlined"
                                                    value={rentInfo.endTime}
                                                />
                                            </Typography>
                                        </Grid>
                                        <Grid item xs={12}>
                                            <Typography component={'span'}>
                                                <TextField fullWidth label="Ghi Chú" variant="outlined" value={rentInfo.note} />
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </Box>
                            </Box>
                        </Box>
                    </Grid>
                    <Grid item xs={6}>
                        <Box sx={{ width: '100%' }}>
                            <Box className="card" component="form">
                                <Typography className="card-header text-center" variant="h6" component="div" sx={{ mb: 2 }}>
                                    Phí Dịch Vụ
                                </Typography>
                                <Typography component="div" className="card-body">
                                    <Grid container spacing={2}>
                                        <Grid item xs={12}>
                                            <Typography component={'span'}>
                                                <TextField
                                                    fullWidth
                                                    label="Phương thức thanh toán"
                                                    variant="outlined"
                                                    value={
                                                        rentInfo.paymentMethod === 'QR'
                                                            ? 'Thanh toán bằng QR'
                                                            : rentInfo.paymentMethod === 'WALLET'
                                                            ? 'Thanh toán bằng Ví'
                                                            : 'Thanh toán tiền mặt'
                                                    }
                                                />
                                            </Typography>
                                        </Grid>
                                        <Grid item xs={12}>
                                            <TextField
                                                fullWidth
                                                label="Tổng Tiền Cần Thanh Toán"
                                                variant="outlined"
                                                value={formatCurrency(rentInfo.total)}
                                            />
                                        </Grid>
                                        <Grid item xs={12}>
                                            <TextField
                                                fullWidth
                                                label="Số Tiền Đã Thanh Toán"
                                                variant="outlined"
                                                value={formatCurrency(rentInfo.deposit)}
                                            />
                                        </Grid>
                                        <Grid item xs={12}>
                                            <TextField
                                                fullWidth
                                                label="Số Tiền Còn Lại Cần Thanh Toán"
                                                variant="outlined"
                                                value={formatCurrency(rentInfo.total - rentInfo.deposit)}
                                            />
                                        </Grid>
                                        <Grid item xs={12}>
                                            <FormControl fullWidth>
                                                <InputLabel id="demo-simple-select-label">Thêm Thời Gian</InputLabel>
                                                <Select
                                                    labelId="demo-simple-select-label"
                                                    id="demo-simple-select"
                                                    value={time}
                                                    label="Thêm Thời Gian"
                                                    onChange={handleChangeTime}
                                                >
                                                    <MenuItem value={0}>Không thêm</MenuItem>
                                                    <MenuItem value={30}>30 phút</MenuItem>
                                                    <MenuItem value={60}>1 giờ</MenuItem>
                                                </Select>
                                            </FormControl>
                                        </Grid>
                                        {time !== 0 ? (
                                            <Grid item xs={12}>
                                                <Typography className="d-flex justify-content-end">
                                                    <Button
                                                        color="success"
                                                        variant="contained"
                                                        onClick={() => handleUpdateTimeRentInfo()}
                                                    >
                                                        Thêm Giờ
                                                    </Button>
                                                </Typography>
                                            </Grid>
                                        ) : (
                                            <></>
                                        )}
                                    </Grid>
                                </Typography>
                            </Box>
                        </Box>
                    </Grid>
                </Grid>
            </Box>
            {rentInfo.paymentStatus ? (
                <Box className="card-footer d-flex justify-content-end">
                    <Button sx={{ mr: 1 }} variant="outlined" onClick={() => handleChangeRentInfoToBill()}>
                        Hoàn Thành
                    </Button>
                    <Button sx={{ mr: 1 }} variant="outlined" onClick={() => handleCreatePayRemainingLink()}>
                        Tạo Mã Thanh Toán
                    </Button>
                </Box>
            ) : (
                <></>
            )}
        </Box>
    );
}
