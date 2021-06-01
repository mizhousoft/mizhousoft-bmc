import React, { PureComponent } from 'react';
import { withRouter, Link, Redirect } from 'react-router-dom';
import { LOGIN_PATH } from '@/config/application';
import SessionStore from '@/session/SessionStore';

class AccountAvatar extends PureComponent {
    render() {
        const account = SessionStore.getAccount();
        if (account === null) {
            return <Redirect to={LOGIN_PATH} />;
        }

        const classname = this.props.isActive ? 'header-avatar-selected' : 'header-avatar';

        return (
            <span style={{ margin: '0 18px 0 28px', display: 'inline-block', position: 'relative' }}>
                <Link to='/profile/account' className={classname}>
                    {account.name}
                </Link>
            </span>
        );
    }
}

export default withRouter(AccountAvatar);
