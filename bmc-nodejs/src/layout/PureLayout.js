import React from 'react';
import { Outlet } from 'react-router-dom';
import { Layout } from 'antd';

import EmptyHeader from '@/views/components/EmptyHeader';

const { Content } = Layout;

export default function PureLayout() {
    return (
        <Layout>
            <EmptyHeader />
            <Layout className='mz-layout'>
                <Content className='mz-layout-content'>
                    <Outlet />
                </Content>
            </Layout>
        </Layout>
    );
}
