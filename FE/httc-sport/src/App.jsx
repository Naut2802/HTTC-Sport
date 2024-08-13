import { Navigate, Outlet, Route, Routes } from 'react-router-dom';

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
import Payment from './components/Pitch/Payment';
import PaymentError from './components/Pitch/Payment/PaymentError';
import PaymentSuccess from './components/Pitch/Payment/PaymentSuccess';
import PitchDetail from './components/Pitch/PitchDetail';
import PitchList from './components/Pitch/PitchList';

//USER
import UserBills from './components/User/UserBills';
import UserInfo from './components/User/UserInfo';
import UserRentInfo from './components/User/UserRentInfo';

//ADMIN
import AddPitch from './components/Admin/AddPitch';
import ListPitchs from './components/Admin/ListPitchs';
import ListUsers from './components/Admin/ListUsers';
import RentInfo from './components/Admin/RentInfo';
import Statistics from './components/Admin/Statistics';

const ProtectedRoute = () => {
    const user = localStorage.getItem('accessToken');
    if (!user) {
        return <Navigate to="/login" replace={true} />;
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
            <Route path="/" component={<Navigate to="/trang-chu" replace={true} />} />
            <Route path="/forgot-password-verify-error" component={<FPVerifyError />} />
            <Route path="/forgot-password-verify-success" component={<FPVerifySuccess />} />
            <Route path="/forgot-password/reset-password" component={<ResetPassword />} />
            <Route path="/payment/rent/success" component={<PaymentSuccess />} />
            <Route path="/payment/rent/error" component={<PaymentError />} />
            <Route component={<UserHomePage />}>
                <Route path="/register" component={<Register />} />
                <Route path="/forgot-password" component={<ForgotPassword />} />
                <Route path="/trang-chu" component={<UserHome />} />
                <Route path="/tin-tuc" component={<News />} />
                <Route path="/lien-he" component={<Contact />} />
                <Route path="/san-bong" component={<PitchList />} />
                <Route path="/chi-tiet-san/:id" component={<PitchDetail />} />
                <Route path="/thanh-toan" component={<Payment />} />
            </Route>m

            <Route component={<ProtectedRoute />}>
                <Route component={<UserHomePage />}>
                    <Route path="/tai-khoan" component={<UserInfo />} />
                    <Route path="/change-password" component={<ChangePassword />} />
                    <Route path="/thong-tin-dat-san" component={<UserRentInfo />} />
                    <Route path="/lich-su-giao-dich" component={<UserBills />} />
                </Route>

                <Route component={<AdminHomePage />}>
                    <Route path="/admin/trang-chu" component={<AdminHome />} />
                    <Route path="/admin/them-san" component={<AddPitch />} />
                    <Route path="/admin/khach-hang" component={<ListUsers />} />
                    <Route path="/admin/thong-ke" component={<Statistics />} />
                    <Route path="/admin/thong-tin-dat-san" component={<RentInfo />} />
                    <Route path="/admin/danh-sach-san" component={<ListPitchs />} />
                    <Route path="/admin/thong-tin-tai-khoan" component={<UserInfo />} />
                </Route>
            </Route>

            <Route component={<UnauthorizedRoute />}>
                <Route path="/login" component={<Login />} />
                <Route path="/authenticate/google" component={<AuthenticateGG />} />
                <Route path="/auth-mail-success" component={<MailAuthSuccess />} />
                <Route path="/auth-mail-error" component={<MailAuthError />} />
            </Route>
        </Routes>
    );
}

export default App;
