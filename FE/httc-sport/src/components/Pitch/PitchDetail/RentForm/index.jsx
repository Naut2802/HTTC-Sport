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
import { useState } from 'react';

export default function RentForm() {
    const [age, setAge] = useState('');
    const [dateValue, setDateValue] = useState(null);
    const [timeValue, setTimeValue] = useState(null);

    const handleChange = (event) => {
        setAge(event.target.value);
    };

    return (
        <Card>
            <CardContent>
                <Typography variant="subtitle2" color="text.dark">
                    <ArrowDropDownIcon fontSize="large" sx={{ color: 'orangered', mb: 1 }} />
                    <span className="fw-bolder fs-5">Đặt sân theo yêu cầu</span>
                </Typography>
                <Box
                    component="form"
                    // onSubmit={handleSubmit(submitSignUp)}
                    noValidate
                    sx={{
                        top: '10%',
                    }}
                >
                    <Typography>
                        <Grid container spacing={1}>
                            <Grid item xs={6}>
                                <Typography>
                                    <TextField
                                        label="Họ"
                                        variant="outlined"
                                        fullWidth
                                        autoComplete="lastName"
                                        // {...register('username')}
                                    />
                                </Typography>
                            </Grid>
                            <Grid item xs={6}>
                                <Typography>
                                    <TextField
                                        label="Tên"
                                        variant="outlined"
                                        fullWidth
                                        autoComplete="firstName"
                                        // {...register('username')}
                                    />
                                </Typography>
                            </Grid>
                            <Grid item xs={12}>
                                <Typography>
                                    <TextField
                                        label="Số điện thoại"
                                        variant="outlined"
                                        fullWidth
                                        autoComplete="phoneNumber"
                                        // {...register('password')}
                                    />
                                </Typography>
                            </Grid>
                            <Grid item xs={12}>
                                <Typography>
                                    <TextField
                                        label="Email"
                                        variant="outlined"
                                        fullWidth
                                        type="email"
                                        autoComplete="email"
                                        // {...register('email')}
                                    />
                                </Typography>
                            </Grid>
                            <Grid item xs={6}>
                                <Typography>
                                    <DatePicker
                                        value={dateValue}
                                        onChange={(newDateValue) => setDateValue(newDateValue)}
                                        label="Chọn ngày"
                                    />
                                </Typography>
                            </Grid>
                            <Grid item xs={6}>
                                <Typography>
                                    <TimePicker
                                        label="Chọn giờ"
                                        value={timeValue}
                                        format="hh:mm A"
                                        timeSteps={{ minutes: 30 }}
                                        onChange={(newTimeValue) => setTimeValue(newTimeValue)}
                                    />
                                </Typography>
                            </Grid>
                            <Grid item xs={12}>
                                <Typography>
                                    <FormControl fullWidth>
                                        <InputLabel id="demo-simple-select-label">Thời gian đá</InputLabel>
                                        <Select
                                            labelId="demo-simple-select-label"
                                            id="demo-simple-select"
                                            value={age}
                                            label="Thời gian đá"
                                            onChange={handleChange}
                                        >
                                            <MenuItem value={60}>1 giờ</MenuItem>
                                            <MenuItem value={90}>1.5 giờ</MenuItem>
                                            <MenuItem value={120}>2 giờ</MenuItem>
                                        </Select>
                                    </FormControl>
                                </Typography>
                            </Grid>
                            <Grid item xs={12}>
                                <Typography>
                                    <TextField
                                        label="Ghi chú"
                                        variant="outlined"
                                        fullWidth
                                        autoComplete="email"
                                        // {...register('email')}
                                    />
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
        </Card>
    );
}
