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

export const handleGetMyInfoAPI = async () => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/user/my-info`);
};
