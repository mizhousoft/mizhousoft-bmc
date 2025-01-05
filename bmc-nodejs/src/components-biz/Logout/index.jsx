import { Modal } from 'antd';

import LogoutImg from '@/assets/imgs/icon-logout.png';
import { LOGIN_ABSOLUTE_PATH } from '@/config/application';
import SessionStore from '@/store/SessionStore';
import httpRequest from '@/utils/http-request';

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
                const requestBody = {
                    url: '/logout.action',
                    data: {},
                };

                httpRequest.post(requestBody).then(({ fetchStatus }) => {
                    SessionStore.logout();

                    if (fetchStatus.okey) {
                        window.location.href = LOGIN_ABSOLUTE_PATH;
                    }
                });
            },
        });
    };

    return (
        <a onClick={onLogout} style={{ marginRight: '15px' }}>
            <img width='22px' height='22px' alt='退出' src={LogoutImg} style={{ verticalAlign: 'middle' }} />
        </a>
    );
}
