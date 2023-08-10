import React from 'react';

const BasicLayout = React.lazy(() => import('@/layout/BasicLayout'));

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
        path: '/account',
        element: <BasicLayout siderMenuId='bmc.account.list' />,
        children: [
            {
                path: 'list',
                element: <AccountList />,
                authz: true,
            },
            {
                path: 'new',
                element: <NewAccount />,
                authz: true,
            },
            {
                path: 'authorize/:id',
                element: <AccountAuthorize />,
                authz: true,
            },
        ],
    },
    {
        path: '/role',
        element: <BasicLayout siderMenuId='bmc.role.list' />,
        children: [
            {
                path: 'list',
                element: <RoleList />,
                authz: true,
            },
            {
                path: 'new',
                element: <NewRole />,
                authz: true,
            },
            {
                path: 'edit/:id',
                element: <EditRole />,
                authz: true,
            },
            {
                path: 'view/:id',
                element: <RoleInfo />,
                authz: true,
            },
        ],
    },
    {
        path: '/auditlog/operate',
        element: <BasicLayout siderMenuId='bmc.auditlog.operation' />,
        children: [
            {
                path: 'list',
                element: <OperationLog />,
                authz: true,
            },
        ],
    },
    {
        path: '/auditlog/security',
        element: <BasicLayout siderMenuId='bmc.auditlog.security' />,
        children: [
            {
                path: 'list',
                element: <SecurityLog />,
                authz: true,
            },
        ],
    },
    {
        path: '/auditlog/system',
        element: <BasicLayout siderMenuId='bmc.auditlog.system' />,
        children: [
            {
                path: 'list',
                element: <SystemLog />,
                authz: true,
            },
        ],
    },
    {
        path: '/auditlog/api',
        element: <BasicLayout siderMenuId='bmc.auditlog.api' />,
        children: [
            {
                path: 'list',
                element: <ApiAuditLog />,
                authz: true,
            },
        ],
    },
    {
        path: '/running/log',
        element: <BasicLayout siderMenuId='bmc.running.log' />,
        children: [
            {
                path: 'list',
                element: <RunningLog />,
                authz: true,
            },
        ],
    },
    {
        path: '/saccount/strategy',
        element: <BasicLayout siderMenuId='bmc.account.strategy' />,
        children: [
            {
                path: '',
                element: <AccountStrategy />,
                authz: true,
            },
        ],
    },
    {
        path: '/password/strategy',
        element: <BasicLayout siderMenuId='bmc.password.strategy' />,
        children: [
            {
                path: '',
                element: <PasswordStrategy />,
                authz: true,
            },
        ],
    },
    {
        path: '/online/account',
        element: <BasicLayout siderMenuId='bmc.account.online' />,
        children: [
            {
                path: '',
                element: <OnlineAccount />,
                authz: true,
            },
        ],
    },
];

export default routes;
