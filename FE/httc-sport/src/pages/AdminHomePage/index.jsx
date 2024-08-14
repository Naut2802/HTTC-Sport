import Box from '@mui/material/Box';
import { styled } from '@mui/material/styles';
import * as React from 'react';
import { Outlet } from 'react-router-dom';
import NavbarAdmin from '~/components/Admin/NavbarAdmin';

const DrawerHeader = styled('div')(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
    justifyContent: 'flex-end',
}));

export default function AdminHomePage() {
    return (
        <Box sx={{ display: 'flex', width: '100%', height: '100vh' }}>
            <NavbarAdmin />
            <Box sx={{ flexGrow: 1, overflow: 'auto' }}>
                <DrawerHeader />
                <div className="container">
                    <Outlet />
                </div>
            </Box>
        </Box>
    );
}
