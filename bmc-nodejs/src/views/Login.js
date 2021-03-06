import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Form, Input, Button, Alert, Card } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import BMC from '@/utils/BMC';
import { COMPANY, LOGIN_TITLE, TEST_ADMIN, TEST_PASSWORD } from '@/config/application';
import { userLogin } from '@/session/sessionService';
import SessionStore from '@/session/SessionStore';

const FormItem = Form.Item;

export default function Login() {
    const navigate = useNavigate();
    const [confirmLoading, setConfirmLoading] = useState(false);
    const [uError, setError] = useState('');

    const onFinish = (values) => {
        setError('');
        setConfirmLoading(true);

        userLogin(values).then(({ fetchStatus, firstLogin, credentialsExpired, remindModifyPasswd }) => {
            if (fetchStatus.okey) {
                if (firstLogin) {
                    navigate('/login/first');
                } else if (credentialsExpired) {
                    navigate('/password/expired');
                } else if (remindModifyPasswd) {
                    navigate('/password/expiring');
                } else {
                    SessionStore.initAccountInfo(() => {
                        const homePath = SessionStore.getHomePath();
                        navigate(homePath);
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
                        account: BMC.isDev() ? TEST_ADMIN : '',
                        password: BMC.isDev() ? TEST_PASSWORD : '',
                    }}
                >
                    <h2 style={{ textAlign: 'center', paddingBottom: '30px' }}>{LOGIN_TITLE}</h2>
                    <FormItem name='account' rules={[{ required: true, message: '??????????????????' }]}>
                        <Input
                            prefix={<UserOutlined />}
                            size='large'
                            placeholder='???????????????'
                            autoComplete='off'
                            maxLength={32}
                        />
                    </FormItem>
                    <FormItem
                        name='password'
                        rules={[
                            {
                                required: true,
                                message: '??????????????????',
                            },
                            {
                                min: 8,
                                message: '?????????????????????8???',
                            },
                        ]}
                    >
                        <Input
                            prefix={<LockOutlined />}
                            size='large'
                            type='password'
                            placeholder='???????????????'
                            autoComplete='off'
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
                            ??????
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
                    {COMPANY}??????????????????
                </div>
            </Card>
        </div>
    );
}
