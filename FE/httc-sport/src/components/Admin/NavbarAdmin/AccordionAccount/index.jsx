import ContactPageIcon from '@mui/icons-material/ContactPage';
import LogoutIcon from '@mui/icons-material/Logout';
import PersonPinIcon from '@mui/icons-material/PersonPin';
import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import * as React from 'react';

import { Box, Typography } from '@mui/material';
import { Link } from 'react-router-dom';

export default function AccordionAccount() {
    const [anchorEl, setAnchorEl] = React.useState(null);
    const open = Boolean(anchorEl);
    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
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
                    <PersonPinIcon sx={{ color: 'white', '&:hover': { color: 'blue' } }} />
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
                            sx={{ color: '#595C5F', '&:hover': { color: 'blue' } }}
                        >
                            <ContactPageIcon sx={{ marginBottom: '4px' }} /> Thông Tin Tài Khoản
                        </Typography>
                        <Typography
                            component={Link}
                            to="/log-out"
                            className="mx-4 my-2 text-decoration-none"
                            sx={{ color: '#595C5F', '&:hover': { color: 'blue' } }}
                        >
                            <LogoutIcon sx={{ marginBottom: '4px' }} /> Đăng Xuất
                        </Typography>
                    </div>
                </Menu>
            </Typography>
        </>
    );
}
