import { Box, Button, TextField, Typography, Grid } from '@mui/material';
import { styled } from '@mui/material/styles';

// Custom styled TextField for validation
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

export default function FormRentInfo() {
    return (
        <Box sx={{ width: '100%', p: 2 }}>
            <Grid container spacing={2} justifyContent="center">
                <Grid item xs={12} sm={8} md={6}>
                    <Box className="card" component="form" noValidate sx={{ p: 2 }}>
                        <Typography className="card-header text-center" variant="h6" component="div" sx={{ mb: 2 }}>
                            Thông Tin
                        </Typography>
                        <Box className="card-body">
                            <Grid container spacing={2}>
                                <Grid item xs={12}>
                                    <ValidationTextField
                                        label="Tên Sân"
                                        variant="outlined"
                                        id="tenSan"
                                        defaultValue=""
                                        sx={{ mb: 2 }}
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        label="Email"
                                        variant="outlined"
                                        id="email"
                                        defaultValue=""
                                        sx={{ mb: 2 }}
                                        type="email"
                                        className="w-100"
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <ValidationTextField
                                        label="Số Điện Thoại"
                                        variant="outlined"
                                        id="soDT"
                                        defaultValue=""
                                        sx={{ mb: 2 }}
                                        className="w-100"
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField
                                        label="Ngày Đá"
                                        variant="outlined"
                                        id="ngayDa"
                                        defaultValue="07/11/2024"
                                        sx={{ mb: 2 }}
                                        type="date"
                                        InputLabelProps={{ shrink: true }}
                                        className="w-100"
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        label="Thời Gian Bắt Đầu"
                                        variant="outlined"
                                        id="timeStart"
                                        defaultValue="20:00"
                                        sx={{ mb: 2 }}
                                        type="time"
                                        InputLabelProps={{ shrink: true }}
                                        className="w-100"
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        label="Thời Gian Kết Thúc"
                                        variant="outlined"
                                        id="timeEnd"
                                        defaultValue="21:30"
                                        sx={{ mb: 2 }}
                                        type="time"
                                        InputLabelProps={{ shrink: true }}
                                        className="w-100"
                                    />
                                </Grid>
                            </Grid>
                        </Box>
                        <Box sx={{ display: 'flex', justifyContent: 'flex-end', mt: 2 }}>
                            <Button variant="outlined" color="success" className="text-capitalize mx-2">
                                Cập Nhật
                            </Button>
                        </Box>
                    </Box>
                </Grid>
            </Grid>
        </Box>
    );
}
