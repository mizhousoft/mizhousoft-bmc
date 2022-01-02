import React from 'react';
import { useLocation, Outlet } from 'react-router-dom';
import { Layout } from 'antd';

import Sidebar from '@/components/Sidebar';
import MainHeader from '@/views/components/MainHeader';

const { Content } = Layout;

const SIDER_MENUS = [
    {
        id: 'bmc.personal.settings',
        name: '个人设置',
        iconFont: 'anticon-profile',
        subMenus: [
            {
                id: 'bmc.setting.myaccount',
                name: '我的帐号',
                path: '/profile/account',
            },
            {
                id: 'bmc.setting.password',
                name: '密码修改',
                path: '/profile/password',
            },
            {
                id: 'bmc.setting.idletimeout',
                name: '闲置时间设置',
                path: '/profile/idletimeout',
            },
        ],
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
