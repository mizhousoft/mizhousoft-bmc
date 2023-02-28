import React from 'react';
import { Outlet } from 'react-router-dom';
import { Layout } from 'antd';

import MainHeader from '@/views/components/MainHeader';

export default function FullLayout({ topMenuId, style = {} }) {
    return (
        <Layout>
            <MainHeader selectedTopMenuId={topMenuId} />
            <Layout className='mz-layout mz-layout-full' style={style}>
                <Outlet />
            </Layout>
        </Layout>
    );
}
