import React from 'react';

const PureLayout = React.lazy(() => import('@/layout/PureLayout'));

const Login = React.lazy(() => import('@/views/Login'));

const FirstLogin = React.lazy(() => import('@/views/profile/FirstLogin'));
const PasswordExpired = React.lazy(() => import('@/views/profile/PasswordExpired'));
const PasswordExpiring = React.lazy(() => import('@/views/profile/PasswordExpiring'));

const routes = [
    {
        path: '/login/first',
        element: <PureLayout />,
        children: [
            {
                path: '',
                element: <FirstLogin />,
                authz: false,
            },
        ],
    },
    {
        path: '/password/expired',
        element: <PureLayout />,
        children: [
            {
                path: '',
                element: <PasswordExpired />,
                authz: false,
            },
        ],
    },
    {
        path: '/password/expiring',
        element: <PureLayout />,
        children: [
            {
                path: '',
                element: <PasswordExpiring />,
                authz: false,
            },
        ],
    },
    {
        path: '/login',
        element: <Login />,
    },
];

export default routes;
