import React, { PureComponent } from 'react';
import { Link } from 'react-router-dom';
import SessionStore from '@/session/SessionStore';

class OpenAuthLink extends PureComponent {
    render() {
        const { authId, to, childrenVisible = false, ...others } = this.props;

        if (SessionStore.hasPermission(authId)) {
            return <Link to={to} {...others} target='_blank' rel='opener' />;
        }
        if (childrenVisible) {
            return this.props.children;
        }
        return null;
    }
}

export default OpenAuthLink;
