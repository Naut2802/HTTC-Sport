import { Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@mui/material';
import * as React from 'react';

function createData(Vip, mucBD, mucKT, uuDai) {
    return { Vip, mucBD, mucKT, uuDai };
}

const rows = [
    createData('0', '0 VNĐ', '1.000.000 VNĐ', 'Giảm 5%'),
    createData('1', '1.000.000 VNĐ', '2.000.000 VNĐ', 'Giảm 5%'),
    createData('2', '2.000.000 VNĐ', '3.000.000 VNĐ', 'Giảm 5%'),
];

export default function VipLevel() {
    return (
        <TableContainer component={Paper}>
            <Table sx={{ minWidth: 250 }} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell>VIP</TableCell>
                        <TableCell>Mức Bắt Đầu</TableCell>
                        <TableCell>Mức Kết Thúc</TableCell>
                        <TableCell align="right">Ưu Đãi</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {rows.map((row) => (
                        <TableRow key={row.Vip} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                            <TableCell component="th" scope="row">
                                {row.Vip}
                            </TableCell>
                            <TableCell>{row.mucBD}</TableCell>
                            <TableCell align="right">{row.mucKT}</TableCell>
                            <TableCell align="right">{row.uuDai}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}
