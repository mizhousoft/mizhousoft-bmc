import React from 'react';
import { Link } from 'react-router-dom';
import SessionStore from '@/session/SessionStore';

export default function AuthLink({ authId, to, childrenVisible = false, children, ...others }) {
    if (SessionStore.hasPermission(authId)) {
        return (
            <Link to={to} {...others}>
                {children}
            </Link>
        );
    }
    if (childrenVisible) {
        return children;
    }
    return null;
}
