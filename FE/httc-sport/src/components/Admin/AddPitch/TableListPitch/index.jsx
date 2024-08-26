import CreateIcon from '@mui/icons-material/Create';
import { Button, TablePagination, Tooltip } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import React, { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import { handleGetPitch, handleGetPitchesAdmin } from '~/apis';

export default function TableListPitch({ onRowClick }) {
    const [pitch, setPitch] = useState([]);
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(5);
    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };
    const fetchData = async (page, size) => {
        try {
            const response = await handleGetPitchesAdmin(page, size);
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
    useEffect(() => {
        fetchData(page, rowsPerPage);
    }, [page, rowsPerPage]);

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
        <div style={{ width: '100%', height: '370px' }}>
            <DataGrid rows={pitch} columns={columns} getRowId={(row) => row.id} hideFooterPagination={true} />
            <TablePagination
                component="div"
                sx={{ border: 1, borderColor: 'divider' }}
                rowsPerPageOptions={[5, 10, 25]}
                count={100}
                page={page}
                onPageChange={handleChangePage}
                rowsPerPage={rowsPerPage}
                onRowsPerPageChange={handleChangeRowsPerPage}
            />
        </div>
    );
}
