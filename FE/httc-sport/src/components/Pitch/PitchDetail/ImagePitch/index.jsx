import { Box, Grid } from '@mui/material';

export default function ImagePitch({ images }) {
    return (
        <Grid container spacing={1}>
            <Grid item xs={8}>
                <Grid container spacing={1}>
                    <Grid item xs={12}>
                        <Box sx={{ height: 310 }}>
                            <img
                                src={images[0] && images[0].url ? images[0].url : 'default-image-url.jpg'}
                                alt="Không có ảnh"
                                style={{ width: '100%', maxHeight: '100%' }}
                            />
                        </Box>
                    </Grid>
                    <Grid item xs={5}>
                        <Box sx={{ height: 170 }}>
                            <img
                                src={images[1] && images[1].url ? images[1].url : 'default-image-url.jpg'}
                                alt="Không có ảnh"
                                style={{ width: '100%', maxHeight: '100%' }}
                            />
                        </Box>
                    </Grid>
                    <Grid item xs={7}>
                        <Box sx={{ height: 170 }}>
                            <img
                                src={images[2] && images[2].url ? images[2].url : 'default-image-url.jpg'}
                                alt="Không có ảnh"
                                style={{ width: '100%', maxHeight: '100%' }}
                            />
                        </Box>
                    </Grid>
                </Grid>
            </Grid>
            <Grid item xs={4}>
                <Grid container spacing={1}>
                    <Grid item xs={12}>
                        <Box sx={{ height: 260 }}>
                            <img
                                src={images[3] && images[3].url ? images[3].url : 'default-image-url.jpg'}
                                alt="Không có ảnh"
                                style={{ width: '100%', maxHeight: '100%' }}
                            />
                        </Box>
                    </Grid>
                    <Grid item xs={12}>
                        <Box sx={{ height: 220 }}>
                            <img
                                src={images[4] && images[4].url ? images[4].url : 'default-image-url.jpg'}
                                alt="Không có ảnh"
                                style={{ width: '100%', maxHeight: '100%' }}
                            />
                        </Box>
                    </Grid>
                </Grid>
            </Grid>
        </Grid>
    );
}
