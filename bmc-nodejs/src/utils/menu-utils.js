import globalMenus from '@/config/global-menu';
import SessionStore from '@/store/SessionStore';

const MENU_ID_MAP = new Map();
globalMenus.forEach((topMenu) => {
    MENU_ID_MAP.set(topMenu.id, topMenu.id);

    if (undefined !== topMenu.children) {
        topMenu.children.forEach((childMenu) => {
            MENU_ID_MAP.set(childMenu.id, topMenu.id);

            if (undefined !== childMenu.children) {
                childMenu.children.forEach((grandsonMenu) => {
                    MENU_ID_MAP.set(grandsonMenu.id, topMenu.id);
                });
            }
        });
    }
});

export default {
    getTopMenuId(menuId) {
        return MENU_ID_MAP.get(menuId);
    },

    getFirstVisibleMenuPath(menus) {
        for (let i = 0; i < menus.length; ++i) {
            const menu = menus[i];
            if (!SessionStore.hasPermission(menu.id)) {
                continue;
            }

            if (menu.path) {
                return menu.path;
            }

            if (!menu.children) {
                continue;
            }

            const path = this.getFirstVisibleMenuPath(menu.children);
            if (undefined !== path) {
                return path;
            }
        }

        return undefined;
    },

    getNavigationMenus() {
        const naviMenus = [];

        for (let i = 0; i < globalMenus.length; ++i) {
            const topMenu = globalMenus[i];
            if (!SessionStore.hasPermission(topMenu.id)) {
                continue;
            }

            let { path } = topMenu;
            if (undefined === path && topMenu.children) {
                path = this.getFirstVisibleMenuPath(topMenu.children);
            }

            if (path) {
                naviMenus.push({
                    id: topMenu.id,
                    name: topMenu.name,
                    path,
                    iconClass: topMenu.iconClass,
                });
            }
        }

        return naviMenus;
    },

    getMenuChildren(topMenuId) {
        const topMenu = globalMenus.find((menu) => menu.id === topMenuId);
        if (topMenu) {
            const children = JSON.parse(JSON.stringify(topMenu.children));

            const siderMenus = [];
            for (let i = 0; i < children.length; ++i) {
                const childMenu = children[i];
                if (!SessionStore.hasPermission(childMenu.id)) {
                    continue;
                }

                if (childMenu.children) {
                    const grandsonMenus = childMenu.children.filter((m) => SessionStore.hasPermission(m.id));

                    if (grandsonMenus.length > 0) {
                        childMenu.children = grandsonMenus;
                        siderMenus.push(childMenu);
                    }
                } else {
                    siderMenus.push(childMenu);
                }
            }

            return siderMenus;
        }

        return [];
    },

    getVisibleMenus() {
        const menus = [];

        for (let i = 0; i < globalMenus.length; ++i) {
            const topMenu = globalMenus[i];
            if (!SessionStore.hasPermission(topMenu.id)) {
                continue;
            }

            const children = this.getMenuChildren(topMenu.id);
            if (children.length === 0) {
                continue;
            }

            const menu = {
                id: topMenu.id,
                name: topMenu.name,
                iconFont: topMenu.iconFont,
                children,
            };
            menus.push(menu);
        }

        return menus;
    },

    getHomePath() {
        for (let i = 0; i < globalMenus.length; ++i) {
            const topMenu = globalMenus[i];
            if (!SessionStore.hasPermission(topMenu.id)) {
                continue;
            }

            let { path } = topMenu;
            if (undefined === path && topMenu.children) {
                path = this.getFirstVisibleMenuPath(topMenu.children);
            }

            if (path) {
                return path;
            }
        }

        // 当没有任何权限，跳转到我的帐号信息页面
        return '/profile/account';
    },

    findSiderMenuId(path, siderMenus) {
        for (let i = 0; i < siderMenus.length; ++i) {
            const siderMenu = siderMenus[i];
            if (undefined === siderMenu.children) {
                if (path === siderMenu.path) {
                    return siderMenu.id;
                }
            } else {
                for (let j = 0; j < siderMenu.children.length; ++j) {
                    const subMenu = siderMenu.children[j];
                    if (path === subMenu.path) {
                        return subMenu.id;
                    }

                    if (subMenu.children) {
                        for (let k = 0; k < subMenu.children.length; ++k) {
                            const childMenu = subMenu.children[k];
                            if (childMenu.path === path) {
                                return childMenu.id;
                            }
                        }
                    }
                }
            }
        }

        return null;
    },

    findOpenMenuKeys(selectMenuId, menus) {
        for (let i = 0; i < menus.length; ++i) {
            const menu = menus[i];

            if (menu.children) {
                for (let j = 0; j < menu.children.length; ++j) {
                    const childMenu = menu.children[j];
                    if (selectMenuId === childMenu.id) {
                        return [menu.id];
                    }

                    if (childMenu.children) {
                        for (let k = 0; k < childMenu.children.length; ++k) {
                            const grandsonMenu = childMenu.children[k];
                            if (grandsonMenu.id === selectMenuId) {
                                return [menu.id, childMenu.id];
                            }
                        }
                    }
                }
            }
        }

        return [];
    },
};
