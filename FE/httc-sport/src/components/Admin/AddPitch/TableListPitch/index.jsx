import CreateIcon from '@mui/icons-material/Create';
import { Button, Tooltip } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import React, { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import { handleGetPitch, handleGetPitchesAdmin } from '~/apis';

export default function TableListPitch({ onRowClick }) {
    const [pitch, setPitch] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await handleGetPitchesAdmin();
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
            const id = params.row.id;
            const response = await handleGetPitch(id);
            const Data = response.data.result;
            onRowClick(Data);
        } catch (error) {
            console.error('Error fetching pitch:', error);
            toast.error('Failed to fetch pitch details');
        }
    };

    const columns = [
        { field: 'pitchName', headerName: 'Tên Sân', flex: 1, minWidth: 100 },
        { field: 'price', headerName: 'Giá', flex: 0.5, minWidth: 60 },
        { field: 'description', headerName: 'Mô Tả', flex: 2, minWidth: 200 },
        { field: 'address', headerName: 'Địa Chỉ', flex: 3, minWidth: 300 },
        {
            field: 'other',
            headerName: 'Khác',
            sortable: false,
            flex: 0.5,
            minWidth: 80,
            renderCell: (params) => (
                <Tooltip title="Chỉnh Sửa">
                    <Button onClick={() => handleEditPitch(params)} sx={{ color: 'green' }}>
                        <CreateIcon />
                    </Button>
                </Tooltip>
            ),
        },
    ];

    return (
        <div style={{ width: '100%', height: '100%' }}>
            <DataGrid
                rows={pitch}
                columns={columns}
                pageSize={10}
                rowsPerPageOptions={[5, 10, 20, 50, 100]}
                getRowId={(row) => row.id}
                autoHeight
            />
        </div>
    );
}
