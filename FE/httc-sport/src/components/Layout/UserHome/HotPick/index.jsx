import StarRateIcon from '@mui/icons-material/StarRate';
import StorefrontSharpIcon from '@mui/icons-material/StorefrontSharp';
import WifiSharpIcon from '@mui/icons-material/WifiSharp';
import { Card, CardContent, CardMedia, Typography } from '@mui/material';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

import { handleGetTop3Pitches } from '~/apis';

function formatCurrency(amount) {
    return amount.toLocaleString('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
}

export default function HotPick() {
    const [pitches, setPitches] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const res = await handleGetTop3Pitches();
                console.log(res.data.result);
                setPitches(res.data.result);
            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, []);

    return (
        <div className="text-start d-flex row">
            {pitches.map((pitch) => (
                <Card
                    key={pitch.id}
                    sx={{
                        maxWidth: 400,
                        marginLeft: 2,
                        marginRight: 2,
                        marginTop: 2,
                    }}
                >
                    <Typography component={Link} to={`/chi-tiet-san/${pitch.id}`}>
                        <CardMedia
                            component="img"
                            height="250"
                            image={pitch.image || 'default-image-url.jpg'}
                            alt={pitch.pitchName}
                            sx={{ borderRadius: '5px' }}
                            className="img-fluid"
                        />
                    </Typography>

                    <CardContent>
                        <Typography
                            gutterBottom
                            variant="h5"
                            component={Link}
                            to={`/chi-tiet-san/${pitch.id}`}
                            className="text-decoration-none text-dark"
                        >
                            <span className="fw-bold fs-2">{pitch.pitchName}</span>
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            <span className="fs-6">Loại Sân : {pitch.type}</span>
                            <br />
                            <span className="fs-6">
                                Đánh Giá : {pitch.rate}
                                <StarRateIcon
                                    sx={{
                                        width: 20,
                                        marginBottom: 1,
                                        color: '#FFC107',
                                    }}
                                />
                            </span>
                            <br />
                            <b className="fs-4 fw-bold text-danger">Giá : {formatCurrency(pitch.price)} / Giờ</b>
                            <br />
                            <span className="fs-5 ">
                                Địa Chỉ : {pitch.street + ', ' + pitch.ward + ', ' + pitch.district + ', ' + pitch.city}
                            </span>
                            <br />
                            <span className="fs-6">
                                Căn Tin
                                <StorefrontSharpIcon
                                    sx={{
                                        marginLeft: 1,
                                    }}
                                />
                            </span>
                            <span className="fs-6 float-end">
                                <WifiSharpIcon
                                    sx={{
                                        marginLeft: 1,
                                    }}
                                />
                                Wifi
                            </span>
                        </Typography>
                    </CardContent>
                </Card>
            ))}
        </div>
    );
}
