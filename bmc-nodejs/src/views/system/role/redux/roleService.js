import { asyncFetch, asyncPost } from '@/utils/request';
import { BASENAME } from '@/config/application';

export function fetchRoles(param) {
    return asyncPost({
        url: `${BASENAME}/role/fetchRoles.action`,
        data: param,
    });
}

export function fetchRoleInfo(param) {
    return asyncFetch({
        url: `${BASENAME}/role/fetchRoleInfo.action`,
        data: param,
    });
}

export function newRole(param) {
    return asyncFetch({
        url: `${BASENAME}/role/newRole.action`,
        data: param,
    });
}

export function addRole(param) {
    return asyncPost({
        url: `${BASENAME}/role/addRole.action`,
        data: param,
    });
}

export function editRole(param) {
    return asyncFetch({
        url: `${BASENAME}/role/editRole.action`,
        data: param,
    });
}

export function modifyRole(param) {
    return asyncPost({
        url: `${BASENAME}/role/modifyRole.action`,
        data: param,
    });
}

export function deleteRole(body) {
    return asyncPost({
        url: `${BASENAME}/role/deleteRole.action`,
        data: body,
    });
}
