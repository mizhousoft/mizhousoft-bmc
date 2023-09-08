import React from 'react';
import { Button, Form, Input, Radio } from 'antd';

const FormItem = Form.Item;
const RadioGroup = Radio.Group;

export default function InputAccountInfo({ gotoList, nextStep, formData }) {
    const [form] = Form.useForm();

    const checkNewPassword = (rule, value) => {
        if (value) {
            if (!/[a-z]/.test(value) || !/[A-Z]/.test(value) || !/\d/.test(value) || !/[!#$%&()*+=@^_~-]/.test(value)) {
                return Promise.reject(
                    new Error(
                        '密码至少包括一个大写字符(A-Z)，一个小写字母(a-z)，一个数字字符，一个特殊字符~!@#$%^&*()_-+=。'
                    )
                );
            }
        }

        return Promise.resolve();
    };

    const checkConfirmPassword = (rule, value) => {
        if (value && value !== form.getFieldValue('password')) {
            return Promise.reject(new Error('密码和确认密码不一样。'));
        }
        return Promise.resolve();
    };

    const onFinish = (values) => {
        const newFormData = { ...formData, ...values };
        nextStep(newFormData);
    };

    return (
        <Form
            onFinish={onFinish}
            form={form}
            labelAlign='left'
            labelCol={{ flex: '120px' }}
            initialValues={{
                name: formData.name,
                password: formData.password,
                confirmPassword: formData.confirmPassword,
                phoneNumber: formData.phoneNumber,
                status: formData.status,
            }}
        >
            <FormItem
                name='name'
                label='帐号'
                rules={[
                    {
                        required: true,
                        message: '请输入帐号。',
                    },
                    {
                        min: 5,
                        message: '帐号最小长度是5。',
                    },
                    {
                        pattern: /^[\dA-Za-z]{1,20}$/,
                        message: '帐号只能包含a-zA-Z0-9。',
                    },
                ]}
            >
                <Input autoComplete='off' maxLength='20' style={{ width: '50%' }} />
            </FormItem>
            <FormItem
                name='password'
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
                <Input type='password' maxLength='32' autoComplete='off' style={{ width: '50%' }} />
            </FormItem>
            <FormItem
                name='confirmPassword'
                label='确认密码'
                dependencies={['password']}
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
                <Input type='password' maxLength='32' autoComplete='off' style={{ width: '50%' }} />
            </FormItem>
            <FormItem
                name='phoneNumber'
                label='手机号'
                labelCol={{ flex: '120px', style: { paddingLeft: '11px' } }}
                rules={[
                    {
                        min: 11,
                        message: '手机号最小长度是11。',
                    },
                ]}
            >
                <Input autoComplete='off' maxLength='11' style={{ width: '50%' }} />
            </FormItem>
            <FormItem
                name='status'
                label='帐号使用状态'
                rules={[
                    {
                        required: true,
                        message: '请选择帐号使用状态。',
                    },
                ]}
            >
                <RadioGroup>
                    <Radio value={2}>正常</Radio>
                    <Radio value={3}>停用</Radio>
                </RadioGroup>
            </FormItem>
            <FormItem className='mz-button-group'>
                <Button type='primary' htmlType='submit'>
                    下一步
                </Button>
                <Button onClick={gotoList}>取消</Button>
            </FormItem>
        </Form>
    );
}
