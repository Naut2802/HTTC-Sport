import { Accordion, AccordionDetails, AccordionSummary, Typography } from '@mui/material';
import { Link } from 'react-router-dom';

import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import AppRegistrationIcon from '@mui/icons-material/AppRegistration';
import ComputerIcon from '@mui/icons-material/Computer';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import HomeIcon from '@mui/icons-material/Home';
import MenuBookIcon from '@mui/icons-material/MenuBook';
import PendingActionsIcon from '@mui/icons-material/PendingActions';
import SportsSoccerIcon from '@mui/icons-material/SportsSoccer';
import SupervisorAccountIcon from '@mui/icons-material/SupervisorAccount';

export default function AccordionAdmin() {
    return (
        <>
            <Typography component="div" sx={{ color: '#595C5F' }}>
                <p className="m-2">HOME</p>
                <Typography
                    component={Link}
                    to="/admin/trang-chu"
                    className="mx-4 text-decoration-none"
                    sx={{ color: '#595C5F', '&:hover': { color: 'blue' } }}
                >
                    <HomeIcon sx={{ marginBottom: '3px' }} /> Trang Chủ
                </Typography>
            </Typography>
            <Typography component="div" className="mt-3" sx={{ color: '#595C5F' }}>
                <p className="m-2">Quản Lý</p>
                <Accordion className="mb-2" sx={{ color: '#595C5F' }}>
                    <AccordionSummary expandIcon={<ExpandMoreIcon />} aria-controls="panel2-content" id="panel2-header">
                        <Typography className="fs-6">
                            <ComputerIcon sx={{ marginBottom: '4px' }} /> Quản Lý Sân
                        </Typography>
                    </AccordionSummary>
                    <AccordionDetails>
                        <div className="d-flex flex-column">
                            <Typography
                                component={Link}
                                to="/admin/danh-sach-san"
                                className="mx-2 mb-2 text-decoration-none"
                                sx={{ color: '#595C5F', '&:hover': { color: 'blue' } }}
                            >
                                <SportsSoccerIcon sx={{ marginBottom: '4px' }} /> Danh Sách Sân
                            </Typography>
                            <Typography
                                component={Link}
                                to="/admin/them-san"
                                className="mx-2 my-2 text-decoration-none"
                                sx={{ color: '#595C5F', '&:hover': { color: 'blue' } }}
                            >
                                <AddCircleOutlineIcon sx={{ marginBottom: '4px' }} /> Thêm Sân
                            </Typography>
                            <Typography
                                component={Link}
                                to="/admin/thong-tin-dat-san"
                                className="mx-2 my-2 text-decoration-none"
                                sx={{ color: '#595C5F', '&:hover': { color: 'blue' } }}
                            >
                                <AppRegistrationIcon sx={{ marginBottom: '4px' }} /> Thông Tin Đặt Sân
                            </Typography>
                            <Typography
                                component={Link}
                                to="/admin/thong-ke"
                                className="mx-2 my-2 text-decoration-none"
                                sx={{ color: '#595C5F', '&:hover': { color: 'blue' } }}
                            >
                                <MenuBookIcon sx={{ marginBottom: '4px' }} /> Thống Kê
                            </Typography>
                            <Typography
                                component={Link}
                                to="/admin/khach-hang"
                                className="mx-2 my-2 text-decoration-none"
                                sx={{ color: '#595C5F', '&:hover': { color: 'blue' } }}
                            >
                                <SupervisorAccountIcon sx={{ marginBottom: '4px' }} /> Khách Hàng
                            </Typography>
                        </div>
                    </AccordionDetails>
                </Accordion>
            </Typography>
            <Typography component="div" sx={{ color: '#595C5F' }}>
                <p className="m-2">Khác</p>
                <Typography
                    component={Link}
                    to="/san-bong"
                    className="mx-4 my-2 text-decoration-none"
                    sx={{ color: '#595C5F', '&:hover': { color: 'blue' } }}
                >
                    <PendingActionsIcon sx={{ marginBottom: '4px' }} /> Đặt Sân
                </Typography>
            </Typography>
        </>
    );
}
