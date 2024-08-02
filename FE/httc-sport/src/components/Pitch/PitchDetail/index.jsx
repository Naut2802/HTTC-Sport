import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import DirectionsCarIcon from '@mui/icons-material/DirectionsCar';
import FoodBankIcon from '@mui/icons-material/FoodBank';
import LocalCafeIcon from '@mui/icons-material/LocalCafe';
import LocationOnIcon from '@mui/icons-material/LocationOn';
import LunchDiningIcon from '@mui/icons-material/LunchDining';
import PlaylistAddCheckIcon from '@mui/icons-material/PlaylistAddCheck';
import RssFeedIcon from '@mui/icons-material/RssFeed';
import StarRoundedIcon from '@mui/icons-material/StarRounded';
import TwoWheelerIcon from '@mui/icons-material/TwoWheeler';

import { Box, Breadcrumbs, Card, CardContent, CircularProgress, Grid, Typography } from '@mui/material';
import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';

import { handleGetPitch } from '~/apis';
import logo from '~/components/Images/logo.png';
import Comments from './Comments';
import ImagePitch from './ImagePitch';
import RentForm from './RentForm';

function formatCurrency(amount) {
    return amount.toLocaleString('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
}

export default function PitchDetail() {
    const { id } = useParams();
    const [pitch, setPitch] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const res = await handleGetPitch(id);
                setPitch(res.data.result);
            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, [id]);

    if (!pitch) {
        return (
            <Box
                sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    gap: '30px',
                    justifyContent: 'center',
                    alignItems: 'center',
                    height: '100vh',
                }}
            >
                <CircularProgress></CircularProgress>
                <Typography>Loading...</Typography>
            </Box>
        );
    }

    return (
        <Box sx={{ my: 3, mx: 10 }}>
            <div className="d-flex justify-content-center">
                <img src={logo} alt="" style={{ width: 120 }} />
            </div>
            <Typography className="fs-3 fw-bolder">Chi Tiết Sân</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="mb-2">
                <Typography className="text-decoration-none text-secondary fs-6" variant="h6" noWrap component={Link} to="/">
                    Trang Chủ
                </Typography>
                <Typography
                    className="text-decoration-none text-secondary fs-6"
                    variant="h6"
                    noWrap
                    component={Link}
                    to="/san-bong"
                >
                    Danh Sách Sân
                </Typography>
                <Typography className="text-decoration-none text-dark fs-6" variant="h6" noWrap component="span">
                    Chi Tiết Sân
                </Typography>
            </Breadcrumbs>
            <Box sx={{ p: 2, backgroundColor: 'whitesmoke' }}>
                <div className="pb-4">
                    <Typography className="text-dark fs-2 fw-bolder" variant="inherit" noWrap>
                        {pitch.pitchName}
                    </Typography>
                    <div className="row">
                        <div className="col-md-6">
                            <Typography sx={{ fontSize: 17 }} variant="subtitle2" color="text.secondary">
                                <LocationOnIcon sx={{ color: 'red' }} />
                                {pitch.street} {pitch.ward} {pitch.district} {pitch.city}
                            </Typography>
                        </div>
                        <div className="col-md-6 text-end">
                            <Typography sx={{ fontSize: 17 }} variant="subtitle2" color="text.secondary">
                                Đánh Giá: 4/5
                                {/* {pitch.reviews.rate} */}
                                <StarRoundedIcon sx={{ mb: 1, color: '#FFC107' }} />
                            </Typography>
                        </div>
                    </div>
                </div>

                <div className="row">
                    <div className="col-md-12 col-12">
                        <div className="row">
                            <div className="col-lg-8">
                                <ImagePitch images={pitch.images} />
                            </div>
                            <div className="col-lg-4">
                                <div className="row">
                                    <div className="col-md-12 col-12">
                                        <Card>
                                            <CardContent>
                                                <Typography variant="subtitle2" color="text.dark">
                                                    <ArrowDropDownIcon fontSize="large" sx={{ color: 'orangered', mb: 1 }} />
                                                    <span className="fw-bolder fs-4">Thông tin sân</span>
                                                </Typography>

                                                <div className="row my-1">
                                                    <div className="col-6">
                                                        <Typography sx={{ fontSize: 17, ml: 2 }} variant="caption">
                                                            Giờ mở cửa:
                                                        </Typography>
                                                    </div>
                                                    <div className="col-6">
                                                        <Typography className="fw-bolder text-end">8h - 23h</Typography>
                                                    </div>
                                                </div>

                                                <div className="row my-1">
                                                    <div className="col-6">
                                                        <Typography sx={{ fontSize: 17, ml: 2 }} variant="caption">
                                                            Tổng số sân:
                                                        </Typography>
                                                    </div>
                                                    <div className="col-6">
                                                        <Typography className="fw-bolder text-end">
                                                            {pitch.total} (Sân 5)
                                                        </Typography>
                                                    </div>
                                                </div>

                                                <div className="row my-1">
                                                    <div className="col-6">
                                                        <Typography sx={{ fontSize: 17, ml: 2 }} variant="caption">
                                                            Giá Sân:
                                                        </Typography>
                                                    </div>
                                                    <div className="col-6">
                                                        <Typography className="fw-bolder text-end">
                                                            {formatCurrency(pitch.price)}
                                                        </Typography>
                                                    </div>
                                                </div>

                                                <div className="row my-1">
                                                    <div className="col-6">
                                                        <Typography sx={{ fontSize: 17, ml: 2 }} variant="caption">
                                                            Mô tả:
                                                        </Typography>
                                                    </div>
                                                    <div className="col-6">
                                                        <Typography className="text-end">{pitch.description}</Typography>
                                                    </div>
                                                </div>

                                                <Card sx={{ mt: 3, backgroundColor: 'whitesmoke' }}>
                                                    <CardContent sx={{ p: 1 }}>
                                                        <Typography variant="subtitle2" color="text.dark">
                                                            <PlaylistAddCheckIcon sx={{ color: 'teal' }} />
                                                            <span className="fw-bolder fs-6">Dịch vụ tiện ích</span>
                                                        </Typography>
                                                        <div className="row p-1">
                                                            <div className="col-lg-6">
                                                                <Typography variant="subtitle1" color="text.dark">
                                                                    <RssFeedIcon fontSize="small" />
                                                                    <span className="fs-6"> Wifi</span>
                                                                </Typography>
                                                                <Typography variant="subtitle1" color="text.dark">
                                                                    <LunchDiningIcon fontSize="small" />
                                                                    <span className="fs-6"> Đồ ăn</span>
                                                                </Typography>
                                                                <Typography variant="subtitle1" color="text.dark">
                                                                    <TwoWheelerIcon fontSize="small" />
                                                                    <span className="fs-6"> Bãi đỗ xe máy</span>
                                                                </Typography>
                                                            </div>
                                                            <div className="col-lg-6">
                                                                <Typography variant="subtitle1" color="text.dark">
                                                                    <FoodBankIcon fontSize="small" />
                                                                    <span className="fs-6"> Căn tin</span>
                                                                </Typography>
                                                                <Typography variant="subtitle1" color="text.dark">
                                                                    <LocalCafeIcon fontSize="small" />
                                                                    <span className="fs-6"> Nước uống</span>
                                                                </Typography>
                                                                <Typography variant="subtitle1" color="text.dark">
                                                                    <DirectionsCarIcon fontSize="small" />
                                                                    <span className="fs-6"> Bãi đỗ xe oto</span>
                                                                </Typography>
                                                            </div>
                                                        </div>
                                                    </CardContent>
                                                </Card>
                                            </CardContent>
                                        </Card>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <Grid sx={{ mt: 2 }} container spacing={2}>
                    <Grid item xs={4}>
                        <RentForm id={pitch.id} />
                    </Grid>
                    <Grid item xs={8}>
                        {/* List Time Per Days */}
                        <Comments />
                    </Grid>
                </Grid>
            </Box>
        </Box>
    );
}
