import { Box, Button, TextField, Typography, styled } from '@mui/material';
import { Controller, useForm } from 'react-hook-form';
import { useEffect } from 'react';
import { handleUpdateUserAdmin } from '~/apis';
import { toast } from 'react-toastify';

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
        <div className="row">
            <div className="col-3"></div>
            <div className="col-6">
                <Box className="card" component="form" noValidate>
                    <Typography className="card-header text-center fs-3" variant="h6" component="div">
                        Quản Lý Thông Tin Khách Hàng
                    </Typography>
                    <Box className="card-body">
                        <Controller
                            name="username"
                            control={control}
                            defaultValue=""
                            render={({ field }) => (
                                <ValidationTextField {...field} label="Tên Tài Khoản" variant="outlined" className="my-2 w-100" />
                            )}
                        />
                        <div className="row">
                            <div className="col-6">
                                <Controller
                                    name="firstName"
                                    control={control}
                                    defaultValue=""
                                    render={({ field }) => (
                                        <ValidationTextField {...field} label="Tên" variant="outlined" className="my-2 w-100" />
                                    )}
                                />
                            </div>
                            <div className="col-6">
                                <Controller
                                    name="lastName"
                                    control={control}
                                    defaultValue=""
                                    render={({ field }) => (
                                        <ValidationTextField {...field} label="Họ" variant="outlined" className="my-2 w-100" />
                                    )}
                                />
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-6">
                                <Controller
                                    name="email"
                                    control={control}
                                    defaultValue=""
                                    render={({ field }) => (
                                        <ValidationTextField {...field} label="Email" variant="outlined" className="my-2 w-100" />
                                    )}
                                />
                            </div>
                            <div className="col-6">
                                <Controller
                                    name="phoneNumber"
                                    control={control}
                                    defaultValue=""
                                    render={({ field }) => (
                                        <ValidationTextField
                                            {...field}
                                            label="Số Điện Thoại"
                                            variant="outlined"
                                            className="my-2 w-100"
                                        />
                                    )}
                                />
                            </div>
                        </div>
                        {/* <div className="row">
                            <div className="col-6">
                                <Controller
                                    name="password"
                                    control={control}
                                    defaultValue=""
                                    render={() => (
                                        <TextField
                                            label="Nhập Mật Khẩu Cũ"
                                            variant="outlined"
                                            className="my-2 w-100"
                                            type="password"
                                            autoComplete="currentPassword"
                                            {...register('currentPassword')}
                                        />
                                    )}
                                />
                            </div>
                            <div className="col-6">
                                <Controller
                                    name="password"
                                    control={control}
                                    defaultValue=""
                                    render={() => (
                                        <TextField
                                            label="Nhập Mật Khẩu Mới"
                                            variant="outlined"
                                            className="my-2 w-100"
                                            type="password"
                                            autoComplete="newPassword"
                                            {...register('newPassword')}
                                        />
                                    )}
                                />
                            </div>
                        </div> */}
                    </Box>
                    <Box className="d-flex w-100 align-items-center my-2 justify-content-end card-footer">
                        <Button
                            variant="outlined"
                            color="success"
                            className="text-capitalize mx-2"
                            onClick={handleSubmit(handleUpdateUser)}
                        >
                            Cập Nhật
                        </Button>
                    </Box>
                </Box>
            </div>
        </div>
    );
}
