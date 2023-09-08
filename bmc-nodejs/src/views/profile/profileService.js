import { BASENAME } from '@/config/application';
import { asyncFetch, asyncPost } from '@/utils/request';

export function fetchMyAccountInfo() {
    return asyncFetch({
        url: `${BASENAME}/setting/account/fetchMyAccountInfo.action`,
    });
}

export function modifyPhoneNumber(body) {
    return asyncPost({
        url: `${BASENAME}/setting/account/modifyPhoneNumber.action`,
        data: body,
    });
}

export function fetchPasswordStrategy() {
    return asyncFetch({
        url: `${BASENAME}/setting/password/fetchPasswordStrategy.action`,
    });
}

export function modifyAccountPassword(body) {
    return asyncPost({
        url: `${BASENAME}/setting/password/modifyPassword.action`,
        data: body,
    });
}

export function fetchIdletimeout() {
    return asyncFetch({
        url: `${BASENAME}/setting/idletimeout/fetchIdletimeout.action`,
    });
}

export function modifyIdletimeout(body) {
    return asyncPost({
        url: `${BASENAME}/setting/idletimeout/modifyIdletimeout.action`,
        data: body,
    });
}

export function modifyFirstLoginPassword(body) {
    return asyncPost({
        url: `${BASENAME}/setting/password/modifyFirstLoginPassword.action`,
        data: body,
    });
}

export function modifyExpiredPassword(body) {
    return asyncPost({
        url: `${BASENAME}/setting/password/modifyExpiredPassword.action`,
        data: body,
    });
}

export function fetchPasswordExpiringDays() {
    return asyncFetch({
        url: `${BASENAME}/setting/password/fetchPasswordExpiringDays.action`,
    });
}

export function modifyExpiringPassword(body) {
    return asyncPost({
        url: `${BASENAME}/setting/password/modifyExpiringPassword.action`,
        data: body,
    });
}
