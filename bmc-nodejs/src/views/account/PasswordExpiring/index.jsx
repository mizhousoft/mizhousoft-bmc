import { useEffect, useRef, useState } from 'react';
import { Alert, Button, Col, Form, Input, message, Row } from 'antd';

import PageException from '@/components/PageException';
import PageLoading from '@/components/PageLoading';
import { LOGIN_ABSOLUTE_PATH } from '@/config/application';
import { LOADING_FETCH_STATUS } from '@/config/common';
import httpRequest from '@/utils/http-request';

const FormItem = Form.Item;

export default function PasswordExpiring() {
    const [form] = Form.useForm();
    const timeoutRef = useRef();

    const [confirmLoading, setConfirmLoading] = useState(false);
    const [fetchStatus, setFetchStatus] = useState(LOADING_FETCH_STATUS);
    const [expiringDay, setExpiringDay] = useState(undefined);

    const checkNewPassword = (rule, value) => {
        if (value) {
            if (!/[a-z]/.test(value) || !/[A-Z]/.test(value) || !/\d/.test(value) || !/[!#$%&()*+=@^_~-]/.test(value)) {
                return Promise.reject(
                    new Error('密码至少包括一个大写字符(A-Z)，一个小写字母(a-z)，一个数字字符，一个特殊字符~!@#$%^&*()_-+=。')
                );
            }
        }

        return Promise.resolve();
    };

    const checkConfirmPassword = (rule, value) => {
        if (value && value !== form.getFieldValue('newPassword')) {
            return Promise.reject(new Error('新密码和确认新密码不一样。'));
        }
        return Promise.resolve();
    };

    const onFinish = (values) => {
        setConfirmLoading(true);

        const requestBody = {
            url: '/setting/password/modifyExpiringPassword.action',
            data: {
                password: values.password?.trim(),
                newPassword: values.newPassword?.trim(),
                confirmNewPassword: values.confirmPassword?.trim(),
            },
        };

        httpRequest.post(requestBody).then(({ fetchStatus }) => {
            setConfirmLoading(false);

            if (fetchStatus.okey) {
                message.success('修改密码成功，2秒后自动跳转到登录界面重新登录。');

                const timeoutId = setTimeout(() => {
                    window.location.href = LOGIN_ABSOLUTE_PATH;
                }, 2000);
                timeoutRef.current = timeoutId;
            } else {
                message.error(fetchStatus.message);
            }
        });
    };

    const onLogout = () => {
        const requestBody = {
            url: '/logout.action',
            data: {},
        };

        httpRequest.post(requestBody).then(({ fetchStatus }) => {
            if (fetchStatus.okey) {
                window.location.href = LOGIN_ABSOLUTE_PATH;
            } else {
                message.error(fetchStatus.message);
            }
        });
    };

    useEffect(() => {
        const requestBody = {
            url: '/setting/password/fetchPasswordExpiringDays.action',
            data: {},
        };

        httpRequest.get(requestBody).then(({ fetchStatus, expiringDay }) => {
            setExpiringDay(expiringDay);
            setFetchStatus(fetchStatus);
        });

        return () => clearTimeout(timeoutRef.current);
    }, []);

    if (fetchStatus.loading) {
        return <PageLoading />;
    }
    if (!fetchStatus.okey) {
        return <PageException fetchStatus={fetchStatus} />;
    }

    const content = `你的密码还有 ${expiringDay} 天过期，为保证你的帐号安全，请你修改密码。修改密码成功，2秒后自动跳转到登录界面重新登录。`;

    return (
        <Row style={{ marginTop: '120px' }}>
            <Col span={4} />
            <Col span={16}>
                <Form
                    onFinish={onFinish}
                    form={form}
                    labelAlign='left'
                    labelCol={{ flex: '120px' }}
                    style={{ backgroundColor: 'white', padding: '25px' }}
                >
                    <Alert title={content} type='warning' showIcon style={{ marginBottom: '40px', fontSize: '15px' }} />

                    <FormItem
                        name='password'
                        label='老密码'
                        rules={[
                            {
                                required: true,
                                message: '请输入你的老密码。',
                            },
                            {
                                min: 8,
                                message: '密码最小长度是8。',
                            },
                        ]}
                    >
                        <Input type='password' maxLength='32' autoComplete='off' />
                    </FormItem>
                    <FormItem
                        name='newPassword'
                        label='新密码'
                        validateFirst
                        rules={[
                            {
                                required: true,
                                message: '请输入你的新密码。',
                            },
                            {
                                min: 8,
                                message: '密码最小长度是8。',
                            },
                            {
                                validator: checkNewPassword,
                            },
                        ]}
                    >
                        <Input type='password' maxLength='32' autoComplete='off' />
                    </FormItem>
                    <FormItem
                        name='confirmPassword'
                        label='确认新密码'
                        dependencies={['newPassword']}
                        validateFirst
                        rules={[
                            {
                                required: true,
                                message: '请输入你的确认新密码。',
                            },
                            {
                                min: 8,
                                message: '密码最小长度是8。',
                            },
                            {
                                validator: checkConfirmPassword,
                            },
                        ]}
                    >
                        <Input type='password' maxLength='32' autoComplete='off' />
                    </FormItem>
                    <div className='mz-button-group center'>
                        <Button type='primary' htmlType='submit' loading={confirmLoading}>
                            确定
                        </Button>
                        <Button onClick={onLogout}>退出</Button>
                    </div>
                </Form>
            </Col>
            <Col span={4} />
        </Row>
    );
}
