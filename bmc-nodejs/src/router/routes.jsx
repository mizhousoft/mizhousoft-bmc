import React from 'react';

import GenericLayout from '@/layout/GenericLayout';
import NaviSiderLayout from '@/layout/NaviSiderLayout';
import ProfileLayout from '@/layout/ProfileLayout';
import Login from '@/views/account/Login';

const FirstLogin = React.lazy(() => import('@/views/account/FirstLogin'));
const PasswordExpired = React.lazy(() => import('@/views/account/PasswordExpired'));
const PasswordExpiring = React.lazy(() => import('@/views/account/PasswordExpiring'));

const AccountProfile = React.lazy(() => import('@/views/account/AccountProfile'));
const PasswordEdit = React.lazy(() => import('@/views/account/PasswordEdit'));
const Idletimeout = React.lazy(() => import('@/views/account/Idletimeout'));

const AccountList = React.lazy(() => import('@/views/system/account/AccountList'));
const NewAccount = React.lazy(() => import('@/views/system/account/NewAccount'));
const AccountAuthorize = React.lazy(() => import('@/views/system/account/AccountAuthorize'));

const RoleList = React.lazy(() => import('@/views/system/role/RoleList'));
const RoleInfo = React.lazy(() => import('@/views/system/role/RoleInfo'));
const NewRole = React.lazy(() => import('@/views/system/role/NewRole'));
const EditRole = React.lazy(() => import('@/views/system/role/EditRole'));

const OperationLog = React.lazy(() => import('@/views/system/auditlog/OperationLog'));
const SecurityLog = React.lazy(() => import('@/views/system/auditlog/SecurityLog'));
const RunningLog = React.lazy(() => import('@/views/system/auditlog/RunningLog'));

const AccountStrategy = React.lazy(() => import('@/views/system/security/AccountStrategy'));
const PasswordStrategy = React.lazy(() => import('@/views/system/security/PasswordStrategy'));
const OnlineAccount = React.lazy(() => import('@/views/system/security/OnlineAccountList'));

const routes = [
    {
        path: '/login/first',
        element: <GenericLayout />,
        children: [
            {
                index: true,
                element: <FirstLogin />,
                authz: false,
            },
        ],
    },
    {
        path: '/password/expired',
        element: <GenericLayout />,
        children: [
            {
                index: true,
                element: <PasswordExpired />,
                authz: false,
            },
        ],
    },
    {
        path: '/password/expiring',
        element: <GenericLayout />,
        children: [
            {
                index: true,
                element: <PasswordExpiring />,
                authz: false,
            },
        ],
    },
    {
        path: '/login',
        element: <Login />,
    },
    {
        path: '/profile/account',
        element: <ProfileLayout siderMenuId='bmc.setting.myaccount' />,
        children: [
            {
                index: true,
                element: <AccountProfile />,
                authz: true,
            },
        ],
    },
    {
        path: '/profile/password',
        element: <ProfileLayout siderMenuId='bmc.setting.password' />,
        children: [
            {
                index: true,
                element: <PasswordEdit />,
                authz: true,
            },
        ],
    },
    {
        path: '/profile/idletimeout',
        element: <ProfileLayout siderMenuId='bmc.setting.idletimeout' />,
        children: [
            {
                index: true,
                element: <Idletimeout />,
                authz: true,
            },
        ],
    },
    {
        path: '/account',
        element: <NaviSiderLayout siderMenuId='bmc.account.list' />,
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
        element: <NaviSiderLayout siderMenuId='bmc.role.list' />,
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
        element: <NaviSiderLayout siderMenuId='bmc.auditlog.operation' />,
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
        element: <NaviSiderLayout siderMenuId='bmc.auditlog.security' />,
        children: [
            {
                path: 'list',
                element: <SecurityLog />,
                authz: true,
            },
        ],
    },
    {
        path: '/running/log',
        element: <NaviSiderLayout siderMenuId='bmc.running.log' />,
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
        element: <NaviSiderLayout siderMenuId='bmc.account.strategy' />,
        children: [
            {
                index: true,
                element: <AccountStrategy />,
                authz: true,
            },
        ],
    },
    {
        path: '/password/strategy',
        element: <NaviSiderLayout siderMenuId='bmc.password.strategy' />,
        children: [
            {
                index: true,
                element: <PasswordStrategy />,
                authz: true,
            },
        ],
    },
    {
        path: '/online/account',
        element: <NaviSiderLayout siderMenuId='bmc.account.online' />,
        children: [
            {
                index: true,
                element: <OnlineAccount />,
                authz: true,
            },
        ],
    },
];

export default routes;
