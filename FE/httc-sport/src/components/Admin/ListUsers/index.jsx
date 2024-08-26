import { Breadcrumbs, Typography } from '@mui/material';
import { Link } from 'react-router-dom';
import FormUpdateUser from './FormUpdateUser';
import TableUsers from './TableUsers';
import { useRef, useState } from 'react';

export default function ListUsers() {
    const [selectedUser, setSelectedUser] = useState(null);
    const onRefresh = useRef(null);

    const handleClick = (users) => {
        setSelectedUser(users);
    };
    return (
        <div className="container my-3">
            <Typography className="fs-3 fw-bold mt-3 mb-2">Quản Lý Khách Hàng</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/admin/trang-chu">
                    Trang Chủ
                </Typography>
                <Typography
                    className="text-decoration-none text-dark"
                    variant="h6"
                    noWrap
                    component={Link}
                    to="/admin/khach-hang"
                >
                    Danh Sách Khách Hàng
                </Typography>
            </Breadcrumbs>
            <hr />

            <FormUpdateUser selectedUser={selectedUser} onRefresh={onRefresh} />

            <TableUsers dataUserTable={handleClick} onRefresh={onRefresh}/>
        </div>
    );
}
