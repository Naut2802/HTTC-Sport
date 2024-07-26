import { Box, Button, FormControl, InputLabel, MenuItem, Select, TextField, Typography } from '@mui/material';
import { useState } from 'react';

export default function ModalServices() {
    const [value, setValue] = useState('');
    const [error, setError] = useState(false);

    const handleChange = (event) => {
        setValue(event.target.value);
        setError(event.target.value === '');
    };

    return (
        <div className="row">
            <Box className="card" component="form" noValidate>
                <Typography className="card-header text-center fs-3" variant="h6" component="div">
                    Bảng Dịch Vụ
                </Typography>
                <Typography component="div" className="card-body ">
                    <Box className="card" component="form" noValidate>
                        <Typography component="div" className="card-body">
                            <TextField
                                label="Tổng Tiền Cần Thanh Toán"
                                variant="outlined"
                                id="tongTien"
                                defaultValue=""
                                className="my-2 w-100"
                            />
                            <div className="row">
                                <div className="col-6">
                                    <TextField
                                        label="Tiền Đã Thanh Toán"
                                        variant="outlined"
                                        id="daTT"
                                        defaultValue=""
                                        className="my-2 w-100"
                                    />
                                </div>
                                <div className="col-6">
                                    <TextField
                                        label="Tiền Còn Lại"
                                        variant="outlined"
                                        id="chuaTT"
                                        defaultValue=""
                                        className="my-2 w-100"
                                    />
                                </div>
                            </div>
                            <FormControl variant="outlined" className="my-2 w-100" error={error}>
                                <InputLabel id="trangThai-label">Trạng Thái</InputLabel>
                                <Select
                                    labelId="trangThai-label"
                                    id="trangThai"
                                    value={value}
                                    onChange={handleChange}
                                    label="Trạng Thái"
                                >
                                    <MenuItem value=""></MenuItem>
                                    <MenuItem value="option1">Đang Đá</MenuItem>
                                    <MenuItem value="option1">Đã Hoàn Thành</MenuItem>
                                </Select>
                            </FormControl>
                            <TextField
                                label="Thêm Thời Gian"
                                variant="outlined"
                                id="addTG"
                                defaultValue=""
                                className="my-2 w-100"
                            />
                            <TextField label="Ghi Chú" variant="outlined" id="ghiChu" defaultValue="" className="my-2 w-100" />
                        </Typography>
                    </Box>
                </Typography>
                <Typography component="div" className="d-flex w-100 align-items-center my-2 justify-content-end card-footer">
                    <Button variant="outlined" color="success" className="text-capitalize mx-2 ">
                        Cập Nhật
                    </Button>
                </Typography>
            </Box>
        </div>
    );
}
