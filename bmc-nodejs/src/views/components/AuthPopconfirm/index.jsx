import React from 'react';
import { Popconfirm } from 'antd';

import SessionStore from '@/store/SessionStore';

export default function AuthPopconfirm({ authId, title, children, ...others }) {
    if (SessionStore.hasPermission(authId)) {
        return (
            <Popconfirm title={title} {...others}>
                {children}
            </Popconfirm>
        );
    }
    return null;
}
