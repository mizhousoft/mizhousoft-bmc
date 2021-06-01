import React, { Component } from 'react';
import { Row, Col, Form, Input, Button, Alert, message } from 'antd';
import FormFlex from '@/constants/flex';
import { logout } from '@/session/sessionService';
import { CONTEXT_LOGIN_PATH } from '@/config/application';
import { modifyExpiredPassword } from '../profileService';

const FormItem = Form.Item;

class PasswordExpired extends Component {
    formRef = React.createRef();

    constructor(props) {
        super(props);
        this.state = {
            confirmLoading: false,
        };
    }

    checkNewPassword = (rule, value) => {
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

    checkConfirmPassword = (rule, value) => {
        if (value && value !== this.formRef.current.getFieldValue('newPassword')) {
            return Promise.reject(new Error('新密码和确认新密码不一样。'));
        }
        return Promise.resolve();
    };

    onFinish = (values) => {
        this.setState({ confirmLoading: true });

        const body = {
            password: values.password,
            newPassword: values.newPassword,
            confirmNewPassword: values.confirmPassword,
        };

        modifyExpiredPassword(body).then(({ fetchStatus }) => {
            this.setState({ confirmLoading: false });

            if (fetchStatus.okey) {
                message.success('修改密码成功，2秒后自动跳转到登录界面重新登录。');

                this.timeoutId = setTimeout(() => {
                    window.location.href = CONTEXT_LOGIN_PATH;
                }, 2000);
            } else {
                message.error(fetchStatus.message);
            }
        });
    };

    logout = () => {
        logout().then(({ fetchStatus }) => {
            if (fetchStatus.okey) {
                window.location.href = CONTEXT_LOGIN_PATH;
            } else {
                message.error(fetchStatus.message);
            }
        });
    };

    componentWillUnmount() {
        clearTimeout(this.timeoutId);
    }

    render() {
        const content =
            '你的密码已经过期，为保证你的帐号安全，请你修改密码。修改密码成功，2秒后自动跳转到登录界面重新登录。';

        return (
            <Row style={{ marginTop: '120px' }}>
                <Col span={4} />
                <Col span={16}>
                    <Form
                        onFinish={this.onFinish}
                        ref={this.formRef}
                        labelAlign='left'
                        style={{ backgroundColor: 'white', padding: '25px' }}
                    >
                        <Alert
                            message={content}
                            type='error'
                            showIcon
                            style={{ marginBottom: '40px', color: 'red', fontSize: '15px' }}
                        />

                        <FormItem
                            name='password'
                            {...FormFlex.w100_lg5_required}
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
                            {...FormFlex.w100_lg5_required}
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
                                    validator: this.checkNewPassword,
                                },
                            ]}
                        >
                            <Input type='password' maxLength='32' autoComplete='off' />
                        </FormItem>
                        <FormItem
                            name='confirmPassword'
                            {...FormFlex.w100_lg5_required}
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
                                    validator: this.checkConfirmPassword,
                                },
                            ]}
                        >
                            <Input type='password' maxLength='32' autoComplete='off' />
                        </FormItem>
                        <div className='mz-button-group center'>
                            <Button type='primary' htmlType='submit' loading={this.state.confirmLoading}>
                                确定
                            </Button>
                            <Button onClick={this.logout}>退出</Button>
                        </div>
                    </Form>
                </Col>
                <Col span={4} />
            </Row>
        );
    }
}

export default PasswordExpired;
