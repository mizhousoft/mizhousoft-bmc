import React, { PureComponent } from 'react';
import SessionStore from '@/session/SessionStore';

class AuthA extends PureComponent {
    render() {
        const { authId, childrenVisible = false, ...others } = this.props;

        if (SessionStore.hasPermission(authId)) {
            return <a {...others} />;
        }
        if (childrenVisible) {
            return this.props.children;
        }
        return null;
    }
}

export default AuthA;
