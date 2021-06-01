import React from 'react';

import BasicLayout from '@/layout/BasicLayout';

const AccountList = React.lazy(() => import('@/views/system/account/AccountList'));
const NewAccount = React.lazy(() => import('@/views/system/account/NewAccount'));
const AccountAuthorize = React.lazy(() => import('@/views/system/account/AccountAuthorize'));

const RoleList = React.lazy(() => import('@/views/system/role/RoleList'));
const RoleInfo = React.lazy(() => import('@/views/system/role/RoleInfo'));
const NewRole = React.lazy(() => import('@/views/system/role/NewRole'));
const EditRole = React.lazy(() => import('@/views/system/role/EditRole'));

const OperationLog = React.lazy(() => import('@/views/system/auditlog/OperationLog'));
const SecurityLog = React.lazy(() => import('@/views/system/auditlog/SecurityLog'));
const SystemLog = React.lazy(() => import('@/views/system/auditlog/SystemLog'));
const ApiAuditLog = React.lazy(() => import('@/views/system/auditlog/ApiAuditLog'));
const RunningLog = React.lazy(() => import('@/views/system/auditlog/RunningLog'));

const AccountStrategy = React.lazy(() => import('@/views/system/security/AccountStrategy'));
const PasswordStrategy = React.lazy(() => import('@/views/system/security/PasswordStrategy'));
const OnlineAccount = React.lazy(() => import('@/views/system/security/OnlineAccountList'));

const routes = [
    {
        siderMenuId: 'bmc.account.list',
        path: '/account',
        layout: BasicLayout,
        routes: [
            {
                path: '/account/list',
                component: AccountList,
                exact: true,
            },
            {
                path: '/account/new',
                component: NewAccount,
                exact: true,
            },
            {
                path: '/account/authorize/:id',
                component: AccountAuthorize,
            },
        ],
    },
    {
        siderMenuId: 'bmc.role.list',
        path: '/role',
        layout: BasicLayout,
        routes: [
            {
                path: '/role/list',
                component: RoleList,
                exact: true,
            },
            {
                path: '/role/new',
                component: NewRole,
                exact: true,
            },
            {
                path: '/role/edit/:id',
                component: EditRole,
            },
            {
                path: '/role/view/:id',
                component: RoleInfo,
            },
        ],
    },
    {
        siderMenuId: 'bmc.auditlog.operation',
        path: '/auditlog/operate/list',
        layout: BasicLayout,
        component: OperationLog,
        exact: true,
    },
    {
        siderMenuId: 'bmc.auditlog.security',
        path: '/auditlog/security/list',
        layout: BasicLayout,
        component: SecurityLog,
        exact: true,
    },
    {
        siderMenuId: 'bmc.auditlog.system',
        path: '/auditlog/system/list',
        layout: BasicLayout,
        component: SystemLog,
        exact: true,
    },
    {
        siderMenuId: 'bmc.auditlog.api',
        path: '/auditlog/api/list',
        layout: BasicLayout,
        component: ApiAuditLog,
        exact: true,
    },
    {
        siderMenuId: 'bmc.running.log',
        path: '/running/log/list',
        layout: BasicLayout,
        component: RunningLog,
        exact: true,
    },
    {
        siderMenuId: 'bmc.account.strategy',
        path: '/saccount/strategy',
        layout: BasicLayout,
        component: AccountStrategy,
        exact: true,
    },
    {
        siderMenuId: 'bmc.password.strategy',
        path: '/password/strategy',
        layout: BasicLayout,
        component: PasswordStrategy,
        exact: true,
    },
    {
        siderMenuId: 'bmc.account.online',
        path: '/online/account',
        layout: BasicLayout,
        component: OnlineAccount,
        exact: true,
    },
];

export default routes;
