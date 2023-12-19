import Cookies from 'js-cookie';

import { BASENAME, CONTEXT_LOGIN_PATH } from '@/config/application';
import { GLOBAL_MENUS } from '@/config/globalMenu';
import DefaultUserStore from '@/store/DefaultUserStore';
import GlobalUserStore from '@/store/GlobalUserStore';
import { asyncFetch } from '@/utils/request';

const ACCOUNT_KEY = 'account_data';
const CSRF_TOKEN_KEY = 'csrf_token';

let session = null;
let sessionFromLocal = false;

export default {
    isAuthenticated() {
        const account = this.getAccount();
        if (account !== null) {
            return true;
        }

        return false;
    },

    isSessionFromLocal() {
        return sessionFromLocal;
    },

    isLocalSessionExist() {
        const jsonAccount = GlobalUserStore.getItem(ACCOUNT_KEY);

        return jsonAccount !== null;
    },

    updateSessionFrom() {
        sessionFromLocal = false;
    },

    logout() {
        session = null;
        GlobalUserStore.clear();
        DefaultUserStore.clear();
    },

    getSession() {
        if (session !== null) {
            return session;
        }

        const jsonAccount = GlobalUserStore.getItem(ACCOUNT_KEY);
        if (jsonAccount) {
            const account = JSON.parse(jsonAccount);
            const csrfToken = GlobalUserStore.getItem(CSRF_TOKEN_KEY);

            session = {
                account,
                csrfToken,
            };
            sessionFromLocal = true;

            return session;
        }

        return null;
    },

    getAccount() {
        const currSession = this.getSession();
        if (currSession !== null) {
            return currSession.account;
        }

        return null;
    },

    getCsrfToken() {
        const currSession = this.getSession();
        if (currSession !== null) {
            const { csrfToken } = currSession;
            if (undefined !== csrfToken) {
                return csrfToken;
            }
        }

        const csrfToken = Cookies.get('csrf-token');
        return csrfToken;
    },

    initAccountInfo(callback) {
        this.fetchMyAccountDetail()
            .then(({ fetchStatus, account, nowTime }) => {
                if (!fetchStatus.okey) {
                    window.location.href = CONTEXT_LOGIN_PATH;
                } else {
                    const csrfToken = this.getCsrfToken();

                    session = {
                        account,
                        csrfToken,
                    };
                    sessionFromLocal = false;

                    GlobalUserStore.setItem(ACCOUNT_KEY, JSON.stringify(account));
                    GlobalUserStore.setItem(CSRF_TOKEN_KEY, csrfToken);

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
