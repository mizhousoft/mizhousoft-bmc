export default [
    {
        id: 'bmc.system.management',
        name: '系统',
        children: [
            {
                id: 'bmc.account.management',
                name: '帐号管理',
                iconFont: 'anticon-admin',
                children: [
                    {
                        id: 'bmc.account.list',
                        name: '帐号',
                        path: '/account/list',
                    },
                    {
                        id: 'bmc.role.list',
                        name: '角色',
                        path: '/role/list',
                    },
                ],
            },
            {
                id: 'bmc.security.management',
                name: '安全管理',
                iconFont: 'anticon-security',
                children: [
                    {
                        id: 'bmc.account.strategy',
                        name: '帐号策略',
                        path: '/saccount/strategy',
                    },
                    {
                        id: 'bmc.password.strategy',
                        name: '密码策略',
                        path: '/password/strategy',
                    },
                    {
                        id: 'bmc.account.online',
                        name: '在线帐号',
                        path: '/online/account',
                    },
                ],
            },
            {
                id: 'bmc.auditlog.management',
                name: '审计日志',
                iconFont: 'anticon-auditlog',
                children: [
                    {
                        id: 'bmc.auditlog.operation',
                        name: '操作日志',
                        path: '/auditlog/operate/list',
                    },
                    {
                        id: 'bmc.auditlog.security',
                        name: '安全日志',
                        path: '/auditlog/security/list',
                    },
                    {
                        id: 'bmc.running.log',
                        name: '本地日志',
                        path: '/running/log/list',
                    },
                ],
            },
        ],
    },
];
