import CancelPresentationRoundedIcon from '@mui/icons-material/CancelPresentationRounded';
import SearchRoundedIcon from '@mui/icons-material/SearchRounded';
import { Box, Button, TablePagination, TextField, Typography } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { DatePicker } from '@mui/x-date-pickers';
import { format } from 'date-fns';
import React, { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { toast } from 'react-toastify';

import { handleGetAllTransactionsAdmin, handleGetAllTransactionsByDate } from '~/apis';

function formatCurrency(amount) {
    return amount.toLocaleString('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
}

export default function TableTransaction() {
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(5);
    const [transaction, setTransaction] = useState([]);
    const [fromDateValue, setFromDateValue] = useState(null);
    const [toDateValue, setToDateValue] = useState(null);
    const { handleSubmit } = useForm();
    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    const submitDateReport = async () => {
        const formattedFromDate = format(fromDateValue.$d, 'yyyy-MM-dd');
        const formattedToDate = format(toDateValue.$d, 'yyyy-MM-dd');
        try {
            const res = await handleGetAllTransactionsByDate(formattedFromDate, formattedToDate);
            setTransaction(res.data.result);
        } catch (error) {}
    };

    const cancelSearchForDate = () => {
        setFromDateValue(null);
        setToDateValue(null);
        fetchData(page, rowsPerPage);
    };

    const fetchData = async (page, size) => {
        try {
            const response = await handleGetAllTransactionsAdmin(page, size);
            const data = response.data.result;
            console.log(data);

            setTransaction(data || []);
        } catch (error) {
            console.error(error);
            toast.error('Failed to fetch data');
        }
    };

    useEffect(() => {
        fetchData(page, rowsPerPage);
    }, [page, rowsPerPage]);

    const rows = transaction.map((item) => ({
        id: item.id,
        ...item,
    }));

    const columns = [
        { field: 'id', headerName: 'ID', flex: 1, minWidth: 120 },
        {
            field: 'transactionDate',
            headerName: 'Ngày thanh toán',
            flex: 1,
            minWidth: 200,
            renderCell: (params) => format(new Date(params.value), 'dd/MM/yyyy'),
        },
        {
            field: 'paymentAmount',
            headerName: 'Số tiền',
            flex: 0.5,
            minWidth: 200,
            renderCell: (params) => <div>{formatCurrency(params.value)}</div>,
        },
        {
            field: 'transactionType',
            headerName: 'Loại Giao Dịch',
            flex: 2,
            minWidth: 200,
            renderCell: (params) =>
                params.value === 'USER_DEPOSIT'
                    ? 'Nạp tiền vào ví'
                    : params.value === 'RENT_PAY'
                    ? 'Thanh toán đặt sân'
                    : 'Admin nạp tiền vào ví',
        },
        {
            field: 'paymentStatus',
            headerName: 'Trạng thái',
            flex: 3,
            minWidth: 200,
            renderCell: (params) => {
                const isSuccess = params.value === true;
                return <div style={{ color: isSuccess ? 'green' : 'red' }}>{isSuccess ? 'Thành công' : 'Không thành công'}</div>;
            },
        },
    ];

    return (
        <div className="container">
            <Box
                sx={{
                    display: 'flex',
                    border: 1,
                    borderColor: 'divider',
                    '& > *': {
                        m: 1,
                    },
                }}
            >
                <Box sx={{ ml: 2 }} component="form" onSubmit={handleSubmit(submitDateReport)} className="d-flex">
                    <Typography component={'span'} sx={{ mr: 1 }}>
                        <DatePicker
                            sx={{ width: '100%' }}
                            label="Chọn khoảng bắt đầu"
                            value={fromDateValue}
                            onChange={(newFromDateValue) => setFromDateValue(newFromDateValue)}
                            renderInput={(params) => <TextField {...params} />}
                        />
                    </Typography>
                    <Typography component={'span'} sx={{ mr: 1 }}>
                        <DatePicker
                            sx={{ width: '100%' }}
                            label="Chọn khoảng kết thúc"
                            value={toDateValue}
                            onChange={(newToDateValue) => setToDateValue(newToDateValue)}
                            renderInput={(params) => <TextField {...params} />}
                        />
                    </Typography>
                    <Button variant="text" type="submit" color="success">
                        Tìm Kiếm <SearchRoundedIcon />
                    </Button>
                </Box>
                <Button variant="text" color="error" onClick={cancelSearchForDate}>
                    Hủy <CancelPresentationRoundedIcon />
                </Button>
            </Box>
            <div style={{ height: '370px', width: '100%' }}>
                <DataGrid rows={rows} columns={columns} hideFooterPagination={true} />
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
        </div>
    );
}
