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
