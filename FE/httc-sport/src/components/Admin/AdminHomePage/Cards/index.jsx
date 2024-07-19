import { Box, Breadcrumbs, Button, Typography } from '@mui/material';
import SupervisedUserCircleIcon from '@mui/icons-material/SupervisedUserCircle';
import BallotIcon from '@mui/icons-material/Ballot';
import { Link } from 'react-router-dom';
function CardsTrangChu() {
    return (
        <>
            <div className="col-2"></div>
            <div className="col-4">
                <Box className="card">
                    <Typography component="div" className="card-header bg-primary text-white">
                        <SupervisedUserCircleIcon className="my-2 mx-1" />
                        Khách Hàng
                    </Typography>
                    <Typography component="div" className="card-body">
                        Danh Sách Khách Hàng <br />
                        Tổng Khách Hàng:
                    </Typography>
                    <Typography
                        component={Link}
                        to="/admin/khach-hang"
                        className="d-flex w-100 justify-content-end align-items-center my-2 card-footer"
                    >
                        <Button variant="outlined" color="success" className="text-capitalize mx-2">
                            Chi Tiết
                        </Button>
                    </Typography>
                </Box>
            </div>
            <div className="col-4">
                <Box className="card">
                    <Typography component="div" className="card-header bg-success text-white">
                        <BallotIcon className="my-2 mx-1" />
                        Sân
                    </Typography>
                    <Typography component="div" className="card-body">
                        Danh Sách Sân <br />
                        Tổng Sân:
                    </Typography>
                    <Typography
                        component={Link}
                        to="/admin/danh-sach-san"
                        className="d-flex w-100 justify-content-end align-items-center my-2 card-footer"
                    >
                        <Button variant="outlined" color="success" className="text-capitalize mx-2">
                            Chi Tiết
                        </Button>
                    </Typography>
                </Box>
            </div>
        </>
    );
}

export default CardsTrangChu;
