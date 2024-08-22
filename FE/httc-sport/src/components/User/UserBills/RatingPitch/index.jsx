import StarIcon from '@mui/icons-material/Star';
import Box from '@mui/material/Box';
import Rating from '@mui/material/Rating';
import { useState } from 'react';

import { Button, TextField, Typography } from '@mui/material';
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

export default function RatingPitch({ billId }) {
    // Receive the ID as a prop
    const [value, setValue] = useState('');
    const [hover, setHover] = useState(-1);
    const [description, setDescription] = useState('');

    const handleChangeValue = (e, newValue) => {
        setValue(newValue);
    };

    const handleChangeActive = (e, newHover) => {
        setHover(newHover);
    };

    const handleChangeDescription = (e) => {
        setDescription(e.target.value);
    };
    const submitReview = async () => {
        // console.log('Submitting review for bill ID:', billId, 'with description:', description);
        const data = {
            rate: value,
            description: description,
        };

        try {
            const re = await handleReviewPitch(billId, data);
            console.log(re);
        } catch (error) {
            console.error(error);
        }
    };

    return (
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
                <Typography variant="body2">{labels[hover !== -1 ? hover : value !== null ? value : 0]}</Typography>
            </Box>
            <Box sx={{ mt: 1 }}></Box>
            <Box sx={{ width: '100%', mt: 1 }}>
                <TextField
                    label="Đánh Giá"
                    variant="outlined"
                    id="validation-outlined-input"
                    defaultValue=""
                    className="my-2 w-100"
                    type="text"
                    value={description}
                    onChange={handleChangeDescription}
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
                <Button variant="contained" className="text-capitalize" onClick={submitReview}>
                    Đánh Giá
                </Button>
            </Box>
        </Box>
    );
}
