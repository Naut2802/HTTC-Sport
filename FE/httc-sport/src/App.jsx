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
import Contact from './components/Layout/Contact';
import News from './components/Layout/News';
import UserHome from './components/Layout/UserHome';

//PAGE
import AdminHomePage from './pages/AdminHomePage';
import UserHomePage from './pages/UserHomePage';

//AUTHENTICATION
import AuthenticateFB from './components/AuthenticateFB';
import AuthenticateGG from './components/AuthenticateGG';

//FIELD
import FieldBooking from './components/Field/FieldBooking';
import FieldDetail from './components/Field/FieldDetail';
import FieldList from './components/Field/FieldList';

//USER
import UserHistoryOrder from './components/User/UserHistoryOrder';
import UserInfo from './components/User/UserInfo';
import UserOrderInfo from './components/User/UserOrderInfo';

//ADMIN
import AddField from './components/Admin/AddField';
import FieldsOrderInfo from './components/Admin/FieldsOrderInfo';
import ListFieldAdmin from './components/Admin/ListFieldAdmin';
import ListUsers from './components/Admin/ListUsers';
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
            <Route path="/" element={<Navigate to="/trang-chu" replace={true} />} />

            <Route path="/register" element={<Register />} />
            <Route path="/forgot-password" element={<ForgotPassword />} />
            <Route path="/forgot-password-verify-error" element={<FPVerifyError />} />
            <Route path="/forgot-password-verify-success" element={<FPVerifySuccess />} />
            <Route path="/forgot-password/reset-password" element={<ResetPassword />} />
            <Route path="/dat-san" element={<FieldBooking />} />

            <Route element={<UserHomePage />}>
                <Route path="/trang-chu" element={<UserHome />} />
                <Route path="/san-bong" element={<FieldList />} />
                <Route path="/tin-tuc" element={<News />} />
                <Route path="/lien-he" element={<Contact />} />
                <Route path="/field-detail" element={<FieldDetail />} />
            </Route>

            <Route element={<ProtectedRoute />}>
                <Route element={<UserHomePage />}>
                    <Route path="/tai-khoan" element={<UserInfo />} />
                    <Route path="/change-password" element={<ChangePassword />} />
                    <Route path="/thong-tin-dat-san" element={<UserOrderInfo />} />
                    <Route path="/lich-su-giao-dich" element={<UserHistoryOrder />} />
                </Route>

                <Route element={<AdminHomePage />}>
                    <Route path="/admin/trang-chu" element={<AdminHome />} />
                    <Route path="/admin/them-san" element={<AddField />} />
                    <Route path="/admin/khach-hang" element={<ListUsers />} />
                    <Route path="/admin/thong-ke" element={<Statistics />} />
                    <Route path="/admin/thong-tin-dat-san" element={<FieldsOrderInfo />} />
                    <Route path="/admin/danh-sach-san" element={<ListFieldAdmin />} />
                    <Route path="/admin/thong-tin-tai-khoan" element={<UserInfo />} />
                </Route>
            </Route>

            <Route element={<UnauthorizedRoute />}>
                <Route path="/login" element={<Login />} />
                <Route path="/authenticate/google" element={<AuthenticateGG />} />
                <Route path="/authenticate/facebook" element={<AuthenticateFB />} />
                <Route path="/auth-mail-success" element={<MailAuthSuccess />} />
                <Route path="/auth-mail-error" element={<MailAuthError />} />
            </Route>
        </Routes>
    );
}

export default App;
