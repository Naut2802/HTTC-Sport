import StarRoundedIcon from '@mui/icons-material/StarRounded';
import { Box, Card, CardContent, TextField, Typography } from '@mui/material';

export default function Comments() {
    return (
        <Box>
            <Card sx={{ width: '100%' }}>
                <CardContent sx={{ px: 5 }}>
                    <Typography variant="h6" className="my-2 text-center">
                        Đánh Giá
                    </Typography>
                    <div className="row my-2">
                        <div className="col-6">
                            <Typography sx={{ fontSize: 17 }} variant="subtitle2" color="text.dark">
                                từ Hoàng Lê
                            </Typography>
                        </div>
                        <div className="col-6 d-flex justify-content-end">
                            <Typography sx={{ fontSize: 17 }} variant="subtitle2" color="text.dark">
                                5
                                <StarRoundedIcon sx={{ mb: 1, color: '#FFC107' }} />
                            </Typography>
                        </div>
                    </div>
                    <div className="my-2">
                        <TextField
                            fullWidth
                            variant="standard"
                            InputProps={{
                                readOnly: true,
                            }}
                            label="Mô tả"
                            value="Sân sạch sẽ, dịch vụ tốt, 10 điểm không có nhưng."
                        />
                    </div>
                </CardContent>
            </Card>
        </Box>
    );
}
