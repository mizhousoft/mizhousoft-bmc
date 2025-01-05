import { Button } from 'antd';

import SessionStore from '@/store/SessionStore';

export default function AuthButton({ authId, ...others }) {
    if (SessionStore.hasPermission(authId)) {
        return <Button {...others} />;
    }
    return null;
}
