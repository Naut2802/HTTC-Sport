import * as React from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";

function createData(tgBD, tgKT, gia) {
  return { tgBD, tgKT, gia };
}

const rows = [
  createData("06:00", "10:00", "200.000"),
  createData("10:00", "16:00", "220.000"),
  createData("16:00", "23:00", "250.000"),
];

function ThoiGianHot() {
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 250 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>Thời Gian Bắt Đầu</TableCell>
            <TableCell>Thời Gian Kết Thúc</TableCell>
            <TableCell align="right">Giá</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <TableRow key={row.name} sx={{ "&:last-child td, &:last-child th": { border: 0 } }}>
              <TableCell component="th" scope="row">
                {row.tgBD}
              </TableCell>
              <TableCell>{row.tgKT}</TableCell>
              <TableCell align="right">{row.gia}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

export default ThoiGianHot;
