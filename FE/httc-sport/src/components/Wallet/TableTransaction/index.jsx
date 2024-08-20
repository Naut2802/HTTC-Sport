import { Breadcrumbs, Grid, Typography } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { format } from 'date-fns';
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { toast } from 'react-toastify';
import { handleGetMyInfoAPI, handleGetTransactionsByUser } from '~/apis';

import logo from '~/components/Images/logo.png';

function formatCurrency(amount) {
    return amount.toLocaleString('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
}
export default function TableTransaction() {
    const [transaction, setTransaction] = useState([]);
    useEffect(() => {
        const fetchData = async () => {
            const responseUser = await handleGetMyInfoAPI();
            const userId = responseUser.data.result.id;

            try {
                const response = await handleGetTransactionsByUser(userId);
                console.log(response.data.result);
                const data = response.data.result;
                setTransaction(data);
            } catch (error) {
                console.error(error);
                toast.error('Failed to fetch data');
            }
        };
        fetchData();
    }, []);

    const rows = transaction.map((item, index) => ({
        indexId: index + 1,
        ...item,
    }));
    const columns = [
        { field: 'indexId', headerName: 'ID', flex: 1, minWidth: 100 },
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
            renderCell: (params) => {
                return <div>{formatCurrency(params.value)}</div>;
            },
        },
        { field: 'transactionType', headerName: 'Loại Giao Dịch', flex: 2, minWidth: 200 },
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
        <div className="my-3 container">
            <div className="d-flex justify-content-center">
                <img src={logo} alt="" style={{ width: 120 }} />
            </div>
            <Typography className="fs-3 fw-bold mt-3 mb-2">Lịch sử giao dịch ví</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
                <Typography className="text-decoration-none text-dark fs-6" variant="h6" noWrap component={Link} to="/">
                    Trang Chủ
                </Typography>
                <Typography
                    className="text-decoration-none text-dark fs-6"
                    variant="h6"
                    noWrap
                    component={Link}
                    to="/lich-su-giao-dich-vi"
                >
                    Lịch sử giao dịch ví
                </Typography>
            </Breadcrumbs>
            <hr />

            <Grid container spacing={2}>
                <Grid item xs={1}></Grid>
                <Grid item xs={10}>
                    <div style={{ width: '100%', height: '100%' }}>
                        <DataGrid
                            rows={rows}
                            columns={columns}
                            pageSize={10}
                            initialState={{
                                pagination: {
                                    paginationModel: { page: 0, pageSize: 5 },
                                },
                            }}
                            rowsPerPageOptions={[5, 10]}
                            getRowId={(row) => row.id}
                            autoHeight
                        />
                    </div>
                </Grid>
            </Grid>
        </div>
    );
}
