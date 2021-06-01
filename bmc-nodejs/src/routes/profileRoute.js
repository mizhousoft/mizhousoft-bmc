import React from 'react';

import ProfileLayout from '@/layout/ProfileLayout';

const MyAccountInfo = React.lazy(() => import('@/views/profile/MyAccountInfo'));
const AccountPassword = React.lazy(() => import('@/views/profile/AccountPassword'));
const Idletimeout = React.lazy(() => import('@/views/profile/Idletimeout'));

const routes = [
    {
        siderMenuId: 'bmc.setting.myaccount',
        path: '/profile/account',
        layout: ProfileLayout,
        component: MyAccountInfo,
        exact: true,
    },
    {
        siderMenuId: 'bmc.setting.password',
        path: '/profile/password',
        layout: ProfileLayout,
        component: AccountPassword,
        exact: true,
    },
    {
        siderMenuId: 'bmc.setting.idletimeout',
        path: '/profile/idletimeout',
        layout: ProfileLayout,
        component: Idletimeout,
        exact: true,
    },
];

export default routes;
