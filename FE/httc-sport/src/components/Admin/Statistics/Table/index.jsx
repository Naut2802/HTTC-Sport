import CancelPresentationRoundedIcon from '@mui/icons-material/CancelPresentationRounded';
import SearchRoundedIcon from '@mui/icons-material/SearchRounded';
import { Box, Button, TablePagination, TextField, Typography } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { DatePicker } from '@mui/x-date-pickers';
import { format } from 'date-fns';
import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { toast } from 'react-toastify';

import { handleExportExcel, handleGetAllBills, handleGetReportByDate } from '~/apis';

function formatCurrency(amount) {
    return amount.toLocaleString('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
}

export default function TableStatistics() {
    const [bills, setBills] = useState([]);
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

    const handleExportWithExcel = async () => {
        try {
            const billIds = bills.map((bill) => bill.id);
            const payloadData = { billIds };

            const res = await handleExportExcel(payloadData);

            if (res.data) {
                const blob = new Blob([res.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
                const url = URL.createObjectURL(blob);
                const link = document.createElement('a');
                link.href = url;
                link.download = 'bill-report.xlsx'; // Set the file name for download

                document.body.appendChild(link);
                link.click();
                link.remove();

                toast.success('Đã xuất dữ liệu ra file excel');
            }
        } catch (error) {
            toast.error('Có lỗi xảy ra khi xuất dữ liệu');
            console.error('Export Excel Error:', error);
        }
    };

    const submitDateReport = async () => {
        const formattedFromDate = format(fromDateValue.$d, 'yyyy-MM-dd');
        const formattedToDate = format(toDateValue.$d, 'yyyy-MM-dd');
        try {
            const res = await handleGetReportByDate(formattedFromDate, formattedToDate);
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
            const res = await handleGetAllBills(page, size);
            setBills(res.data.result);
            console.log(res.data.result);
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
        { field: 'name', headerName: 'Họ Tên', width: 100 },
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
            width: 130,
            renderCell: (params) => formatCurrency(params.value),
        },
        {
            field: 'paymentMethod',
            headerName: 'Phương Thức Thanh Toán',
            width: 200,
            renderCell: (params) =>
                params.value === 'QR'
                    ? 'Thanh Toán Bằng QR'
                    : params.value === 'CASH'
                    ? 'Thanh Toán Tiền Mặt'
                    : 'Thanh Toán Bằng Ví',
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
                <div style={{ marginRight: '20px' }} className="d-flex justify-content-end">
                    <Button variant="contained" color="success" onClick={() => handleExportWithExcel()}>
                        Xuất dữ liệu (Excel)
                    </Button>
                </div>
            </Box>

            <div style={{ height: '390px', width: '100%' }}>
                <DataGrid key={rows.id} rows={rows} columns={columns} hideFooterPagination={true} />
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
        </>
    );
}
