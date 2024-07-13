/* eslint-disable no-restricted-globals */
import { Box, Breadcrumbs, Button, Card, CardContent, TextField, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';
import { useEffect } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { Link, useNavigate } from 'react-router-dom';

import { toast } from 'react-toastify';
import { handleChangeInfoUser, handleGetMyInfoAPI } from '~/apis';
import logo from '~/components/Images/logo.png';
import MucVip from './MucVip';

const ValidationTextField = styled(TextField)({
    width: '100%',
    '& input:valid + fieldset': {
        borderColor: '#E0E3E7',
        borderWidth: 1,
    },
    '& input:invalid + fieldset': {
        borderColor: 'red',
        borderWidth: 1,
    },
    '& input:valid:focus + fieldset': {
        borderLeftWidth: 4,
        padding: '4px !important', // override inline-style
    },
});

function ThongTinUser() {
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
            <Typography className="fs-3 fw-bold mt-3 mb-2">Danh Sách Sân</Typography>
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
                <Card sx={{ display: 'flex', width: '100%' }}>
                    <div className="col-6 border rounded-2 my-2">
                        <Box
                            component="form"
                            onSubmit={handleSubmit(submitChangeInfo)}
                            sx={{ display: 'flex', flexDirection: 'column', height: '100%' }}
                        >
                            <CardContent>
                                <Typography className="text-center text-dark fw-bold" variant="h6" noWrap>
                                    Thông Tin Cá Nhân
                                </Typography>
                                <hr />
                                <Controller
                                    name="username"
                                    control={control}
                                    render={({ field }) => (
                                        <ValidationTextField
                                            {...field}
                                            label="Tài Khoản"
                                            variant="outlined"
                                            className="my-2"
                                            InputProps={{
                                                readOnly: true,
                                            }}
                                        />
                                    )}
                                />
                                <Controller
                                    name="email"
                                    control={control}
                                    render={({ field }) => (
                                        <ValidationTextField
                                            {...field}
                                            label="Email"
                                            variant="outlined"
                                            className="my-2 w-100"
                                            type="email"
                                            InputProps={{
                                                readOnly: true,
                                            }}
                                        />
                                    )}
                                />
                                <div className="d-flex">
                                    <Controller
                                        name="lastName"
                                        control={control}
                                        render={({ field }) => (
                                            <ValidationTextField {...field} label="Họ" variant="outlined" className="my-2 w-50" />
                                        )}
                                    />
                                    <Controller
                                        name="firstName"
                                        control={control}
                                        render={({ field }) => (
                                            <ValidationTextField
                                                {...field}
                                                label="Tên"
                                                variant="outlined"
                                                className="my-2 w-50 ms-1"
                                            />
                                        )}
                                    />
                                </div>
                                <Controller
                                    name="phoneNumber"
                                    control={control}
                                    render={({ field }) => (
                                        <ValidationTextField
                                            {...field}
                                            label="Số Điện Thoại"
                                            variant="outlined"
                                            className="my-2"
                                        />
                                    )}
                                />
                                <hr />
                                <div className="d-flex justify-content-between align-items-center my-2">
                                    <Button component={Link} to="/doi-mat-khau" variant="outlined" className="text-capitalize">
                                        Đổi Mật Khẩu
                                    </Button>
                                    <Button type="submit" variant="outlined" color="success" className="text-capitalize">
                                        Cập Nhật
                                    </Button>
                                </div>
                            </CardContent>
                        </Box>
                    </div>
                    <div className="col-6 d-flex justify-content-center">
                        <div className="w-100 mx-2 my-2">
                            <MucVip />
                        </div>
                    </div>
                </Card>
            </div>
        </div>
    );
}

export default ThongTinUser;
