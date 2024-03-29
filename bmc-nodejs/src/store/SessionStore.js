import Cookies from 'js-cookie';

import { BASENAME } from '@/config/application';
import DefaultUserStore from '@/store/DefaultUserStore';

let session;

export default {
    isAuthenticated() {
        const account = this.getAccount();
        if (account !== null && account !== undefined) {
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

    updateAccount(account) {
        const csrfToken = this.getCsrfToken();

        session = {
            account,
            csrfToken,
        };
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
};
