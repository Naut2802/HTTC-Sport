import { Breadcrumbs, Grid, Typography } from '@mui/material';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { handleGetPitches, handleGetPitchesWithFilter, handleProvinces } from '~/apis';

import logo from '~/components/Images/logo.png';
import List from './List';
import PitchCategories from './PitchCategories';

export default function PitchList() {
    const [pitches, setPitches] = useState([]);
    const [dataCity, setDataCity] = useState(null);
    const [districts, setDistricts] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const res = await handleGetPitches();
                setPitches(res.data.result);
            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, []);

    useEffect(() => {
        const fetchProvinces = async () => {
            try {
                const res = await handleProvinces();
                const hcmCity = res.data[49];
                setDataCity(res.data[49].Name);
                setDistricts(hcmCity ? hcmCity.Districts : []);
            } catch (error) {
                console.error('Error fetching data: ', error);
                setDataCity([]);
            }
        };
        fetchProvinces();
    }, []);

    const updatePitches = async (data) => {
        try {
            const res = await handleGetPitchesWithFilter(data);
            setPitches(res.data.result);
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <div className="container my-3">
            <div className="d-flex justify-content-center">
                <img src={logo} alt="" style={{ width: 120 }} />
            </div>
            <Typography className="fs-3 fw-bolder">Danh Sách Sân</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="mb-2">
                <Typography className="text-decoration-none text-secondary fs-6" variant="h6" noWrap component={Link} to="/">
                    Trang Chủ
                </Typography>
                <Typography className="text-decoration-none text-dark fs-6" variant="h6" noWrap component={Link} to="/san-bong">
                    Danh Sách Sân
                </Typography>
            </Breadcrumbs>
            <Grid container spacing={4}>
                <Grid item xs={3}>
                    <PitchCategories updatePitches={updatePitches} dataCity={dataCity} districts={districts} />
                </Grid>
                <Grid item xs={9}>
                    <List pitches={pitches} />
                </Grid>
            </Grid>
        </div>
    );
}
