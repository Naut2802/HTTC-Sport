import { Button, Grid, TablePagination } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { format } from 'date-fns';
import { useEffect, useState } from 'react';

import { handleGetAllRentInfoAdmin } from '~/apis';
import Popup from '~/components/Layout/Popup';
import RentInfoDetails from '../RentInfoDetails';

function formatCurrency(amount) {
    return amount.toLocaleString('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
}

export default function TableRentInfo() {
    const [rentInfo, setRentInfo] = useState([]);
    const [selectedRentInfo, setSelectedRentInfo] = useState(null);
    const [openPopupDetail, setOpenPopupDetail] = useState(false);
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(5);
    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };
    const handleViewDetails = (number) => {
        const selectedInfo = rentInfo[number - 1];
        setSelectedRentInfo(selectedInfo);
        setOpenPopupDetail(true);
    };

    const fetchData = async (page, size) => {
        try {
            const res = await handleGetAllRentInfoAdmin(page, size);
            setRentInfo(res.data.result);
            console.log(res.data.result);
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        fetchData(page, rowsPerPage);
    }, [page, rowsPerPage]);

    const columns = [
        { field: 'number', headerName: 'STT', width: 10 },
        { field: 'id', headerName: 'ID', width: 80 },
        { field: 'pitchName', headerName: 'Tên Sân', width: 130 },
        { field: 'email', headerName: 'Email', width: 200 },
        { field: 'phoneNumber', headerName: 'Số Điện Thoại', width: 130 },
        {
            field: 'rentedAt',
            headerName: 'Ngày Đá',
            width: 120,
            renderCell: (params) => format(new Date(params.value), 'dd/MM/yyyy'),
        },
        {
            field: 'deposit',
            headerName: 'Tiền Cọc',
            sortable: false,
            width: 130,
            renderCell: (params) => formatCurrency(params.value),
        },
        {
            field: 'total',
            headerName: 'Tổng Tiền',
            sortable: false,
            width: 130,
            renderCell: (params) => formatCurrency(params.value),
        },
        {
            field: 'paymentStatus',
            headerName: 'Tình Trạng Thanh Toán Cọc',
            sortable: false,
            width: 200,
            renderCell: (params) => {
                const { number } = params.row;
                return !rentInfo[number - 1].paymentStatus ? (
                    <Button color="error">Chưa Thanh Toán</Button>
                ) : (
                    <Button color="success">Đã Thanh Toán</Button>
                );
            },
        },
        {
            field: 'detail',
            headerName: 'Chi Tiết',
            sortable: false,
            width: 100,
            renderCell: (params) => {
                const { number } = params.row;
                return (
                    <Button onClick={() => handleViewDetails(number)} variant="text">
                        Xem
                    </Button>
                );
            },
        },
    ];

    const rows = rentInfo.map((info, index) => ({
        number: index + 1,
        id: info.id,
        pitchName: info.pitchName,
        email: info.email,
        phoneNumber: info.phoneNumber,
        rentedAt: info.rentedAt,
        deposit: info.deposit,
        total: info.total,
    }));

    return (
        <div style={{ height: '370px', width: '100%' }}>
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
            <Popup openPopup={openPopupDetail} setOpenPopup={setOpenPopupDetail}>
                <Grid container>
                    <Grid item>
                        {selectedRentInfo && (
                            <RentInfoDetails
                                rentInfo={selectedRentInfo}
                                onRentInfoUpdate={fetchData}
                                closePopup={() => setOpenPopupDetail(false)}
                            />
                        )}
                    </Grid>
                </Grid>
            </Popup>
        </div>
    );
}
