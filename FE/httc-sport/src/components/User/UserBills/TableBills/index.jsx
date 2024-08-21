/* eslint-disable react-hooks/exhaustive-deps */
import { Button } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { format } from 'date-fns';
import { useEffect, useState } from 'react';

import { handleGetAllBillsByUser } from '~/apis';
import ModalRating from '../ModalRating';

function formatCurrency(amount) {
    return amount.toLocaleString('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
}

export default function TableBills() {
    const [bills, setBills] = useState([]);
    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const userId = localStorage.getItem('userId');

    const fetchData = async () => {
        try {
            const res = await handleGetAllBillsByUser(userId);
            setBills(res.data.result);
            console.log(res.data.result);
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        fetchData();
    }, []);

    const columns = [
        { field: 'id', headerName: 'ID', width: 20 },
        { field: 'pitchName', headerName: 'Tên Sân', width: 140 },
        { field: 'email', headerName: 'Email', width: 200 },
        { field: 'phoneNumber', headerName: 'Số Điện Thoại', width: 130 },
        {
            field: 'rentedAt',
            headerName: 'Ngày Đá',
            width: 120,
            renderCell: (params) => format(new Date(params.value), 'dd/MM/yyyy'),
        },
        { field: 'startTime', headerName: 'Bắt Đầu', width: 100 },
        { field: 'endTime', headerName: 'Kết Thúc', width: 100 },
        {
            field: 'total',
            headerName: 'Tổng Tiền',
            width: 120,
            renderCell: (params) => formatCurrency(params.value),
        },
        {
            field: 'paymentMethod',
            headerName: 'Phương Thức Thanh Toán',
            width: 200,
            renderCell: () => {
                return bills.paymentMethod === 'QR'
                    ? 'Thanh Toán Bằng QR'
                    : bills.paymentMethod === 'CASH'
                    ? 'Thanh Toán Bằng Tiền Mặt'
                    : 'Thanh Toán Bằng Ví';
            },
        },
        {
            field: 'danhGia',
            headerName: 'Đánh Giá',
            sortable: false,
            width: 90,
            renderCell: () => (
                <Button onClick={handleOpen} variant="text">
                    Đánh Giá
                </Button>
            ),
        },
    ];

    const rows = bills.map((info) => ({
        id: info.id,
        pitchName: info.pitchName,
        email: info.email,
        phoneNumber: info.phoneNumber,
        rentedAt: info.rentedAt,
        startTime: info.startTime,
        endTime: info.endTime,
        total: info.total,
        paymentMethod: info.paymentMethod,
    }));

    return (
        <div style={{ width: '100%' }}>
            <DataGrid
                rows={rows}
                columns={columns}
                pageSizeOptions={[5, 10, 20, 50, 100]}
                initialState={{
                    pagination: {
                        paginationModel: { page: 0, pageSize: 5 },
                    },
                }}
            />
            <ModalRating open={open} handleClose={handleClose} />
        </div>
    );
}
