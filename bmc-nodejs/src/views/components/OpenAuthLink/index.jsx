import React from 'react';
import { Link } from 'react-router-dom';
import SessionStore from '@/session/SessionStore';

export default function OpenAuthLink({ authId, to, childrenVisible = false, children, ...others }) {
    if (SessionStore.hasPermission(authId)) {
        return (
            <Link to={to} {...others} target='_blank' rel='opener'>
                {children}
            </Link>
        );
    }
    if (childrenVisible) {
        return children;
    }

    return null;
}
