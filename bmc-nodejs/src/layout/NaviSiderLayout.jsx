import React from 'react';
import { Layout } from 'antd';
import { Outlet, useLocation } from 'react-router-dom';

import NavigationHeader from '@/components-biz/NavigationHeader';
import Sidebar from '@/components-biz/Sidebar';
import menuUtils from '@/utils/menu-utils';

const { Content } = Layout;

export default function NaviSiderLayout({ siderMenuId }) {
    const location = useLocation();

    const topMenuId = menuUtils.getTopMenuId(siderMenuId);
    const naviMenus = menuUtils.getNavigationMenus();
    const siderMenus = menuUtils.getMenuChildren(topMenuId);

    return (
        <Layout>
            <NavigationHeader activeKey={topMenuId} menus={naviMenus} />
            <Layout className='mz-layout'>
                <Sidebar siderMenus={siderMenus} activeKey={siderMenuId} path={location.pathname} />
                <Content className='mz-layout-content'>
                    <Outlet />
                </Content>
            </Layout>
        </Layout>
    );
}
