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
import React, { useEffect, useState } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { toast } from 'react-toastify';

import { handleCreatePitch, handleDelImgs, handleProvinces, handleUpdatePitch } from '~/apis';
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
//
export default function FormAddPitch({ selectedPitch }) {
    const savedPitch = JSON.parse(sessionStorage.getItem('selectedPitch'));
    const { register, handleSubmit, control, setValue, reset } = useForm();

    const [dataCity, setDataCity] = useState([]);
    const [districts, setDistricts] = useState([]);
    const [wards, setWards] = useState([]);
    const [selectedImages, setSelectedImages] = useState([]);
    const [fileNames, setFileNames] = useState([]);

    //Dữ liệu API Thành phố HCM
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await handleProvinces();
                const hcmCity = response.data[49]; // Lấy thành phố Hồ Chí Minh từ vị trí thứ 49
                setDataCity(hcmCity ? [hcmCity] : []);
            } catch (error) {
                console.error('Error fetching data: ', error);
                setDataCity([]);
            }
        };

        fetchData();
    }, []);

    //Edit fill thông tin
    useEffect(() => {
        if (selectedPitch) {
            setValue('pitchName', selectedPitch.pitchName);
            setValue('price', selectedPitch.price);
            setValue('street', selectedPitch.street);
            setValue('city', selectedPitch.city);
            setValue('district', selectedPitch.district);
            setValue('ward', selectedPitch.ward);
            setValue('description', selectedPitch.description);
            setValue('type', selectedPitch.type);
            setValue('total', selectedPitch.total);
            setValue('images', selectedPitch.images);
            setSelectedImages(selectedPitch.images || []);
            const city = selectedPitch.city;
            const district = selectedPitch.district;

            if (city) {
                handleCityChange({ target: { value: city } });
            }
            if (district) {
                handleDistrictChange({ target: { value: district } });
            }
        } else {
            if (savedPitch) {
                setValue('pitchName', savedPitch.pitchName);
                setValue('price', savedPitch.price);
                setValue('street', savedPitch.street);
                setValue('city', savedPitch.city);
                setValue('district', savedPitch.district);
                setValue('ward', savedPitch.ward);
                setValue('description', savedPitch.description);
                setValue('type', savedPitch.type);
                setValue('total', savedPitch.total);
                setValue('images', savedPitch.images);
                setSelectedImages(savedPitch.images || []);

                const city = savedPitch.city;
                const district = savedPitch.district;

                // Update city and district if they exist
                if (city) {
                    handleCityChange({ target: { value: city } });
                }
                if (district) {
                    // Use setTimeout to ensure that districts are updated before calling this function
                    setTimeout(() => handleDistrictChange({ target: { value: district } }), 0);
                }
            }
        }
    }, [selectedPitch, setValue, dataCity, districts]);

    //Khi Thay đổi Thành phố lấy Quận theo TP
    const handleCityChange = (event) => {
        const cityId = event.target.value;
        const selectedCity = dataCity.find((city) => city.Name === cityId);
        setDistricts(selectedCity ? selectedCity.Districts : []);
        setWards([]);
    };

    //Khi Thay đổi Quận lấy Phường theo Quận
    const handleDistrictChange = (event) => {
        const districtId = event.target.value;
        const selectedDistrict = districts.find((district) => district.Name === districtId);
        setWards(selectedDistrict ? selectedDistrict.Wards : []);
    };

    //Khi chọn thay đổi Image fill Image và tạo url tạm thời cho Images
    const handleImageChange = (event) => {
        const files = event.target.files;
        console.log('Files selected:', files);
        const fileArray = Array.from(files);
        const fileNamesList = fileArray.map((file) => file.name);
        console.log('File names:', fileNamesList);
        setFileNames(fileNamesList);

        // Tạo URL tạm thời cho hình ảnh mới
        const newImageUrls = fileArray.map((file) => URL.createObjectURL(file));
        setSelectedImages((prevImages) => [...prevImages, ...newImageUrls]);
    };

    // Xóa image khi click vào hình
    const handleDeleteImage = async (image, index) => {
        const pitchId = selectedPitch.id;
        const publicId = image.publicId;

        try {
            console.log('Xóa image:', image.publicId);
            console.log('Xóa image:', pitchId);

            const response = await handleDelImgs(pitchId, publicId);
            console.log(response);

            if (response.data.message === 'Xóa ảnh thành công') {
                setSelectedImages((prevImages) => prevImages.filter((_, i) => i !== index));
                toast.success('Hình ảnh đã được xóa thành công!');
            }
        } catch (error) {
            console.error('Failed to delete image:', error);
            toast.error('Không thể xóa hình ảnh!');
        }
    };

    //Thêm Sân
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
            toast.success('Thêm sân thành công!');
            console.log('Pitch added successfully');
        } catch (error) {
            console.error('Failed to add pitch:', error);
        }
    };

    //Cập Nhật Sân
    const submitUpdatePitch = async (data) => {
        console.log(savedPitch?.id);
        console.log(selectedPitch?.id);
        try {
            const pitchId = selectedPitch?.id || savedPitch?.id;
            const res = await handleUpdatePitch(pitchId, data);

            if (res && res.data) {
                toast.success(res.data.message || 'Cập nhật sân thành công!');
                sessionStorage.removeItem('selectedPitch');
            } else {
                toast.error('Cập nhật sân thất bại!');
            }
        } catch (error) {
            console.error('Error updating pitch:', error);
            toast.error('Cập nhật sân thất bại!');
        }
    };

    //Reset Form Sân
    const submitReset = () => {
        reset({
            id: '',
            pitchName: '',
            price: '',
            street: '',
            city: '',
            district: '',
            ward: '',
            description: '',
            type: '',
            total: '',
            images: [],
        });
        setSelectedImages([]);
        setFileNames([]);

        sessionStorage.removeItem('selectedPitch');
    };

    //Điều khiện submit button
    const onSubmit = async (data) => {
        console.log('Data to be submitted:', data);
        if (selectedPitch || savedPitch) {
            await submitUpdatePitch(data);
        } else {
            await submitAddPitch(data);
        }
    };

    return (
        <div className="row my-2">
            <div className="col-6">
                <Box className="card" sx={{ height: '100%' }}>
                    <Typography className="card-header text-center fs-3" variant="h6" component="div">
                        Hình Ảnh Sân
                    </Typography>
                    <div className="row">
                        <Typography className="card-body fs-3" variant="h6" component="div">
                            <div className="m-2">
                                {selectedImages.length > 0 ? (
                                    selectedImages.map((image, index) => (
                                        <img
                                            key={index}
                                            src={image.url || image}
                                            alt={`Hình ảnh ${index}`}
                                            style={{ width: '190px', minHeight: '190px' }}
                                            onClick={() => handleDeleteImage(image, index)}
                                            className="m-1 rounded items-algin-center"
                                        />
                                    ))
                                ) : (
                                    <p>Chưa có hình</p>
                                )}
                            </div>
                        </Typography>
                    </div>
                </Box>
            </div>
            <div className="col-6">
                <Box className="card" component="form" noValidate onSubmit={handleSubmit(onSubmit)}>
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
                            <input
                                type="file"
                                multiple
                                {...register('images')}
                                className="my-2 w-100"
                                onChange={handleImageChange}
                            />
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
                        <Button variant="outlined" color="success" className="text-capitalize mx-1" type="submit">
                            {selectedPitch?.id || savedPitch?.id ? 'Cập Nhật' : 'Thêm'}
                        </Button>
                        <Button variant="outlined" color="inherit" type="button" onClick={submitReset}>
                            Làm Mới
                        </Button>
                    </Typography>
                </Box>
            </div>
        </div>
    );
}
