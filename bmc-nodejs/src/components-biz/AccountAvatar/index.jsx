import { Link, Navigate } from 'react-router';

import FontIcon from '@/components/FontIcon';
import { LOGIN_PATH } from '@/config/application';
import SessionStore from '@/store/SessionStore';

export default function AccountAvatar({ isActive }) {
    const account = SessionStore.getAccount();
    if (account === undefined) {
        return <Navigate to={LOGIN_PATH} />;
    }

    return (
        <span className='mz-header-avatar'>
            <Link to='/profile/account' className={isActive ? 'active' : ''}>
                {account.name}

                <FontIcon type='anticon-arrow_down' style={{ fontSize: '0.8em', verticalAlign: 'middle', marginLeft: '4px' }} />
            </Link>
        </span>
    );
}
