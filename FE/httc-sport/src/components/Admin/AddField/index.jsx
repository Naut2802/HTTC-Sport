import { Breadcrumbs, Typography } from '@mui/material';
import { Link } from 'react-router-dom';
import FormAddField from './FormAddField';
import TableListField from './TableListField';

export default function AddField() {
    return (
        <div className="my-3 container">
            <Typography className="fs-3 fw-bold mt-3 mb-2">Thêm Sân</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/admin/trang-chu">
                    Trang Chủ
                </Typography>
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/admin/them-san">
                    Thêm Sân
                </Typography>
            </Breadcrumbs>
            <hr />

            <FormAddField />

            <TableListField />
        </div>
    );
}
