import React, { Suspense } from 'react';
import { Navigate, createBrowserRouter } from 'react-router-dom';

import { PageLoading } from '@/components/UIComponent';
import { BASENAME } from '@/config/application';
import SessionStore from '@/session/SessionStore';
import ErrorBoundary from '@/components/ErrorBoundary';

import RequireAuth from './RequireAuth';
import loginRoute from './loginRoute';
import profileRoute from './profileRoute';
import systemRoutes from './systemRoute';

const routes = [...loginRoute, ...systemRoutes, ...profileRoute];

routes.forEach((route) => {
    route.element = <Suspense fallback={<PageLoading />}>{route.element}</Suspense>;

    if (route.children) {
        route.children.forEach((child) => {
            const { element } = child;
            child.errorElement = <ErrorBoundary />;

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
    } else if (route.component) {
        const { element } = route;
        route.errorElement = <ErrorBoundary />;

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

const router = createBrowserRouter(routes, {
    basename: BASENAME,
});

export default router;
