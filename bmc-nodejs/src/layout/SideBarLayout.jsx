import React from 'react';
import { Layout } from 'antd';
import { Outlet, useLocation } from 'react-router';

import LogoImg from '@/assets/imgs/icon-logo.png';
import NavigationHeader from '@/components-biz/NavigationHeader';
import Sidebar from '@/components-biz/Sidebar';
import { APPLICATION_SHORT_NAME } from '@/config/application';
import menuUtils from '@/utils/menu-utils';

const { Content } = Layout;

export default function SideBarLayout({ siderMenuId }) {
    const location = useLocation();

    const topMenuId = menuUtils.getTopMenuId(siderMenuId);
    const siderMenus = menuUtils.getVisibleMenus();

    const header = (
        <div className='header-logo'>
            <img src={LogoImg} alt='' />
            <div className='header-logo-name'>{APPLICATION_SHORT_NAME}</div>
        </div>
    );

    return (
        <Layout>
            <div style={{ width: '200px', overflow: 'hidden', flex: '0 0 200px', maxWidth: '200px', minWidth: '200px' }} />
            <Sidebar header={header} siderMenus={siderMenus} activeKey={siderMenuId} path={location.pathname} className='mz-sidebar' />
            <Layout className='mz-layout'>
                <NavigationHeader activeKey={topMenuId} />
                <div className='mz-layout-shadow' />
                <Content className='mz-layout-content'>
                    <Outlet />
                </Content>
            </Layout>
        </Layout>
    );
}
