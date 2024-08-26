/* eslint-disable react-hooks/exhaustive-deps */
import CancelPresentationRoundedIcon from '@mui/icons-material/CancelPresentationRounded';
import SearchRoundedIcon from '@mui/icons-material/SearchRounded';
import { Box, Button, Grid, TablePagination, TextField, Typography } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { DatePicker } from '@mui/x-date-pickers';
import { format } from 'date-fns';
import { useEffect, useState } from 'react';

import { useForm } from 'react-hook-form';
import { handleGetAllBillsByUser, handleGetByUserReportAndDate } from '~/apis';
import Popup from '~/components/Layout/Popup';
import ModalRating from '../ModalRating';
import ReviewDetail from '../ReviewDetail';

function formatCurrency(amount) {
    return amount.toLocaleString('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
}

export default function TableBills() {
    const userId = localStorage.getItem('userId');
    const [bills, setBills] = useState([]);
    const [open, setOpen] = useState(false);
    const [openPopup, setOpenPopup] = useState(false);
    const [selectedBill, setSelectedBill] = useState(null);
    const [selectedBillId, setSelectedBillId] = useState(null);
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(5);
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

    const handleOpen = (id) => {
        setSelectedBillId(id);
        setOpen(true);
    };

    const handleClose = () => setOpen(false);

    const handleReviewDetail = (id, index) => {
        setSelectedBill(bills[index]);
        setSelectedBillId(id);
        setOpenPopup(true);
    };

    const submitDateReport = async () => {
        const formattedFromDate = format(fromDateValue.$d, 'yyyy-MM-dd');
        const formattedToDate = format(toDateValue.$d, 'yyyy-MM-dd');
        try {
            const res = await handleGetByUserReportAndDate(userId, formattedFromDate, formattedToDate);
            console.log(res);
            setBills(res.data.result.bills);
        } catch (error) {}
    };

    const cancelSearchForDate = () => {
        setFromDateValue(null);
        setToDateValue(null);
        fetchData(page, rowsPerPage);
    };

    const fetchData = async (page, size) => {
        try {
            const res = await handleGetAllBillsByUser(userId, page, size);
            setBills(res.data.result);
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        fetchData(page, rowsPerPage);
    }, [page, rowsPerPage]);

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
            width: 130,
            renderCell: (params) => {
                const billIndex = params.row.index;
                return !params.row.rate ? (
                    <Button variant="text" onClick={() => handleOpen(params.row.id)}>
                        Đánh Giá
                    </Button>
                ) : (
                    <Button variant="text" onClick={() => handleReviewDetail(params.row.id, billIndex)}>
                        Xem Chi Tiết
                    </Button>
                );
            },
        },
    ];

    const rows = bills.map((info, index) => ({
        index: index,
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
        rate: info.rate,
    }));

    return (
        <>
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
            <ModalRating open={open} handleClose={handleClose} selectedBillId={selectedBillId} />

            <Popup openPopup={openPopup} setOpenPopup={setOpenPopup}>
                <Grid container>
                    <Grid item>{selectedBillId && <ReviewDetail selectedBill={selectedBill} />}</Grid>
                </Grid>
            </Popup>
        </>
    );
}
