import { Box, Grid } from '@mui/material';

export default function ImagePitch({ images }) {
    return (
        <Grid container spacing={1}>
            <Grid item xs={8}>
                <Grid container spacing={1}>
                    <Grid item xs={12}>
                        <Box sx={{ height: 310 }}>
                            <img src={images[0].url} alt="" style={{ width: '100%', maxHeight: '100%' }} />
                        </Box>
                    </Grid>
                    <Grid item xs={5}>
                        <Box sx={{ height: 160 }}>
                            <img src={images[1].url} alt="" style={{ width: '100%', maxHeight: '100%' }} />
                        </Box>
                    </Grid>
                    <Grid item xs={7}>
                        <Box sx={{ height: 160 }}>
                            <img src={images[2].url} alt="" style={{ width: '100%', maxHeight: '100%' }} />
                        </Box>
                    </Grid>
                </Grid>
            </Grid>
            <Grid item xs={4}>
                <Grid container spacing={1}>
                    <Grid item xs={12}>
                        <Box sx={{ height: 200 }}>
                            <img src={images[3].url} alt="" style={{ width: '100%', maxHeight: '100%' }} />
                        </Box>
                    </Grid>
                    <Grid item xs={12}>
                        <Box sx={{ height: 280 }}>
                            <img src={images[4].url} alt="" style={{ width: '100%', maxHeight: '100%' }} />
                        </Box>
                    </Grid>
                </Grid>
            </Grid>
        </Grid>
    );
}
