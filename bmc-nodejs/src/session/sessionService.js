import SessionStore from './SessionStore';
import { BASENAME } from '@/config/application';
import { asyncFetch, asyncPost } from '@/utils/request';

export function userLogin(body) {
    return asyncPost({
        url: `${BASENAME}/login.action`,
        data: body,
    });
}

export function logout() {
    return asyncFetch({
        url: `${BASENAME}/logout.action`,
    })
        .then((resp) => {
            SessionStore.logout();
            return resp;
        })
        .catch((error) => error);
}

export function fetchMyAccountDetail() {
    return asyncFetch({
        url: `${BASENAME}/account/fetchMyAccountDetail.action`,
    });
}
