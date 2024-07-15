import { Button, Tooltip } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import * as React from 'react';

import CreateIcon from '@mui/icons-material/Create';

export default function TableListField() {
    const columns = [
        { field: 'id', headerName: 'Mã Sân', width: 100 },
        { field: 'loaiSan', headerName: 'Loại Sân', width: 100 },
        { field: 'tenSan', headerName: 'Tên Sân', width: 250 },
        {
            field: 'gia',
            headerName: 'Giá',
            width: 160,
        },
        {
            field: 'mota',
            headerName: 'Mô Tả',
            width: 350,
        },
        {
            field: 'trangThai',
            headerName: 'Trạng Thái',
            sortable: false,
            width: 160,
        },
        {
            field: 'orther',
            headerName: 'Khác',
            sortable: false,
            width: 160,
            renderCell: () => (
                <Tooltip title="Chỉnh Sửa" variant="solid">
                    <Button sx={{ color: 'green' }}>
                        <CreateIcon />
                    </Button>
                </Tooltip>
            ),
        },
    ];

    const rows = [
        {
            id: '1',
            loaiSan: '5',
            tenSan: 'Sân Min Hòn',
            gia: '490.000',
            moTa: '',
            trangThai: 'Hoạt Động',
        },
        {
            id: '2',
            loaiSan: '7',
            tenSan: 'Sân Min Hòn',
            gia: '490.000',
            moTa: '',
            trangThai: 'Không Hoạt Động',
        },
        {
            id: '3',
            loaiSan: '5',
            tenSan: 'Sân Min Hòn',
            gia: '490.000',
            moTa: '',
            trangThai: 'Hoạt Động',
        },
    ];

    return (
        <div style={{ width: '100%' }}>
            <DataGrid key={rows.id} rows={rows} columns={columns} pageSizeOptions={[5, 10, 20, 50, 100]} />
        </div>
    );
}
