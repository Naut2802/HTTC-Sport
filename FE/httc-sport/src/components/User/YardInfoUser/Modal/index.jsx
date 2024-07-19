import React from 'react';
import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Typography from '@mui/material/Typography';

import HinhSan from './HinhSan';
import ThongTinChiTiet from './ThongTinChiTiet';



function XemChiTiet({ open, handleClose }) {

    const style = {
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: '80%',
        bgcolor: 'background.paper',
        border: '2px solid #000',
        boxShadow: 24,
        p: 4,
    };

   
    return (
        <Modal
            open={open}
            onClose={handleClose}
            aria-labelledby="modal-modal-title"
            aria-describedby="modal-modal-description"
        >
            <Box sx={style}>
                <Typography id="modal-modal-title" variant="h6" component="h2">
                    Thông Tin Chi Tiết
                </Typography>
                <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                <div className="row">
                    <div className="col-4">
                        <HinhSan/>
                    </div>
                    <div className="col-8">
                        <ThongTinChiTiet/>
                    </div>
                </div>
                </Typography>
            </Box>
        </Modal>
    );
}

export default XemChiTiet;
