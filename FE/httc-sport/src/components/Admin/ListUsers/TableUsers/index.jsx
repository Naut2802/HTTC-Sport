import { Button, Tooltip } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import * as React from 'react';

import CreateIcon from '@mui/icons-material/Create';
import DeleteIcon from '@mui/icons-material/Delete';

export default function TableUsers() {
    const columns = [
        { field: 'id', headerName: 'ID', width: 100 },
        { field: 'email', headerName: 'Email', width: 250 },
        { field: 'tenTK', headerName: 'Tên TK', width: 200 },
        {
            field: 'ho',
            headerName: 'Họ',
            width: 100,
        },
        {
            field: 'ten',
            headerName: 'Tên',
            width: 100,
        },
        {
            field: 'sdt',
            headerName: 'Số Điện Thoại',
            width: 150,
        },
        {
            field: 'vip',
            headerName: 'VIP',
            width: 100,
        },
        {
            field: 'trangThai',
            headerName: 'Trạng Thái',
            sortable: false,
            width: 120,
        },

        {
            field: 'orther',
            headerName: 'Khác',
            sortable: false,
            width: 150,
            renderCell: () => (
                <div>
                    <Tooltip title="Chỉnh Sửa" variant="solid">
                        <Button sx={{ color: 'green' }}>
                            <CreateIcon />
                        </Button>
                    </Tooltip>
                    <Tooltip title="Chỉnh Sửa" variant="solid">
                        <Button sx={{ color: 'red' }}>
                            <DeleteIcon />
                        </Button>
                    </Tooltip>
                </div>
            ),
        },
    ];

    const rows = [
        {
            id: '1',
            email: 'leminhhoang241299@gmail.com',
            tenTK: 'hoanglmn',
            ho: 'Lê',
            ten: 'Hoàng',
            sdt: '0901333123',
            vip: '1',
            trangThai: 'Hoạt Động',
        },
        {
            id: '2',
            email: 'tuandc@gmail.com',
            tenTK: 'tuandc',
            ho: 'Đỗ',
            ten: 'Tuấn',
            sdt: '0901333123',
            vip: '2',
            trangThai: 'Hoạt Động',
        },
    ];
    return (
        <div className="w-100 my-2">
            <DataGrid key={rows.id} rows={rows} columns={columns} pageSizeOptions={[5, 10, 20, 50, 100]} />
        </div>
    );
}
// import { Button, Tooltip } from '@mui/material';
// import { DataGrid } from '@mui/x-data-grid';
// import { useEffect, useState } from 'react';
// import CreateIcon from '@mui/icons-material/Create';
// import DeleteIcon from '@mui/icons-material/Delete';
// import { handleGetMyInfoAPI } from '~/apis';

// export default function TableUsers() {
//     const [users, setUsers] = useState([]);

//     useEffect(() => {
//         const fetchData = async () => {
//             try {
//                 const response = await handleGetMyInfoAPI();
//                 const dataWithId = response.data.result.map((item, index) => ({
//                     ...item,
//                     id: item.id || index,
//                 }));
//                 setUsers(dataWithId);
//                 console.log(dataWithId);

//                 if (Array.isArray(dataWithId)) {
//                     setUsers(dataWithId);
//                 } else {
//                     console.error('Dữ liệu trả về không phải là mảng:', dataWithId);
//                 }
//             } catch (error) {
//                 console.error('Error fetching user data:', error);
//             }
//         };
//         fetchData();
//     }, []);

//     const columns = [
//         { field: 'id', headerName: 'ID', width: 200 },
//         { field: 'email', headerName: 'Email', width: 250 },
//         { field: 'username', headerName: 'Tên TK', width: 200 },
//         { field: 'lastName', headerName: 'Họ', width: 100 },
//         { field: 'firstName', headerName: 'Tên', width: 100 },
//         { field: 'phoneNumber', headerName: 'Số Điện Thoại', width: 150 },
//         {
//             field: 'isEnabled',
//             headerName: 'Trạng Thái',
//             width: 120,
//             renderCell: (params) => (params.value ? 'Hoạt Động' : 'Không Hoạt Động'),
//         },
//         {
//             field: 'orther',
//             headerName: 'Khác',
//             sortable: false,
//             width: 150,
//             renderCell: () => (
//                 <div>
//                     <Tooltip title="Chỉnh Sửa" variant="solid">
//                         <Button sx={{ color: 'green' }}>
//                             <CreateIcon />
//                         </Button>
//                     </Tooltip>
//                     <Tooltip title="Xóa" variant="solid">
//                         <Button sx={{ color: 'red' }}>
//                             <DeleteIcon />
//                         </Button>
//                     </Tooltip>
//                 </div>
//             ),
//         },
//     ];

//     return (
//         <div className="w-100 my-2">
//             <DataGrid
//                 rows={users}
//                 columns={columns}
//                 pageSize={5}
//                 rowsPerPageOptions={[5, 10, 20, 50, 100]}
//                 getRowId={(row) => row.id}
//             />
//         </div>
//     );
// }
