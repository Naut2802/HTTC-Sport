import * as React from 'react';
import { Breadcrumbs, Button, Tooltip, Typography } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import DeleteIcon from '@mui/icons-material/Delete';
import CreateIcon from '@mui/icons-material/Create';

import hinhanh1 from '~/components/Images/sanquynhnhu/anh_san_1_1.png';
import { Link } from 'react-router-dom';

export default function ListFieldAdmin() {
    const handleClick = () => {
        console.log('abc');
    };
    const columns = [
        { field: 'id', headerName: 'ID', width: 50 },
        { field: 'tenSan', headerName: 'Tên Sân', width: 130 },
        {
            field: 'hinhAnh',
            headerName: 'Hình Sân',
            width: 150,
            renderCell: (params) => (
                <img
                    src={params.value}
                    alt="Hình ảnh"
                    style={{
                        width: '100%',
                        height: 'auto',
                        objectFit: 'cover',
                        marginBottom: 2,
                        marginTop: 2,
                        borderRadius: '5px',
                    }}
                />
            ),
        },
        {
            field: 'loaiSan',
            headerName: 'Loại Sân',
            width: 100,
            align: 'center',
        },
        {
            field: 'gia',
            headerName: 'Giá Sân',
            width: 120,
        },
        {
            field: 'diaChi',
            headerName: 'Địa Chỉ',
            width: 383,
        },
        {
            field: 'trangThai',
            headerName: 'Trạng Thái',
            sortable: false,
            width: 90,
        },
        {
            field: 'khac',
            headerName: 'Khác',
            sortable: false,
            width: 160,
            renderCell: () => (
                <>
                    <Tooltip title="Chỉnh Sửa" variant="solid">
                        <Button onClick={handleClick} sx={{ color: 'green' }}>
                            <CreateIcon />
                        </Button>
                    </Tooltip>
                    <Tooltip title="Xóa" variant="solid">
                        <Button onClick={handleClick} sx={{ color: 'red' }}>
                            <DeleteIcon />
                        </Button>
                    </Tooltip>
                </>
            ),
        },
    ];

    const rows = [
        {
            id: '1',
            tenSan: 'Sân Min Hòn',
            hinhAnh: hinhanh1,
            loaiSan: '5',
            gia: '490.000',
            diaChi: '399/45 Bình Thành  , P. Bình Hưng Hoà B, Quận Bình Tân',
            trangThai: 'Hoạt động',
        },
        {
            id: '2',
            tenSan: 'Sân Min Hòn',
            hinhAnh: hinhanh1,
            loaiSan: '5',
            gia: '490.000',
            diaChi: '399/45 Bình Thành  , P. Bình Hưng Hoà B, Quận Bình Tân',
            trangThai: 'Hoạt động',
        },
        {
            id: '3',
            tenSan: 'Sân Min Hòn',
            hinhAnh: hinhanh1,
            loaiSan: '5',
            gia: '490.000',
            diaChi: '399/45 Bình Thành  , P. Bình Hưng Hoà B, Quận Bình Tân',
            trangThai: 'Hoạt động',
        },
    ];

    return (
        <>
            <Typography className="fs-3 fw-bold mt-3 mb-2">Danh Sách Sân</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/admin/trang-chu">
                    Trang Chủ
                </Typography>
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/admin/san-bong">
                    Danh Sách Sân
                </Typography>
            </Breadcrumbs>
            <div style={{ width: '90%' }}>
                <DataGrid
                    key={rows.id}
                    rows={rows}
                    columns={columns}
                    pageSizeOptions={[5, 10, 20, 50, 100]}
                    getRowHeight={() => 150} // Hoặc sử dụng số cố định, ví dụ: 120
                    sx={{
                        '& .MuiDataGrid-row': {
                            alignItems: 'center',
                        },
                    }}
                />
            </div>
        </>
    );
}
