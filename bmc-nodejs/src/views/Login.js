import React, { Component } from 'react';
import { withRouter, Redirect } from 'react-router-dom';
import { Form, Input, Button, Alert, Card } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import BMC from '@/utils/BMC';
import { COMPANY, LOGIN_TITLE, TEST_ADMIN, TEST_PASSWORD } from '@/config/application';
import { userLogin } from '@/session/sessionService';
import SessionStore from '@/session/SessionStore';

const FormItem = Form.Item;

class Login extends Component {
    constructor(props) {
        super(props);

        this.state = {
            redirectToReferrer: false,
            confirmLoading: false,
            error: '',
            from: '',
            account: BMC.isDev() ? TEST_ADMIN : '',
            password: BMC.isDev() ? TEST_PASSWORD : '',
        };
    }

    onFinish = (values) => {
        this.setState({ confirmLoading: true, error: '' });

        userLogin(values).then(({ fetchStatus, firstLogin, credentialsExpired, remindModifyPasswd }) => {
            if (fetchStatus.okey) {
                if (firstLogin) {
                    this.setState({ from: '/login/first', redirectToReferrer: true, error: '', confirmLoading: false });
                } else if (credentialsExpired) {
                    this.setState({
                        from: '/password/expired',
                        redirectToReferrer: true,
                        error: '',
                        confirmLoading: false,
                    });
                } else if (remindModifyPasswd) {
                    this.setState({
                        from: '/password/expiring',
                        redirectToReferrer: true,
                        error: '',
                        confirmLoading: false,
                    });
                } else {
                    const $this = this;
                    SessionStore.initAccountInfo(() => {
                        $this.setState({ redirectToReferrer: true, error: '', confirmLoading: false });
                    });
                }
            } else {
                const error = fetchStatus.message;
                this.setState({ redirectToReferrer: false, error, confirmLoading: false });
            }
        });
    };

    render() {
        const { redirectToReferrer } = this.state;

        let from = this.props.location.state;
        if (!from) {
            from = this.state.from || SessionStore.getHomePath();
        }

        if (redirectToReferrer) {
            return <Redirect to={from} />;
        }

        return (
            <div className='mz-login-container'>
                <Card className='mz-login-card' bodyStyle={{ paddingBottom: '0px' }}>
                    <Form
                        onFinish={this.onFinish}
                        initialValues={{
                            account: this.state.account,
                            password: this.state.password,
                        }}
                    >
                        <h2 style={{ textAlign: 'center', paddingBottom: '30px' }}>{LOGIN_TITLE}</h2>
                        <FormItem name='account' rules={[{ required: true, message: '请输入帐号！' }]}>
                            <Input
                                prefix={<UserOutlined />}
                                size='large'
                                placeholder='请输入帐号'
                                autoComplete='off'
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
                                loading={this.state.confirmLoading}
                            >
                                登录
                            </Button>
                        </FormItem>
                        {this.state.error !== '' && (
                            <Alert
                                type='error'
                                message={this.state.error}
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
}

export default withRouter(Login);
