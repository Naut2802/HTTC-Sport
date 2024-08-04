import CreateIcon from '@mui/icons-material/Create';
import { Button, Tooltip } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import React, { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import { handleGetPitchesAdmin, handleGetPitchAdmin } from '~/apis';

export default function TableListPitch({ onRowClick }) {
    const [pitch, setPitch] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await handleGetPitchesAdmin();
                // Lọc dữ liệu giữ các sân có trạng thái === true
                const dataWithId = response.data.result
                    .filter((item) => item.isEnabled)
                    .map((item, index) => ({
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

    const handleEditPitch = async (params) => {
        try {
            const pitchId = params.row.id; // Lấy ID từ params
            const response = await handleGetPitchAdmin(pitchId);
            const Data = response.data.result;
            console.log(Data);
            // Gọi onRowClick với dữ liệu trả về từ API
            onRowClick(Data);
        } catch (error) {
            console.error('Error fetching pitch:', error);
            toast.error('Failed to fetch pitch details');
        }
    };

    const columns = [
        { field: 'pitchName', headerName: 'Tên Sân', width: 150 },
        { field: 'price', headerName: 'Giá', width: 80 },
        { field: 'description', headerName: 'Mô Tả', width: 250 },
        { field: 'address', headerName: 'Địa Chỉ', width: 670 },
        {
            field: 'other',
            headerName: 'Khác',
            sortable: false,
            width: 100,
            renderCell: (params) => (
                <Tooltip title="Chỉnh Sửa" variant="solid">
                    <Button onClick={() => handleEditPitch(params)} sx={{ color: 'green' }}>
                        <CreateIcon />
                    </Button>
                </Tooltip>
            ),
        },
    ];

    return (
        <div style={{ width: '100%' }}>
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
