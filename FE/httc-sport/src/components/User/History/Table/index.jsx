import * as React from 'react';
// import XemChiTiet from '../Modal';
import { DataGrid } from '@mui/x-data-grid';
import { Button } from '@mui/material';
import DanhGia from '../Modal';

function TableLichSuGD () {
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    const columns = [
        { field: 'id', headerName: 'Mã Hóa Đơn', width: 100 },
        { field: 'email', headerName: 'Email', width: 250 },
        { field: 'sdt', headerName: 'Số Điện Thoại', width: 120 },
        {
            field: 'ngayThanhToan',
            headerName: 'Ngày Thanh Toán',
            width: 160,
        },
        {
            field: 'tgBD',
            headerName: 'Thời Gian Bắt Đầu',
            width: 160,
        },
        {
            field: 'tgKT',
            headerName: 'Thời Gian Kết Thúc',
            width: 160,
        },
        {
            field: 'tongTien',
            headerName: 'Tổng Tiền',
            sortable: false,
            width: 160,
        },
        {
            field: 'danhGia',
            headerName: 'Đánh Giá',
            sortable: false,
            width: 160,
            renderCell: () => (
                <Button onClick={handleOpen} variant='text'>Đánh Giá</Button>
            ),
        },
    ];

    const rows = [
        {
            id: '1',
            email: 'leminhhoang241299@gmail.com',
            sdt : '0901555123',
            ngayThanhToan: '2024-02-06',
            tgBD: '18:30',
            tgKT: '19:30',
            tongTien: '490.000'
        },
    ];

    return (
        <div style={{ width: '100%' }}>
            <DataGrid 
                rows={rows}
                columns={columns}
                pageSizeOptions={[5, 10, 20, 50, 100]}
            />
            <DanhGia open={open} handleClose={handleClose} />
        </div>
    );
}

export default TableLichSuGD