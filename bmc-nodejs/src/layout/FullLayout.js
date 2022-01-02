import React from 'react';
import { Outlet } from 'react-router-dom';
import { Layout } from 'antd';

import MainHeader from '@/views/components/MainHeader';

export default function FullLayout({ topMenuId }) {
    return (
        <Layout>
            <MainHeader selectedTopMenuId={topMenuId} />
            <Layout className='mz-layout'>
                <Outlet />
            </Layout>
        </Layout>
    );
}
