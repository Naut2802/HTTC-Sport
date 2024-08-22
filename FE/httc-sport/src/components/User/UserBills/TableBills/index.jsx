import { useState, useEffect } from 'react';
import { Button, TablePagination } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { format } from 'date-fns';

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
    const [selectedBillId, setSelectedBillId] = useState(null);
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(5);
    const [pageSize, setPageSize] = useState(5);
    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };
    const handleOpen = (id) => {
        setSelectedBillId(id);
        setOpen(true);
    };

    const handleClose = () => setOpen(false);
    const userId = localStorage.getItem('userId');

    const fetchData = async (page, size) => {
        try {
            const res = await handleGetAllBillsByUser(userId, page, size);
            setBills(res.data.result);
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        fetchData(page, pageSize);
    }, [page, pageSize]);

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
            renderCell: (params) => {
                return params.value === 'QR'
                    ? 'Thanh Toán Bằng QR'
                    : params.value === 'CASH'
                    ? 'Thanh Toán Bằng Tiền Mặt'
                    : 'Thanh Toán Bằng Ví';
            },
        },
        {
            field: 'danhGia',
            headerName: 'Đánh Giá',
            sortable: false,
            width: 90,
            renderCell: (params) => (
                <Button onClick={() => handleOpen(params.row.id)} variant="text">
                    Đánh Giá
                </Button>
            ),
        },
    ];

    const rows = bills.map((info) => ({
        id: info.id,
        pitchName: info.pitchName,
        name: info.lastName + ' ' + info.firstName,
        email: info.email,
        phoneNumber: info.phoneNumber,
        rentedAt: info.rentedAt,
        startTime: info.startTime,
        endTime: info.endTime,
        total: info.total,
        paymentMethod: info.paymentMethod,
    }));

    return (
        <>
            <div className="my-5">
                <DataGrid rows={rows} columns={columns} />
                <TablePagination
                    component="div"
                    count={100}
                    page={page}
                    onPageChange={handleChangePage}
                    rowsPerPage={rowsPerPage}
                    onRowsPerPageChange={handleChangeRowsPerPage}
                />
            </div>
            <ModalRating open={open} handleClose={handleClose} selectedBillId={selectedBillId} />
        </>
    );
}
