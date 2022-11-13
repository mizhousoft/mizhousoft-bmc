import axios from 'axios';
import { CONTEXT_LOGIN_PATH } from '@/config/application';
import SessionStore from '@/session/SessionStore';

const ERROR_CODE_MAP = new Map([
    [401, '请先登录系统'],
    [403, '你没有权限访问'],
    [404, '服务器请求地址不存在，请联系管理员'],
    [405, '服务器请求地址不存在，请联系管理员'],
    [500, '服务器内部错误，请联系管理员'],
    [503, '服务器不可用，请联系管理员'],
    [504, '服务器请求超时，请联系管理员'],
]);

const instance = axios.create({
    headers: {
        'Cache-Control': 'no-cache',
        Expires: '0',
        Pragma: 'no-cache',
        Accept: 'application/json;charset=UTF-8',
    },
});

instance.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response && error.response.data && error.response.data.location) {
            SessionStore.logout();
            window.location.href = CONTEXT_LOGIN_PATH;
            return Promise.reject(error);
        }
        return Promise.reject(error);
    }
);

function axiosRequest(options) {
    const { url, data, form } = options;
    let { method } = options;

    if (!method) {
        method = 'get';
    }

    switch (method.toLowerCase()) {
        case 'get':
            return instance.get(url, {
                params: data,
            });
        case 'post':
            return instance.post(url, data);
        case 'upload':
            return instance.post(url, form, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
        default:
            return instance.get(url, {
                params: data,
            });
    }
}

function request(options) {
    return axiosRequest(options)
        .then((response) => {
            const { status, data } = response;

            const fetchStatus = {
                loading: false,
                okey: true,
                statusCode: status,
            };

            const { okey, error } = data;
            if (undefined !== okey && undefined !== error && !okey) {
                fetchStatus.okey = false;
                fetchStatus.message = error;
            }

            return {
                fetchStatus,
                ...data,
            };
        })
        .catch((error) => {
            const { response } = error;
            let msg;
            let statusCode;

            if (response && response instanceof Object) {
                const { data, statusText, status } = response;
                statusCode = status;
                msg = ERROR_CODE_MAP.get(statusCode) || data.message || statusText;
            } else {
                statusCode = 600;
                msg = error.message || 'Network Error';
            }

            return {
                fetchStatus: {
                    loading: false,
                    okey: false,
                    statusCode,
                    message: msg,
                },
            };
        });
}

export function post(options) {
    options.method = 'post';

    return request(options);
}

export function get(options) {
    options.method = 'get';

    return request(options);
}

export function upload(options) {
    options.method = 'upload';

    return request(options);
}

export async function asyncFetch(options) {
    const result = await get(options)
        .then((resp) => resp)
        .catch((error) => error);

    return result;
}

export async function asyncPost(options) {
    const result = await post(options)
        .then((resp) => resp)
        .catch((error) => error);

    return result;
}

export async function asyncUpload(options) {
    const result = await upload(options)
        .then((resp) => resp)
        .catch((error) => error);

    return result;
}
