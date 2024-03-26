import React from 'react';
import { Layout } from 'antd';
import { Outlet } from 'react-router-dom';

import EmptyHeader from '@/biz-components/EmptyHeader';

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
