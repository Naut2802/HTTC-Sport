import { Breadcrumbs, Typography } from '@mui/material';
import { Link } from 'react-router-dom';

import DashboardStatistics from './DashboardStatistics';
import TableStatistics from './Table';

export default function Statistics() {
    return (
        <div className="container my-3">
            <Typography className="fs-3 fw-bold mt-3 mb-2">Thống kê</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/admin/trang-chu">
                    Trang Chủ
                </Typography>
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/admin/thong-ke">
                    Thống Kê
                </Typography>
            </Breadcrumbs>
            <hr />
            <div className="container">
                <DashboardStatistics />
            </div>
            <div>
                <TableStatistics />
            </div>
        </div>
    );
}
