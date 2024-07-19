import { Breadcrumbs, Typography } from '@mui/material';
import logo from '../../Images/logo.png';
import { Link } from 'react-router-dom';
import FormSetYard from './FormSetYard';

function DatSan() {
    return (
        <div className="my-3 container">
            <div className="d-flex justify-content-center">
                <img src={logo} alt="" style={{ width: 120 }} />
            </div>
            <Typography className="fs-3 fw-bold mt-3 mb-2">Đặt Sân</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/trang-chu">
                    Trang Chủ
                </Typography>
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/san">
                    Sân
                </Typography>
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/dat-san">
                    Đặt Sân
                </Typography>
            </Breadcrumbs>
            <hr />
            <FormSetYard />
        </div>
    );
}

export default DatSan;
