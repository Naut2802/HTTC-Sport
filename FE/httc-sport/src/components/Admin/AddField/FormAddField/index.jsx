import {
    Box,
    Button,
    FormControl,
    FormControlLabel,
    FormLabel,
    Radio,
    RadioGroup,
    TextField,
    Typography,
} from '@mui/material';
import { styled } from '@mui/material/styles';
import { handleCreatePitch, handleProvinces } from '~/apis';
import { useForm, Controller } from 'react-hook-form';
import { toast } from 'react-toastify';
import { useEffect, useState } from 'react';


export default function FormAddField() {
    const {register , handleSubmit, control} = useForm();
    
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
    }
    
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
                <Box
                    className="card"
                    sx={{ height: '100%' }}
                >
                    <Typography className="card-header text-center fs-3" variant="h6" component="div">
                        Hình Ảnh Sân
                    </Typography>
                    <Typography className="card-body text-center fs-3" variant="h6" component="div">
                        <img src="" alt="" />
                    </Typography>
                </Box>
            </div>
            <div className="col-6">
                <Box
                    className="card"
                    component="form"
                    noValidate
                    onSubmit={handleSubmit(submitAddPitch)}
                >
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
                                    <ValidationTextField
                                        {...field}
                                        label="Tên Sân"
                                        variant="outlined"
                                        className="my-2 w-100"
                                    />
                                )}
                            />
                            <div className='row'>
                                <div className='col-8'>
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
                                <div className='col-4'>
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
                            <div className='row'>
                                <div className='col-3'>
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
                                <div className='col-3'>
                                    <Controller
                                        name="ward"
                                        control={control}
                                        defaultValue=""
                                        render={({ field }) => (
                                            <ValidationTextField
                                                {...field}
                                                label="Phường"
                                                variant="outlined"
                                                className="my-2 w-100"
                                            />
                                        )}
                                    />
                                </div>
                                <div className='col-3'>
                                    <Controller
                                        name="district"
                                        control={control}
                                        defaultValue=""
                                        render={({ field }) => (
                                            <ValidationTextField
                                                {...field}
                                                label="Quận"
                                                variant="outlined"
                                                className="my-2 w-100"
                                            />
                                        )}
                                    />
                                </div>
                                <div className='col-3'>
                                    <Controller
                                        name="city"
                                        control={control}
                                        defaultValue=""
                                        render={({ field }) => (
                                            <ValidationTextField
                                                {...field}
                                                label="Thành Phố"
                                                variant="outlined"
                                                className="my-2 w-100"
                                            />
                                        )}
                                    />
                                </div>
                            </div>
                            <FormLabel id="demo-radio-buttons-group-label">Status</FormLabel>
                            <Controller
                                name="type"
                                control={control}
                                defaultValue="onl"
                                render={({ field }) => (
                                    <RadioGroup {...field} aria-labelledby="demo-radio-buttons-group-label">
                                        <Typography component="div" className="d-flex">
                                            <FormControlLabel value="1" control={<Radio />} label="Hoạt Động" />
                                            <FormControlLabel value="0" control={<Radio />} label="Không Hoạt Động" />
                                        </Typography>
                                    </RadioGroup>
                                )}
                            />
                            <FormLabel id="demo-radio-buttons-group-label">Ảnh Sân</FormLabel>
                            <input
                                type="file"
                                multiple
                                {...register("images")}
                                className="my-2 w-100"
                            />
                            <Controller
                                name="description"
                                control={control}
                                defaultValue=""
                                render={({ field }) => (
                                    <ValidationTextField
                                        {...field}
                                        label="Mô Tả"
                                        variant="outlined"
                                        className="my-2 w-100"
                                    />
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
