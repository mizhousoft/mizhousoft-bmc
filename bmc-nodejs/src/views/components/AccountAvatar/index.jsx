import React from 'react';
import { Link, Navigate } from 'react-router-dom';

import FontIcon from '@/components/FontIcon';
import { LOGIN_PATH } from '@/config/application';
import SessionStore from '@/session/SessionStore';

export default function AccountAvatar({ isActive }) {
    const account = SessionStore.getAccount();
    if (account === null) {
        return <Navigate to={LOGIN_PATH} replace />;
    }

    return (
        <span className='mz-header-avatar'>
            <Link to='/profile/account' className={isActive ? 'active' : ''} replace>
                {account.name}

                <FontIcon
                    type='anticon-arrow_down'
                    style={{ fontSize: '0.9em', verticalAlign: 'middle', marginLeft: '4px' }}
                />
            </Link>
        </span>
    );
}
