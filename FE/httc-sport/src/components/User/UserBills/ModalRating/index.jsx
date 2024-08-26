import StarIcon from '@mui/icons-material/Star';
import { Box, Button, Modal, Rating, TextField, Typography } from '@mui/material';
import { useState } from 'react';

import { handleReviewPitch } from '~/apis';

const labels = {
    1: 'Tôi Không Thích',
    2: 'Tôi Cảm Thấy Hơi Tệ',
    3: 'Tôi Cảm Thấy Bình Thường',
    4: 'Tôi Rất Thích',
    5: 'Tôi Rất Hài Lòng Về Dịch Vụ',
};

function getLabelText(value) {
    return `${value} Star${value !== 1 ? 's' : ''}, ${labels[value]}`;
}

export default function ModalRating({ open, handleClose, selectedBillId }) {
    const style = {
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 400,
        bgcolor: 'background.paper',
        border: '2px solid #000',
        boxShadow: 24,
        p: 4,
    };

    // Khởi tạo giá trị của rating là số 0 thay vì chuỗi rỗng
    const [value, setValue] = useState(0);
    const [hover, setHover] = useState(-1);
    const [description, setDescription] = useState('');

    const handleChangeValue = (event, newValue) => {
        setValue(newValue);
    };

    const handleChangeActive = (event, newHover) => {
        setHover(newHover);
    };

    const handleChangeDescription = (event) => {
        setDescription(event.target.value);
    };

    const submitReview = async () => {
        if (value === 0) {
            alert('Vui lòng chọn mức đánh giá!');
            return;
        }

        const data = {
            rate: value,
            description: description,
        };

        try {
            const response = await handleReviewPitch(selectedBillId, data);
            console.log('Đánh giá thành công:', response);
            handleClose(); // Đóng modal sau khi đánh giá thành công
        } catch (error) {
            console.error('Đã xảy ra lỗi:', error);
        }
    };

    return (
        <Modal open={open} onClose={handleClose} aria-labelledby="modal-modal-title" aria-describedby="modal-modal-description">
            <Box sx={style}>
                <Typography id="modal-modal-title" variant="h6" component="h2" align="center">
                    Đánh Giá
                </Typography>
                <Typography component="div" id="modal-modal-description" sx={{ mt: 2 }}>
                    <Box
                        sx={{
                            width: '100%',
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                        }}
                    >
                        <Rating
                            name="hover-feedback"
                            value={value}
                            getLabelText={getLabelText}
                            onChange={handleChangeValue}
                            onChangeActive={handleChangeActive}
                            emptyIcon={<StarIcon style={{ opacity: 0.55 }} fontSize="inherit" />}
                        />
                        <Box sx={{ mt: 1 }}>
                            <Typography component="div" variant="body2">
                                {labels[hover !== -1 ? hover : value] || 'Chưa có đánh giá'}
                            </Typography>
                        </Box>
                        <Box sx={{ mt: 1 }}></Box>
                        <Box sx={{ width: '100%', mt: 1 }}>
                            <TextField
                                label="Mô Tả"
                                variant="outlined"
                                id="validation-outlined-input"
                                value={description}
                                onChange={handleChangeDescription}
                                fullWidth
                                multiline
                                rows={4}
                            />
                        </Box>
                        <Box
                            sx={{
                                width: '100%',
                                display: 'flex',
                                justifyContent: 'space-between',
                                alignItems: 'center',
                                mt: 2,
                            }}
                        >
                            <Button variant="contained" onClick={submitReview}>
                                Đánh Giá
                            </Button>
                        </Box>
                    </Box>
                </Typography>
            </Box>
        </Modal>
    );
}
