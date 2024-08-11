import { toast } from 'react-toastify';
import { DataGrid } from '@mui/x-data-grid';
import { useEffect, useState } from 'react';
import { Button, Tooltip } from '@mui/material';
import CreateIcon from '@mui/icons-material/Create';
import DeleteIcon from '@mui/icons-material/Delete';
import { handleGetUserAdmin, handleGetUsersAdmin, handleDeleteUserAdmin, handleActiveUserAdmin } from '~/apis';

export default function TableUsers({ dataUserTable }) {
    const [users, setUsers] = useState([]);
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await handleGetUserAdmin();
                // console.log(response.data.result);
                const dataUser = response.data.result;
                const filteredUsers = dataUser.filter((user) => !user.roles.some((role) => role.roleName === 'ADMIN'));
                setUsers(filteredUsers);
            } catch (error) {
                console.error('Error fetching user data:', error);
            }
        };
        fetchData();
    }, []);

    const handleEditUser = async (users) => {
        const userId = users.id;
        try {
            const re = await handleGetUsersAdmin(userId);
            const dataUser = re.data.result;
            dataUserTable(dataUser);
        } catch (error) {
            toast.error('Lỗi');
            console.log(error);
        }
    };

    const handleDeleteUser = async (user) => {
        const userId = user.id;
        try {
            const re = await handleDeleteUserAdmin(userId);
            console.log(re.data.result);
            toast.success('Xóa thành công');
        } catch (error) {
            toast.error('Xóa không thành công');
            console.log(error);
        }
    };

    const handleActiveUser = async (user) => {
        const userId = user.id;
        const data = user.row.isEnable;
        console.log(userId);
        try {
            const response = await handleActiveUserAdmin(userId, data);
            if (response && response.status === 200) {
                const dataUser = response.data.result;
                console.log(dataUser);
                toast.success('Kích hoạt tài khoản thành công');
            }
        } catch (error) {
            if (error.response && error.response.status) {
                console.log('Error Response Data:', error.response.data);
            } else {
                console.log('Error Message:', error.message);
            }
            toast.error('Kích hoạt tài khoản không thành công');
        }
    };

    const columns = [
        { field: 'email', headerName: 'Email', width: 280 },
        { field: 'username', headerName: 'Tên Tài Khoản', width: 150 },
        { field: 'lastName', headerName: 'Họ', width: 150 },
        { field: 'firstName', headerName: 'Tên', width: 100 },
        { field: 'phoneNumber', headerName: 'Số Điện Thoại', width: 180 },
        {
            field: 'isEnabled',
            headerName: 'Trạng Thái',
            width: 200,
            renderCell: (users) => (users.value ? 'Hoạt Động' : 'Không Hoạt Động'),
        },
        {
            field: 'orther',
            headerName: 'Khác',
            sortable: false,
            headerAlign: 'center',
            width: 150,
            renderCell: (users) => (
                <div>
                    {users.row.isEnabled ? (
                        <>
                            <Tooltip title="Chỉnh Sửa" variant="solid">
                                <Button sx={{ color: 'green' }} onClick={() => handleEditUser(users)}>
                                    <CreateIcon />
                                </Button>
                            </Tooltip>
                            <Tooltip title="Xóa" variant="solid">
                                <Button sx={{ color: 'red' }} onClick={() => handleDeleteUser(users)}>
                                    <DeleteIcon />
                                </Button>
                            </Tooltip>
                        </>
                    ) : (
                        <Tooltip title="Kích Hoạt" variant="solid">
                            <Button sx={{ color: 'red' }} onClick={() => handleActiveUser(users)}>
                                Kích hoạt
                            </Button>
                        </Tooltip>
                    )}
                </div>
            ),
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
