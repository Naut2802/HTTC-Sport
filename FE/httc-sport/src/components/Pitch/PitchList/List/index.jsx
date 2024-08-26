import StarRoundedIcon from '@mui/icons-material/StarRounded';
import StorefrontSharpIcon from '@mui/icons-material/StorefrontSharp';
import WifiSharpIcon from '@mui/icons-material/WifiSharp';
import { Box, Card, CardContent, CardMedia, Typography } from '@mui/material';
import { Link } from 'react-router-dom';

function formatCurrency(amount) {
    return amount.toLocaleString('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
}

export default function List({ pitches }) {
    return (
        <div className="row">
            {pitches.map((pitch) => (
                <Card key={pitch.id} sx={{ display: 'flex', maxWidth: 'auto', mb: 2 }}>
                    <CardMedia
                        component={Link}
                        to={`/chi-tiet-san/${pitch.id}`}
                        sx={{ width: 260 }}
                        image={pitch.image ? pitch.image : 'default-image-url.jpg'}
                        alt={pitch.pitchName}
                        className="img-fluid"
                    />
                    <Box>
                        <CardContent>
                            <Typography
                                className="text-decoration-none text-dark fs-3 fw-bolder"
                                variant="subtitle2"
                                component={Link}
                                to={`/chi-tiet-san/${pitch.id}`}
                            >
                                {pitch.pitchName}
                            </Typography>

                            <Typography sx={{ fontSize: 18 }} variant="subtitle2" color="text.secondary">
                                <strong>Số Sân: </strong>
                                <span className="text-dark">{pitch.total} Sân</span>
                            </Typography>

                            <Typography sx={{ fontSize: 18 }} variant="subtitle2" color="text.secondary">
                                <strong>Đánh Giá: </strong>
                                <span className="text-dark">
                                    {pitch.rate === 0 ? (
                                        ' Chưa có'
                                    ) : (
                                        <>
                                            {pitch.rate}/5 <StarRoundedIcon sx={{ mb: 1, color: '#FFC107' }} />
                                        </>
                                    )}{' '}
                                </span>
                            </Typography>

                            <Typography sx={{ fontSize: 18 }} variant="subtitle2" color="text.secondary">
                                <strong>Giá Sân: </strong>
                                <span className="text-danger"> {formatCurrency(pitch.price)} </span>
                            </Typography>

                            <Typography sx={{ fontSize: 18 }} variant="subtitle2" color="text.secondary">
                                <strong>Địa Chỉ: </strong>
                                <span className="text-dark">
                                    {pitch.street + ', ' + pitch.ward + ', ' + pitch.district + ', ' + pitch.city}
                                </span>
                            </Typography>

                            <Typography sx={{ mt: 1 }} variant="subtitle2" color="text.secondary">
                                <span className="fs-6">
                                    Căn Tin
                                    <StorefrontSharpIcon sx={{ marginLeft: 1, marginRight: 1 }} />
                                </span>
                                <span className="fs-6 border-start">
                                    <WifiSharpIcon sx={{ marginRight: 1, marginLeft: 1 }} />
                                    Wifi
                                </span>
                            </Typography>
                        </CardContent>
                    </Box>
                </Card>
            ))}
        </div>
    );
}
