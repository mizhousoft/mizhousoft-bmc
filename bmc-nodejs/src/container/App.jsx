import { ConfigProvider, message } from 'antd';
import zhCN from 'antd/lib/locale/zh_CN';
import dayjs from 'dayjs';
import { RouterProvider } from 'react-router';

import 'dayjs/locale/zh-cn';

import router from '../router';

import '@/assets/css/antd.css';
import '@/assets/css/public.css';

message.config({ top: 120, duration: 2 });
dayjs.locale('zh-cn');

export default function App() {
    return (
        <ConfigProvider locale={zhCN}>
            <RouterProvider router={router} />
        </ConfigProvider>
    );
}
