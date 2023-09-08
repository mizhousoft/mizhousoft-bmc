import { BASENAME } from '@/config/application';
import { asyncFetch, asyncPost } from '@/utils/request';

export function fetchRunningLogNames(param) {
    return asyncFetch({
        url: `${BASENAME}/runninglog/fetchRunningLogNames.action`,
        data: param,
    });
}

export function fetchRunningLogFileNames(param) {
    return asyncFetch({
        url: `${BASENAME}/runninglog/fetchRunningLogFileNames.action`,
        data: param,
    });
}

export function fetchApiAuditLogs(param) {
    return asyncPost({
        url: `${BASENAME}/auditlog/fetchApiAuditLogs.action`,
        data: param,
    });
}

export function fetchSecurityLogs(param) {
    return asyncPost({
        url: `${BASENAME}/auditlog/fetchSecurityLogs.action`,
        data: param,
    });
}

export function fetchOperationLogs(param) {
    return asyncPost({
        url: `${BASENAME}/auditlog/fetchOperationLogs.action`,
        data: param,
    });
}

export function fetchSystemLogs(param) {
    return asyncPost({
        url: `${BASENAME}/auditlog/fetchSystemLogs.action`,
        data: param,
    });
}
