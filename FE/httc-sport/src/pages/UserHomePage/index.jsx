import { Outlet } from 'react-router-dom';
import Header from '~/components/Layout/Header';

export default function UserHomePage() {
    return (
        <>
            <Header />
            <Outlet />
        </>
    );
}
