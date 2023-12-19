import React, { useState } from 'react';
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { Alert, Button, Card, Form, Input } from 'antd';
import Cookies from 'js-cookie';

import { BASENAME, COMPANY, LOGIN_TITLE } from '@/config/application';
import { userLogin } from '@/session/sessionService';
import SessionStore from '@/session/SessionStore';

const FormItem = Form.Item;

export default function Login() {
    const [confirmLoading, setConfirmLoading] = useState(false);
    const [uError, setError] = useState('');

    const onFinish = (values) => {
        setError('');
        setConfirmLoading(true);

        // 清除浏览器自带的无效cookie
        Object.keys(Cookies.get()).forEach((cookieName) => {
            const neededAttributes = {};
            Cookies.remove(cookieName, neededAttributes);
        });
        // 清除本地缓存
        SessionStore.logout();

        userLogin(values).then(({ fetchStatus, firstLogin, credentialsExpired, remindModifyPasswd }) => {
            if (fetchStatus.okey) {
                if (firstLogin) {
                    window.location.href = `${BASENAME}/login/first`;
                } else if (credentialsExpired) {
                    window.location.href = `${BASENAME}/password/expired`;
                } else if (remindModifyPasswd) {
                    window.location.href = `${BASENAME}/password/expiring`;
                } else {
                    SessionStore.initAccountInfo(() => {
                        const homePath = SessionStore.getHomePath();
                        window.location.href = BASENAME + homePath;
                    });
                }
            } else {
                setError(fetchStatus.message);
                setConfirmLoading(false);
            }
        });
    };

    return (
        <div className='mz-login-container'>
            <Card className='mz-login-card' bodyStyle={{ paddingBottom: '0px' }}>
                <Form
                    onFinish={onFinish}
                    initialValues={{
                        account: ENV_TEST_ADMIN,
                        password: ENV_TEST_PASSWORD,
                    }}
                >
                    <h2 style={{ textAlign: 'center', paddingBottom: '30px' }}>{LOGIN_TITLE}</h2>
                    <FormItem name='account' rules={[{ required: true, message: '请输入帐号！' }]}>
                        <Input
                            prefix={<UserOutlined />}
                            size='large'
                            placeholder='请输入帐号'
                            autoComplete='new-password'
                            maxLength={32}
                        />
                    </FormItem>
                    <FormItem
                        name='password'
                        rules={[
                            {
                                required: true,
                                message: '请输入密码！',
                            },
                            {
                                min: 8,
                                message: '密码最小长度是8。',
                            },
                        ]}
                    >
                        <Input
                            prefix={<LockOutlined />}
                            size='large'
                            type='password'
                            placeholder='请输入密码'
                            autoComplete='new-password'
                            maxLength={32}
                        />
                    </FormItem>
                    <FormItem colon={false}>
                        <Button
                            type='primary'
                            block
                            htmlType='submit'
                            size='large'
                            style={{ marginTop: '10px' }}
                            loading={confirmLoading}
                        >
                            登录
                        </Button>
                    </FormItem>
                    {uError !== '' && (
                        <Alert
                            type='error'
                            message={uError}
                            showIcon
                            style={{ textAlign: 'left', marginBottom: '20px' }}
                        />
                    )}
                </Form>
                <div
                    style={{
                        marginTop: '40px',
                        color: 'rgba(0, 0, 0, .45)',
                        textAlign: 'center',
                        fontSize: '15px',
                    }}
                >
                    {COMPANY}提供技术支持
                </div>
            </Card>
        </div>
    );
}
