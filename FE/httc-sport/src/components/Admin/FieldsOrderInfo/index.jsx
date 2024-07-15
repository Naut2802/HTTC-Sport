import { Breadcrumbs, Typography } from '@mui/material';
import { Link } from 'react-router-dom';
import FormFieldsOrder from './FormFieldsOrder';
import TableFieldsOrder from './TableFieldsOrder';

export default function FieldsOrderInfo() {
    return (
        <div className="my-3 container">
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
                <FormFieldsOrder />
            </div>
            <div>
                <TableFieldsOrder />
            </div>
        </div>
    );
}
