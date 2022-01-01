import React from 'react';
import SessionStore from '@/session/SessionStore';

export default function AuthA({ authId, childrenVisible = false, children, ...others }) {
    if (SessionStore.hasPermission(authId)) {
        return <a {...others}>{children}</a>;
    }
    if (childrenVisible) {
        return children;
    }

    return null;
}
