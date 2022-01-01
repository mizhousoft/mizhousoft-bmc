import React, { Suspense } from 'react';
import { useLocation, Switch, Route, Redirect } from 'react-router-dom';
import { Layout } from 'antd';

import Sidebar from '@/components/Sidebar';
import { PageLoading } from '@/components/UIComponent';
import RouteRender from '@/components/RouteRender';
import SessionStore from '@/session/SessionStore';

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

export default function ProfileLayout({ route }) {
    const location = useLocation();

    return (
        <Layout>
            <MainHeader selectedTopMenuId='' />
            <Layout className='mz-layout'>
                <Sidebar siderMenus={SIDER_MENUS} selectedMenuId={route.siderMenuId} path={location.pathname} />
                <Content className='mz-layout-content'>
                    <Suspense fallback={<PageLoading />}>
                        <Switch>
                            {route.component && (
                                <RouteRender
                                    path={route.path}
                                    authz={route.authz}
                                    exact={route.exact}
                                    component={route.component}
                                    meta={route.meta}
                                />
                            )}
                            {route.routes &&
                                route.routes.map((subRoute, i) => <RouteRender key={subRoute.path} {...subRoute} />)}
                            <Route component={() => <Redirect push to={SessionStore.getHomePath()} />} />
                        </Switch>
                    </Suspense>
                </Content>
            </Layout>
        </Layout>
    );
}
