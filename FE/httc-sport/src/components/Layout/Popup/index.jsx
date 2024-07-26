import { Dialog, DialogContent } from '@mui/material';
import React from 'react';

function Popup(props) {
    const { children, openPopup, setOpenPopup } = props;

    return (
        <>
            <Dialog open={openPopup} onClose={() => setOpenPopup(false)} maxWidth="lg">
                <DialogContent dividers>{children}</DialogContent>
            </Dialog>
        </>
    );
}

export default Popup;
