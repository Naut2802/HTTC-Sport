import { Breadcrumbs, Typography } from '@mui/material';
import { Link } from 'react-router-dom';
import CardInfoHome from './CardInfoHome';
import DashboardHome from './DashboardHome';
import TableListUserHome from './TableListUserHome';

export default function AdminHome() {
    return (
        <div className="my-3 container">
            <Typography className="fs-3 fw-bold mt-3 mb-2">Trang Chủ Admin</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/admin/trang-chu">
                    Trang Chủ
                </Typography>
            </Breadcrumbs>
            <hr />

            <CardInfoHome />

            <DashboardHome />

            <TableListUserHome />
        </div>
    );
}
