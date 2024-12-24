import React from 'react';
import { Layout } from 'antd';
import { Outlet } from 'react-router';

import GenericHeader from '@/components-biz/GenericHeader';

const { Content } = Layout;

export default function GenericLayout() {
    return (
        <Layout>
            <GenericHeader />
            <Layout className='mz-layout'>
                <Content className='mz-layout-content'>
                    <Outlet />
                </Content>
            </Layout>
        </Layout>
    );
}
