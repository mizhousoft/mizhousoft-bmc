import { BASENAME } from '@/config/application';
import { asyncFetch, asyncPost } from '@/utils/request';

export function fetchPasswordStrategy() {
    return asyncFetch({
        url: `${BASENAME}/system/fetchPasswordStrategy.action`,
    });
}

export function modifyPasswordStrategy(body) {
    return asyncPost({
        url: `${BASENAME}/system/modifyPasswordStrategy.action`,
        data: body,
    });
}

export function fetchAccountStrategy() {
    return asyncFetch({
        url: `${BASENAME}/system/fetchAccountStrategy.action`,
    });
}

export function modifyAccountStrategy(body) {
    return asyncPost({
        url: `${BASENAME}/system/modifyAccountStrategy.action`,
        data: body,
    });
}

export function fetchOnlineAccounts(body) {
    return asyncFetch({
        url: `${BASENAME}/system/fetchOnlineAccounts.action`,
        data: body,
    });
}

export function logoffOnlineAccount(body) {
    return asyncPost({
        url: `${BASENAME}/system/logoffOnlineAccount.action`,
        data: body,
    });
}
