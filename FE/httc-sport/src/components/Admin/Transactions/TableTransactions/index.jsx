import React, { useEffect, useState } from 'react';
import { DataGrid } from '@mui/x-data-grid';
import { toast } from 'react-toastify';
import { handleGetAllTransactionsAdmin } from '~/apis';
import { format } from 'date-fns';
import { Breadcrumbs, TablePagination, Typography } from '@mui/material';
import logo from '~/components/Images/logo.png';
import { Link } from 'react-router-dom';
function formatCurrency(amount) {
    return amount.toLocaleString('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
}

export default function TableTransaction() {
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(5);
    const [pageSize, setPageSize] = useState(5);
    const [transaction, setTransaction] = useState([]);
    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
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
        fetchData(page, pageSize);
    }, [page, pageSize]);

    const rows = transaction.map((item) => ({
        id: item.id,
        ...item,
    }));

    const columns = [
        { field: 'id', headerName: 'ID', flex: 1, minWidth: 100 },
        {
            field: 'transactionDate',
            headerName: 'Ngày thanh toán',
            flex: 1,
            minWidth: 350,
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
            renderCell: (params) => (params.value === 'USER_DEPOSIT' ? 'Nạp tiền vào ví' : params.value),
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
        </div>
    );
}
