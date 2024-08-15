import { DataGrid } from '@mui/x-data-grid';
import { useEffect, useState } from 'react';
import { handleGetUserAdmin } from '~/apis';

export default function TableListUserHome() {
    const [users, setUsers] = useState([]);
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await handleGetUserAdmin();
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
        fetchData();
    }, []);

    const columns = [
        { field: 'email', headerName: 'Email', width: 280 },
        { field: 'username', headerName: 'Tên Tài Khoản', width: 150 },
        { field: 'lastName', headerName: 'Họ', width: 180 },
        { field: 'firstName', headerName: 'Tên', width: 150 },
        { field: 'phoneNumber', headerName: 'Số Điện Thoại', width: 150 },
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
        <div className="w-100 my-2">
            <DataGrid
                rows={users}
                columns={columns}
                pageSize={5}
                rowsPerPageOptions={[5, 10, 20, 50, 100]}
                getRowId={(row) => row.id}
            />
        </div>
    );
}
