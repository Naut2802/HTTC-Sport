import {
    Box,
    Button,
    FormControl,
    FormControlLabel,
    FormLabel,
    InputLabel,
    MenuItem,
    Radio,
    RadioGroup,
    Select,
    TextField,
    Typography,
} from '@mui/material';
import { styled } from '@mui/material/styles';
import { handleCreatePitch, handleProvinces } from '~/apis';
import { useForm, Controller } from 'react-hook-form';
import { toast } from 'react-toastify';
import { useEffect, useState } from 'react';

export default function FormAddField() {
    const { register, handleSubmit, control } = useForm();

    const [dataCity, setDataCity] = useState({});
    const [districts, setDistricts] = useState([]);
    const [wards, setWards] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await handleProvinces();
                const cityData = response.data[49];

                if (cityData || Array.isArray(cityData)) {
                    setDataCity(cityData);
                    console.log(cityData);
                } else {
                    setDataCity([cityData]);
                }
            } catch (error) {
                console.error('Error fetching data: ', error);
                setDataCity([]);
            }
        };

        fetchData();
    }, []);

    const handleCityChange = (event) => {
        const cityId = event.target.value;
        console.log('Selected City Id:', cityId);
        console.log('Available City:', dataCity);

        // const selectedCity = dataCity.find(item => item.Name === cityId);
        // console.log('Selected City:', selectedCity);

        if (cityId) {
            setDistricts(dataCity.Districts || []);
        } else {
            setDistricts([]);
        }
    };

    const handleDistrictChange = (event) => {
        const districtId = event.target.value;
        console.log('Selected District Id:', districtId);
        console.log('Available Districts:', districts);

        const selectedDistrict = districts.find((item) => item.Name === districtId);
        console.log('Selected District:', selectedDistrict);

        if (selectedDistrict) {
            setWards(selectedDistrict.Wards || []);
        } else {
            setWards([]);
        }
    };

    const submitAddPitch = async (data) => {
        const formData = new FormData();
        formData.append('pitchName', data.pitchName);
        formData.append('price', data.price);
        formData.append('street', data.street);
        formData.append('ward', data.ward);
        formData.append('district', data.district);
        formData.append('city', data.city);
        formData.append('description', data.description);
        formData.append('type', data.type);
        formData.append('total', data.total);

        Array.from(data.images).forEach((image) => {
            formData.append('images', image);
        });

        try {
            const res = await handleCreatePitch(formData);
            toast.success(res.data.message);
            console.log('Pitch added successfully');
        } catch (error) {
            console.error('Failed to add pitch:', error);
        }
    };

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

    return (
        <div className="row my-2">
            <div className="col-6">
                <Box className="card" sx={{ height: '100%' }}>
                    <Typography className="card-header text-center fs-3" variant="h6" component="div">
                        Hình Ảnh Sân
                    </Typography>
                    <Typography className="card-body text-center fs-3" variant="h6" component="div">
                        <img src="" alt="" />
                    </Typography>
                </Box>
            </div>
            <div className="col-6">
                <Box className="card" component="form" noValidate onSubmit={handleSubmit(submitAddPitch)}>
                    <Typography className="card-header text-center fs-3" variant="h6" component="div">
                        Thông Tin Sân
                    </Typography>
                    <Typography component="div" className="card-body">
                        <FormControl variant="outlined" className="my-2 w-100">
                            <Controller
                                name="pitchName"
                                control={control}
                                defaultValue=""
                                render={({ field }) => (
                                    <ValidationTextField {...field} label="Tên Sân" variant="outlined" className="my-2 w-100" />
                                )}
                            />
                            <div className="row">
                                <div className="col-8">
                                    <Controller
                                        name="price"
                                        control={control}
                                        defaultValue=""
                                        render={({ field }) => (
                                            <ValidationTextField
                                                {...field}
                                                label="Giá"
                                                variant="outlined"
                                                className="my-2 w-100"
                                            />
                                        )}
                                    />
                                </div>
                                <div className="col-4">
                                    <Controller
                                        name="total"
                                        control={control}
                                        defaultValue=""
                                        render={({ field }) => (
                                            <ValidationTextField
                                                {...field}
                                                label="Tổng Sân"
                                                variant="outlined"
                                                className="my-2 w-100"
                                            />
                                        )}
                                    />
                                </div>
                            </div>

                            <div className="my-2 row">
                                <div className="col-6">
                                    <Controller
                                        name="street"
                                        control={control}
                                        defaultValue={dataCity.Name}
                                        render={({ field }) => (
                                            <ValidationTextField
                                                {...field}
                                                label="Đường"
                                                variant="outlined"
                                                className="my-2 w-100"
                                            />
                                        )}
                                    />
                                </div>
                                <div className="col-6 mt-2">
                                    <Controller
                                        name="city"
                                        control={control}
                                        defaultValue=""
                                        render={({ field }) => (
                                            <FormControl fullWidth>
                                                <InputLabel id="city-select-label">Thành Phố</InputLabel>
                                                <Select
                                                    {...field}
                                                    labelId="city-select-label"
                                                    id="city-select"
                                                    label="Thành Phố"
                                                    onChange={(event) => {
                                                        handleCityChange(event);
                                                        field.onChange(event);
                                                    }}
                                                >
                                                    {dataCity ? (
                                                        <MenuItem key={dataCity.Id} value={dataCity.Name}>
                                                            {dataCity.Name}
                                                        </MenuItem>
                                                    ) : (
                                                        <MenuItem disabled>No city available</MenuItem>
                                                    )}
                                                </Select>
                                            </FormControl>
                                        )}
                                    />
                                </div>
                            </div>
                            <div className="my-2 row">
                                <div className="col-6">
                                    <Controller
                                        name="district"
                                        control={control}
                                        defaultValue=""
                                        render={({ field }) => (
                                            <FormControl fullWidth>
                                                <InputLabel id="district-select-label">Quận</InputLabel>
                                                <Select
                                                    {...field}
                                                    labelId="district-select-label"
                                                    id="district-select"
                                                    label="Quận"
                                                    onChange={(event) => {
                                                        handleDistrictChange(event);
                                                        field.onChange(event); // Đảm bảo `react-hook-form` nhận diện sự thay đổi
                                                    }}
                                                >
                                                    {Array.isArray(districts) &&
                                                        districts.map((item) => (
                                                            <MenuItem key={item.Id} value={item.Name}>
                                                                {item.Name}
                                                            </MenuItem>
                                                        ))}
                                                </Select>
                                            </FormControl>
                                        )}
                                    />
                                </div>
                                <div className="col-6">
                                    <Controller
                                        name="ward"
                                        control={control}
                                        defaultValue=""
                                        render={({ field }) => (
                                            <FormControl fullWidth>
                                                <InputLabel id="ward-select-label">Phường</InputLabel>
                                                <Select {...field} labelId="ward-select-label" id="ward-select" label="Phường">
                                                    {Array.isArray(wards) && wards.length > 0 ? (
                                                        wards.map((ward) => (
                                                            <MenuItem key={ward.Id} value={ward.Name}>
                                                                {ward.Name}
                                                            </MenuItem>
                                                        ))
                                                    ) : (
                                                        <MenuItem disabled>No wards available</MenuItem>
                                                    )}
                                                </Select>
                                            </FormControl>
                                        )}
                                    />
                                </div>
                            </div>
                            <FormLabel id="demo-radio-buttons-group-label">Loại Sân</FormLabel>
                            <Controller
                                name="type"
                                control={control}
                                defaultValue="onl"
                                render={({ field }) => (
                                    <RadioGroup {...field} aria-labelledby="demo-radio-buttons-group-label">
                                        <Typography component="div" className="d-flex">
                                            <FormControlLabel value="Sân 5" control={<Radio />} label="Sân 5" />
                                            <FormControlLabel value="Sân 7" control={<Radio />} label="Sân 7" />
                                        </Typography>
                                    </RadioGroup>
                                )}
                            />
                            <FormLabel id="demo-radio-buttons-group-label">Ảnh Sân</FormLabel>
                            <input type="file" multiple {...register('images')} className="my-2 w-100" />
                            <Controller
                                name="description"
                                control={control}
                                defaultValue=""
                                render={({ field }) => (
                                    <ValidationTextField {...field} label="Mô Tả" variant="outlined" className="my-2 w-100" />
                                )}
                            />
                        </FormControl>
                    </Typography>
                    <Typography component="div" className="d-flex w-100 align-items-center my-2 card-footer">
                        <Button variant="outlined" color="success" className="text-capitalize mx-2" type="submit">
                            Thêm Sân
                        </Button>
                        <Button variant="outlined" color="secondary" className="text-capitalize mx-2">
                            Cập Nhật
                        </Button>
                        <Button variant="outlined" color="inherit" className="text-capitalize mx-2">
                            Làm Mới
                        </Button>
                    </Typography>
                </Box>
            </div>
        </div>
    );
}
