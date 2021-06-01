import React, { PureComponent } from 'react';
import { Popconfirm } from 'antd';
import SessionStore from '@/session/SessionStore';

class AuthPopconfirm extends PureComponent {
    render() {
        const { authId, title, children, ...others } = this.props;

        if (SessionStore.hasPermission(authId)) {
            return (
                <Popconfirm title={title} {...others}>
                    {children}
                </Popconfirm>
            );
        }
        return null;
    }
}

export default AuthPopconfirm;
