import { Button, Grid } from '@mui/material';
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

    const handleViewDetails = (number) => {
        const selectedInfo = rentInfo[number - 1];
        setSelectedRentInfo(selectedInfo);
        setOpenPopupDetail(true);
    };

    const fetchData = async () => {
        try {
            const res = await handleGetAllRentInfoAdmin();
            setRentInfo(res.data.result);
            console.log(res.data.result);
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        fetchData();
    }, []);

    const columns = [
        { field: 'number', headerName: 'STT', width: 10 },
        { field: 'id', headerName: 'ID', width: 100 },
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
            headerName: 'Tình Trạng Thanh Toán',
            sortable: false,
            width: 180,
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
        <div className="my-3" sx={{ width: 'auto' }}>
            <DataGrid
                key={rows.id}
                rows={rows}
                columns={columns}
                initialState={{
                    pagination: {
                        paginationModel: { page: 0, pageSize: 5 },
                    },
                }}
                pageSizeOptions={[5, 10, 20, 50, 100]}
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
