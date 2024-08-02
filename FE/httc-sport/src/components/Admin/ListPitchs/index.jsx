import React, { useEffect, useState } from 'react';
import { Breadcrumbs, Button, Tooltip, Typography } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import DeleteIcon from '@mui/icons-material/Delete';
import CreateIcon from '@mui/icons-material/Create';
import { handleGetPitches, handleDeletePitch } from '~/apis';
import { toast } from 'react-toastify';

import { Link } from 'react-router-dom';

export default function ListPitchs({ onRowClick }) {
    const [pitch, setPitch] = useState([]);

    //Dữ liệu call Api
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

    //Xóa sân
    const handleDelClick = async (row) => {
        const pitchId = row.id;
        if (pitchId === undefined) {
            toast.error('Không thấy ID');
            return;
        }
        try {
            const response = await handleDeletePitch(pitchId);
            console.log('API response:', response);

            if (response && response.data) {
                toast.success(response.data.message);
                setPitch((prevRows) => prevRows.filter((item) => item.id !== pitchId));
            } else {
                toast.error('Xóa sân thất bại!');
            }
        } catch (error) {
            console.error('Failed to delete pitch:', error);
            toast.error('Xóa sân thất bại!');
        }
    };

    //truyền dữ liệu vào column qua field = name
    const columns = [
        { field: 'pitchName', headerName: 'Tên Sân', width: 150 },
        {
            field: 'images',
            headerName: 'Hình Ảnh',
            width: 220,
            renderCell: (params) => {
                if (params.value && params.value.length > 0) {
                    return (
                        <div
                            style={{
                                height: '100%',
                                display: 'flex',
                                alignItems: 'center',
                                justifyContent: 'center',
                                margin: '10px',
                            }}
                        >
                            <img
                                src={params.value[0].url}
                                alt="Hình Ảnh"
                                style={{ height: '190px', width: '190px', objectFit: 'contain' }}
                            />
                        </div>
                    );
                } else {
                    return <span>No Image</span>;
                }
            },
        },
        { field: 'price', headerName: 'Giá', width: 120 },
        { field: 'description', headerName: 'Mô Tả', width: 180 },
        { field: 'address', headerName: 'Địa Chỉ', width: 460 },
        {
            field: 'other',
            headerName: 'Khác',
            sortable: false,
            width: 150,
            renderCell: (params) => (
                <>
                    <Tooltip title="Chỉnh Sửa" variant="solid">
                        <Button onClick={() => onRowClick(handleEditClick(params.row))} sx={{ color: 'green' }}>
                            <CreateIcon />
                        </Button>
                    </Tooltip>
                    <Tooltip title="Xóa" variant="solid">
                        <Button sx={{ color: 'red' }}>
                            <DeleteIcon onClick={() => handleDelClick(params.row)} />
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
                rowHeight={150}
            />
        </div>
    );
}
