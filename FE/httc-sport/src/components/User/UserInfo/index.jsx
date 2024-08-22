import { Box, Breadcrumbs, Button, Card, CardContent, Grid, TextField, Typography } from '@mui/material';
import logo from '~/components/Images/logo.png';
import { useEffect } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

import { handleChangeInfoUser, handleGetMyInfoAPI } from '~/apis';
import VipLevel from './VipLevel';

export default function UserInfo() {
    const navigate = useNavigate();
    const { handleSubmit, control, reset } = useForm({
        defaultValues: {
            username: '',
            email: '',
            lastName: '',
            firstName: '',
            phoneNumber: '',
        },
    });

    useEffect(() => {
        const fetchData = async () => {
            try {
                const res = await handleGetMyInfoAPI();
                reset(res.data.result);
            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, [reset]);

    const submitChangeInfo = async (data) => {
        try {
            const userId = localStorage.getItem('userId');
            const res = await handleChangeInfoUser(userId, data);
            console.log(res.data);
            toast.success('Bạn vừa cập nhật thông tin thành công!');
            navigate('/tai-khoan');
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <div className="my-3 container">
            <div className="d-flex justify-content-center">
                <img src={logo} alt="Logo" style={{ width: 120 }} />
            </div>
            <Typography className="fs-3 fw-bold mt-3 mb-2">Thông Tin</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
                <Typography className="text-decoration-none text-dark fs-6" variant="h6" noWrap component={Link} to="/">
                    Trang Chủ
                </Typography>
                <Typography className="text-decoration-none text-dark fs-6" variant="h6" noWrap component={Link} to="/tai-khoan">
                    Thông Tin Cá Nhân
                </Typography>
            </Breadcrumbs>
            <hr />
            <div className="row">
                <Grid container spacing={2}>
                    <Grid item xs={6}>
                        <Card sx={{ width: '100%' }}>
                            <Box
                                component="form"
                                className="border rounded-2"
                                onSubmit={handleSubmit(submitChangeInfo)}
                                sx={{ display: 'flex', flexDirection: 'column', height: '100%' }}
                            >
                                <CardContent>
                                    <Typography className="text-center text-dark fw-bold" variant="h6" noWrap>
                                        Thông Tin Cá Nhân
                                    </Typography>
                                    <hr />
                                    <Grid container spacing={1}>
                                        <Grid item xs={6}>
                                            <Controller
                                                name="username"
                                                control={control}
                                                render={({ field }) => (
                                                    <TextField
                                                        {...field}
                                                        label="Tài Khoản"
                                                        variant="outlined"
                                                        fullWidth
                                                        InputProps={{
                                                            readOnly: true,
                                                        }}
                                                    />
                                                )}
                                            />
                                        </Grid>
                                        <Grid item xs={6}>
                                            <Controller
                                                name="email"
                                                control={control}
                                                render={({ field }) => (
                                                    <TextField
                                                        {...field}
                                                        label="Email"
                                                        variant="outlined"
                                                        fullWidth
                                                        type="email"
                                                        InputProps={{
                                                            readOnly: true,
                                                        }}
                                                    />
                                                )}
                                            />
                                        </Grid>
                                        <Grid item xs={4}>
                                            <Controller
                                                name="lastName"
                                                control={control}
                                                render={({ field }) => (
                                                    <TextField
                                                        {...field}
                                                        label="Họ"
                                                        variant="outlined"
                                                        fullWidth
                                                        value={field.value || ''}
                                                    />
                                                )}
                                            />
                                        </Grid>
                                        <Grid item xs={4}>
                                            <Controller
                                                name="firstName"
                                                control={control}
                                                render={({ field }) => (
                                                    <TextField
                                                        {...field}
                                                        label="Tên"
                                                        variant="outlined"
                                                        fullWidth
                                                        value={field.value || ''}
                                                    />
                                                )}
                                            />
                                        </Grid>
                                        <Grid item xs={4}>
                                            <Controller
                                                name="phoneNumber"
                                                control={control}
                                                render={({ field }) => (
                                                    <TextField
                                                        {...field}
                                                        label="Số Điện Thoại"
                                                        variant="outlined"
                                                        fullWidth
                                                        value={field.value || ''}
                                                    />
                                                )}
                                            />
                                        </Grid>
                                    </Grid>
                                    <hr />
                                    <div className="d-flex justify-content-between align-items-center my-2">
                                        <Button
                                            component={Link}
                                            to="/change-password"
                                            variant="outlined"
                                            className="text-capitalize"
                                        >
                                            Đổi Mật Khẩu
                                        </Button>
                                        <Button type="submit" variant="outlined" color="success" className="text-capitalize">
                                            Cập Nhật
                                        </Button>
                                    </div>
                                </CardContent>
                            </Box>
                        </Card>
                    </Grid>
                    <Grid item xs={6}>
                        <VipLevel />
                    </Grid>
                </Grid>
            </div>
        </div>
    );
}
