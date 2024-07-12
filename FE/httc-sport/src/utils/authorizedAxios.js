import axios from 'axios';
import { toast } from 'react-toastify';
import { handleLogoutAPI, refreshTokenAPI } from '~/apis';

let authorizedAxiosInstance = axios.create();

authorizedAxiosInstance.defaults.timeout = 1000 * 60 * 10;
authorizedAxiosInstance.defaults.withCredentials = true;

let isRefreshing = false;
let refreshSubscribers = [];

authorizedAxiosInstance.interceptors.request.use(
    (config) => {
        const accessToken = localStorage.getItem('accessToken');
        // const refreshToken = localStorage.getItem('refreshToken');
        if (accessToken) {
            config.headers.Authorization = `Bearer ${accessToken}`;
        }
        // else if (refreshToken) {
        //     config.headers.Authorization = `Bearer ${refreshToken}`;
        // }

        return config;
    },
    (error) => Promise.reject(error),
);

authorizedAxiosInstance.interceptors.response.use(
    (response) => response,
    async (error) => {
        const {
            config,
            response: { status },
        } = error;
        const originalRequest = config;

        // const handleLogout = async () => {
        //     await handleLogoutAPI();
        //     toast.info('Phiên đăng nhập hết hạn, vui lòng đăng nhập lại!');
        //     navigate('/login');
        // }

        if (status === 410 && !originalRequest._retry) {
            if (isRefreshing) {
                try {
                    const accessToken = await new Promise((resolve, reject) => {
                        refreshSubscribers.push((token) => {
                            originalRequest.headers.Authorization = `Bearer ${token}`;
                            resolve(authorizedAxiosInstance(originalRequest));
                        });
                    });
                    return accessToken;
                } catch (err) {
                    return Promise.reject(err);
                }
            }
            originalRequest._retry = true;
            isRefreshing = true;
            try {
                const response = await refreshTokenAPI(localStorage.getItem('userId'));
                const { userId, accessToken } = response.data.result;
                localStorage.setItem('accessToken', accessToken);
                localStorage.setItem('userId', userId);
                // Retry original request
                refreshSubscribers.forEach((callback) => callback(accessToken));
                refreshSubscribers = [];
                return authorizedAxiosInstance(originalRequest);
            } catch (err) {
                await handleLogoutAPI();
                localStorage.removeItem('accessToken');
                localStorage.removeItem('userId');
                window.location.replace('/dang-nhap');
                // toast.info('Phiên đăng nhập đã hết hạn, vui lòng đăng nhập lại!!!');
                return Promise.reject(err);
            } finally {
                isRefreshing = false;
            }
        }

        if (status === 401) {
            // // await handleLogoutAPI();
            // // window.location.replace('/login');
            localStorage.removeItem('accessToken');
            localStorage.removeItem('userId');
            toast.info('Phiên đăng nhập đã hết hạn, vui lòng đăng nhập lại!!!');
        }

        if (status !== 410) {
            toast.error(error.response?.data?.message || error?.message);
        }

        return Promise.reject(error);
    },
);

export default authorizedAxiosInstance;
