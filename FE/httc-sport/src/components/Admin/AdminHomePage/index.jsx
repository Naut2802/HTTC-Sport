import { Breadcrumbs, Typography } from '@mui/material';
import { Link } from 'react-router-dom';
import CardsTrangChu from './Cards';
import ThongKe from './Dashboard';
import TableUser from './Table';

function TrangChu() {
    return (
        <div className="my-3 container">
            <Typography className="fs-3 fw-bold mt-3 mb-2">Thêm Sân</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/admin/trang-chu">
                    Trang Chủ
                </Typography>
            </Breadcrumbs>
            <hr />
            <div className="row">
                <CardsTrangChu />
            </div>
            <ThongKe />
            <div>
                <TableUser />
            </div>
        </div>
    );
}

export default TrangChu;
