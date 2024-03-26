import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';

import { LOGIN_PATH } from '@/config/application';
import SessionStore from '@/store/SessionStore';

export default function RequireAuth({ children }) {
    const location = useLocation();

    if (!SessionStore.isAuthenticated()) {
        return <Navigate to={LOGIN_PATH} state={{ from: location }} replace />;
    }

    return children;
}
