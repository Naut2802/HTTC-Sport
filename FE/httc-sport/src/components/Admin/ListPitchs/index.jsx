import { Breadcrumbs, Button, Card, Tooltip, Typography } from '@mui/material';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { toast } from 'react-toastify';
import { handleDeletePitch, handleGetPitches } from '~/apis';

export default function ListPitchs() {
    const [pitches, setPitch] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const re = await handleGetPitches();
                console.log(re.data.result);
                setPitch(re.data.result);
            } catch (error) {
                console.error(error);
                toast.error('Đổ dữ liệu thất bại');
            }
        };
        fetchData();
    }, []);

    const handleEditClick = (data) => {
        console.log(data);
        sessionStorage.setItem('selectedPitch', JSON.stringify(data));
        window.location.href = '/admin/them-san';
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
                                src={pitch.images[0].url}
                                alt={pitch.pitchName}
                                style={{
                                    width: '100%',
                                    height: 'auto',
                                }}
                                className="rounded m-2"
                            />
                        </Typography>
                        <Typography className="col-8 mt-2" component="div">
                            <div className="d-flex justify-content-end mx-4">
                                <span> Trạng Thái: {pitch.status ? 'Ngưng Hoạt Động' : 'Hoạt Động'}</span>
                            </div>
                            <div className="fs-3 fw-bold">{pitch.pitchName}</div>
                            <div className="my-1">
                                <span>Loại Sân: {pitch.type}</span>
                            </div>
                            <div className="my-1">
                                <span>Giá: {pitch.price}</span>
                            </div>
                            <div className="my-1">
                                <span>Địa Chỉ: {`${pitch.street}, ${pitch.ward}, ${pitch.city}`}</span>
                            </div>
                            <div className="my-1">
                                <span>Mô Tả: {pitch.description}</span>
                            </div>
                            <div className="d-flex justify-content-end">
                                <Tooltip title="Chỉnh Sửa">
                                    <Button variant="contained" className="my-2" onClick={() => handleEditClick(pitch)}>
                                        Chỉnh Sửa
                                    </Button>
                                </Tooltip>
                                <Tooltip title="Xóa">
                                    <Button variant="contained" className="my-2 mx-2" onClick={() => handleDelClick(pitch.id)}>
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
