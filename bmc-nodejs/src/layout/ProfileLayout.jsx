import React from 'react';
import { Layout } from 'antd';
import { Outlet, useLocation } from 'react-router-dom';

import Sidebar from '@/components/Sidebar';
import MainHeader from '@/views/components/MainHeader';

const { Content } = Layout;

const SIDER_MENUS = [
    {
        id: 'bmc.setting.myaccount',
        name: '帐号信息',
        iconFont: 'anticon-account',
        path: '/profile/account',
    },
    {
        id: 'bmc.setting.password',
        name: '密码修改',
        iconFont: 'anticon-password',
        path: '/profile/password',
    },
    {
        id: 'bmc.setting.idletimeout',
        name: '闲置时间设置',
        iconFont: 'anticon-idletime',
        path: '/profile/idletimeout',
    },
];

export default function ProfileLayout({ siderMenuId }) {
    const location = useLocation();

    return (
        <Layout>
            <MainHeader selectedTopMenuId='' />
            <Layout className='mz-layout'>
                <Sidebar siderMenus={SIDER_MENUS} selectedMenuId={siderMenuId} path={location.pathname} />
                <Content className='mz-layout-content'>
                    <Outlet />
                </Content>
            </Layout>
        </Layout>
    );
}
