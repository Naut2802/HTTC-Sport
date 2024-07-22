import { useEffect, useState } from 'react';
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
import { useForm, Controller } from 'react-hook-form';
import { toast } from 'react-toastify';
import { handleCreatePitch, handleProvinces } from '~/apis';

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

export default function FormAddField({ selectedPitch }) {
    const { register, handleSubmit, control, setValue } = useForm();

    const [dataCity, setDataCity] = useState([]);
    const [districts, setDistricts] = useState([]);
    const [wards, setWards] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await handleProvinces();
                const hcmCity = response.data[49]; // Lấy thành phố Hồ Chí Minh từ vị trí thứ 49
                setDataCity(hcmCity ? [hcmCity] : []);
                console.log(response.data);
            } catch (error) {
                console.error('Error fetching data: ', error);
                setDataCity([]);
            }
        };

        fetchData();
    }, []);

    const handleCityChange = (event) => {
        const cityId = event.target.value;
        const selectedCity = dataCity.find((city) => city.Name === cityId);
        setDistricts(selectedCity ? selectedCity.Districts : []);
        setWards([]);
    };

    const handleDistrictChange = (event) => {
        const districtId = event.target.value;
        const selectedDistrict = districts.find((district) => district.Name === districtId);
        setWards(selectedDistrict ? selectedDistrict.Wards : []);
    };

    useEffect(() => {
        if (selectedPitch) {
            setValue('pitchName', selectedPitch.pitchName || '');
            setValue('price', selectedPitch.price || '');
            setValue('street', selectedPitch.street || '');
            setValue('city', selectedPitch.city || '');
            setValue('district', selectedPitch.district || '');
            setValue('ward', selectedPitch.ward || '');
            setValue('description', selectedPitch.description || '');
            setValue('type', selectedPitch.type || '');
            setValue('total', selectedPitch.total || '');
            setValue('images', selectedPitch.images || '');

            // Update wards and districts based on city and district values
            const city = selectedPitch.city;
            const district = selectedPitch.district;

            if (city) {
                handleCityChange({ target: { value: city } });
            }
            if (district) {
                handleDistrictChange({ target: { value: district } });
            }
        }
    }, [selectedPitch, setValue]);

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

        if (data.images) {
            Array.from(data.images).forEach((image) => {
                formData.append('images', image);
            });
        }

        try {
            const res = await handleCreatePitch(formData);
            toast.success(res.data.message);
            console.log('Pitch added successfully');
        } catch (error) {
            console.error('Failed to add pitch:', error);
        }
    };

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
                                        defaultValue=""
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
                                                    {dataCity.map((city) => (
                                                        <MenuItem key={city.Name} value={city.Name}>
                                                            {city.Name}
                                                        </MenuItem>
                                                    ))}
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
                                                        field.onChange(event);
                                                    }}
                                                >
                                                    {districts.map((item) => (
                                                        <MenuItem key={item.Name} value={item.Name}>
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
                                                    {wards.length > 0 ? (
                                                        wards.map((ward) => (
                                                            <MenuItem key={ward.Name} value={ward.Name}>
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
