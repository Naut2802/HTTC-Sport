/* eslint-disable react-hooks/exhaustive-deps */
import StarRoundedIcon from '@mui/icons-material/StarRounded';
import { Box, Card, CardContent, TextField, Typography } from '@mui/material';
import { useEffect, useState } from 'react';
import { handleGetPitch } from '~/apis';

export default function Comments({ id }) {
    const [reviews, setReviews] = useState([]);

    useEffect(() => {
        const fechData = async () => {
            try {
                const re = await handleGetPitch(id);
                console.log(re.data.result.reviews[0].rate);
                setReviews(re.data.result.reviews);
                console.log(reviews);
            } catch (error) {}
        };
        fechData();
    }, []);

    return (
        <Box>
            {reviews.map((review, index) => (
                <Card key={index} sx={{ width: '100%', mb: 2 }}>
                    <CardContent sx={{ px: 5 }}>
                        <Typography variant="h6" className="my-2 text-center">
                            Đánh Giá
                        </Typography>
                        <div className="row my-2">
                            <div className="col-6">
                                <Typography sx={{ fontSize: 17 }} variant="subtitle2" color="text.dark">
                                    từ {review.firstname ? review.firstname : review.username}
                                </Typography>
                            </div>
                            <div className="col-6 d-flex justify-content-end">
                                <Typography sx={{ fontSize: 17 }} variant="subtitle2" color="text.dark">
                                    {review.rate}
                                    <StarRoundedIcon sx={{ mb: 1, color: '#FFC107' }} />
                                </Typography>
                            </div>
                        </div>
                        <div className="my-2">
                            <TextField
                                fullWidth
                                variant="standard"
                                InputProps={{
                                    readOnly: true,
                                }}
                                label="Mô tả"
                                value={review.description}
                            />
                        </div>
                    </CardContent>
                </Card>
            ))}
        </Box>
    );
}
