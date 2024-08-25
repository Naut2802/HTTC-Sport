import ChatTwoToneIcon from '@mui/icons-material/ChatTwoTone';
import LogoutIcon from '@mui/icons-material/Logout';
import PersonPinIcon from '@mui/icons-material/PersonPin';
import { Box, Fab, Grid, IconButton, Tooltip, Typography } from '@mui/material';
import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import * as React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

import ContactPageIcon from '@mui/icons-material/ContactPage';
import HomeWorkIcon from '@mui/icons-material/HomeWork';

import { handleLogoutAPI } from '~/apis';
import Chat from '~/components/Chat';
import Popup from '~/components/Layout/Popup';

export default function AccordionAccount() {
    const [anchorEl, setAnchorEl] = React.useState(null);
    const [openPopupChat, setOpenPopupChat] = React.useState(false);
    const open = Boolean(anchorEl);
    const navigate = useNavigate();

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleOpenChat = () => {
        setOpenPopupChat(true);
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
            <Fab
                size="large"
                color="success"
                variant="extended"
                sx={{
                    position: 'fixed',
                    bottom: 22,
                    right: 22,
                    zIndex: (theme) => theme.zIndex.drawer + 1,
                }}
                onClick={() => handleOpenChat()}
            >
                <ChatTwoToneIcon sx={{ mr: 1 }} />
                Chat
            </Fab>
            <Box sx={{ flexGrow: 1 }} />
            <Typography variant="h6" component="div">
                <Tooltip title="Trang Chủ">
                    <IconButton component={Link} to="/" sx={{ color: 'white' }}>
                        <HomeWorkIcon />
                    </IconButton>
                </Tooltip>
                <Tooltip title="Tài Khoản" arrow>
                    <IconButton
                        aria-controls={open ? 'basic-menu' : undefined}
                        aria-haspopup="true"
                        aria-expanded={open ? 'true' : undefined}
                        onClick={handleClick}
                        sx={{ color: 'white', '&:hover': { color: 'green' } }}
                    >
                        <PersonPinIcon />
                    </IconButton>
                </Tooltip>
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

            <Popup openPopup={openPopupChat} setOpenPopup={setOpenPopupChat}>
                <Grid container>
                    <Grid item>
                        <Chat />
                    </Grid>
                </Grid>
            </Popup>
        </>
    );
}
