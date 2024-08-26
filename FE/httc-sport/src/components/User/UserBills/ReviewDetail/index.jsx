/* eslint-disable react-hooks/exhaustive-deps */
import { Box, Grid, Rating, TextField, Typography } from '@mui/material';
import { format } from 'date-fns';
import { useEffect, useState } from 'react';

import { handleGetReviewByBill } from '~/apis';
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

export default function ReviewDetail({ selectedBill }) {
    const [review, setReview] = useState();
    const fetchData = async () => {
        try {
            const res = await handleGetReviewByBill(selectedBill.id);
            console.log(res.data.result);
            setReview(res.data.result);
        } catch (error) {}
    };

    useEffect(() => {
        fetchData();
    }, []);
    return (
        <div className="my-3 container">
            <div className="d-flex justify-content-center mb-4">
                <img src={logo} alt="" style={{ width: 120 }} />
            </div>

            <Box className="card" component="form" noValidate>
                <Typography className="card-header text-center fs-3" variant="h6" component="div">
                    Chi Tiết Đánh Giá
                </Typography>
                <Typography component="div" className="card-body ">
                    <Box className="card" noValidate>
                        <Typography component="div" className="card-body">
                            <Grid container spacing={1}>
                                <Grid item xs={2}>
                                    <Typography component={'span'}>
                                        <TextField label="Họ" variant="outlined" fullWidth value={selectedBill.lastName} />
                                    </Typography>
                                </Grid>
                                <Grid item xs={2}>
                                    <Typography component={'span'}>
                                        <TextField label="Tên" variant="outlined" fullWidth value={selectedBill.firstName} />
                                    </Typography>
                                </Grid>
                                <Grid item xs={4}>
                                    <Typography component={'span'}>
                                        <TextField
                                            label="Email"
                                            variant="outlined"
                                            fullWidth
                                            value={!review ? '' : review.username}
                                        />
                                    </Typography>
                                </Grid>
                                <Grid item xs={4}>
                                    <Typography component={'span'}>
                                        <TextField
                                            label="Số điện thoại"
                                            variant="outlined"
                                            fullWidth
                                            value={selectedBill.phoneNumber}
                                        />
                                    </Typography>
                                </Grid>
                                <Grid item xs={4}>
                                    <Typography component={'span'}>
                                        <TextField
                                            label="Ngày đá"
                                            variant="outlined"
                                            fullWidth
                                            value={formatDate(selectedBill.rentedAt)}
                                        />
                                    </Typography>
                                </Grid>
                                <Grid item xs={4}>
                                    <Typography component={'span'}>
                                        <TextField
                                            label="Giờ bắt đầu"
                                            variant="outlined"
                                            fullWidth
                                            value={selectedBill.startTime}
                                        />
                                    </Typography>
                                </Grid>
                                <Grid item xs={4}>
                                    <Typography component={'span'}>
                                        <TextField
                                            label="Giờ kết thúc"
                                            variant="outlined"
                                            fullWidth
                                            value={selectedBill.endTime}
                                        />
                                    </Typography>
                                </Grid>
                                <Grid item xs={4}>
                                    <Typography component={'span'}>
                                        <TextField label="Tên Sân" variant="outlined" fullWidth value={selectedBill.pitchName} />
                                    </Typography>
                                </Grid>
                                <Grid item xs={4}>
                                    <Typography component={'span'}>
                                        <TextField
                                            label="Loại Sân"
                                            variant="outlined"
                                            fullWidth
                                            value={selectedBill.typePitch === 5 ? 'Sân 5' : 'Sân 7'}
                                        />
                                    </Typography>
                                </Grid>
                                <Grid item xs={4}>
                                    <Typography component={'span'}>
                                        <TextField
                                            label="Tổng tiền"
                                            variant="outlined"
                                            fullWidth
                                            value={formatCurrency(selectedBill.total)}
                                        />
                                    </Typography>
                                </Grid>
                                <Grid item xs={4}>
                                    <Typography component={'span'} sx={{ ml: 6 }}>
                                        <Typography sx={{ ml: 1 }} component="legend">
                                            Số sao đánh giá được ghi nhận
                                        </Typography>
                                        <Rating readOnly size="large" value={!review ? 0 : review.rate} />
                                    </Typography>
                                </Grid>
                                <Grid item xs={8}>
                                    <Typography component={'span'}>
                                        <TextField
                                            label="Mô tả đánh giá"
                                            variant="outlined"
                                            fullWidth
                                            value={!review ? '' : review.description}
                                        />
                                    </Typography>
                                </Grid>
                            </Grid>
                        </Typography>
                    </Box>
                </Typography>
            </Box>
        </div>
    );
}
