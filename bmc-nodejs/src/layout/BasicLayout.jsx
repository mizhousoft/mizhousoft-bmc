import React from 'react';
import { Layout } from 'antd';
import { Outlet, useLocation } from 'react-router-dom';

import AuthSidebar from '@/biz-components/AuthSidebar';
import MainHeader from '@/biz-components/MainHeader';
import { getTopMenuId, getTopSubMenus } from '@/config/global-menu';

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
