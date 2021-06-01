import React, { PureComponent, Suspense } from 'react';
import { Switch, Route, Redirect } from 'react-router-dom';
import { Layout } from 'antd';

import RouteRender from '@/components/RouteRender';
import { PageLoading } from '@/components/UIComponent';
import SessionStore from '@/session/SessionStore';

import MainHeader from '@/views/components/MainHeader';

class FullLayout extends PureComponent {
    render() {
        const { route } = this.props;

        return (
            <Layout>
                <MainHeader selectedTopMenuId={route.topMenuId} />
                <Layout className='mz-layout'>
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
                </Layout>
            </Layout>
        );
    }
}

export default FullLayout;
