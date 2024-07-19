import { Breadcrumbs, Typography } from '@mui/material';
import { Link } from 'react-router-dom';

import logo from '../../Images/logo.png';   
import TableLichSuGD from './Table';

function LichSuGiaoDich() {
    return (
        <div className="my-3 container">
            <div className="d-flex justify-content-center">
                <img src={logo} alt="" style={{ width: 120 }} />
            </div>
            <Typography className="fs-3 fw-bold mt-3 mb-2">Danh Sách Sân</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
                <Typography className="text-decoration-none text-dark fs-6" variant="h6" noWrap component={Link} to="/">
                    Trang Chủ
                </Typography>
                <Typography
                    className="text-decoration-none text-dark fs-6"
                    variant="h6"
                    noWrap
                    component={Link}
                    to="/lich-su-giao-dich"
                >
                    Lịch Sử Giao Dịch
                </Typography>
            </Breadcrumbs>
            <hr />
            <Typography>
                <TableLichSuGD/>
            </Typography>
        </div>
    )
}

export default LichSuGiaoDich