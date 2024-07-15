import { Button } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import * as React from 'react';

import ModalDetail from '../ModalDetail';

export default function TableOrderInfo() {
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    const columns = [
        { field: 'id', headerName: 'ID', width: 70 },
        { field: 'tenSan', headerName: 'Tên Sân', width: 130 },
        { field: 'emailUser', headerName: 'Email', width: 250 },
        {
            field: 'sdt',
            headerName: 'Số Điện Thoại',
            width: 160,
        },
        {
            field: 'ngayDa',
            headerName: 'Ngày Đá',
            width: 160,
        },
        {
            field: 'tongTien',
            headerName: 'Tổng Tiền',
            sortable: false,
            width: 160,
        },
        {
            field: 'chiTiet',
            headerName: 'Chi Tiết',
            sortable: false,
            width: 160,
            renderCell: () => (
                <Button onClick={handleOpen} variant="text">
                    Xem Chi Tiết
                </Button>
            ),
        },
    ];

    const rows = [
        {
            id: '1',
            tenSan: 'Sân Min Hòn',
            emailUser: 'leminhhoang241299@gmail.com',
            sdt: '0917024078',
            ngayDa: '2024-01-26',
            tongTien: '440000',
        },
    ];

    return (
        <div style={{ width: '100%' }}>
            <DataGrid rows={rows} columns={columns} pageSizeOptions={[5, 10, 20, 50, 100]} />
            <ModalDetail open={open} handleClose={handleClose} />
        </div>
    );
}
