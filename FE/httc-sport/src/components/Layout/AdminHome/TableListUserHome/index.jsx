import { TablePagination } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { useEffect, useState } from 'react';
import { handleGetUserAdmin } from '~/apis';

export default function TableListUserHome() {
    const [users, setUsers] = useState([]);
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(5);
    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    const fetchData = async (page, size) => {
        try {
            const response = await handleGetUserAdmin(page, size);
            // console.log(response.data.result);
            const dataUser = response.data.result;
            const filteredUsers = dataUser.filter(
                (user) => !user.roles.some((role) => role.roleName === 'ADMIN' || user.isEnabled === false),
            );
            setUsers(filteredUsers);
        } catch (error) {
            console.error('Error fetching user data:', error);
        }
    };

    useEffect(() => {
        fetchData(page, rowsPerPage);
    }, [page, rowsPerPage]);

    const columns = [
        { field: 'email', headerName: 'Email', width: 220 },
        { field: 'username', headerName: 'Tên Tài Khoản', width: 220 },
        { field: 'lastName', headerName: 'Họ', width: 140 },
        { field: 'firstName', headerName: 'Tên', width: 140 },
        {
            field: 'phoneNumber',
            headerName: 'Số Điện Thoại',
            width: 200,
            renderCell: (users) => (users.value ? users.phoneNumber : 'Chưa có thông tin'),
        },
        {
            field: 'isEnabled',
            headerName: 'Trạng Thái',
            width: 200,
            renderCell: (users) => (users.value ? 'Hoạt Động' : 'Không Hoạt Động'),
        },
        {
            field: 'vip',
            headerName: 'VIP',
            sortable: false,
            width: 150,
            renderCell: (users) => {
                if (users.value === 'VIP_0') {
                    return 'Vip 0';
                } else if (users.value === 'VIP_1') {
                    return 'Vip 1';
                } else if (users.value === 'VIP_2') {
                    return 'Vip 2';
                } else {
                    return 'Vip 3';
                }
            },
        },
    ];

    return (
        <div style={{ height: '370px', width: '100%', marginTop: '40px' }}>
            <DataGrid rows={users} columns={columns} getRowId={(row) => row.id} hideFooterPagination={true} />
            <TablePagination
                component="div"
                sx={{ border: 1, borderColor: 'divider' }}
                rowsPerPageOptions={[5, 10, 25]}
                count={100}
                page={page}
                onPageChange={handleChangePage}
                rowsPerPage={rowsPerPage}
                onRowsPerPageChange={handleChangeRowsPerPage}
            />
        </div>
    );
}
