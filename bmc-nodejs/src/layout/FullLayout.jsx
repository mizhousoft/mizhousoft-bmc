import React from 'react';
import { Layout } from 'antd';
import { Outlet } from 'react-router-dom';

import MainHeader from '@/biz-components/MainHeader';

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
