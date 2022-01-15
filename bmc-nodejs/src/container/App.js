import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import { ConfigProvider, message } from 'antd';
import zhCN from 'antd/lib/locale-provider/zh_CN';
import { BASENAME } from '@/config/application';
import AppRoutes from '../routes';
import 'antd/dist/antd.css';
import '@/static/css/antd.css';
import '@/static/css/public.css';
import '@/static/css/style.css';

message.config({ top: 120, duration: 2 });

export default function App() {
    return (
        <ConfigProvider locale={zhCN}>
            <BrowserRouter basename={BASENAME}>
                <AppRoutes />
            </BrowserRouter>
        </ConfigProvider>
    );
}
