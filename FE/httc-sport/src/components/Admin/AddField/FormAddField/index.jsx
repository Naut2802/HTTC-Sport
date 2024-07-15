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
import { useState } from 'react';

export default function FormAddField() {
    const [value, setValue] = useState('');
    const [error, setError] = useState(false);

    const handleChange = (event) => {
        setValue(event.target.value);
        setError(event.target.value === '');
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
            padding: '4px !important', // override inline-style
        },
    });

    return (
        <div className="row my-2">
            <div className="col-6">
                <Box
                    className="card"
                    component="form"
                    noValidate
                    sx={{
                        height: '100%',
                    }}
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
                <Box className="card" component="form" noValidate>
                    <Typography className="card-header text-center fs-3" variant="h6" component="div">
                        Thông Tin Sân
                    </Typography>
                    <Typography component="div" className="card-body ">
                        <Box className="card" component="form" noValidate>
                            <Typography component="div" className="card-body">
                                <FormControl variant="outlined" className="my-2 w-100" error={error}>
                                    <InputLabel id="loaiSan-label">Loại Sân</InputLabel>
                                    <Select
                                        labelId="loaiSan-label"
                                        id="loaiSan"
                                        value={value}
                                        onChange={handleChange}
                                        label="Loại Sân"
                                    >
                                        <MenuItem value=""></MenuItem>
                                        <MenuItem value="option1">Option 1</MenuItem>
                                    </Select>
                                    <ValidationTextField
                                        label="Tên Sân"
                                        variant="outlined"
                                        id="tenSan"
                                        defaultValue=""
                                        className="my-2 w-100"
                                    />
                                    <ValidationTextField
                                        label="Giá"
                                        variant="outlined"
                                        id="gia"
                                        defaultValue=""
                                        className="my-2 w-100"
                                    />
                                    <FormLabel id="demo-radio-buttons-group-label">Gender</FormLabel>
                                    <RadioGroup
                                        aria-labelledby="demo-radio-buttons-group-label"
                                        defaultValue="onl"
                                        name="radio-buttons-group"
                                    >
                                        <Typography component="div" className="d-flex">
                                            <FormControlLabel value="onl" control={<Radio />} label="Hoạt Động" />
                                            <FormControlLabel value="off" control={<Radio />} label="Không Hoạt Động" />
                                        </Typography>
                                    </RadioGroup>
                                    <FormLabel id="demo-radio-buttons-group-label">Ảnh Sân</FormLabel>
                                    <ValidationTextField
                                        variant="outlined"
                                        id="hinhAnh"
                                        defaultValue=""
                                        className="my-2 w-100"
                                        type="file"
                                    />
                                    <ValidationTextField
                                        label="Địa Chỉ"
                                        variant="outlined"
                                        id="diaChi"
                                        defaultValue=""
                                        className="my-2 w-100"
                                    />
                                    <ValidationTextField
                                        label="Mô Tả"
                                        variant="outlined"
                                        id="moTa"
                                        defaultValue=""
                                        className="my-2 w-100"
                                    />
                                </FormControl>
                            </Typography>
                        </Box>
                    </Typography>
                    <Typography component="div" className="d-flex w-100 align-items-center my-2 card-footer">
                        <Button variant="outlined" color="success" className="text-capitalize mx-2">
                            Đặt Sân
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
