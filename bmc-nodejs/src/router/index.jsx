import React, { Suspense } from 'react';
import { createBrowserRouter, Navigate } from 'react-router';

import ErrorBoundary from '@/components/ErrorBoundary';
import PageLoading from '@/components/PageLoading';
import { BASENAME } from '@/config/application';
import menuUtils from '@/utils/menu-utils';
import authcLoader from './authcLoader';
import routes from './routes';

const routeList = routes;

const defaultPath = menuUtils.getHomePath();

function recursive(route) {
    const { element } = route;

    route.errorElement = <ErrorBoundary />;
    route.element = <Suspense fallback={<PageLoading />}>{element}</Suspense>;

    if (route.children) {
        route.children.forEach((child) => {
            recursive(child);
        });

        const indexRoute = route.children.find((item) => item.index);
        if (undefined === indexRoute) {
            route.children.push({
                index: true,
                element: <Navigate to={defaultPath} replace />,
            });
        }
    }
}

routeList.forEach((route) => {
    if (route.authz) {
        route.loader = authcLoader;
    }

    if (route.children) {
        recursive(route);
    }
});

routeList.push({
    path: '*',
    element: <Navigate to={defaultPath} replace />,
});

const router = createBrowserRouter(routeList, {
    basename: BASENAME,
});

export default router;
