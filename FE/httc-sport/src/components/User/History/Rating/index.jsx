import * as React from 'react';
import Box from '@mui/material/Box';
import Rating from '@mui/material/Rating';
import StarIcon from '@mui/icons-material/Star';


import { styled } from '@mui/material/styles';
import { Button, TextField, Typography } from '@mui/material';

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

function Racting() {
  const [value, setValue] = React.useState(null);
  const [hover, setHover] = React.useState(-1);

  const ValidationTextField = styled(TextField)({
    width: '100%',
    '& input:valid + fieldset': {
        borderColor: '#E0E3E7',
        borderWidth: 1,
    },
    '& input:invalid + fieldset': {
        borderColor: 'red',
        borderWidth: 1,
    },
    '& input:valid:focus + fieldset': {
        borderLeftWidth: 4,
        padding: '4px !important', // override inline-style
    },
});

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
                onChange={(event, newValue) => {
                    setValue(newValue);
                }}
                onChangeActive={(event, newHover) => {
                    setHover(newHover);
                }}
                emptyIcon={<StarIcon style={{ opacity: 0.55 }} fontSize="inherit" />}
            />
            <Box sx={{ mt: 1 }}>
            <p>{labels[hover !== -1 ? hover : value !== null ? value : 0]}</p>
            </Box>
            <Box sx={{ mt: 1 }}>
            </Box>
            <Typography component="div" className="w-100">
                <TextField
                    label="Đánh Giá"
                    variant="outlined"
                    id="validation-outlined-input"
                    defaultValue=""
                    className="my-2 w-100"
                    type="text"
                />
            </Typography>
            <Typography
                component="div"
                className="d-flex w-100 justify-content-between align-items-center my-2"
            >
                <div className="d-flex align-items-center"></div>
                <Button variant="contained" className="text-capitalize">
                    Đánh Giá
                </Button>
            </Typography>
        </Box>
  );
}

export default Racting
