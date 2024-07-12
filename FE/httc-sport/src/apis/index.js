import authorizedAxiosInstance from '~/utils/authorizedAxios';
import { API_ROOT } from '~/utils/constants';

export const refreshTokenAPI = async (userId) => {
    return await authorizedAxiosInstance.put(`${API_ROOT}/api/auth/refresh-token`, {
        userId: userId,
    });
};

export const handleLogInAPI = async (data) => {
    return await authorizedAxiosInstance.post(`${API_ROOT}/api/auth/sign-in`, data);
};

export const handleLogoutAPI = async () => {
    return await authorizedAxiosInstance.delete(`${API_ROOT}/api/auth/logout`);
};

export const handleSignUpAPI = async (data) => {
    return await authorizedAxiosInstance.post(`${API_ROOT}/api/auth/sign-up`, data);
};

export const handleGetMyInfoAPI = async () => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/user/my-info`);
};

export const handleReSendVerifyMail = async (token) => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/auth/sign-up/resend-verification-token?token=${token}`);
};
