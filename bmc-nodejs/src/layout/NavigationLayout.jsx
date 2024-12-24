import React from 'react';
import { Layout } from 'antd';
import { Outlet } from 'react-router';

import NavigationHeader from '@/components-biz/NavigationHeader';
import menuUtils from '@/utils/menu-utils';

export default function NavigationLayout({ topMenuId, style = {} }) {
    const naviMenus = menuUtils.getNavigationMenus();

    return (
        <Layout>
            <NavigationHeader activeKey={topMenuId} menus={naviMenus} />
            <Layout className='mz-layout mz-layout-full' style={style}>
                <Outlet />
            </Layout>
        </Layout>
    );
}
