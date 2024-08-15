import { Breadcrumbs, Typography } from '@mui/material';
import { Link } from 'react-router-dom';

import FormRentInfo from './FormRentInfo';
import TableRentInfo from './TableRentInfo';

export default function RentInfo() {
    return (
        <div className="container my-3 ">
            <Typography className="fs-3 fw-bold mt-3 mb-2">Thông Tin Sân</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/admin/trang-chu">
                    Trang Chủ
                </Typography>
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/admin/them-san">
                    Thông Tin Sân
                </Typography>
            </Breadcrumbs>
            <hr />
            <div className="my-2">
                <FormRentInfo />
            </div>
            <div>
                <TableRentInfo />
            </div>
        </div>
    );
}
