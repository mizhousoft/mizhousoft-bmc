import React from 'react';
import { Link, Redirect } from 'react-router-dom';
import { LOGIN_PATH } from '@/config/application';
import SessionStore from '@/session/SessionStore';

export default function AccountAvatar({ isActive }) {
    const account = SessionStore.getAccount();
    if (account === null) {
        return <Redirect to={LOGIN_PATH} />;
    }

    const classname = isActive ? 'header-avatar-selected' : 'header-avatar';

    return (
        <span style={{ margin: '0 18px 0 28px', display: 'inline-block', position: 'relative' }}>
            <Link to='/profile/account' className={classname}>
                {account.name}
            </Link>
        </span>
    );
}
