import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import SessionStore from '@/session/SessionStore';
import { LOGIN_PATH } from '@/config/application';

export default function RequireAuth({ children }) {
    const location = useLocation();

    if (!SessionStore.isAuthenticated()) {
        return <Navigate to={LOGIN_PATH} state={{ from: location }} replace />;
    }

    return children;
}
