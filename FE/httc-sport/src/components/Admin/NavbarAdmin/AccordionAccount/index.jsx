import ContactPageIcon from '@mui/icons-material/ContactPage';
import LogoutIcon from '@mui/icons-material/Logout';
import PersonPinIcon from '@mui/icons-material/PersonPin';
import { Box, Typography } from '@mui/material';
import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import * as React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

import { handleLogoutAPI } from '~/apis';

export default function AccordionAccount() {
    const [anchorEl, setAnchorEl] = React.useState(null);
    const open = Boolean(anchorEl);
    const navigate = useNavigate();

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleLogOut = async () => {
        await handleLogoutAPI();
        toast.info('Bạn đã đăng xuất!');
        localStorage.removeItem('accessToken');
        localStorage.removeItem('token');
        localStorage.removeItem('role');
        localStorage.removeItem('userId');
        navigate('/trang-chu');
    };

    return (
        <>
            <Box sx={{ flexGrow: 1 }} />
            <Typography variant="h6" component="div">
                <Button
                    id="basic-button"
                    aria-controls={open ? 'basic-menu' : undefined}
                    aria-haspopup="true"
                    aria-expanded={open ? 'true' : undefined}
                    onClick={handleClick}
                >
                    <PersonPinIcon sx={{ color: 'white', '&:hover': { color: 'green' } }} />
                </Button>
                <Menu
                    id="basic-menu"
                    anchorEl={anchorEl}
                    open={open}
                    onClose={handleClose}
                    MenuListProps={{
                        'aria-labelledby': 'basic-button',
                    }}
                >
                    <div className="d-flex flex-column">
                        <Typography
                            component={Link}
                            to="/admin/thong-tin-tai-khoan"
                            className="mx-4 my-2 text-decoration-none"
                            sx={{ color: 'black', '&:hover': { color: 'green' } }}
                        >
                            <ContactPageIcon sx={{ marginBottom: '4px' }} /> Thông Tin Tài Khoản
                        </Typography>
                        <Button
                            sx={{
                                fontSize: '1rem',
                                fontWeight: 400,
                                textTransform: 'none',
                                justifyContent: 'start',
                                pl: 2,
                                color: 'black',
                                '&:hover': {
                                    color: 'green',
                                },
                            }}
                            size="large"
                            onClick={handleLogOut}
                        >
                            <LogoutIcon sx={{ marginBottom: '4px', mx: 1 }} /> Đăng Xuất
                        </Button>
                    </div>
                </Menu>
            </Typography>
        </>
    );
}
