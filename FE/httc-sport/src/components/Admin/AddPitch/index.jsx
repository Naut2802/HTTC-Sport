import { Breadcrumbs, Typography } from '@mui/material';
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import FormAddPitch from './FormAddPitch';
import TableListPitch from './TableListPitch';

export default function AddPitch() {
    const [selectedPitch, setSelectedPitch] = useState(null);

    const handleRowClick = (pitch) => {
        setSelectedPitch(pitch);
    };

    return (
        <div className="my-3 container">
            <Typography className="fs-3 fw-bold mt-3 mb-2">Thêm Sân</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/admin/trang-chu">
                    Trang Chủ
                </Typography>
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/admin/them-san">
                    Thêm Sân
                </Typography>
            </Breadcrumbs>
            <hr />

            <FormAddPitch selectedPitch={selectedPitch} />

            <TableListPitch onRowClick={handleRowClick} />
        </div>
    );
}
