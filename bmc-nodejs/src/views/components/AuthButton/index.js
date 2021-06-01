import React, { PureComponent } from 'react';
import { Button } from 'antd';
import SessionStore from '@/session/SessionStore';

class AuthButton extends PureComponent {
    render() {
        const { authId, ...others } = this.props;

        if (SessionStore.hasPermission(authId)) {
            return <Button {...others} />;
        }
        return null;
    }
}

export default AuthButton;
