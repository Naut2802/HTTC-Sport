import { Box } from '@mui/material';

import sanQN_1 from '~/components/Images/sanquynhnhu/anh_san_1_1.png';

function HinhSan() {
    return (
        <Box className="card" component="form" noValidate>
            <p className="fs-2 text-center mt-2 fw-bold">Sân Quỳnh Như</p>
            <img src={sanQN_1} atl="" />
        </Box>
    );
}

export default HinhSan;
