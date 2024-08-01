import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import {
    Box,
    Button,
    Card,
    CardContent,
    FormControl,
    Grid,
    InputLabel,
    MenuItem,
    Select,
    TextField,
    Typography,
} from '@mui/material';
import { DatePicker, TimePicker } from '@mui/x-date-pickers';
import { format } from 'date-fns';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { toast } from 'react-toastify';

import { handleRentPitch } from '~/apis';
import Popup from '~/components/Layout/Popup';
import Payment from '../../Payment';

export default function RentForm({ id }) {
    const { register, handleSubmit } = useForm();
    const [time, setTime] = useState('');
    const [type, setType] = useState(5);
    const [payment, setPayment] = useState('');
    const [dateValue, setDateValue] = useState(null);
    const [timeValue, setTimeValue] = useState(null);
    const [openPopup, setOpenPopup] = useState(false);
    const [dataPayment, setDataPayment] = useState(null);
    const [resPayment, setResPayment] = useState(null);

    const handleChangeTime = (event) => {
        setTime(event.target.value);
    };

    const handleChangeType = (event) => {
        setType(event.target.value);
    };

    const handleChangePayment = (event) => {
        setPayment(event.target.value);
    };

    const submitRentPitch = async (data) => {
        if (dateValue && timeValue) {
            const formattedDate = format(dateValue.$d, 'yyyy-MM-dd');
            const formattedTime = format(timeValue.$d, 'HH:mm:ss');
            data = {
                ...data,
                pitchId: id,
                rentedAt: formattedDate,
                startTime: formattedTime,
                rentTime: time,
                typePitch: type,
                paymentMethod: payment,
            };
            setDataPayment(data);
            const res = await handleRentPitch(data);
            setResPayment(res.data.result);
            setOpenPopup(true);
        } else {
            toast.error('Vui lòng chọn ngày và giờ hợp lệ!');
        }
    };

    return (
        <Card>
            <CardContent>
                <Grid container spacing={1} sx={{ mb: 3 }}>
                    <Grid item xs={8}>
                        <ArrowDropDownIcon fontSize="large" sx={{ color: 'orangered', mb: 1 }} />
                        <span className="fw-bolder fs-5">Form Đặt Sân</span>
                    </Grid>
                    <Grid item xs={4}>
                        <FormControl variant="outlined" sx={{ pt: 1 }} fullWidth>
                            <InputLabel id="demo-simple-select-label">Loại Sân</InputLabel>
                            <Select
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                size="small"
                                value={type}
                                label="Loại Sân"
                                onChange={handleChangeType}
                            >
                                <MenuItem value={5}>Sân 5</MenuItem>
                                <MenuItem value={7}>Sân 7</MenuItem>
                            </Select>
                        </FormControl>
                    </Grid>
                </Grid>
                <Typography variant="subtitle2" color="text.dark"></Typography>
                <Box
                    component="form"
                    onSubmit={handleSubmit(submitRentPitch)}
                    noValidate
                    sx={{
                        top: '10%',
                    }}
                >
                    <Typography component={'span'}>
                        <Grid container spacing={1}>
                            <Grid item xs={6}>
                                <Typography component={'span'}>
                                    <TextField
                                        label="Họ"
                                        variant="outlined"
                                        fullWidth
                                        autoComplete="lastName"
                                        {...register('lastName')}
                                    />
                                </Typography>
                            </Grid>
                            <Grid item xs={6}>
                                <Typography component={'span'}>
                                    <TextField
                                        label="Tên"
                                        variant="outlined"
                                        fullWidth
                                        autoComplete="firstName"
                                        {...register('firstName')}
                                    />
                                </Typography>
                            </Grid>
                            <Grid item xs={12}>
                                <Typography component={'span'}>
                                    <TextField
                                        label="Số điện thoại"
                                        variant="outlined"
                                        fullWidth
                                        autoComplete="phoneNumber"
                                        {...register('phoneNumber')}
                                    />
                                </Typography>
                            </Grid>
                            <Grid item xs={12}>
                                <Typography component={'span'}>
                                    <TextField
                                        label="Email"
                                        variant="outlined"
                                        fullWidth
                                        type="email"
                                        autoComplete="email"
                                        {...register('email')}
                                    />
                                </Typography>
                            </Grid>
                            <Grid item xs={6}>
                                <Typography component={'span'}>
                                    <DatePicker
                                        sx={{ width: '100%' }}
                                        label="Chọn ngày"
                                        value={dateValue}
                                        onChange={(newDateValue) => setDateValue(newDateValue)}
                                        renderInput={(params) => <TextField {...params} />}
                                    />
                                </Typography>
                            </Grid>
                            <Grid item xs={6}>
                                <Typography component={'span'}>
                                    <TimePicker
                                        sx={{ width: '100%' }}
                                        label="Chọn giờ"
                                        format="HH:mm"
                                        timeSteps={{ minutes: 30 }}
                                        value={timeValue}
                                        onChange={(newTimeValue) => setTimeValue(newTimeValue)}
                                        renderInput={(params) => <TextField {...params} />}
                                    />
                                </Typography>
                            </Grid>
                            <Grid item xs={12}>
                                <Typography component={'span'}>
                                    <FormControl fullWidth>
                                        <InputLabel id="demo-simple-select-label">Thời gian đá</InputLabel>
                                        <Select
                                            labelId="demo-simple-select-label"
                                            id="demo-simple-select"
                                            value={time}
                                            label="Thời gian đá"
                                            onChange={handleChangeTime}
                                        >
                                            <MenuItem value={60}>1 giờ</MenuItem>
                                            <MenuItem value={90}>1.5 giờ</MenuItem>
                                            <MenuItem value={120}>2 giờ</MenuItem>
                                        </Select>
                                    </FormControl>
                                </Typography>
                            </Grid>
                            <Grid item xs={12}>
                                <Typography component={'span'}>
                                    <TextField
                                        label="Ghi chú"
                                        variant="outlined"
                                        fullWidth
                                        autoComplete="note"
                                        {...register('note')}
                                    />
                                </Typography>
                            </Grid>
                            <Grid item xs={12}>
                                <Typography component={'span'}>
                                    <FormControl fullWidth>
                                        <InputLabel id="demo-simple-select-label">Phương thức thanh toán</InputLabel>
                                        <Select
                                            labelId="demo-simple-select-label"
                                            id="demo-simple-select"
                                            value={payment}
                                            label="Phương thức thanh toán"
                                            onChange={handleChangePayment}
                                        >
                                            <MenuItem value={'QR'}>Thanh toán bằng QR</MenuItem>
                                            <MenuItem value={'Ví'}>Thanh toán bằng Ví HTTC</MenuItem>
                                        </Select>
                                    </FormControl>
                                </Typography>
                            </Grid>
                        </Grid>
                    </Typography>
                    <Typography sx={{ mt: 2 }}>
                        <Button
                            sx={{
                                width: '100%',
                                '&:hover': {
                                    color: 'white',
                                    backgroundColor: 'blue',
                                },
                            }}
                            type="submit"
                            variant="outlined"
                            size="large"
                            color="primary"
                            className="text-capitalize"
                        >
                            Đặt Sân
                        </Button>
                    </Typography>
                </Box>
            </CardContent>
            <Popup openPopup={openPopup} setOpenPopup={setOpenPopup}>
                <Grid container>
                    <Grid item>
                        <Payment resData={dataPayment} resPayment={resPayment} resDate={dateValue} />
                    </Grid>
                </Grid>
            </Popup>
        </Card>
    );
}
