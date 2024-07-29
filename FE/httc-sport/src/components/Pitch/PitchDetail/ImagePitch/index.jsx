import { Box, Grid } from '@mui/material';

import san1 from '~/components/Images/sanquynhnhu/anh_san_1_1.png';
import san2 from '~/components/Images/sanquynhnhu/anh_san_1_2.png';
import san3 from '~/components/Images/sanquynhnhu/anh_san_1_3.png';
import san4 from '~/components/Images/sanquynhnhu/anh_san_1_4.png';

export default function ImagePitch() {
    return (
        <Grid container spacing={1}>
            <Grid item xs={8}>
                <Grid container spacing={1}>
                    <Grid item xs={12}>
                        <Box sx={{ height: 310 }}>
                            <img src={san3} alt="" style={{ width: '100%', maxHeight: '100%' }} />
                        </Box>
                    </Grid>
                    <Grid item xs={5}>
                        <Box sx={{ height: 160 }}>
                            <img src={san1} alt="" style={{ width: '100%', maxHeight: '100%' }} />
                        </Box>
                    </Grid>
                    <Grid item xs={7}>
                        <Box sx={{ height: 160 }}>
                            <img src={san2} alt="" style={{ width: '100%', maxHeight: '100%' }} />
                        </Box>
                    </Grid>
                </Grid>
            </Grid>
            <Grid item xs={4}>
                <Grid container spacing={1}>
                    <Grid item xs={12}>
                        <Box sx={{ height: 200 }}>
                            <img src={san3} alt="" style={{ width: '100%', maxHeight: '100%' }} />
                        </Box>
                    </Grid>
                    <Grid item xs={12}>
                        <Box sx={{ height: 280 }}>
                            <img src={san4} alt="" style={{ width: '100%', maxHeight: '100%' }} />
                        </Box>
                    </Grid>
                </Grid>
            </Grid>
        </Grid>
    );
}
