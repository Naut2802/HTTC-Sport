import { Box, Button, TablePagination } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { format } from 'date-fns';
import { useEffect, useState } from 'react';
import { toast } from 'react-toastify';

import { handleExportExcel, handleGetAllBills } from '~/apis';

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
                    flexDirection: 'column',
                    border: 1,
                    borderColor: 'divider',
                    '& > *': {
                        m: 1,
                    },
                }}
            >
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
