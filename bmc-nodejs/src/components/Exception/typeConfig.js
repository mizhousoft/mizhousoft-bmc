import img403 from './403.svg';
import img404 from './404.svg';
import img500 from './500.svg';

const config = {
    401: {
        img: img403,
        title: '401',
        desc: '请重新登录系统',
    },
    403: {
        img: img403,
        title: '403',
        desc: '抱歉，你沒有权限访问该页面',
    },
    404: {
        img: img404,
        title: '404',
        desc: '抱歉，你访问的页面不存在',
    },
    500: {
        img: img500,
        title: '500',
        desc: '抱歉，服务器出错了',
    },
};

export default config;
