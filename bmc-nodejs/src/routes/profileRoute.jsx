import React from 'react';

const ProfileLayout = React.lazy(() => import('@/layout/ProfileLayout'));

const MyAccountInfo = React.lazy(() => import('@/views/profile/MyAccountInfo'));
const AccountPassword = React.lazy(() => import('@/views/profile/AccountPassword'));
const Idletimeout = React.lazy(() => import('@/views/profile/Idletimeout'));

const routes = [
    {
        path: '/profile/account',
        element: <ProfileLayout siderMenuId='bmc.setting.myaccount' />,
        children: [
            {
                path: '',
                element: <MyAccountInfo />,
                authz: true,
            },
        ],
    },
    {
        path: '/profile/password',
        element: <ProfileLayout siderMenuId='bmc.setting.password' />,
        children: [
            {
                path: '',
                element: <AccountPassword />,
                authz: true,
            },
        ],
    },
    {
        path: '/profile/idletimeout',
        element: <ProfileLayout siderMenuId='bmc.setting.idletimeout' />,
        children: [
            {
                path: '',
                element: <Idletimeout />,
                authz: true,
            },
        ],
    },
];

export default routes;
