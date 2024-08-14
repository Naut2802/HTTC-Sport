import { Breadcrumbs, Typography } from '@mui/material';
import { Link } from 'react-router-dom';

import logo from '~/components/Images/logo.png';
import TableRentInfo from './TableRentInfo';

export default function UserRentInfo() {
    const userId = localStorage.getItem('userId');

    return (
        <div className="my-3 container">
            <div className="d-flex justify-content-center">
                <img src={logo} alt="" style={{ width: 120 }} />
            </div>
            <Typography className="fs-3 fw-bold mt-3 mb-2">Thông tin đặt sân</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
                <Typography className="text-decoration-none text-secondary fs-6" variant="h6" noWrap component={Link} to="/">
                    Trang Chủ
                </Typography>
                <Typography
                    className="text-decoration-none text-dark fs-6"
                    variant="h6"
                    noWrap
                    component={Link}
                    to="/thong-tin-dat-san"
                >
                    Thông Tin Đặt Sân
                </Typography>
            </Breadcrumbs>
            <hr />
            <Typography component={'span'}>
                <TableRentInfo userId={userId} />
            </Typography>
        </div>
    );
}
