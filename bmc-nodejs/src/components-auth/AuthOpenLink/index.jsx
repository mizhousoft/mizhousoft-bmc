import { Link } from 'react-router';

import SessionStore from '@/store/SessionStore';

export default function AuthOpenLink({ authId, to, childrenVisible = false, children, ...others }) {
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
