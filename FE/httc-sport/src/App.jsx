import { Navigate, Outlet, Route, Routes } from 'react-router-dom';

import DangKy from './components/Account/DangKy';
import DangNhap from './components/Account/DangNhap';
import DoiMatKhau from './components/Account/DoiMatKhau';
import NhapEmail from './components/Account/NhapEmail';
import NhapOTP from './components/Account/NhapOTP';
import QuenMatKhau from './components/Account/QuenMK';
import Authenticate from './components/Authenticate';
import ChiTietSan from './components/Layout/ChiTietSan';
import San from './components/Layout/DanhSachSan';
import Contact from './components/Layout/LienHe';
import New from './components/Layout/TinTuc';
import UserHome from './components/Layout/UserHome';
import ThongTinUser from './components/User/ThongTinUser';
import Home from './pages/Home';

const ProtectedRoute = () => {
    const user = localStorage.getItem('accessToken');
    if (!user) {
        return <Navigate to="/dang-nhap" replace={true} />;
    }
    return <Outlet />;
};

const UnauthorizedRoute = () => {
    const user = localStorage.getItem('userId');
    if (user) return <Navigate to="/" replace={true} />;
    return <Outlet />;
};

function App() {
    return (
        <Routes>
            <Route path="/" element={<Navigate to="/trang-chu" replace={true} />} />

            <Route element={<Home />}>
                <Route path="/trang-chu" element={<UserHome />} />
                <Route path="/san-bong" element={<San />} />
                <Route path="/tin-tuc" element={<New />} />
                <Route path="/lien-he" element={<Contact />} />
                <Route path="/chi-tiet-san" element={<ChiTietSan />} />
                <Route path="/dang-ky" element={<DangKy />} />
                <Route path="/quen-mat-khau" element={<QuenMatKhau />} />
                <Route path="/quen-mat-khau-1" element={<NhapEmail />} />
                <Route path="/quen-mat-khau-2" element={<NhapOTP />} />
                <Route path="/quen-mat-khau-3" element={<DoiMatKhau />} />
            </Route>

            <Route element={<ProtectedRoute />}>
                <Route element={<Home />}>
                    <Route path="/tai-khoan" element={<ThongTinUser />} />
                </Route>
            </Route>

            <Route element={<UnauthorizedRoute />}>
                <Route path="/dang-nhap" element={<DangNhap />} />
                <Route path="/authenticate" element={<Authenticate />} />
            </Route>
        </Routes>
    );
}

export default App;
