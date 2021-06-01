import React from 'react';

import PureLayout from '@/layout/PureLayout';

import Login from '@/views/Login';

const FirstLogin = React.lazy(() => import('@/views/profile/FirstLogin'));
const PasswordExpired = React.lazy(() => import('@/views/profile/PasswordExpired'));
const PasswordExpiring = React.lazy(() => import('@/views/profile/PasswordExpiring'));

const routes = [
    {
        path: '/login/first',
        layout: PureLayout,
        component: FirstLogin,
        authz: false,
        exact: true,
    },
    {
        path: '/password/expired',
        layout: PureLayout,
        component: PasswordExpired,
        authz: false,
        exact: true,
    },
    {
        path: '/password/expiring',
        layout: PureLayout,
        component: PasswordExpiring,
        authz: false,
        exact: true,
    },
    {
        path: '/login',
        component: Login,
        authz: false,
        exact: true,
    },
];

export default routes;
