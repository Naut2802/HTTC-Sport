import { Box, Button, Modal, Tooltip } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import * as React from 'react';

import CreateIcon from '@mui/icons-material/Create';
import RemoveRedEyeIcon from '@mui/icons-material/RemoveRedEye';
import ModalAddServices from '../ModalAddServices';

export default function TableFieldsOrder() {
    const style = {
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 500,
        bgcolor: 'background.paper',
        border: '2px solid #000',
        boxShadow: 24,
        p: 4,
    };

    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const columns = [
        { field: 'id', headerName: 'ID', flex: 1 },
        { field: 'tenSan', headerName: 'Tên Sân', width: 130 },
        { field: 'email', headerName: 'Email', width: 250 },
        { field: 'soDT', headerName: 'Số Điện Thoại', width: 130 },
        { field: 'ngayDa', headerName: 'Ngày Đá', width: 130 },
        { field: 'tgBD', headerName: 'Thời Gian Bắt Đầu', width: 150 },
        { field: 'tgKT', headerName: 'Thời Gian Kết Thúc', width: 150 },
        { field: 'tienCoc', headerName: 'Đã Cọc', width: 120 },
        { field: 'trangThai', headerName: 'Trạng Thái', width: 150 },
        {
            field: 'edit',
            headerName: 'Chỉnh Sửa',
            sortable: false,
            width: 90,
            renderCell: () => (
                <Tooltip title="Chỉnh Sửa" variant="solid">
                    <Button sx={{ color: 'green' }}>
                        <CreateIcon />
                    </Button>
                </Tooltip>
            ),
        },
        {
            field: 'dichVu',
            headerName: 'Dịch Vụ',
            sortable: false,
            width: 90,
            renderCell: () => (
                <Tooltip title="Dịch Vụ" variant="solid">
                    <Button onClick={handleOpen} variant="text" className="text-capitalize">
                        <RemoveRedEyeIcon />
                    </Button>
                    <Modal
                        open={open}
                        onClose={handleClose}
                        aria-labelledby="modal-modal-title"
                        aria-describedby="modal-modal-description"
                    >
                        <Box sx={style}>
                            <Box sx={{ mt: 2 }}>
                                <ModalAddServices />
                            </Box>
                        </Box>
                    </Modal>
                </Tooltip>
            ),
        },
    ];

    const rows = [
        {
            id: '1',
            tenSan: 'Sân Min Hòn',
            email: 'leminhhoang241299@gmail.com',
            soDT: '0372323666',
            ngayDa: '2024-01-24',
            tgBD: '16:00',
            tgKT: '	18:00',
            tienCoc: '220.000',
            trangThai: 'Chưa Thanh Toán',
        },
    ];

    return (
        <div className="my-3" style={{ width: '100%' }}>
            <DataGrid key={rows.id} rows={rows} columns={columns} pageSizeOptions={[5, 10, 20, 50, 100]} sx={{ width: 'auto' }} />
        </div>
    );
}
