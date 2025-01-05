import SessionStore from '@/store/SessionStore';

export default function AuthA({ authId, childrenVisible = false, disabled = false, children, ...others }) {
    if (disabled) {
        return <a disabled>{children}</a>;
    }
    if (SessionStore.hasPermission(authId)) {
        return <a {...others}>{children}</a>;
    }
    if (childrenVisible) {
        return <a disabled>{children}</a>;
    }

    return null;
}
