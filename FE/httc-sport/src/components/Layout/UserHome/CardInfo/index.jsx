import { Button, Card, CardMedia, Typography } from '@mui/material';
import { Link } from 'react-router-dom';

import lienhe from '~/components/Images/lienhe.jpg';
import sanbong from '~/components/Images/sanbong.jpg';
import tintuc from '~/components/Images/tintuc.jpg';

export default function CardInfo() {
    return (
        <>
            <div className="row">
                <div className="col-1"></div>
                <div className="col-10 ">
                    <Card className="card" sx={{ maxWidth: 'auto', marginLeft: 2, marginRight: 2 }}>
                        <CardMedia component="img" className="img-fluid" image={sanbong} alt="Image title" />
                        <div className="card-img-overlay">
                            <Typography variant="h5" component={Link} to="a" sx={{ textDecoration: 'none', color: 'black' }}>
                                <h1>Sân Bóng</h1>
                            </Typography>
                            <Button size="small" color="inherit" variant="contained">
                                Chi Tiết
                            </Button>
                        </div>
                    </Card>
                </div>
                <div className="col-1"></div>
            </div>
            <div className="row my-2">
                <div className="col-6 ">
                    <Card className="card" sx={{ maxWidth: 'auto', marginLeft: 2, marginRight: 2 }}>
                        <CardMedia component="img" className="img-fluid" image={tintuc} alt="Image title" />
                        <div className="card-img-overlay">
                            <Typography
                                variant="h5"
                                component={Link}
                                to="/tin-tuc"
                                sx={{ textDecoration: 'none', color: 'white' }}
                            >
                                <h1>Tin Tức</h1>
                            </Typography>
                            <Button component={Link} to="/tin-tuc" size="small" color="inherit" variant="contained">
                                Chi Tiết
                            </Button>
                        </div>
                    </Card>
                </div>
                <div className="col-6">
                    <Card className="card" sx={{ maxWidth: 'auto', marginLeft: 2, marginRight: 2 }}>
                        <CardMedia component="img" className="img-fluid" image={lienhe} alt="Image title" />
                        <div className="card-img-overlay">
                            <Typography variant="h5" component={Link} to="a" sx={{ textDecoration: 'none', color: 'white' }}>
                                <h1>Liên Hệ</h1>
                            </Typography>
                            <Button size="small" color="inherit" variant="contained">
                                Chi Tiết
                            </Button>
                        </div>
                    </Card>
                </div>
            </div>
        </>
    );
}
