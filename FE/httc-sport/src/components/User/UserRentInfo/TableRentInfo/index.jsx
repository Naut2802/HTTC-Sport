import { Button, Grid } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { format } from 'date-fns';
import { useEffect, useState } from 'react';

import { handleGetAllRentInfoByUser } from '~/apis';
import Popup from '~/components/Layout/Popup';
import ModalDetail from '../ModalDetail';
import RePayment from '../RePayment';

function formatCurrency(amount) {
    return amount.toLocaleString('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
}

export default function TableRentInfo({ userId }) {
    const [rentInfo, setRentInfo] = useState([]);
    const [openPopupDetail, setOpenPopupDetail] = useState(false);
    const [openPopupRePayment, setOpenPopupRePayment] = useState(false);
    const [selectedRentInfo, setSelectedRentInfo] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const res = await handleGetAllRentInfoByUser(userId);
                setRentInfo(res.data.result);
                console.log(res.data.result);
            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, [userId]);

    const handleViewDetails = (id) => {
        const selectedInfo = rentInfo[id - 1];
        setSelectedRentInfo(selectedInfo);
        setOpenPopupDetail(true);
    };

    const handleRePayment = (id) => {
        const selectedInfo = rentInfo[id - 1];
        setSelectedRentInfo(selectedInfo);
        setOpenPopupRePayment(true);
    };

    const columns = [
        { field: 'id', headerName: 'ID', width: 70 },
        { field: 'pitchName', headerName: 'Tên Sân', width: 130 },
        { field: 'email', headerName: 'Email', width: 250 },
        {
            field: 'rentedAt',
            headerName: 'Ngày Đá',
            width: 160,
            renderCell: (params) => format(new Date(params.value), 'dd/MM/yyyy'),
        },
        {
            field: 'deposit',
            headerName: 'Tiền Cọc',
            sortable: false,
            width: 160,
            renderCell: (params) => {
                const { id } = params.row;
                return rentInfo[id - 1].deposit === 0 ? (
                    <span className="text-danger">Vui lòng thanh toán!</span>
                ) : (
                    formatCurrency(params.value)
                );
            },
        },
        {
            field: 'total',
            headerName: 'Tổng Tiền',
            sortable: false,
            width: 160,
            renderCell: (params) => formatCurrency(params.value),
        },
        {
            field: 'paymentStatus',
            headerName: 'Tình Trạng Thanh Toán',
            sortable: false,
            width: 160,
            renderCell: (params) => {
                const { id } = params.row;
                return !rentInfo[id - 1].paymentStatus ? (
                    <Button variant="text" onClick={() => handleRePayment(id)}>
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
            width: 160,
            renderCell: (params) => {
                const { id } = params.row;
                return (
                    <Button onClick={() => handleViewDetails(id)} variant="text">
                        Xem Chi Tiết
                    </Button>
                );
            },
        },
    ];

    const rows = rentInfo.map((info, index) => ({
        id: index + 1,
        pitchName: info.pitchName,
        email: info.email,
        rentedAt: info.rentedAt,
        deposit: info.deposit,
        total: info.total,
    }));

    return (
        <div style={{ width: '100%', height: '350px' }}>
            <DataGrid rows={rows} columns={columns} getRowId={(rows) => rows.id} pageSizeOptions={[5, 10, 20, 50, 100]} />
            <Popup openPopup={openPopupDetail} setOpenPopup={setOpenPopupDetail}>
                <Grid container>
                    <Grid item>{selectedRentInfo && <ModalDetail rentInfo={selectedRentInfo} />}</Grid>
                </Grid>
            </Popup>
            <Popup openPopup={openPopupRePayment} setOpenPopup={setOpenPopupRePayment}>
                <Grid container>
                    <Grid item>{selectedRentInfo && <RePayment rentInfo={selectedRentInfo}/>}</Grid>
                </Grid>
            </Popup>
        </div>
    );
}
