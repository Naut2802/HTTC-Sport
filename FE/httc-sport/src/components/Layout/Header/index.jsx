/* eslint-disable no-restricted-globals */
// import ChatTwoToneIcon from '@mui/icons-material/ChatTwoTone';
import AccountBalanceWalletIcon from '@mui/icons-material/AccountBalanceWallet';
import AccountBoxIcon from '@mui/icons-material/AccountBox';
import AdminPanelSettingsIcon from '@mui/icons-material/AdminPanelSettings';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import LoginIcon from '@mui/icons-material/Login';
import MenuIcon from '@mui/icons-material/Menu';
import { AppBar, Box, Button, Container, Grid, IconButton, Menu, MenuItem, Toolbar, Tooltip, Typography } from '@mui/material';
import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import slugify from 'slugify'; // Import slugify nè

import { handleGetMyInfoAPI, handleLogoutAPI } from '~/apis';
import LoginWithModal from '~/components/Account/LoginWithModal';
// import Chat from '~/components/Chat';
import Popup from '../Popup';

const pages = ['Trang Chủ', 'Sân Bóng', 'Tin Tức', 'Liên Hệ']; // Mảng trang trên navbar nè
const settings = ['Tài Khoản', 'Thông Tin Đặt Sân', 'Lịch Sử Giao Dịch']; //Mảng dòng của cái avatar click ra nè
const wallet = ['Nạp Tiền', 'Lịch Sử Giao Dịch Ví'];

function formatCurrency(amount) {
    if (amount == null || isNaN(amount)) {
        return '0 ₫';
    }
    return Number(amount).toLocaleString('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
}

export default function Header() {
    const [anchorElNav, setAnchorElNav] = useState(null);
    const [anchorElUser, setAnchorElUser] = useState(null);
    const [openPopupLogin, setOpenPopupLogin] = useState(false);
    // const [openPopupChat, setOpenPopupChat] = useState(false);
    const [userWallet, setUserWallet] = useState(null);
    const [money, setMoney] = useState(null);
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    const checkRole = localStorage.getItem('role');
    var checkUser = null;
    const checkUserInStorage = localStorage.getItem('accessToken');
    if (checkUserInStorage) {
        checkUser = checkUserInStorage;
    }

    useEffect(() => {
        const fetchData = async () => {
            if (checkUser) {
                try {
                    const response = await handleGetMyInfoAPI();
                    const moneyWallet = response.data.result.wallet.money;
                    const dataUser = response.data.result;
                    setUser(dataUser);
                    setMoney(moneyWallet);
                } catch (error) {
                    console.error('Lỗi đổ dữ liệu: ', error);
                }
            }
        };
        fetchData();
    }, [checkUser]);

    const handleLogOut = async () => {
        await handleLogoutAPI();
        toast.info('Bạn đã đăng xuất!');
        localStorage.removeItem('accessToken');
        localStorage.removeItem('token');
        localStorage.removeItem('role');
        localStorage.removeItem('userId');
        navigate('/trang-chu');
    };

    const handleOpenLogin = () => {
        setOpenPopupLogin(true);
    };

    // const handleOpenChat = () => {
    //     setOpenPopupChat(true);
    // };

    const handleOpenNavMenu = (event) => {
        setAnchorElNav(event.currentTarget);
    };

    const handleOpenUserMenu = (event) => {
        setAnchorElUser(event.currentTarget);
    };

    const handleOpenWallet = (event) => {
        setUserWallet(event.currentTarget);
    };

    const handleCloseNavMenu = () => {
        setAnchorElNav(null);
    };

    const handleCloseUserMenu = () => {
        setAnchorElUser(null);
    };

    const handleCloseWallet = () => {
        setUserWallet(null);
    };

    return (
        <>
            {/* {checkUser ? (
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
            ) : (
                <></>
            )} */}

            <AppBar
                position="sticky"
                sx={{
                    backgroundColor: '#CCCCCC',
                    top: 0,
                    zIndex: (theme) => theme.zIndex.drawer + 1,
                }}
            >
                <Container maxWidth="xl">
                    <Toolbar disableGutters>
                        <Typography
                            variant="h6"
                            noWrap
                            component={Link}
                            to="/trang-chu"
                            sx={{
                                mr: 2,
                                display: { xs: 'none', md: 'flex' },
                                fontWeight: 700,
                                letterSpacing: '.3rem',
                                color: 'inherit',
                                textDecoration: 'none',
                            }}
                        >
                            <span className="fs-2 fw-bold text-dark">HTTC Sport</span>
                        </Typography>
                        <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
                            <IconButton
                                size="large"
                                aria-label="account of current user"
                                aria-controls="menu-appbar"
                                aria-haspopup="true"
                                onClick={handleOpenNavMenu}
                                color="inherit"
                            >
                                <MenuIcon sx={{ color: 'black' }} />
                            </IconButton>
                            <Menu
                                id="menu-appbar"
                                anchorEl={anchorElNav}
                                anchorOrigin={{
                                    vertical: 'bottom',
                                    horizontal: 'left',
                                }}
                                keepMounted
                                transformOrigin={{
                                    vertical: 'top',
                                    horizontal: 'left',
                                }}
                                open={Boolean(anchorElNav)}
                                onClose={handleCloseNavMenu}
                                sx={{
                                    display: { xs: 'block', md: 'none' },
                                }}
                            >
                                {pages.map(
                                    (
                                        page, // Dùng map hiển thị Trang Chủ nè
                                    ) => (
                                        <MenuItem key={page} onClick={handleCloseNavMenu}>
                                            <Typography fontSize="capitalize" textAlign="center">
                                                {page}
                                            </Typography>
                                        </MenuItem>
                                    ),
                                )}
                            </Menu>
                        </Box>
                        <Box
                            sx={{
                                flexGrow: 1,
                                display: { xs: 'none', md: 'flex', justifyContent: 'center' },
                            }}
                        >
                            {pages.map((page) => {
                                const slug = slugify(page, { lower: true, strict: true });
                                return (
                                    <Button
                                        key={page}
                                        component={Link}
                                        to={`/${slug}`}
                                        sx={{ my: 2, mx: 2, color: 'black', display: 'block' }}
                                    >
                                        {page}
                                    </Button>
                                );
                            })}
                        </Box>

                        {!checkUser ? (
                            <Button
                                onClick={() => handleOpenLogin()}
                                sx={{ ml: 1, color: 'teal' }}
                                startIcon={<LoginIcon />}
                                size="small"
                            >
                                Đăng nhập
                            </Button>
                        ) : (
                            <Box sx={{ flexGrow: 0, marginLeft: 2 }} className="d-flex">
                                <Typography component="div" className="text-dark">
                                    {user?.firstName ? user?.firstName : user?.username}
                                </Typography>
                                <Tooltip title="Ví" className="mx-4">
                                    <IconButton sx={{ p: 0 }} onClick={handleOpenWallet}>
                                        <Typography sx={{ color: '#5A5A5A' }}>
                                            <AccountBalanceWalletIcon />
                                        </Typography>
                                    </IconButton>
                                </Tooltip>
                                <Menu
                                    sx={{ mt: '45px' }}
                                    id="menu-appbar"
                                    anchorEl={userWallet}
                                    anchorOrigin={{
                                        vertical: 'top',
                                        horizontal: 'right',
                                    }}
                                    keepMounted
                                    transformOrigin={{
                                        vertical: 'top',
                                        horizontal: 'right',
                                    }}
                                    open={Boolean(userWallet)}
                                    onClose={handleCloseWallet}
                                >
                                    <Typography
                                        component="div"
                                        className="container text-decoration-none text-dark mx-2 my-2 d-flex "
                                    >
                                        <Typography component="div" className="align-items-center">
                                            <AttachMoneyIcon />:
                                        </Typography>
                                        <Typography component="div" className="align-items-center fs-6 mx-2">
                                            {formatCurrency(money)}
                                        </Typography>
                                    </Typography>
                                    {wallet.map((wallet) => (
                                        <MenuItem key={wallet} onClick={handleCloseUserMenu}>
                                            <Typography component="div" textAlign="center">
                                                <Typography
                                                    component={Link}
                                                    to={`/${slugify(wallet, { lower: true, strict: true, locale: 'vi' })}`}
                                                    className="text-decoration-none text-dark"
                                                >
                                                    {wallet}
                                                </Typography>
                                            </Typography>
                                        </MenuItem>
                                    ))}
                                </Menu>
                                <Tooltip title="Tài khoản">
                                    <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                                        <AccountBoxIcon />
                                    </IconButton>
                                </Tooltip>
                                {checkRole === 'ADMIN' ? (
                                    <Tooltip title="Admin" className="mx-4">
                                        <IconButton component={Link} to="/admin/trang-chu" sx={{ p: 0 }}>
                                            <AdminPanelSettingsIcon />
                                        </IconButton>
                                    </Tooltip>
                                ) : (
                                    <></>
                                )}

                                <Menu
                                    sx={{ mt: '45px' }}
                                    id="menu-appbar"
                                    anchorEl={anchorElUser}
                                    anchorOrigin={{
                                        vertical: 'top',
                                        horizontal: 'right',
                                    }}
                                    keepMounted
                                    transformOrigin={{
                                        vertical: 'top',
                                        horizontal: 'right',
                                    }}
                                    open={Boolean(anchorElUser)}
                                    onClose={handleCloseUserMenu}
                                >
                                    {settings.map((setting) => (
                                        <MenuItem key={setting} onClick={handleCloseUserMenu}>
                                            <Typography component="div" textAlign="center">
                                                <Typography
                                                    component={Link}
                                                    to={`/${slugify(setting, { lower: true, strict: true, locale: 'vi' })}`}
                                                    className="text-decoration-none text-dark"
                                                >
                                                    {setting}
                                                </Typography>
                                            </Typography>
                                        </MenuItem>
                                    ))}
                                    <Button
                                        sx={{
                                            fontSize: '1rem',
                                            fontWeight: 400,
                                            textTransform: 'none',
                                            justifyContent: 'start',
                                            pl: 2,
                                        }}
                                        size="small"
                                        className="text-dark w-100"
                                        onClick={handleLogOut}
                                    >
                                        Đăng Xuất
                                    </Button>
                                </Menu>
                            </Box>
                        )}
                    </Toolbar>
                </Container>

                <Popup openPopup={openPopupLogin} setOpenPopup={setOpenPopupLogin}>
                    <Grid container>
                        <Grid item>
                            <LoginWithModal />
                        </Grid>
                    </Grid>
                </Popup>
                {/* <Popup openPopup={openPopupChat} setOpenPopup={setOpenPopupChat}>
                    <Grid container>
                        <Grid item>
                            <Chat />
                        </Grid>
                    </Grid>
                </Popup> */}
            </AppBar>
        </>
    );
}
