import React from 'react';
import { Link } from 'react-router-dom';

import SessionStore from '@/session/SessionStore';

export default function AuthLink({ authId, to, childrenVisible = false, disabled = false, children, ...others }) {
    if (disabled) {
        return <a disabled>{children}</a>;
    }
    if (SessionStore.hasPermission(authId)) {
        return (
            <Link to={to} {...others}>
                {children}
            </Link>
        );
    }
    if (childrenVisible) {
        return <a disabled>{children}</a>;
    }
    return null;
}
