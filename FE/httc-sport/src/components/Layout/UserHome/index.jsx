import * as React from 'react';

import logo from '~/components/Images/logo.png';
import Banner from './Banner';
import HotPick from './HotPick';
import CardInfo from './CardInfo';

function UserHome() {
    return (
        <div className="my-3 container">
            <div className="d-flex justify-content-center">
                <img src={logo} alt="" style={{ width: 120 }} />
            </div>
            <hr />
            <Banner />
            <div className="text-center">
                <hr />
                <span className="fs-1 text-center">Lựa Chọn Nổi Bật</span>
            </div>
            <HotPick />
            <hr />
            <CardInfo/>
        </div>
    );
}

export default UserHome;
