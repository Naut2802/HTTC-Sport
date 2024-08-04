import { Breadcrumbs, Button, Card, Tooltip, Typography } from '@mui/material';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { toast } from 'react-toastify';
import { handleDeletePitch, handleGetPitch, handleGetPitchesAdmin } from '~/apis';

function formatCurrency(amount) {
    return amount.toLocaleString('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
}

export default function ListPitchs() {
    const [pitches, setPitch] = useState([]);
    const [selectedPitch, setSelectedPitch] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const re = await handleGetPitchesAdmin();
                // console.log(re.data.result);

                setPitch(re.data.result);
            } catch (error) {
                console.error(error);
                toast.error('Đổ dữ liệu thất bại');
            }
        };
        fetchData();
    }, []);

    const handleEditClick = async (data) => {
        const id = data.id;
        console.log(id);
        try {
            const re = await handleGetPitch(id);
            // console.log(re.data.result);
            const dataPitch = re.data.result;
            setSelectedPitch(dataPitch);
            sessionStorage.setItem('selectedPitch', JSON.stringify(dataPitch));
            window.location.href = '/admin/them-san';
        } catch (error) {
            console.error('Lỗi: ', error);
        }
    };

    const handleDelClick = async (pitch) => {
        console.log(pitch);
        const pitchId = pitch;
        if (pitchId === undefined) {
            toast.error('Không thấy ID');
            return;
        }
        try {
            const response = await handleDeletePitch(pitchId);
            console.log('API Response:', response);

            if (response && response.data) {
                toast.success(response.data.message);
                setPitch((prevRows) => prevRows.filter((item) => item.id !== pitchId));
            } else {
                toast.error('Xóa sân thất bại!');
            }
        } catch (error) {
            console.error('Lỗi xóa Sân:', error);
            toast.error('Xóa sân thất bại!');
        }
        window.location.reload();
    };

    return (
        <div>
            <Typography className="fs-3 fw-bold mt-3 mb-2">Danh Sách Sân</Typography>
            <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/admin/trang-chu">
                    Trang Chủ
                </Typography>
                <Typography className="text-decoration-none text-dark" variant="h6" noWrap component={Link} to="/admin/san-bong">
                    Danh Sách Sân
                </Typography>
            </Breadcrumbs>
            {pitches.map((pitch) => (
                <Card className="rounded my-3" key={pitch.id}>
                    <div className="row">
                        <Typography className="col-4 " component="div">
                            <img
                                src={pitch.image && pitch.image.url ? pitch.image.url : 'default-image-url.jpg'}
                                alt={pitch.image ? pitch.pitchName : 'Sân Ngưng Hoạt Động'}
                                style={{
                                    width: '100%',
                                    minHeightt: '50%',
                                }}
                                className="rounded m-2"
                            />
                        </Typography>
                        <Typography className="col-8 mt-2" component="div">
                            <div className="d-flex justify-content-end mx-4">
                                Trạng Thái:
                                <span className={`fw-bold mx-2 ${pitch.isEnabled ? 'text-success' : 'text-danger'}`}>
                                    {pitch.isEnabled ? 'Hoạt Động' : 'Ngưng Hoạt Động'}
                                </span>
                            </div>
                            <div className="fs-3 fw-bold">{pitch.pitchName}</div>
                            <div className="my-1">
                                <span>Loại Sân: {pitch.type}</span>
                            </div>
                            <div className="my-1">
                                Giá:
                                <span className="fw-bolder mx-1">{formatCurrency(pitch.price)}</span>
                            </div>
                            <div className="my-1">
                                <span>Địa Chỉ: {`${pitch.street}, ${pitch.ward}, ${pitch.city}`}</span>
                            </div>
                            <div className="my-1">
                                <span>Mô Tả: {pitch.description}</span>
                            </div>
                            <div className="d-flex justify-content-end">
                                <Tooltip title={pitch.isEnabled ? 'Chỉnh Sửa' : 'Sân không khả dụng'}>
                                    <span>
                                        <Button
                                            variant="contained"
                                            className="my-2"
                                            onClick={() => handleEditClick(pitch)}
                                            disabled={!pitch.isEnabled}
                                        >
                                            Chỉnh Sửa
                                        </Button>
                                    </span>
                                </Tooltip>

                                <Tooltip title={pitch.isEnabled ? 'Xóa' : 'Sân không khả dụng'}>
                                    <Button
                                        variant="contained"
                                        className="my-2 mx-2"
                                        onClick={() => handleDelClick(pitch.id)}
                                        disabled={!pitch.isEnabled}
                                    >
                                        Xóa
                                    </Button>
                                </Tooltip>
                            </div>
                        </Typography>
                    </div>
                </Card>
            ))}
        </div>
    );
}
