/* eslint-disable react-hooks/exhaustive-deps */
import DeleteForeverTwoToneIcon from '@mui/icons-material/DeleteForeverTwoTone';
import { Button, Grid, TablePagination } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { format } from 'date-fns';
import { useEffect, useState } from 'react';

import { toast } from 'react-toastify';
import { handleDeleteRentInfo, handleGetAllRentInfoByUser, handleRentPitch } from '~/apis';
import Popup from '~/components/Layout/Popup';
import ModalDetail from '../ModalDetail';
import RePayment from '../RePayment';

function formatCurrency(amount) {
    return amount.toLocaleString('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
}

function calculateMinutesDifference(startTime, endTime) {
    // Chuyển đổi chuỗi thời gian thành đối tượng Date cho cùng một ngày (ngày mặc định)
    const today = new Date().toISOString().split('T')[0];
    const startDate = new Date(`${today}T${startTime}`);
    const endDate = new Date(`${today}T${endTime}`);
    const diffInMs = endDate - startDate;
    const diffInMinutes = Math.floor(diffInMs / (1000 * 60));

    return diffInMinutes;
}

function subtractMinutesFromTime(startTime, minutesToSubtract) {
    const today = new Date().toISOString().split('T')[0];
    const startDate = new Date(`${today}T${startTime}`);
    startDate.setMinutes(startDate.getMinutes() - minutesToSubtract);
    const hours = String(startDate.getHours()).padStart(2, '0');
    const minutes = String(startDate.getMinutes()).padStart(2, '0');
    const seconds = String(startDate.getSeconds()).padStart(2, '0');

    return `${hours}:${minutes}:${seconds}`;
}

export default function TableRentInfo({ userId }) {
    const [rentInfo, setRentInfo] = useState([]);
    const [openPopupDetail, setOpenPopupDetail] = useState(false);
    const [openPopupRePayment, setOpenPopupRePayment] = useState(false);
    const [selectedRentInfo, setSelectedRentInfo] = useState(null);
    const [rentInfoRePayment, setRentInfoRePayment] = useState(null);
    const [resPayment, setResPayment] = useState(null);
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(5);
    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    const fetchData = async (page, size) => {
        try {
            const res = await handleGetAllRentInfoByUser(userId, page, size);
            setRentInfo(res.data.result);
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        fetchData(page, rowsPerPage);
    }, [page, rowsPerPage]);

    const handleViewDetails = (number) => {
        const selectedInfo = rentInfo[number - 1];
        setSelectedRentInfo(selectedInfo);
        setOpenPopupDetail(true);
    };

    const handleRePayment = async (number) => {
        const selectedInfo = rentInfo[number - 1];
        await handleDeleteRentInfo(selectedInfo.id);

        const minutesToSubtract = 1;
        const newStartTime = subtractMinutesFromTime(selectedInfo.startTime, minutesToSubtract);
        const rentTime = calculateMinutesDifference(newStartTime, selectedInfo.endTime);
        const data = {
            email: selectedInfo.email,
            firstName: selectedInfo.firstName,
            lastName: selectedInfo.lastName,
            note: selectedInfo.note,
            paymentMethod: selectedInfo.paymentMethod,
            phoneNumber: selectedInfo.phoneNumber,
            pitchId: selectedInfo.pitchId,
            rentTime: rentTime,
            rentedAt: selectedInfo.rentedAt,
            startTime: newStartTime,
            typePitch: selectedInfo.typePitch,
        };
        const res = await handleRentPitch(data);
        if (res.data.result.rentSuccess) {
            setResPayment(res.data.result);
            setRentInfoRePayment(data);
            setOpenPopupRePayment(true);
        } else {
            toast.error('Không thể tiếp tục vì không còn sân trống trong thời gian này nữa. Vui lòng đặt lại!');
        }
        fetchData();
    };

    const handleDeleteRentInfoByID = async (number) => {
        const selectedInfo = rentInfo[number - 1];
        const res = await handleDeleteRentInfo(selectedInfo.id);
        toast.success(res.data.message);
        fetchData();
    };

    const columns = [
        { field: 'number', headerName: 'STT', width: 20 },
        { field: 'id', headerName: 'ID', width: 80 },
        { field: 'pitchName', headerName: 'Tên Sân', width: 170 },
        { field: 'email', headerName: 'Email', width: 220 },
        {
            field: 'rentedAt',
            headerName: 'Ngày Đá',
            width: 120,
            renderCell: (params) => format(new Date(params.value), 'dd/MM/yyyy'),
        },
        {
            field: 'deposit',
            headerName: 'Tiền Cọc',
            width: 160,
            renderCell: (params) => {
                const { number } = params.row;
                return rentInfo[number - 1].deposit === 0 ? (
                    <span className="text-danger">Chưa thanh toán cọc!</span>
                ) : (
                    formatCurrency(params.value)
                );
            },
        },
        {
            field: 'total',
            headerName: 'Tổng Tiền',
            width: 120,
            renderCell: (params) => formatCurrency(params.value),
        },
        {
            field: 'paymentStatus',
            headerName: 'Tình Trạng Thanh Toán',
            sortable: false,
            width: 160,
            renderCell: (params) => {
                const { number } = params.row;
                return !rentInfo[number - 1].paymentStatus ? (
                    <Button variant="text" onClick={() => handleRePayment(number)}>
                        Tiếp Tục
                    </Button>
                ) : (
                    <Button disabled>Đã Thanh Toán</Button>
                );
            },
        },
        {
            field: 'detail',
            headerName: 'Chi Tiết',
            sortable: false,
            width: 90,
            renderCell: (params) => {
                const { number } = params.row;
                return (
                    <Button onClick={() => handleViewDetails(number)} variant="text">
                        Xem
                    </Button>
                );
            },
        },
        {
            field: 'delelte',
            headerName: 'Xóa',
            sortable: false,
            width: 90,
            renderCell: (params) => {
                const { number } = params.row;
                return !rentInfo[number - 1].paymentStatus ? (
                    <Button onClick={() => handleDeleteRentInfoByID(number)} color="error" variant="text">
                        <DeleteForeverTwoToneIcon sx={{ textAlign: 'start' }} />
                    </Button>
                ) : (
                    <Button disabled></Button>
                );
            },
        },
    ];

    const rows = rentInfo.map((info, index) => ({
        number: index + 1,
        id: info.id,
        pitchName: info.pitchName,
        email: info.email,
        rentedAt: info.rentedAt,
        deposit: info.deposit,
        total: info.total,
    }));

    return (
        <>
            <div style={{ width: '100%', height: '370px' }}>
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
            <Popup openPopup={openPopupDetail} setOpenPopup={setOpenPopupDetail}>
                <Grid container>
                    <Grid item>{selectedRentInfo && <ModalDetail rentInfo={selectedRentInfo} />}</Grid>
                </Grid>
            </Popup>
            <Popup openPopup={openPopupRePayment} setOpenPopup={setOpenPopupRePayment}>
                <Grid container>
                    <Grid item>{rentInfoRePayment && <RePayment rentInfo={rentInfoRePayment} resPayment={resPayment} />}</Grid>
                </Grid>
            </Popup>
        </>
    );
}
