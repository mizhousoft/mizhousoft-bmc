import { BASENAME } from '@/config/application';
import { asyncFetch, asyncPost } from '@/utils/request';

export function fetchAccountInfoList(param) {
    return asyncFetch({
        url: `${BASENAME}/account/fetchAccountInfoList.action`,
        data: param,
    });
}

export function fetchRolesOnNew(param) {
    return asyncFetch({
        url: `${BASENAME}/account/new/fetchRoles.action`,
        data: param,
    });
}

export function addAccount(body) {
    return asyncPost({
        url: `${BASENAME}/account/addAccount.action`,
        data: body,
    });
}

export function resetPassword(body) {
    return asyncPost({
        url: `${BASENAME}/account/resetPassword.action`,
        data: body,
    });
}

export function disableAccount(body) {
    return asyncPost({
        url: `${BASENAME}/account/disableAccount.action`,
        data: body,
    });
}

export function enableAccount(body) {
    return asyncPost({
        url: `${BASENAME}/account/enableAccount.action`,
        data: body,
    });
}

export function unlockAccount(body) {
    return asyncPost({
        url: `${BASENAME}/account/unlockAccount.action`,
        data: body,
    });
}

export function deleteAccount(body) {
    return asyncPost({
        url: `${BASENAME}/account/deleteAccount.action`,
        data: body,
    });
}

export function fetchRolesOnAuthorize(param) {
    return asyncFetch({
        url: `${BASENAME}/account/authorize/fetchRoles.action`,
        data: param,
    });
}

export function fetchAccountRolesOnAuthorize(param) {
    return asyncFetch({
        url: `${BASENAME}/account/authorize/fetchAccountRoles.action`,
        data: param,
    });
}

export function authorizeAccount(body) {
    return asyncPost({
        url: `${BASENAME}/account/authorizeAccount.action`,
        data: body,
    });
}
