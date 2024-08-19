/* eslint-disable no-restricted-globals */
import LoginIcon from '@mui/icons-material/Login';
import MenuIcon from '@mui/icons-material/Menu';
import { AppBar, Box, Button, Container, Grid, IconButton, Menu, MenuItem, Toolbar, Tooltip, Typography } from '@mui/material';
import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import slugify from 'slugify'; // Import slugify nè

import AccountBalanceWalletIcon from '@mui/icons-material/AccountBalanceWallet';
import AccountBoxIcon from '@mui/icons-material/AccountBox';
import AdminPanelSettingsIcon from '@mui/icons-material/AdminPanelSettings';
import { handleGetMyInfoAPI, handleLogoutAPI } from '~/apis';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';

import LoginWithModal from '~/components/Account/LoginWithModal';
import Popup from '../Popup';

const pages = ['Trang Chủ', 'Sân Bóng', 'Tin Tức', 'Liên Hệ']; // Mảng trang trên navbar nè
const settings = ['Tài Khoản', 'Thông Tin Đặt Sân', 'Lịch Sử Giao Dịch']; //Mảng dòng của cái avatar click ra nè
const vi = ['Nạp Tiền', 'Lịch Sử Giao Dịch Ví'];

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
    const [openPopup, setOpenPopup] = useState(false);
    const [ViUser, setViUser] = useState(null);
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
                    console.log(dataUser);
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

    const handleOpenNavMenu = (event) => {
        setAnchorElNav(event.currentTarget);
    };

    const handleOpenUserMenu = (event) => {
        setAnchorElUser(event.currentTarget);
    };

    const handleOpenVi = (event) => {
        setViUser(event.currentTarget);
    };

    const handleCloseNavMenu = () => {
        setAnchorElNav(null);
    };

    const handleCloseUserMenu = () => {
        setAnchorElUser(null);
    };

    const handleCloseVi = () => {
        setViUser(null);
    };

    // const Search = styled('div')(({ theme }) => ({
    //     position: 'relative',
    //     borderRadius: theme.shape.borderRadius,
    //     backgroundColor: alpha(theme.palette.common.black, 0.15),
    //     '&:hover': {
    //         backgroundColor: alpha(theme.palette.common.black, 0.25),
    //     },
    //     marginLeft: 0,
    //     width: '100%',
    //     [theme.breakpoints.up('sm')]: {
    //         marginLeft: theme.spacing(1),
    //         width: 'auto',
    //     },
    //     color: 'black',
    // }));

    // const SearchIconWrapper = styled('div')(({ theme }) => ({
    //     padding: theme.spacing(0, 2),
    //     height: '100%',
    //     position: 'absolute',
    //     pointerEvents: 'none',
    //     display: 'flex',
    //     alignItems: 'center',
    //     justifyContent: 'center',
    //     color: 'black',
    // }));

    // const StyledInputBase = styled(InputBase)(({ theme }) => ({
    //     width: '100%',
    //     '& .MuiInputBase-input': {
    //         padding: theme.spacing(1, 1, 1, 0),
    //         paddingLeft: `calc(1em + ${theme.spacing(4)})`,
    //         transition: theme.transitions.create('width'),
    //         [theme.breakpoints.up('sm')]: {
    //             width: '12ch',
    //             '&:focus': {
    //                 width: '20ch',
    //             },
    //         },
    //     },
    // }));

    return (
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
                    {/* <Box>
                        <Search>
                            <SearchIconWrapper>
                                <SearchIcon
                                    sx={{
                                        // Tìm Kiếm
                                        color: 'black',
                                    }}
                                />
                            </SearchIconWrapper>
                            <StyledInputBase placeholder="Tìm kiếm..." inputProps={{ 'aria-label': 'search' }} />
                        </Search>
                    </Box> */}
                    {!checkUser ? (
                        <Button
                            onClick={() => setOpenPopup(true)}
                            sx={{ ml: 1, color: 'teal' }}
                            startIcon={<LoginIcon />}
                            size="small"
                        >
                            Đăng nhập
                        </Button>
                    ) : (
                        <Box sx={{ flexGrow: 0, marginLeft: 2 }} className="d-flex">
                            <Typography component="div" className="text-dark">
                                Xin Chào : {user?.lastName} {user?.firstName}
                            </Typography>
                            <Tooltip title="Ví" className="mx-4">
                                <IconButton sx={{ p: 0 }} onClick={handleOpenVi}>
                                    <Typography sx={{ color: '#5A5A5A' }}>
                                        <AccountBalanceWalletIcon />
                                    </Typography>
                                </IconButton>
                            </Tooltip>
                            <Menu
                                sx={{ mt: '45px' }}
                                id="menu-appbar"
                                anchorEl={ViUser}
                                anchorOrigin={{
                                    vertical: 'top',
                                    horizontal: 'right',
                                }}
                                keepMounted
                                transformOrigin={{
                                    vertical: 'top',
                                    horizontal: 'right',
                                }}
                                open={Boolean(ViUser)}
                                onClose={handleCloseVi}
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
                                {vi.map((vi) => (
                                    <MenuItem key={vi} onClick={handleCloseUserMenu}>
                                        <Typography component="div" textAlign="center">
                                            <Typography
                                                component={Link}
                                                to={`/${slugify(vi, { lower: true, strict: true, locale: 'vi' })}`}
                                                className="text-decoration-none text-dark"
                                            >
                                                {vi}
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

            <Popup openPopup={openPopup} setOpenPopup={setOpenPopup}>
                <Grid container>
                    <Grid item>
                        <LoginWithModal />
                    </Grid>
                </Grid>
            </Popup>
        </AppBar>
    );
}
