import React, { Suspense } from 'react';
import { Switch, Route, Redirect } from 'react-router-dom';
import { Layout } from 'antd';

import RouteRender from '@/components/RouteRender';
import { PageLoading } from '@/components/UIComponent';
import SessionStore from '@/session/SessionStore';

import EmptyHeader from '@/views/components/EmptyHeader';

const { Content } = Layout;

export default function PureLayout({ route }) {
    return (
        <Layout>
            <EmptyHeader />
            <Layout className='mz-layout'>
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
