/* eslint-disable jsx-a11y/img-redundant-alt */
/* eslint-disable jsx-a11y/alt-text */
import { Grid, TextField } from '@mui/material';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import { format } from 'date-fns';
import { useEffect, useState } from 'react';

import { handleGetPitch } from '~/apis';

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

export default function ModalDetail({ rentInfo }) {
    const [pitch, setPitch] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const id = rentInfo.pitchId;
                const res = await handleGetPitch(id);
                setPitch(res.data.result);
            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, [rentInfo.pitchId]);

    return (
        <Box component={'div'}>
            <Typography sx={{ fontSize: 22 }} variant="subtitle2" component={'span'}>
                --- Thông Tin Chi Tiết ---
            </Typography>
            <Typography component={'span'} sx={{ mt: 2 }}>
                <Grid container spacing={1}>
                    <Grid item xs={4}>
                        <Box className="card">
                            <Typography sx={{ width: '100%' }} component={'span'} className="card-body">
                                {pitch && pitch.images && pitch.images.length > 0 ? (
                                    <img src={pitch.images[0].url} alt="Pitch Image" className="img-fluid" />
                                ) : (
                                    <Typography>Không có hình ảnh</Typography>
                                )}
                            </Typography>
                        </Box>
                    </Grid>
                    <Grid item xs={8}>
                        <Box className="card" noValidate>
                            <Typography component="div" className="card-body">
                                <Grid container spacing={1}>
                                    <Grid item xs={2}>
                                        <Typography component={'span'}>
                                            <TextField label="Họ" variant="outlined" fullWidth value={rentInfo.lastName} />
                                        </Typography>
                                    </Grid>
                                    <Grid item xs={2}>
                                        <Typography component={'span'}>
                                            <TextField label="Tên" variant="outlined" fullWidth value={rentInfo.firstName} />
                                        </Typography>
                                    </Grid>
                                    <Grid item xs={3}>
                                        <Typography component={'span'}>
                                            <TextField
                                                label="Số điện thoại"
                                                variant="outlined"
                                                fullWidth
                                                value={rentInfo.phoneNumber}
                                            />
                                        </Typography>
                                    </Grid>
                                    <Grid item xs={5}>
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
                                            <TextField
                                                label="Giờ bắt đầu"
                                                variant="outlined"
                                                fullWidth
                                                value={rentInfo.startTime}
                                            />
                                        </Typography>
                                    </Grid>
                                    <Grid item xs={4}>
                                        <Typography component={'span'}>
                                            <TextField
                                                label="Giờ kết thúc"
                                                variant="outlined"
                                                fullWidth
                                                value={rentInfo.endTime}
                                            />
                                        </Typography>
                                    </Grid>
                                    <Grid item xs={8}>
                                        <Typography component={'span'}>
                                            <TextField label="Tên Sân" variant="outlined" fullWidth value={rentInfo.pitchName} />
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
                                    <Grid item xs={12}>
                                        <Typography component={'span'}>
                                            <TextField
                                                label="Địa Chỉ"
                                                variant="outlined"
                                                fullWidth
                                                value={
                                                    pitch.street + ', ' + pitch.ward + ', ' + pitch.district + ', ' + pitch.city
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
                                                value={rentInfo.note === '' ? '(không có)' : rentInfo.note}
                                            />
                                        </Typography>
                                    </Grid>
                                    <Grid item xs={6}>
                                        <Typography component={'span'}>
                                            <TextField
                                                label="Tiền cọc"
                                                variant="outlined"
                                                fullWidth
                                                value={formatCurrency(rentInfo.deposit)}
                                            />
                                        </Typography>
                                    </Grid>
                                    <Grid item xs={6}>
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
                    </Grid>
                </Grid>
            </Typography>
        </Box>
    );
}
