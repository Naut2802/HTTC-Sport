import CreateIcon from '@mui/icons-material/Create';
import RemoveRedEyeIcon from '@mui/icons-material/RemoveRedEye';
import { Box, Button, Modal, Tooltip } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { format } from 'date-fns';

import { useEffect, useState } from 'react';
import { handleGetAllRentInfoAdmin } from '~/apis';
import ModalServices from '../ModalServices';

function formatCurrency(amount) {
    return amount.toLocaleString('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
}

export default function TableRentInfo() {
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

    const [rentInfo, setRentInfo] = useState([]);
    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    const handleViewDetails = (id) => {
        const selectedInfo = rentInfo[id - 1];
        // setSelectedRentInfo(selectedInfo);
        // setOpenPopupDetail(true);
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const res = await handleGetAllRentInfoAdmin();
                setRentInfo(res.data.result);
                console.log(res.data.result);
            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, []);

    const columns = [
        { field: 'id', headerName: 'ID', width: 70 },
        { field: 'pitchName', headerName: 'Tên Sân', width: 130 },
        { field: 'email', headerName: 'Email', width: 250 },
        { field: 'phoneNumber', headerName: 'Số Điện Thoại', width: 130 },
        {
            field: 'rentedAt',
            headerName: 'Ngày Đá',
            width: 160,
            renderCell: (params) => format(new Date(params.value), 'dd/MM/yyyy'),
        },
        {
            field: 'deposit',
            headerName: 'Tiền Cọc',
            sortable: false,
            width: 160,
            renderCell: (params) => {
                const { id } = params.row;
                return rentInfo[id - 1].deposit === 0 ? (
                    <span className="text-danger">Vui lòng thanh toán!</span>
                ) : (
                    formatCurrency(params.value)
                );
            },
        },
        {
            field: 'total',
            headerName: 'Tổng Tiền',
            sortable: false,
            width: 160,
            renderCell: (params) => formatCurrency(params.value),
        },
        {
            field: 'paymentStatus',
            headerName: 'Tình Trạng Thanh Toán',
            sortable: false,
            width: 160,
            renderCell: (params) => {
                const { id } = params.row;
                return !rentInfo[id - 1].paymentStatus ? (
                    <Button disabled>Chưa Thanh Toán</Button>
                ) : (
                    <Button disabled>Đã Thanh Toán</Button>
                );
            },
        },
        {
            field: 'detail',
            headerName: 'Chi Tiết',
            sortable: false,
            width: 160,
            renderCell: (params) => {
                const { id } = params.row;
                return (
                    <Button onClick={() => handleViewDetails(id)} variant="text">
                        Xem Chi Tiết
                    </Button>
                );
            },
        },
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
                                <ModalServices />
                            </Box>
                        </Box>
                    </Modal>
                </Tooltip>
            ),
        },
    ];

    const rows = rentInfo.map((info, index) => ({
        id: index + 1,
        pitchName: info.pitchName,
        email: info.email,
        phoneNumber: info.phoneNumber,
        rentedAt: info.rentedAt,
        deposit: info.deposit,
        total: info.total,
    }));

    return (
        <div className="my-3" sx={{ width: 'auto' }}>
            <DataGrid key={rows.id} rows={rows} columns={columns} pageSizeOptions={[5, 10, 20, 50, 100]} />
        </div>
    );
}
