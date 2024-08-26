import { Breadcrumbs, Typography } from '@mui/material';
import { Link } from 'react-router-dom';

import TableTransactions from './TableTransactions';

import logo from '~/components/Images/logo.png';

export default function Transactions() {
    return (
        <div className="container my-3">
            <div className="d-flex justify-content-center">
                <img src={logo} alt="" style={{ width: 120 }} />
            </div>
            <Typography className="fs-3 fw-bold mt-3 mb-2"> Lịch Sử Giao Dịch Ví</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/admin/trang-chu">
                    Trang Chủ
                </Typography>
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/admin/thong-ke">
                    Lịch Sử Giao Dịch Ví
                </Typography>
            </Breadcrumbs>
            <hr />
            <div className="container">
                <TableTransactions />
            </div>
        </div>
    );
}
