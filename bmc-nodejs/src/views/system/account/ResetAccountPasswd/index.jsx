import React, { useState } from 'react';
import { Button, Form, Input, message, Modal } from 'antd';

import AuthA from '@/biz-components/AuthA';
import httpRequest from '@/utils/http-request';

const FormItem = Form.Item;
const formItemLayout = {
    labelCol: {
        xs: { span: 5 },
        sm: { span: 5 },
    },
    wrapperCol: {
        xs: { span: 19 },
        sm: { span: 19 },
    },
};

export default function ResetAccountPasswd({ accountId }) {
    const [form] = Form.useForm();

    const [confirmLoading, setConfirmLoading] = useState(false);
    const [visible, setVisible] = useState(false);

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
            return Promise.reject(new Error('密码和确认密码不一样。'));
        }
        return Promise.resolve();
    };

    const onFinish = (values) => {
        setConfirmLoading(true);

        const requestBody = {
            url: '/account/resetPassword.action',
            data: {
                id: accountId,
                newPassword: values.newPassword?.trim(),
                confirmNewPassword: values.confirmNewPassword?.trim(),
            },
        };

        httpRequest.post(requestBody).then(({ fetchStatus }) => {
            setConfirmLoading(false);

            if (fetchStatus.okey) {
                message.success('重置帐号密码成功。');
                setVisible(false);
            } else {
                message.error(fetchStatus.message);
            }
        });
    };

    return (
        <>
            <AuthA authId='bmc.account.password.reset' onClick={() => setVisible(true)}>
                重置密码
            </AuthA>

            {visible && (
                <Modal
                    title='重置帐号密码'
                    open
                    centered
                    closable={false}
                    maskClosable={false}
                    footer={null}
                    onCancel={() => setVisible(false)}
                    className='mz-modal'
                >
                    <Form preserve={false} onFinish={onFinish} form={form} labelAlign='left'>
                        <FormItem
                            name='newPassword'
                            {...formItemLayout}
                            label='密码'
                            validateFirst
                            rules={[
                                {
                                    required: true,
                                    message: '请输入密码。',
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
                            name='confirmNewPassword'
                            {...formItemLayout}
                            label='确认密码'
                            dependencies={['newPassword']}
                            validateFirst
                            rules={[
                                {
                                    required: true,
                                    message: '请输入确认密码。',
                                },
                                {
                                    min: 8,
                                    message: '确认密码最小长度是8。',
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
                                确认
                            </Button>
                            <Button onClick={() => setVisible(false)}>取消</Button>
                        </div>
                    </Form>
                </Modal>
            )}
        </>
    );
}
