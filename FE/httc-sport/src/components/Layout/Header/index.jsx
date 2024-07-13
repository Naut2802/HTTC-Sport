/* eslint-disable no-restricted-globals */
import LoginIcon from '@mui/icons-material/Login';
import MenuIcon from '@mui/icons-material/Menu';
import SearchIcon from '@mui/icons-material/Search';
import {
    AppBar,
    Avatar,
    Box,
    Button,
    Container,
    IconButton,
    InputBase,
    Menu,
    MenuItem,
    Toolbar,
    Tooltip,
    Typography,
} from '@mui/material';
import { alpha, styled } from '@mui/material/styles';
import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import slugify from 'slugify'; // Import slugify nè

import { handleLogoutAPI } from '~/apis';
import avt from '~/components/Images/avt.jpg';

const pages = ['Trang Chủ', 'Sân Bóng', 'Tin Tức', 'Liên Hệ']; // Mảng trang trên navbar nè
const settings = ['Tài Khoản', 'Thông Tin Đặt Sân', 'Lịch Sử Giao Dịch']; //Mảng dòng của cái avatar click ra nè

function Header() {
    const [anchorElNav, setAnchorElNav] = React.useState(null);
    const [anchorElUser, setAnchorElUser] = React.useState(null);
    const navigate = useNavigate();

    var checkUser = null;
    const checkUserInStorage = localStorage.getItem('accessToken');
    if (checkUserInStorage) {
        checkUser = checkUserInStorage;
    }

    const handleLogOut = async () => {
        await handleLogoutAPI();
        toast.info('Bạn đã đăng xuất!');
        localStorage.removeItem('accessToken');
        localStorage.removeItem('userId');
        navigate('/trang-chu');
    };

    const handleOpenNavMenu = (event) => {
        setAnchorElNav(event.currentTarget);
    };

    const handleOpenUserMenu = (event) => {
        setAnchorElUser(event.currentTarget);
    };

    const handleCloseNavMenu = () => {
        setAnchorElNav(null);
    };

    const handleCloseUserMenu = () => {
        setAnchorElUser(null);
    };

    const Search = styled('div')(({ theme }) => ({
        position: 'relative',
        borderRadius: theme.shape.borderRadius,
        backgroundColor: alpha(theme.palette.common.black, 0.15),
        '&:hover': {
            backgroundColor: alpha(theme.palette.common.black, 0.25),
        },
        marginLeft: 0,
        width: '100%',
        [theme.breakpoints.up('sm')]: {
            marginLeft: theme.spacing(1),
            width: 'auto',
        },
        color: 'black',
    }));

    const SearchIconWrapper = styled('div')(({ theme }) => ({
        padding: theme.spacing(0, 2),
        height: '100%',
        position: 'absolute',
        pointerEvents: 'none',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        color: 'black',
    }));

    const StyledInputBase = styled(InputBase)(({ theme }) => ({
        width: '100%',
        '& .MuiInputBase-input': {
            padding: theme.spacing(1, 1, 1, 0),
            paddingLeft: `calc(1em + ${theme.spacing(4)})`,
            transition: theme.transitions.create('width'),
            [theme.breakpoints.up('sm')]: {
                width: '12ch',
                '&:focus': {
                    width: '20ch',
                },
            },
        },
    }));

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
                    <Box>
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
                    </Box>

                    {!checkUser ? (
                        <Typography component={Link} to="/dang-nhap" variant="">
                            <Button sx={{ ml: 1, color: 'teal' }} startIcon={<LoginIcon />} size="small">
                                Đăng nhập
                            </Button>
                        </Typography>
                    ) : (
                        <Box sx={{ flexGrow: 0, marginLeft: 2 }}>
                            <Tooltip title="Open settings">
                                <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                                    <Avatar alt="" src={avt} />
                                </IconButton>
                            </Tooltip>
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
                                                component={Link} //Cái này là hiển thị mảng của Settings nè
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
        </AppBar>
    );
}

export default Header;
