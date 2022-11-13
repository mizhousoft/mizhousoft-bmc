import React from 'react';
import { Button } from 'antd';
import SessionStore from '@/session/SessionStore';

export default function AuthButton({ authId, ...others }) {
    if (SessionStore.hasPermission(authId)) {
        return <Button {...others} />;
    }
    return null;
}
