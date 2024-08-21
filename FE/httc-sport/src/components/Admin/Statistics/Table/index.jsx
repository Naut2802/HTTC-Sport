import { DataGrid } from '@mui/x-data-grid';
import { format } from 'date-fns';
import { useEffect, useState } from 'react';

import { handleGetAllBills } from '~/apis';

function formatCurrency(amount) {
    return amount.toLocaleString('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
}

export default function TableStatistics() {
    const [bills, setBills] = useState([]);
    const fetchData = async () => {
        try {
            const res = await handleGetAllBills();
            setBills(res.data.result);
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        fetchData();
    }, []);

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
            renderCell: () => {
                return bills.paymentMethod === 'QR'
                    ? 'Thanh Toán Bằng QR'
                    : bills.paymentMethod === 'CASH'
                    ? 'Thanh Toán Bằng Tiền Mặt'
                    : 'Thanh Toán Bằng Ví';
            },
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
        <div style={{ width: '100%' }}>
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
        </div>
    );
}
