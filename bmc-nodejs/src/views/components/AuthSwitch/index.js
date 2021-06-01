import React, { PureComponent } from 'react';
import { Switch } from 'antd';
import SessionStore from '@/session/SessionStore';

class AuthSwitch extends PureComponent {
    render() {
        const { authId, defaultChecked, checkedChildren, unCheckedChildren, ...others } = this.props;

        if (SessionStore.hasPermission(authId)) {
            return (
                <Switch
                    checkedChildren={checkedChildren}
                    unCheckedChildren={unCheckedChildren}
                    defaultChecked={defaultChecked}
                    {...others}
                />
            );
        }

        const children = defaultChecked ? checkedChildren : unCheckedChildren;
        return children;
    }
}

export default AuthSwitch;
