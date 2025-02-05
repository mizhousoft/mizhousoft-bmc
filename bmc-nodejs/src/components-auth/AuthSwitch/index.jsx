import { Switch } from 'antd';

import SessionStore from '@/store/SessionStore';

export default function AuthSwitch({ authId, defaultChecked, checkedChildren, unCheckedChildren, ...others }) {
    if (SessionStore.hasPermission(authId)) {
        return (
            <Switch checkedChildren={checkedChildren} unCheckedChildren={unCheckedChildren} defaultChecked={defaultChecked} {...others} />
        );
    }

    const children = defaultChecked ? checkedChildren : unCheckedChildren;
    return children;
}
