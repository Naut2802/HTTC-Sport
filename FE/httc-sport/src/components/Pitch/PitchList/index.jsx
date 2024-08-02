import StorefrontSharpIcon from '@mui/icons-material/StorefrontSharp';
import WifiSharpIcon from '@mui/icons-material/WifiSharp';
import { Box, Breadcrumbs, Card, CardContent, CardMedia, Typography } from '@mui/material';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { handleGetPitches } from '~/apis';

import logo from '~/components/Images/logo.png';
import SortGauge from './SortGauge';
import SortRating from './SortRating';

function formatCurrency(amount) {
    return amount.toLocaleString('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
}

export default function PitchList() {
    const [pitches, setPitches] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const res = await handleGetPitches();
                setPitches(res.data.result);
            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, []);

    return (
        <div className="my-3 container">
            <div className="d-flex justify-content-center">
                <img src={logo} alt="" style={{ width: 120 }} />
            </div>
            <Typography className="fs-3 fw-bold mt-3 mb-2">Danh Sách Sân</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/trang-chu">
                    Trang Chủ
                </Typography>
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/san-bong">
                    Danh Sách Sân
                </Typography>
            </Breadcrumbs>
            <div className="row">
                <div className="col-3 mt-2">
                    <SortRating />
                    <SortGauge />
                </div>
                <div className="col-9 mt-2">
                    {pitches.map((pitch) => (
                        <Card key={pitch.id} sx={{ display: 'flex', maxWidth: 'auto', mb: 2 }}>
                            <Typography
                                className="text-decoration-none text-dark fs-3 fw-bold"
                                variant="h6"
                                noWrap
                                component={Link}
                                to={`/chi-tiet-san/${pitch.id}`}
                            >
                                <CardMedia
                                    component="img"
                                    sx={{ width: 260 }}
                                    image={pitch.images[0].url} // Nếu API trả về đường dẫn ảnh
                                    alt={pitch.pitchName}
                                    className="img-fluid"
                                />
                            </Typography>
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
                                        <strong>Giá Sân: </strong>
                                        <span className="text-danger"> {formatCurrency(pitch.price)} </span>
                                    </Typography>

                                    <Typography sx={{ fontSize: 18 }} variant="subtitle2" color="text.secondary">
                                        <strong>Địa Chỉ: </strong>
                                        <span className="text-dark">
                                            {pitch.street + ' ' + pitch.ward + ' ' + pitch.district + ' ' + pitch.city}
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
            </div>
        </div>
    );
}
