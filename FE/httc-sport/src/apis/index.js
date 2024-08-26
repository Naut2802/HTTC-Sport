import axios from 'axios';
import authorizedAxiosInstance from '~/utils/authorizedAxios';
import { API_ROOT } from '~/utils/constants';

// ----------------------------------TOKEN API-------------------------------------
export const refreshTokenAPI = async (userId) => {
    return await authorizedAxiosInstance.put(`${API_ROOT}/api/auth/refresh-token`, {
        userId: userId,
    });
};

// --------------------------------ACCOUNT API-------------------------------------
export const handleLogInAPI = async (data) => {
    return await authorizedAxiosInstance.post(`${API_ROOT}/api/auth/sign-in`, data);
};

export const handleLogInWithGGAPI = async (authCode) => {
    return await authorizedAxiosInstance.post(`${API_ROOT}/api/auth/outbound/google/authenticate?code=${authCode}`, {
        credentials: 'include',
    });
};

export const handleLogoutAPI = async () => {
    return await authorizedAxiosInstance.delete(`${API_ROOT}/api/auth/logout`);
};

export const handleSignUpAPI = async (data) => {
    return await authorizedAxiosInstance.post(`${API_ROOT}/api/auth/sign-up`, data);
};

export const handleReSendVerifyMail = async (token) => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/auth/sign-up/resend-verification-token?token=${token}`);
};

// ----------------------------------USER API--------------------------------------

export const handleGetMyInfoAPI = async () => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/user/my-info`);
};

export const handleChangeInfoUser = async (userId, data) => {
    return await authorizedAxiosInstance.patch(`${API_ROOT}/api/v1/user/${userId}`, data);
};

export const handleChangePasswordUser = async (userId, data) => {
    return await authorizedAxiosInstance.patch(`${API_ROOT}/api/v1/user/change-password/${userId}`, data);
};

export const handleCheckMailForgotPassword = async (email) => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/user/forgot-password?email=${email}`);
};

export const handleResetPasswordUser = async (token, data) => {
    return await authorizedAxiosInstance.post(`${API_ROOT}/api/v1/user/forgot-password/reset-password?token=${token}`, data);
};

export const handleGetUserAdmin = async (page, size) => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/user`, {
        params: {
            page: page,
            size: size,
        },
    });
};

export const handleGetUsersAdmin = async (userId) => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/user/${userId}`);
};

export const handleDeleteUserAdmin = async (userId) => {
    return await authorizedAxiosInstance.delete(`${API_ROOT}/api/v1/user/${userId}`);
};

export const handleUpdateUserAdmin = async (userId, data) => {
    return await authorizedAxiosInstance.put(`${API_ROOT}/api/v1/user/${userId}`, data);
};

export const handleActiveUserAdmin = async (userId, data) => {
    return await authorizedAxiosInstance.patch(`${API_ROOT}/api/v1/user/active/${userId}`, data);
};

export const handleTopUpUser = async (data) => {
    return await authorizedAxiosInstance.post(`${API_ROOT}/api/v1/wallet/top-up`, data);
};

export const handleCreatePaymentLinkWallet = async (id) => {
    return await authorizedAxiosInstance.post(`${API_ROOT}/api/v1/payment/user-top-up/${id}`);
};

export const handleConfirmTransaction = async (code, orderCode, status) => {
    return await authorizedAxiosInstance.post(
        `${API_ROOT}/api/v1/wallet/confirm-transaction?code=${code}&orderCode=${orderCode}&status=${status}`,
    );
};

export const handleGetTransactionsByUser = async (userId, page, size) => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/transaction/${userId}`, {
        params: {
            page: page,
            size: size,
        },
    });
};

export const handleGetAllBillsUser = async (userId) => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/bill/get-all-bills-by-user/${userId}`);
};

// ----------------------------------PITCH API--------------------------------------

export const handleGetPitchesAdmin = async (page, size) => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/pitch/admin`, {
        params: {
            page: page,
            size: size,
        },
    });
};

export const handleGetTop3Pitches = async () => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/pitch/get-top-3`);
};

export const handleGetPitches = async () => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/pitch`);
};

export const handleGetPitchesWithFilter = async (data) => {
    return await authorizedAxiosInstance.get(
        `${API_ROOT}/api/v1/pitch?rates=${data.rates}&district=${data.district}&city=${data.city}&name=${data.name}&price=${data.price}&type=${data.type}`,
    );
};

export const handleGetPitch = async (id) => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/pitch/${id}`);
};

export const handleChangePitchInfo = async (pitchId, data) => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/pitch/${pitchId}`, data);
};

export const handleUpdatePitch = async (pitchId, data) => {
    return await authorizedAxiosInstance.put(`${API_ROOT}/api/v1/pitch/${pitchId}`, data, {
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    });
};

export const handleCreatePitch = async (data) => {
    return await authorizedAxiosInstance.post(`${API_ROOT}/api/v1/pitch`, data, {
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    });
};

export const handleDeletePitch = async (pitchId) => {
    return await authorizedAxiosInstance.delete(`${API_ROOT}/api/v1/pitch/${pitchId}`);
};

export const handleDelImgs = async (pitchId, publicId) => {
    return await authorizedAxiosInstance.patch(`${API_ROOT}/api/v1/pitch/${pitchId}/${publicId}`);
};

export const handleProvinces = async () => {
    return await axios.get('https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json', {
        reponseType: '/json',
    });
};

export const handleActivePitch = async (pitchId, data) => {
    return await authorizedAxiosInstance.patch(`${API_ROOT}/api/v1/pitch/active/${pitchId}`, data);
};

// -----------------------------------RENT PITCH API--------------------------------------

export const handleRentPitch = async (data) => {
    return await authorizedAxiosInstance.post(`${API_ROOT}/api/v1/rent-pitch`, data);
};

export const handleCreatePaymentLink = async (id, deposit) => {
    return await authorizedAxiosInstance.post(`${API_ROOT}/api/v1/payment/rent-pitch/${id}?deposit=${deposit}`);
};

export const handleConfirmRent = async (code, orderCode, status) => {
    return await authorizedAxiosInstance.post(
        `${API_ROOT}/api/v1/rent-pitch/confirm-rent?code=${code}&orderCode=${orderCode}&status=${status}`,
    );
};

// ----------------------------------- RENT INFO API--------------------------------------

export const handleGetRentInfoById = async (id) => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/rent-pitch/${id}`);
};

export const handleGetAllRentInfoByUser = async (userId, page, size) => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/rent-pitch/get-all-by-user/${userId}`, {
        params: {
            page: page,
            size: size,
        },
    });
};

export const handleDeleteRentInfo = async (id) => {
    return await authorizedAxiosInstance.delete(`${API_ROOT}/api/v1/rent-pitch/${id}`);
};

// -----------------------------------ADMIN RENT INFO API--------------------------------------

export const handleGetAllRentInfoAdmin = async (page, size) => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/rent-pitch`, {
        params: {
            page: page,
            size: size,
        },
    });
};

export const handleUpdateRentInfo = async (id, rentTime) => {
    return await authorizedAxiosInstance.put(`${API_ROOT}/api/v1/rent-pitch/${id}?rentTime=${rentTime}`);
};

export const handleExchangeRentInfoToBill = async (id) => {
    return await authorizedAxiosInstance.post(`${API_ROOT}/api/v1/rent-pitch/rent-info-to-bill/${id}`);
};

export const handlePayRemaining = async (id, paymentMethod) => {
    return await authorizedAxiosInstance.post(`${API_ROOT}/api/v1/rent-pitch/pay-remaining/${id}?paymentMethod=${paymentMethod}`);
};

export const handleConfirmPayRemaining = async (code, orderCode, status) => {
    return await authorizedAxiosInstance.post(
        `${API_ROOT}/api/v1/rent-pitch/confirm-pay-remaining?code=${code}&orderCode=${orderCode}&status=${status}`,
    );
};

export const handleGetAllTransactionsAdmin = async (page, size) => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/transaction`, {
        params: {
            page: page,
            size: size,
        },
    });
};

// -----------------------------------REPORT API--------------------------------------

export const handleAnalytics = async () => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/report/analytics`);
};

export const handleGetAllBills = async (page, size) => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/bill`, {
        params: {
            page: page,
            size: size,
        },
    });
};

export const handleGetAllBillsByUser = async (userId, page, size) => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/bill/get-all-bills-by-user/${userId}`, {
        params: {
            page: page,
            size: size,
        },
    });
};

export const handleExportExcel = async (billIds) => {
    return await authorizedAxiosInstance.post(`${API_ROOT}/api/v1/report/export-excel`, billIds, {
        responseType: 'blob',
    });
};

// -----------------------------------REVIEW API--------------------------------------

export const handleReviewPitch = async (billId, data) => {
    return await authorizedAxiosInstance.post(`${API_ROOT}/api/v1/review/${billId}`, data);
};

export const handleGetAllReviewsUser = async (userId) => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/review/get-all-reviews-by-user/${userId}`);
};

// -----------------------------------CHAT API--------------------------------------

export const handleGetChatMessages = async (roomId) => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/user/chat-room/${roomId}`);
};
