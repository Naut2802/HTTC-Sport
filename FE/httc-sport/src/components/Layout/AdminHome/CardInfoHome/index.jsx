import BallotIcon from '@mui/icons-material/Ballot';
import SupervisedUserCircleIcon from '@mui/icons-material/SupervisedUserCircle';
import { Box, Button, Typography } from '@mui/material';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { handleAnalytics } from '~/apis';
export default function CardInfoHome() {
    const [count, setCount] = useState();
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await handleAnalytics();
                const total = response.data.result;
                console.log(total);
                setCount(total);
            } catch (error) {
                console.error('Lỗi đỗ dữ liệu: ', error);
            }
        };
        fetchData();
    }, []);
    return (
        <div className="row">
            <div className="col-1"></div>
            <div className="col-5">
                <Box className="card">
                    <Typography component="div" className="card-header bg-primary text-white">
                        <SupervisedUserCircleIcon className="my-2 mx-1" />
                        Khách Hàng
                    </Typography>
                    <Typography component="div" className="card-body">
                        Danh Sách Khách Hàng <br />
                        Tổng Khách Hàng: {count?.totalUser}
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
            <div className="col-5">
                <Box className="card">
                    <Typography component="div" className="card-header bg-success text-white">
                        <BallotIcon className="my-2 mx-1" />
                        Sân
                    </Typography>
                    <Typography component="div" className="card-body">
                        Danh Sách Sân <br />
                        Tổng Sân: {count?.totalPitches}
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
        </div>
    );
}
