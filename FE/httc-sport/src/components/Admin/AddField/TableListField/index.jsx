import React, { useEffect, useState } from 'react';
import { Button, Tooltip } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import CreateIcon from '@mui/icons-material/Create';
import { handleGetPitch } from '~/apis';
import { toast } from 'react-toastify';

export default function TableListField({ onRowClick }) {
    const [pitch, setPitch] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const re = await handleGetPitch();
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
                    <Button onClick={() => onRowClick(params.row)} sx={{ color: 'green' }}>
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
