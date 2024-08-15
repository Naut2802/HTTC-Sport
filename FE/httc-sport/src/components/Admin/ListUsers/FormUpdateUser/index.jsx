import { Box, Button, TextField, Typography, Grid, styled } from '@mui/material';
import { Controller, useForm } from 'react-hook-form';
import { useEffect } from 'react';
import { handleUpdateUserAdmin } from '~/apis';
import { toast } from 'react-toastify';

// Custom styled TextField for validation
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
        padding: '4px !important',
    },
});

export default function FormUpdateUser({ selectedUser }) {
    const { handleSubmit, control, setValue } = useForm();

    useEffect(() => {
        if (selectedUser) {
            setValue('username', selectedUser.username || '');
            setValue('email', selectedUser.email || '');
            setValue('phoneNumber', selectedUser.phoneNumber || '');
            setValue('firstName', selectedUser.firstName || '');
            setValue('lastName', selectedUser.lastName || '');
        }
    }, [selectedUser, setValue]);

    const handleUpdateUser = async (data) => {
        const userId = selectedUser?.id;

        console.log('User ID:', userId);
        console.log('Dữ liệu:', data);

        try {
            const re = await handleUpdateUserAdmin(userId, data);
            console.log(re);

            toast.success('Cập Nhập Thành Công');
        } catch (error) {
            toast.error('Cập Nhập Không Thành Công');
            console.log(error);
        }
    };

    return (
        <Box sx={{ width: '100%', p: 2 }}>
            <Grid container spacing={2} justifyContent="center">
                <Grid item xs={12} sm={10} md={8} lg={6}>
                    <Box className="card" component="form" noValidate sx={{ p: 2 }}>
                        <Typography className="card-header text-center" variant="h6" component="div" sx={{ mb: 2 }}>
                            Quản Lý Thông Tin Khách Hàng
                        </Typography>
                        <Box className="card-body">
                            <Grid container spacing={2}>
                                <Grid item xs={12}>
                                    <Controller
                                        name="username"
                                        control={control}
                                        defaultValue=""
                                        render={({ field }) => (
                                            <ValidationTextField {...field} label="Tên Tài Khoản" variant="outlined" />
                                        )}
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <Controller
                                        name="firstName"
                                        control={control}
                                        defaultValue=""
                                        render={({ field }) => <ValidationTextField {...field} label="Tên" variant="outlined" />}
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <Controller
                                        name="lastName"
                                        control={control}
                                        defaultValue=""
                                        render={({ field }) => <ValidationTextField {...field} label="Họ" variant="outlined" />}
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <Controller
                                        name="email"
                                        control={control}
                                        defaultValue=""
                                        render={({ field }) => (
                                            <ValidationTextField {...field} label="Email" variant="outlined" />
                                        )}
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <Controller
                                        name="phoneNumber"
                                        control={control}
                                        defaultValue=""
                                        render={({ field }) => (
                                            <ValidationTextField {...field} label="Số Điện Thoại" variant="outlined" />
                                        )}
                                    />
                                </Grid>
                            </Grid>
                        </Box>
                        <Box sx={{ display: 'flex', justifyContent: 'flex-end', mt: 2 }}>
                            <Button variant="outlined" color="success" onClick={handleSubmit(handleUpdateUser)}>
                                Cập Nhật
                            </Button>
                        </Box>
                    </Box>
                </Grid>
            </Grid>
        </Box>
    );
}
