import authorizedAxiosInstance from '~/utils/authorizedAxios';
import { API_ROOT } from '~/utils/constants';
import axios from 'axios'

export const refreshTokenAPI = async (userId) => {
    return await authorizedAxiosInstance.put(`${API_ROOT}/api/auth/refresh-token`, {
        userId: userId,
    });
};

export const handleLogInAPI = async (data) => {
    return await authorizedAxiosInstance.post(`${API_ROOT}/api/auth/sign-in`, data);
};

export const handleLogInWithGGAPI = async (authCode) => {
    return await authorizedAxiosInstance.post(`${API_ROOT}/api/auth/outbound/google/authenticate?code=${authCode}`, {
        credentials: 'include',
    });
};

// export const handleLogInWithFBAPI = async (authCode) => {
//     return await authorizedAxiosInstance.post(`${API_ROOT}/api/auth/outbound/facebook/authenticate?code=${authCode}`, {
//         credentials: 'include',
//     });
// };

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

export const handleGetPitch = async () => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/pitch`);
};

export const handleChangePitchInfo = async (pitchId, data) => {
    return await authorizedAxiosInstance.get(`${API_ROOT}/api/v1/pitch/${pitchId}`, data);
};

export const handleCreatePitch = async (data) => {
    return await authorizedAxiosInstance.post(`${API_ROOT}/api/v1/pitch`, data, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });;
};

export const handleProvinces = async () => {
    return await axios.get
    ('https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json',{
        reponseType: '/json'
    });
};