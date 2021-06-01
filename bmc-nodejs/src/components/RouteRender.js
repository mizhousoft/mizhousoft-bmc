import React from 'react';
import { Route, Redirect } from 'react-router-dom';
import SessionStore from '@/session/SessionStore';
import { LOGIN_PATH } from '@/config/application';

export default function RouteRender(route) {
    if (!Object.prototype.hasOwnProperty.call(route, 'authz') || route.authz) {
        if (!SessionStore.isAuthenticated()) {
            return <Redirect to={LOGIN_PATH} />;
        }
    }

    const exact = route.exact ?? false;

    if (undefined !== route.layout) {
        return <Route path={route.path} render={(props) => <route.layout {...props} route={route} />} />;
    }

    return (
        <Route path={route.path} exact={exact} render={(props) => <route.component {...props} meta={route.meta} />} />
    );
}
