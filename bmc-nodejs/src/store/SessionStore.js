import Cookies from 'js-cookie';

import { BASENAME, CONTEXT_LOGIN_PATH } from '@/config/application';
import { GLOBAL_MENUS } from '@/config/global-menu';
import DefaultUserStore from '@/store/DefaultUserStore';
import httpRequest from '@/utils/http-request';

let session;

export default {
    isAuthenticated() {
        const account = this.getAccount();
        if (account !== null) {
            return true;
        }

        return false;
    },

    logout() {
        session = undefined;

        DefaultUserStore.clearAll();
    },

    getSession() {
        if (session !== undefined) {
            return session;
        }

        const csrfToken = Cookies.get('csrf-token');
        if (undefined === csrfToken || csrfToken === null) {
            return undefined;
        }

        const request = new XMLHttpRequest();
        request.open('GET', `${BASENAME}/account/fetchMyAccountDetail.action`, false);
        request.setRequestHeader('X-Csrf-Token', csrfToken);
        request.setRequestHeader('Accept', 'application/json;charset=UTF-8');
        request.setRequestHeader('Cache-Control', 'no-cache');
        request.setRequestHeader('Pragma', 'no-cache');
        request.send(null);

        if (request.status === 200) {
            const response = JSON.parse(request.responseText);

            session = {
                account: response.account,
                csrfToken,
            };

            return session;
        }

        return undefined;
    },

    getAccount() {
        const currSession = this.getSession();
        if (undefined !== currSession) {
            return currSession.account;
        }

        return undefined;
    },

    getCsrfToken() {
        if (session !== undefined) {
            const { csrfToken } = session;
            if (undefined !== csrfToken) {
                return csrfToken;
            }
        }

        const csrfToken = Cookies.get('csrf-token');
        return csrfToken;
    },

    initAccountInfo(callback) {
        const requestBody = {
            url: '/account/fetchMyAccountDetail.action',
            data: {},
        };

        httpRequest.get(requestBody).then(({ fetchStatus, account, nowTime }) => {
            if (fetchStatus.okey) {
                const csrfToken = this.getCsrfToken();

                session = {
                    account,
                    csrfToken,
                };

                if (callback) {
                    callback();
                }
            } else {
                window.location.href = CONTEXT_LOGIN_PATH;
            }
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
};
