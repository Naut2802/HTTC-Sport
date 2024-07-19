import { Breadcrumbs, Typography } from '@mui/material';
import { Link } from 'react-router-dom';

import logo from '../../Images/logo.png';
import TableDatSanUser from './Table';

function ThongTinUser() {
    // const theme = useTheme();
    // const { count, page, rowsPerPage, onPageChange } = props;

    // const handleFirstPageButtonClick = (event) => {
    //     onPageChange(event, 0);
    // };

    // const handleBackButtonClick = (event) => {
    //     onPageChange(event, page - 1);
    // };

    // const handleNextButtonClick = (event) => {
    //     onPageChange(event, page + 1);
    // };

    // const handleLastPageButtonClick = (event) => {
    //     onPageChange(event, Math.max(0, Math.ceil(count / rowsPerPage) - 1));
    // };

    return (
        <div className="my-3 container">
            <div className="d-flex justify-content-center">
                <img src={logo} alt="" style={{ width: 120 }} />
            </div>
            <Typography className="fs-3 fw-bold mt-3 mb-2">Danh Sách Sân</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
                <Typography className="text-decoration-none text-dark fs-6" variant="h6" noWrap component={Link} to="/">
                    Trang Chủ
                </Typography>
                <Typography
                    className="text-decoration-none text-dark fs-6"
                    variant="h6"
                    noWrap
                    component={Link}
                    to="/thong-tin-dat-san"
                >
                    Thông Tin Đặt Sân
                </Typography>
            </Breadcrumbs>
            <hr />
            <Typography>
                <TableDatSanUser />
            </Typography>
        </div>
    );
}

export default ThongTinUser;
