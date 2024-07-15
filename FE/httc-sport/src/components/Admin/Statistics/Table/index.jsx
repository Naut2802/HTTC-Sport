import { DataGrid } from '@mui/x-data-grid';
import * as React from 'react';

export default function TableStatistics() {
    const columns = [
        { field: 'id', headerName: 'ID', width: 100 },
        { field: 'maHD', headerName: 'Mã Hóa Đơn', width: 100 },
        { field: 'soDT', headerName: 'Số Điện Thoại', width: 150 },
        { field: 'tenSan', headerName: 'Tên Sân', width: 150 },
        { field: 'ngayXuat', headerName: 'Ngày Xuất', width: 200 },
        { field: 'tgBD', headerName: 'Thời Gian Bắt Đầu', width: 200 },
        { field: 'tgKT', headerName: 'Thời Gian Kết Thúc', width: 200 },
        { field: 'tongTien', headerName: 'Tổng Tiền', width: 100 },
    ];

    const rows = [
        {
            id: '1',
            maHD: 'HD01',
            soDT: '0901333123',
            tenSan: 'Sân Quỳnh Như',
            ngayXuat: '11/07/2024',
            tgBD: '20:00',
            tgKT: '21:30',
            tongTien: ' 490.000',
        },
    ];
    return (
        <div style={{ width: '100%' }}>
            <DataGrid key={rows.id} rows={rows} columns={columns} pageSizeOptions={[5, 10, 20, 50, 100]} />
        </div>
    );
}
