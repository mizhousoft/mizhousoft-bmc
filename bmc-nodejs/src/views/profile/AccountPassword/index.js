import React, { Component } from 'react';
import { Form, Input, Button, Alert, message } from 'antd';
import FormFlex from '@/constants/flex';
import { LOADING_FETCH_STATUS } from '@/constants/common';
import { PageLoading, PageException } from '@/components/UIComponent';
import PasswordNote from '@/views/components/PasswordNote';
import SessionStore from '@/session/SessionStore';
import { fetchPasswordStrategy, modifyAccountPassword } from '../profileService';

const FormItem = Form.Item;

class AccountPassword extends Component {
    formRef = React.createRef();

    constructor(props) {
        super(props);
        this.state = {
            fetchStatus: LOADING_FETCH_STATUS,

            passwordStrategy: undefined,
            confirmLoading: false,
        };
    }

    countCharNumber = (value, c) => {
        let count = 0;
        for (let j = 0; j < value.length; ++j) {
            const cc = value.charAt(j);
            if (c === cc) {
                count += 1;
            }
        }

        return count;
    };

    hasCharExceedNumber = (value, charAppearSize) => {
        for (let i = 0; i < value.length; ++i) {
            const c = value.charAt(i);
            const count = this.countCharNumber(value, c);
            if (count > charAppearSize) {
                return true;
            }
        }

        return false;
    };

    checkNewPassword = (rule, value) => {
        if (value) {
            const { name } = SessionStore.getAccount();
            const { charAppearSize } = this.state;

            if (value.includes(name)) {
                return Promise.reject(new Error('密码不能包含帐号名。'));
            }
            if (value.includes(name.split('').reverse().join(''))) {
                return Promise.reject(new Error('密码不能包含倒序的帐号名。'));
            }
            if (!/[a-z]/.test(value) || !/[A-Z]/.test(value) || !/\d/.test(value) || !/[!#$%&()*+=@^_~-]/.test(value)) {
                return Promise.reject(
                    new Error(
                        '密码至少包括一个大写字符(A-Z)，一个小写字母(a-z)，一个数字字符，一个特殊字符~!@#$%^&*()_-+=。'
                    )
                );
            }
            if (this.hasCharExceedNumber(value, charAppearSize)) {
                return Promise.reject(new Error(`同一字符不能出现超过${charAppearSize}次。`));
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

        modifyAccountPassword(body).then(({ fetchStatus }) => {
            this.setState({ confirmLoading: false });

            if (fetchStatus.okey) {
                this.formRef.current.resetFields();
                message.success('修改密码成功。');
            } else {
                message.error(fetchStatus.message);
            }
        });
    };

    componentDidMount() {
        fetchPasswordStrategy().then(({ fetchStatus, passwordStrategy }) => {
            this.setState({
                fetchStatus,
                passwordStrategy,
            });
        });
    }

    renderBody() {
        const { fetchStatus, passwordStrategy } = this.state;

        if (fetchStatus.loading) {
            return <PageLoading />;
        }
        if (!fetchStatus.okey) {
            return <PageException fetchStatus={fetchStatus} />;
        }

        const content = `在${passwordStrategy.modifyTimeInterval}分钟内，只能修改一次密码，不能连续修改密码。`;

        return (
            <Form onFinish={this.onFinish} ref={this.formRef} labelAlign='left'>
                <Alert message={content} type='info' showIcon style={{ marginBottom: '18px' }} />

                <FormItem
                    name='password'
                    {...FormFlex.w100_lg4}
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
                    {...FormFlex.w100_lg4}
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
                    {...FormFlex.w100_lg4}
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
                <FormItem {...FormFlex.w50_lg4} label=' ' colon={false}>
                    <Button type='primary' htmlType='submit' loading={this.state.confirmLoading}>
                        确定
                    </Button>
                </FormItem>
                <FormItem {...FormFlex.w100_lg4}>
                    <PasswordNote
                        charAppearSize={this.state.charAppearSize}
                        historyRepeatSize={this.state.historyRepeatSize}
                    />
                </FormItem>
            </Form>
        );
    }

    render() {
        return (
            <>
                <div className='mz-page-head'>
                    <div className='title'>密码修改</div>
                </div>

                <div className='mz-page-content'>
                    <div className='mz-page-content-body'>{this.renderBody()}</div>
                </div>
            </>
        );
    }
}

export default AccountPassword;
