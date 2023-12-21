import React from 'react';
import { Modal } from 'antd';

import LogoutImg from '@/assets/imgs/icon-logout.png';
import { AButton } from '@/components/UIComponent';
import { CONTEXT_LOGIN_PATH } from '@/config/application';
import { logout } from '@/session/sessionService';
import SessionStore from '@/session/SessionStore';

export default function Logout() {
    const onLogout = () => {
        Modal.confirm({
            autoFocusButton: null,
            title: '退出帐号',
            content: <div>你确定要退出帐号吗？</div>,
            okText: '确认',
            cancelText: '取消',
            style: {
                top: (window.innerHeight - 250) / 2,
            },
            onOk() {
                logout().then(({ fetchStatus }) => {
                    SessionStore.logout();

                    if (fetchStatus.okey) {
                        window.location.href = CONTEXT_LOGIN_PATH;
                    }
                });
            },
        });
    };

    return (
        <AButton onClick={onLogout} style={{ marginRight: '15px' }}>
            <img width='22px' height='22px' alt='退出' src={LogoutImg} style={{ verticalAlign: 'middle' }} />
        </AButton>
    );
}
