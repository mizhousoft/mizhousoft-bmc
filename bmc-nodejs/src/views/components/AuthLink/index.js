import React, { PureComponent } from 'react';
import { Link } from 'react-router-dom';
import SessionStore from '@/session/SessionStore';

class AuthLink extends PureComponent {
    render() {
        const { authId, to, childrenVisible = false, ...others } = this.props;

        if (SessionStore.hasPermission(authId)) {
            return <Link to={to} {...others} />;
        }
        if (childrenVisible) {
            return this.props.children;
        }
        return null;
    }
}

export default AuthLink;
