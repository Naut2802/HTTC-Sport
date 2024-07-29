import CreateIcon from '@mui/icons-material/Create';
import DeleteIcon from '@mui/icons-material/Delete';
import { Breadcrumbs, Button, Tooltip, Typography } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import React, { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import { handleGetPitches } from '~/apis';

import { Link, useNavigate } from 'react-router-dom';

export default function ListFieldAdmin({ onRowClick }) {
    const [pitch, setPitch] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const re = await handleGetPitches();
                const dataWithId = re.data.result.map((item, index) => ({
                    ...item,
                    id: item.id || index,
                    address: `${item.street || ''}, ${item.ward || ''}, ${item.district || ''}, ${item.city || ''}`,
                }));
                setPitch(dataWithId);
            } catch (error) {
                console.error(error);
                toast.error('Failed to fetch data');
            }
        };
        fetchData();
    }, []);

    const handleEditClick = (row) => {
        sessionStorage.setItem('selectedPitch', JSON.stringify(row));
        window.location.href = '/admin/them-san';
    };

    const columns = [
        { field: 'pitchName', headerName: 'Tên Sân', width: 150 },
        { field: 'images', headerName: 'Hình Ảnh', width: 150 },
        { field: 'price', headerName: 'Giá', width: 80 },
        { field: 'description', headerName: 'Mô Tả', width: 150 },
        { field: 'address', headerName: 'Địa Chỉ', width: 570 },
        {
            field: 'other',
            headerName: 'Khác',
            sortable: false,
            width: 100,
            renderCell: (params) => (
                <>
                    <Tooltip title="Chỉnh Sửa" variant="solid">
                        <Button onClick={() => onRowClick(handleEditClick(params.row))} sx={{ color: 'green' }}>
                            <CreateIcon />
                        </Button>
                    </Tooltip>
                    <Tooltip title="Xóa" variant="solid">
                        <Button sx={{ color: 'red' }}>
                            <DeleteIcon />
                        </Button>
                    </Tooltip>
                </>
            ),
        },
    ];

    return (
        <div style={{ width: '100%' }}>
            <Typography className="fs-3 fw-bold mt-3 mb-2">Danh Sách Sân</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/admin/trang-chu">
                    Trang Chủ
                </Typography>
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/admin/san-bong">
                    Danh Sách Sân
                </Typography>
            </Breadcrumbs>
            <DataGrid
                rows={pitch}
                columns={columns}
                pageSize={10}
                rowsPerPageOptions={[5, 10, 20, 50, 100]}
                getRowId={(row) => row.id}
            />
        </div>
    );
}
