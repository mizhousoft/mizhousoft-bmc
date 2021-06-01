import DefaultUserStore from '@/store/DefaultUserStore';
import { CONTEXT_LOGIN_PATH, BASENAME } from '@/config/application';
import { GLOBAL_MENUS } from '@/config/globalMenu';
import { asyncFetch } from '@/utils/request';

const SESSION_KEY = 'SESSION_KEY';

let memSession = null;

export default {
    isAuthenticated() {
        const account = this.getAccount();
        if (account !== null) {
            return true;
        }

        return false;
    },

    logout() {
        memSession = null;
        DefaultUserStore.clear();
    },

    getAccount() {
        if (memSession !== null) {
            return memSession.account;
        }

        const jsonSession = DefaultUserStore.getItem(SESSION_KEY);
        if (jsonSession) {
            memSession = JSON.parse(jsonSession);
            return memSession.account;
        }

        return null;
    },

    initAccountInfo(callback) {
        this.fetchMyAccountDetail()
            .then(({ fetchStatus, account, nowTime }) => {
                if (!fetchStatus.okey) {
                    window.location.href = CONTEXT_LOGIN_PATH;
                } else {
                    memSession = {
                        account,
                        nowTime,
                    };
                    DefaultUserStore.setItem(SESSION_KEY, JSON.stringify(memSession));

                    if (callback) {
                        callback();
                    }
                }
            })
            .catch((error) => {
                window.location.href = CONTEXT_LOGIN_PATH;
            });
    },

    hasPermission(authId) {
        const account = this.getAccount();
        if (account) {
            if (account.permissions) {
                const { permissions } = account;

                return permissions.includes(authId);
            }
        }

        return false;
    },

    getHomePath() {
        for (let i = 0; i < GLOBAL_MENUS.length; ++i) {
            const topMenu = GLOBAL_MENUS[i];
            if (!this.hasPermission(topMenu.id)) {
                continue;
            }

            if (topMenu.path) {
                return topMenu.path;
            }

            if (!topMenu.subMenus) {
                continue;
            }

            for (let j = 0; j < topMenu.subMenus.length; ++j) {
                const subMenu = topMenu.subMenus[j];
                if (!this.hasPermission(subMenu.id)) {
                    continue;
                }

                if (subMenu.path) {
                    return subMenu.path;
                }

                if (!subMenu.subMenus) {
                    continue;
                }

                for (let k = 0; k < subMenu.subMenus.length; ++k) {
                    const childMenu = subMenu.subMenus[k];
                    if (!this.hasPermission(childMenu.id)) {
                        continue;
                    }

                    if (childMenu.path) {
                        return childMenu.path;
                    }
                }
            }
        }

        // 当没有任何权限，跳转到我的帐号信息页面
        return '/profile/account';
    },

    getFirstAccessiblePath(menus) {
        for (let i = 0; i < menus.length; ++i) {
            const menu = menus[i];
            if (!this.hasPermission(menu.id)) {
                continue;
            }

            if (menu.path) {
                return menu.path;
            }

            if (!menu.subMenus) {
                continue;
            }

            for (let j = 0; j < menu.subMenus.length; ++j) {
                const subMenu = menu.subMenus[j];
                if (!this.hasPermission(subMenu.id)) {
                    continue;
                }

                if (subMenu.path) {
                    return subMenu.path;
                }

                if (!subMenu.subMenus) {
                    continue;
                }

                for (let k = 0; k < subMenu.subMenus.length; ++k) {
                    const childMenu = subMenu.subMenus[k];
                    if (!this.hasPermission(childMenu.id)) {
                        continue;
                    }

                    if (childMenu.path) {
                        return childMenu.path;
                    }
                }
            }
        }

        // 当没有任何权限，跳转到我的帐号信息页面
        return null;
    },

    getFirstAccessibleUrl(menus) {
        const path = this.getFirstAccessiblePath(menus);
        if (path === null) {
            return path;
        }

        return BASENAME + path;
    },

    fetchMyAccountDetail() {
        return asyncFetch({
            url: `${BASENAME}/account/fetchMyAccountDetail.action`,
        });
    },
};
