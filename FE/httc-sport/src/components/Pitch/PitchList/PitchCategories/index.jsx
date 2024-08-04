import SearchRoundedIcon from '@mui/icons-material/SearchRounded';
import {
    Box,
    Button,
    FormControl,
    Grid,
    InputAdornment,
    InputLabel,
    MenuItem,
    Select,
    TextField,
    Typography,
} from '@mui/material';
import { useState } from 'react';
import { useForm } from 'react-hook-form';

export default function PitchCategories({ updatePitches, dataCity, districts }) {
    const { register, handleSubmit, reset } = useForm();
    const [type, setType] = useState(0);
    const [rates, setRates] = useState(0);
    const [district, setDistrict] = useState(0);
    const [price, setPrice] = useState(0);

    const handleChangeDistrict = (event) => {
        setDistrict(event.target.value);
    };

    const handleChangeType = (event) => {
        setType(event.target.value);
    };

    const handleChangeRates = (event) => {
        setRates(event.target.value);
    };

    const handleChangePrice = (event) => {
        setPrice(event.target.value);
    };

    const handleResetFilter = () => {
        setType(0);
        setRates(0);
        setDistrict(0);
        setPrice(0);
        reset({
            name: '',
        });
    };

    const submitSearch = (data) => {
        const newType = '';
        const newRates = '';
        const newCity = '';
        const newDis = '';
        const newPrice = '';
        data = {
            ...data,
            type: type === 0 ? newType : type,
            rates: rates === 0 ? newRates : rates,
            city: district === 0 ? newCity : dataCity,
            district: district === 0 ? newDis : district,
            price: price === 0 ? newPrice : price,
        };
        updatePitches(data);
    };

    return (
        <Box component="form" onSubmit={handleSubmit(submitSearch)}>
            <Grid container spacing={1}>
                <Grid item xs={12}>
                    <Typography component={'span'}>
                        <TextField
                            InputProps={{
                                startAdornment: (
                                    <InputAdornment position="start">
                                        <SearchRoundedIcon />
                                    </InputAdornment>
                                ),
                            }}
                            label="Tìm kiếm"
                            variant="outlined"
                            fullWidth
                            autoComplete="name"
                            {...register('name')}
                        />
                    </Typography>
                </Grid>
                <Grid item xs={12}>
                    <FormControl variant="standard" fullWidth>
                        <InputLabel id="demo-simple-select-label">Loại Sân</InputLabel>
                        <Select
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            value={type}
                            label="Loại Sân"
                            onChange={handleChangeType}
                        >
                            <MenuItem value={'7'}>Sân 7</MenuItem>
                            <MenuItem value={'11'}>Sân 11</MenuItem>
                            <MenuItem value={0}>Tất Cả</MenuItem>
                        </Select>
                    </FormControl>
                </Grid>
                <Grid item xs={12}>
                    <FormControl variant="standard" fullWidth>
                        <InputLabel id="demo-simple-select-label">Đánh Giá</InputLabel>
                        <Select
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            value={rates}
                            label="Đánh Giá"
                            onChange={handleChangeRates}
                        >
                            <MenuItem value={'1-3'}>1 Sao - 3 Sao</MenuItem>
                            <MenuItem value={'3-4'}>3 Sao - 4 Sao</MenuItem>
                            <MenuItem value={'4-5'}>4 Sao - 5 Sao</MenuItem>
                            <MenuItem value={0}>Tất Cả</MenuItem>
                        </Select>
                    </FormControl>
                </Grid>
                <Grid item xs={12}>
                    <FormControl variant="standard" fullWidth>
                        <InputLabel id="demo-simple-select-label">Khu Vực</InputLabel>
                        <Select
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            value={district}
                            label="Khu Vực"
                            onChange={handleChangeDistrict}
                        >
                            {districts.map((item) => (
                                <MenuItem key={item.Id} value={item.Name}>
                                    {item.Name}
                                </MenuItem>
                            ))}
                            <MenuItem value={0}>Tất Cả</MenuItem>
                        </Select>
                    </FormControl>
                </Grid>
                <Grid item xs={12}>
                    <FormControl variant="standard" fullWidth>
                        <InputLabel id="demo-simple-select-label">Mức Giá</InputLabel>
                        <Select
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            value={price}
                            label="Mức Giá"
                            onChange={handleChangePrice}
                        >
                            <MenuItem value={'100000-300000'}>100.000đ - 300.000đ</MenuItem>
                            <MenuItem value={'300000-500000'}>300.000đ - 500.000đ</MenuItem>
                            <MenuItem value={'500000-1000000'}>500.000đ - 1.000.000đ</MenuItem>
                            <MenuItem value={'1000000-2000000'}>1.000.000đ - 2.000.000đ</MenuItem>
                            <MenuItem value={0}>Tất Cả</MenuItem>
                        </Select>
                    </FormControl>
                </Grid>
                <Grid item xs={6}>
                    <Button
                        fullWidth
                        sx={{
                            '&:hover': {
                                color: 'white',
                                backgroundColor: 'crimson',
                            },
                        }}
                        onClick={handleResetFilter}
                        variant="outlined"
                        size="large"
                        color="error"
                        className="text-capitalize"
                    >
                        Đặt Lại
                    </Button>
                </Grid>
                <Grid item xs={6}>
                    <Button
                        fullWidth
                        sx={{
                            '&:hover': {
                                color: 'white',
                                backgroundColor: 'darkgreen',
                            },
                        }}
                        type="submit"
                        variant="outlined"
                        size="large"
                        color="success"
                        className="text-capitalize"
                    >
                        Tìm Kiếm
                    </Button>
                </Grid>
            </Grid>
        </Box>
    );
}
