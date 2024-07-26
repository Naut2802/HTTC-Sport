import StarRateIcon from '@mui/icons-material/StarRate';
import StorefrontSharpIcon from '@mui/icons-material/StorefrontSharp';
import WifiSharpIcon from '@mui/icons-material/WifiSharp';
import { Card, CardActionArea, CardContent, CardMedia, Typography } from '@mui/material';
import { Link } from 'react-router-dom';

import sanQN_1 from '~/components/Images/sanquynhnhu/anh_san_1_1.png';

export default function HotPick() {
    return (
        <div className="text-start d-flex row">
            <Card
                sx={{
                    maxWidth: 400,
                    marginLeft: 2,
                    marginRight: 2,
                    marginTop: 2,
                }}
            >
                <CardActionArea>
                    <Typography gutterBottom variant="h5" component={Link} to="/san-bong">
                        <CardMedia component="img" height="250" image={sanQN_1} alt="" sx={{ borderRadius: '5px' }} />
                    </Typography>
                    <CardContent>
                        <Typography
                            gutterBottom
                            variant="h5"
                            component={Link}
                            to="/san-bong"
                            className="text-decoration-none text-dark"
                        >
                            <span className="fw-bold fs-2">Sân Quỳnh Như</span>
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            <span className="fs-6">Loại Sân : Sân 5</span>
                            <br />
                            <span className="fs-6">
                                Đánh Giá : 5
                                <StarRateIcon
                                    sx={{
                                        width: 20,
                                        marginBottom: 1,
                                        color: '#FFC107',
                                    }}
                                />
                            </span>
                            <br />
                            <b className="fs-4 fw-bold text-danger">Giá : 200.000 / Giờ</b>
                            <br />
                            <span className="fs-5 ">Địa Chỉ : 206 Vườn Lài, An Phú Đông, Quận 11</span>
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
                </CardActionArea>
            </Card>
            <Card
                sx={{
                    maxWidth: 400,
                    marginLeft: 2,
                    marginRight: 2,
                    marginTop: 2,
                }}
            >
                <CardActionArea>
                    <Typography gutterBottom variant="h5" component={Link} to="/san-bong">
                        <CardMedia component="img" height="250" image={sanQN_1} alt="" sx={{ borderRadius: '5px' }} />
                    </Typography>
                    <CardContent>
                        <Typography
                            gutterBottom
                            variant="h5"
                            component={Link}
                            to="/san-bong"
                            className="text-decoration-none text-dark"
                        >
                            <span className="fw-bold fs-2">Sân Quỳnh Như</span>
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            <span className="fs-6">Loại Sân : Sân 5</span>
                            <br />
                            <span className="fs-6">
                                Đánh Giá : 5
                                <StarRateIcon
                                    sx={{
                                        width: 20,
                                        marginBottom: 1,
                                        color: '#FFC107',
                                    }}
                                />
                            </span>
                            <br />
                            <b className="fs-4 fw-bold text-danger">Giá : 200.000 / Giờ</b>
                            <br />
                            <span className="fs-5 ">Địa Chỉ : 206 Vườn Lài, An Phú Đông, Quận 11</span>
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
                </CardActionArea>
            </Card>
            <Card
                sx={{
                    maxWidth: 400,
                    marginLeft: 2,
                    marginRight: 2,
                    marginTop: 2,
                }}
            >
                <CardActionArea>
                    <Typography gutterBottom variant="h5" component={Link} to="/san-bong">
                        <CardMedia component="img" height="250" image={sanQN_1} alt="" sx={{ borderRadius: '5px' }} />
                    </Typography>
                    <CardContent>
                        <Typography
                            gutterBottom
                            variant="h5"
                            component={Link}
                            to="/san-bong"
                            className="text-decoration-none text-dark"
                        >
                            <span className="fw-bold fs-2">Sân Quỳnh Như</span>
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            <span className="fs-6">Loại Sân : Sân 5</span>
                            <br />
                            <span className="fs-6">
                                Đánh Giá : 5
                                <StarRateIcon
                                    sx={{
                                        width: 20,
                                        marginBottom: 1,
                                        color: '#FFC107',
                                    }}
                                />
                            </span>
                            <br />
                            <b className="fs-4 fw-bold text-danger">Giá : 200.000 / Giờ</b>
                            <br />
                            <span className="fs-5 ">Địa Chỉ : 206 Vườn Lài, An Phú Đông, Quận 11</span>
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
                </CardActionArea>
            </Card>
        </div>
    );
}
