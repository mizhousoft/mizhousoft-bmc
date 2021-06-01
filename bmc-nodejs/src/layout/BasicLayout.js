import React, { PureComponent, Suspense } from 'react';
import { Switch, Route, Redirect } from 'react-router-dom';
import { Layout } from 'antd';

import RouteRender from '@/components/RouteRender';
import { PageLoading } from '@/components/UIComponent';
import SessionStore from '@/session/SessionStore';
import { getTopMenuId, getTopSubMenus } from '@/config/globalMenu';

import MainHeader from '@/views/components/MainHeader';
import AuthSidebar from '@/views/components/AuthSidebar';

const { Content } = Layout;

class BasicLayout extends PureComponent {
    render() {
        const { route, location } = this.props;

        const topMenuId = getTopMenuId(route.siderMenuId);
        const SIDER_MENUS = getTopSubMenus(topMenuId);

        return (
            <Layout>
                <MainHeader selectedTopMenuId={topMenuId} />
                <Layout className='mz-layout'>
                    <AuthSidebar siderMenus={SIDER_MENUS} selectedMenuId={route.siderMenuId} path={location.pathname} />
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
                                    route.routes.map((subRoute, i) => (
                                        <RouteRender key={subRoute.path} {...subRoute} />
                                    ))}
                                <Route component={() => <Redirect push to={SessionStore.getHomePath()} />} />
                            </Switch>
                        </Suspense>
                    </Content>
                </Layout>
            </Layout>
        );
    }
}

export default BasicLayout;
