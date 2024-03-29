import { redirect } from 'react-router-dom';

import { LOGIN_PATH } from '@/config/application';
import SessionStore from '@/store/SessionStore';

export default function authcLoader() {
    if (!SessionStore.isAuthenticated()) {
        throw redirect(LOGIN_PATH);
    }

    return null;
}
