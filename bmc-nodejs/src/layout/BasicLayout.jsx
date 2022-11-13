import React from 'react';
import { useLocation, Outlet } from 'react-router-dom';
import { Layout } from 'antd';

import { getTopMenuId, getTopSubMenus } from '@/config/globalMenu';

import MainHeader from '@/views/components/MainHeader';
import AuthSidebar from '@/views/components/AuthSidebar';

const { Content } = Layout;

export default function BasicLayout({ siderMenuId }) {
    const location = useLocation();

    const topMenuId = getTopMenuId(siderMenuId);
    const SIDER_MENUS = getTopSubMenus(topMenuId);

    return (
        <Layout>
            <MainHeader selectedTopMenuId={topMenuId} />
            <Layout className='mz-layout'>
                <AuthSidebar siderMenus={SIDER_MENUS} selectedMenuId={siderMenuId} path={location.pathname} />
                <Content className='mz-layout-content'>
                    <Outlet />
                </Content>
            </Layout>
        </Layout>
    );
}
