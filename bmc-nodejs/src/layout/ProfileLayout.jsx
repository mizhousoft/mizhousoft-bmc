import { Layout } from 'antd';
import { Outlet, useLocation } from 'react-router';

import NavigationHeader from '@/components-biz/NavigationHeader';
import Sidebar from '@/components-biz/Sidebar';
import menuUtils from '@/utils/menu-utils';

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
    const naviMenus = menuUtils.getNavigationMenus();

    return (
        <Layout>
            <NavigationHeader activeKey='' menus={naviMenus} />
            <Layout className='mz-layout'>
                <Sidebar siderMenus={SIDER_MENUS} activeKey={siderMenuId} path={location.pathname} />
                <Content className='mz-layout-content'>
                    <Outlet />
                </Content>
            </Layout>
        </Layout>
    );
}
