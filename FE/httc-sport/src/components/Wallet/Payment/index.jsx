import { Box, Breadcrumbs, Button, FormControl, Grid, InputLabel, MenuItem, Select, TextField, Typography } from '@mui/material';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { toast } from 'react-toastify';
import { handleCreatePaymentLinkWallet, handleGetMyInfoAPI, handleTopUpUser } from '~/apis';

import logo from '~/components/Images/logo.png';

export default function Payment() {
    const [money, setMoney] = useState('');
    const [user, setUser] = useState();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const responseUser = await handleGetMyInfoAPI();
                const dataUser = responseUser.data.result;
                setUser(dataUser);
            } catch (error) {
                console.error('Lỗi đỗ dữ liệu: ', error);
            }
        };
        fetchData();
    }, []);

    const handleChangeMoney = (event) => {
        setMoney(event.target.value);
    };
    const handlPayment = async () => {
        const id = user?.wallet.id;
        const amount = parseInt(money);

        if (id === null) {
            return;
        }
        const dataUser = {
            walletId: id,
            paymentAmount: amount,
        };
        console.log(user);

        const response = await handleTopUpUser(dataUser);
        const idPayment = response.data.result.id;
        console.log('Nạp Thành Công', response.data);
        try {
            const res = await handleCreatePaymentLinkWallet(idPayment);
            console.log(res);
            if (res.data.result.success) {
                window.location.href = res.data.result.checkoutUrl;
            } else {
                toast.warning('Có lỗi xảy ra !!!');
            }
        } catch (error) {
            console.error('Lỗi nạp tiền: ', error);
        }
    };

    return (
        <div className="my-3 container">
            <div className="d-flex justify-content-center">
                <img src={logo} alt="" style={{ width: 120 }} />
            </div>
            <Typography className="fs-3 fw-bold mt-3 mb-2">Nạp tiền ví</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
                <Typography className="text-decoration-none text-dark fs-6" variant="h6" noWrap component={Link} to="/">
                    Trang Chủ
                </Typography>
                <Typography
                    className="text-decoration-none text-dark fs-6"
                    variant="h6"
                    noWrap
                    component={Link}
                    to="/lich-su-giao-dich"
                >
                    Nạp tiền ví
                </Typography>
            </Breadcrumbs>
            <hr />
            <Grid sx={{ my: 4 }} container spacing={2}>
                <Grid item xs={3}></Grid>
                <Grid item xs={6}>
                    <Box className="card" component="form" noValidate>
                        <Typography className="card-header text-center fs-3" variant="h6" component="div">
                            Nạp tiền Ví
                        </Typography>
                        <Typography component="div" className="card-body ">
                            <Box className="card" noValidate>
                                <Typography component="div" className="card-body">
                                    <Grid container spacing={1}>
                                        <Grid item xs={12}>
                                            <Typography component={'span'}>
                                                <TextField
                                                    label="Nhập số tiền"
                                                    variant="outlined"
                                                    fullWidth
                                                    value={money}
                                                    onChange={(e) => setMoney(e.target.value)}
                                                    InputLabelProps={{ shrink: true }}
                                                />
                                            </Typography>
                                        </Grid>
                                        <Grid item xs={12}>
                                            <FormControl variant="standard" fullWidth>
                                                <InputLabel id="demo-simple-select-label">Chọn mệnh giá nạp</InputLabel>
                                                <Select
                                                    labelId="demo-simple-select-label"
                                                    id="demo-simple-select"
                                                    value={money}
                                                    label="Chọn số tiền muốn nạp"
                                                    onChange={handleChangeMoney} // Cập nhật giá trị khi người dùng chọn một mệnh giá
                                                >
                                                    <MenuItem value={'20000'}>20.000</MenuItem>
                                                    <MenuItem value={'50000'}>50.000</MenuItem>
                                                    <MenuItem value={'100000'}>100.000</MenuItem>
                                                    <MenuItem value={'200000'}>200.000</MenuItem>
                                                    <MenuItem value={'500000'}>500.000</MenuItem>
                                                </Select>
                                            </FormControl>
                                        </Grid>
                                    </Grid>
                                </Typography>
                            </Box>
                        </Typography>
                        <Typography component="div" className="text-end card-footer">
                            <Button
                                sx={{
                                    '&:hover': {
                                        color: 'white',
                                        backgroundColor: 'darkseagreen',
                                    },
                                }}
                                variant="outlined"
                                color="success"
                                className="text-capitalize ms-2"
                                onClick={handlPayment}
                            >
                                Nạp Tiền
                            </Button>
                        </Typography>
                    </Box>
                </Grid>
                <Grid item xs={3}></Grid>
            </Grid>
        </div>
    );
}
