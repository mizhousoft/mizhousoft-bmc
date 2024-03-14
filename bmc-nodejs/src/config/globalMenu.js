export const GLOBAL_MENUS = [
    {
        id: 'bmc.system.management',
        name: '系统',
        subMenus: [
            {
                id: 'bmc.account.management',
                name: '帐号管理',
                iconFont: 'anticon-admin',
                subMenus: [
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
                subMenus: [
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
                subMenus: [
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

const MENU_ID_MAP = new Map();

function initMenuIdMapping(menus) {
    menus.forEach((topMenu) => {
        MENU_ID_MAP.set(topMenu.id, topMenu.id);

        const { subMenus } = topMenu;
        if (undefined !== subMenus) {
            subMenus.forEach((subMenu) => {
                MENU_ID_MAP.set(subMenu.id, topMenu.id);

                const childMenus = subMenu.subMenus;
                if (undefined !== childMenus) {
                    childMenus.forEach((childMenu) => {
                        MENU_ID_MAP.set(childMenu.id, topMenu.id);
                    });
                }
            });
        }
    });
}

initMenuIdMapping(GLOBAL_MENUS);

export function getTopMenuId(menuId) {
    return MENU_ID_MAP.get(menuId);
}

export function getTopSubMenus(topMenuId) {
    for (let i = 0; i < GLOBAL_MENUS.length; ++i) {
        const topMenu = GLOBAL_MENUS[i];
        if (topMenu.id === topMenuId) {
            return JSON.parse(JSON.stringify(topMenu.subMenus));
        }
    }

    return [];
}
