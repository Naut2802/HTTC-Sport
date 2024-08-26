import { Navigate, Outlet, Route, Routes } from 'react-router-dom';
import { toast } from 'react-toastify';

//ACCOUNT
import ChangePassword from './components/Account/ChangePassword';
import ForgotPassword from './components/Account/ForgotPassword';
import FPVerifyError from './components/Account/ForgotPassword/FPVerifyError';
import FPVerifySuccess from './components/Account/ForgotPassword/FPVerifySuccess';
import ResetPassword from './components/Account/ForgotPassword/ResetPassword';
import Login from './components/Account/Login';
import Register from './components/Account/Register';
import MailAuthError from './components/Account/Register/MailAuthError';
import MailAuthSuccess from './components/Account/Register/MailAuthSuccess';

//LAYOUTS
import AdminHome from './components/Layout/AdminHome';
import AuthenticateGG from './components/Layout/AuthenticateGG';
import Contact from './components/Layout/Contact';
import News from './components/Layout/News';
import UserHome from './components/Layout/UserHome';

//PAGE
import AdminHomePage from './pages/AdminHomePage';
import UserHomePage from './pages/UserHomePage';

//PITCH
import PaymentError from './components/Pitch/Payment/PaymentError';
import PaymentSuccess from './components/Pitch/Payment/PaymentSuccess';
import PitchDetail from './components/Pitch/PitchDetail';
import PitchList from './components/Pitch/PitchList';

//USER
import UserBills from './components/User/UserBills';
import UserInfo from './components/User/UserInfo';
import UserRentInfo from './components/User/UserRentInfo';
import Payment from './components/Wallet/Payment';
import PaymentErrorTransaction from './components/Wallet/Payment/PaymentErrorTransaction';
import PaymentSuccessTransaction from './components/Wallet/Payment/PaymentSuccessTransaction';

//ADMIN
import AddPitch from './components/Admin/AddPitch';
import ListPitchs from './components/Admin/ListPitchs';
import ListUsers from './components/Admin/ListUsers';
import RentInfo from './components/Admin/RentInfo';
import PayRemainingError from './components/Admin/RentInfo/PayRemainingError';
import PayRemainingSuccess from './components/Admin/RentInfo/PayRemainingSuccess';
import Statistics from './components/Admin/Statistics';
import TableTransaction from './components/Wallet/TableTransaction';
import Transactions from './components/Admin/Transactions';

const ProtectedRoute = () => {
    const user = localStorage.getItem('accessToken');
    if (!user) {
        return <Navigate to="/login" replace={true} />;
    }
    return <Outlet />;
};

const AdminRoute = () => {
    const checkRole = localStorage.getItem('role');
    if (checkRole === 'ADMIN') {
        return <Outlet />;
    } else {
        toast.warning('Bạn không đủ quyền hạn để truy cập đến đường dẫn này!!!');
        return <Navigate to="/" replace={true} />;
    }
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
            <Route path="/forgot-password-verify-error" element={<FPVerifyError />} />
            <Route path="/forgot-password-verify-success" element={<FPVerifySuccess />} />
            <Route path="/forgot-password/reset-password" element={<ResetPassword />} />
            <Route path="/payment/rent/success" element={<PaymentSuccess />} />
            <Route path="/payment/rent/error" element={<PaymentError />} />
            <Route path="/payment/deposit/success" element={<PaymentSuccessTransaction />} />
            <Route path="/payment/deposit/error" element={<PaymentErrorTransaction />} />
            <Route path="/payment/rent/pay-remaining/success" element={<PayRemainingSuccess />} />
            <Route path="/payment/rent/pay-remaining/error" element={<PayRemainingError />} />

            <Route element={<UserHomePage />}>
                <Route path="/register" element={<Register />} />
                <Route path="/forgot-password" element={<ForgotPassword />} />
                <Route path="/trang-chu" element={<UserHome />} />
                <Route path="/tin-tuc" element={<News />} />
                <Route path="/lien-he" element={<Contact />} />
                <Route path="/san-bong" element={<PitchList />} />
                <Route path="/chi-tiet-san/:id" element={<PitchDetail />} />
                <Route path="/nap-tien" element={<Payment />} />
                <Route path="/lich-su-giao-dich-vi" element={<TableTransaction />} />
            </Route>

            <Route element={<ProtectedRoute />}>
                <Route element={<UserHomePage />}>
                    <Route path="/tai-khoan" element={<UserInfo />} />
                    <Route path="/change-password" element={<ChangePassword />} />
                    <Route path="/thong-tin-dat-san" element={<UserRentInfo />} />
                    <Route path="/lich-su-giao-dich" element={<UserBills />} />
                </Route>

                <Route element={<AdminRoute />}>
                    <Route element={<AdminHomePage />}>
                        <Route path="/admin/trang-chu" element={<AdminHome />} />
                        <Route path="/admin/them-san" element={<AddPitch />} />
                        <Route path="/admin/khach-hang" element={<ListUsers />} />
                        <Route path="/admin/thong-ke" element={<Statistics />} />
                        <Route path="/admin/thong-tin-dat-san" element={<RentInfo />} />
                        <Route path="/admin/danh-sach-san" element={<ListPitchs />} />
                        <Route path="/admin/thong-tin-tai-khoan" element={<UserInfo />} />
                        <Route path="/admin/lich-su-giao-dich-vi" element={<Transactions />} />
                    </Route>
                </Route>
            </Route>
            <Route element={<UnauthorizedRoute />}>
                <Route path="/login" element={<Login />} />
                <Route path="/authenticate/google" element={<AuthenticateGG />} />
                <Route path="/auth-mail-success" element={<MailAuthSuccess />} />
                <Route path="/auth-mail-error" element={<MailAuthError />} />
            </Route>
        </Routes>
    );
}

export default App;
