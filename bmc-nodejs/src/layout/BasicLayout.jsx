import React from 'react';
import { Layout } from 'antd';
import { Outlet, useLocation } from 'react-router-dom';

import { getTopMenuId, getTopSubMenus } from '@/config/globalMenu';
import AuthSidebar from '@/views/components/AuthSidebar';
import MainHeader from '@/views/components/MainHeader';

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
