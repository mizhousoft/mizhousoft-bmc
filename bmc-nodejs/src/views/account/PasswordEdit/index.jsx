import React, { useEffect, useState } from 'react';
import { Alert, Button, Form, Input, message } from 'antd';

import PasswordNote from '@/components-biz/PasswordNote';
import PageComponent from '@/components/PageComponent';
import PageException from '@/components/PageException';
import PageLoading from '@/components/PageLoading';
import { LOADING_FETCH_STATUS } from '@/config/common';
import SessionStore from '@/store/SessionStore';
import httpRequest from '@/utils/http-request';

const FormItem = Form.Item;

export default function PasswordEdit() {
    const [form] = Form.useForm();

    const [confirmLoading, setConfirmLoading] = useState(false);
    const [uFetchStatus, setFetchStatus] = useState(LOADING_FETCH_STATUS);
    const [uPasswordStrategy, setPasswordStrategy] = useState(undefined);

    const countCharNumber = (value, c) => {
        let count = 0;
        for (let j = 0; j < value.length; ++j) {
            const cc = value.charAt(j);
            if (c === cc) {
                count += 1;
            }
        }

        return count;
    };

    const hasCharExceedNumber = (value, charAppearSize) => {
        for (let i = 0; i < value.length; ++i) {
            const c = value.charAt(i);
            const count = countCharNumber(value, c);
            if (count > charAppearSize) {
                return true;
            }
        }

        return false;
    };

    const checkNewPassword = (rule, value) => {
        if (value) {
            const { name } = SessionStore.getAccount();
            const { charAppearSize } = uPasswordStrategy;

            if (value.includes(name)) {
                return Promise.reject(new Error('密码不能包含帐号名。'));
            }
            if (value.includes(name.split('').reverse().join(''))) {
                return Promise.reject(new Error('密码不能包含倒序的帐号名。'));
            }
            if (!/[a-z]/.test(value) || !/[A-Z]/.test(value) || !/\d/.test(value) || !/[!#$%&()*+=@^_~-]/.test(value)) {
                return Promise.reject(
                    new Error('密码至少包括一个大写字符(A-Z)，一个小写字母(a-z)，一个数字字符，一个特殊字符~!@#$%^&*()_-+=。')
                );
            }
            if (hasCharExceedNumber(value, charAppearSize)) {
                return Promise.reject(new Error(`同一字符不能出现超过${charAppearSize}次。`));
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
            url: '/setting/password/modifyPassword.action',
            data: {
                password: values.password?.trim(),
                newPassword: values.newPassword?.trim(),
                confirmNewPassword: values.confirmPassword?.trim(),
            },
        };

        httpRequest.post(requestBody).then(({ fetchStatus }) => {
            setConfirmLoading(false);

            if (fetchStatus.okey) {
                form.resetFields();
                message.success('修改密码成功。');
            } else {
                message.error(fetchStatus.message);
            }
        });
    };

    useEffect(() => {
        const requestBody = {
            url: '/setting/password/fetchPasswordStrategy.action',
            data: {},
        };

        httpRequest.get(requestBody).then(({ fetchStatus, passwordStrategy }) => {
            setPasswordStrategy(passwordStrategy);
            setFetchStatus(fetchStatus);
        });
    }, []);

    const breadcrumbs = ['密码修改'];

    if (uFetchStatus.loading) {
        return <PageLoading breadcrumbs={breadcrumbs} />;
    }
    if (!uFetchStatus.okey) {
        return <PageException breadcrumbs={breadcrumbs} fetchStatus={uFetchStatus} />;
    }

    const content = `在${uPasswordStrategy.modifyTimeInterval}分钟内，只能修改一次密码，不能连续修改密码。`;

    return (
        <PageComponent breadcrumbs={breadcrumbs}>
            <Form onFinish={onFinish} form={form} labelAlign='left' labelCol={{ flex: '120px' }}>
                <Alert message={content} type='info' showIcon style={{ marginBottom: '18px' }} />

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
                <FormItem label=' ' colon={false}>
                    <Button type='primary' htmlType='submit' loading={confirmLoading}>
                        确定
                    </Button>
                </FormItem>
                <FormItem>
                    <PasswordNote
                        charAppearSize={uPasswordStrategy.charAppearSize}
                        historyRepeatSize={uPasswordStrategy.historyRepeatSize}
                    />
                </FormItem>
            </Form>
        </PageComponent>
    );
}
