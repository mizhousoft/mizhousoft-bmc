import React, { Suspense } from 'react';
import { useRoutes, Navigate } from 'react-router-dom';

import { PageLoading } from '@/components/UIComponent';
import SessionStore from '@/session/SessionStore';
import RequireAuth from './RequireAuth';

import loginRoute from './loginRoute';
import profileRoute from './profileRoute';
import accoutRoutes from './accountRoute';

const routes = [...loginRoute, ...accoutRoutes, ...profileRoute];

routes.forEach((route) => {
    if (route.children) {
        route.children.forEach((child) => {
            const { element } = child;

            if (undefined === child.authz || child.authz) {
                child.element = (
                    <RequireAuth>
                        <Suspense fallback={<PageLoading />}>{element}</Suspense>
                    </RequireAuth>
                );
            } else {
                child.element = <Suspense fallback={<PageLoading />}>{element}</Suspense>;
            }
        });
    }
    else if (route.component) {
        const {element} = route;

        if (undefined === route.authz || route.authz) {
            route.element = (
                <RequireAuth>
                    <Suspense fallback={<PageLoading />}>{element}</Suspense>
                </RequireAuth>
            );
        } else {
            route.element = <Suspense fallback={<PageLoading />}>{element}</Suspense>;
        }
    }
});

routes.push({
    path: '*',
    element: <Navigate to={SessionStore.getHomePath()} replace />,
});

export default function AppRoutes() {
    const routing = useRoutes(routes);

    return routing;
}
