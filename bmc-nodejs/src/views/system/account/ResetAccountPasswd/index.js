import React, { Component } from 'react';
import { Form, Input, Button, Modal, message } from 'antd';
import AuthA from '@/views/components/AuthA';
import { resetPassword } from '../redux/accountService';

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

class ResetAccountPasswd extends Component {
    formRef = React.createRef();

    constructor(props) {
        super(props);

        this.state = {
            confirmLoading: false,

            visible: false,
        };
    }

    showModal = () => {
        this.setState({ visible: true });
    };

    hideModal = () => {
        this.setState({ visible: false });
    };

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
            return Promise.reject(new Error('密码和确认密码不一样。'));
        }
        return Promise.resolve();
    };

    onFinish = (values) => {
        const { accountId } = this.props;

        this.setState({ confirmLoading: true });

        const body = {
            id: accountId,
            newPassword: values.newPassword,
            confirmNewPassword: values.confirmNewPassword,
        };

        resetPassword(body).then(({ fetchStatus }) => {
            this.setState({ confirmLoading: false });

            if (fetchStatus.okey) {
                message.success('重置帐号密码成功。');
                this.hideModal();
            } else {
                message.error(fetchStatus.message);
            }
        });
    };

    renderBody = () => {
        const { visible, confirmLoading } = this.state;

        if (!visible) {
            return null;
        }

        return (
            <Modal
                title='重置帐号密码'
                visible
                centered
                closable={false}
                maskClosable={false}
                footer={null}
                onCancel={this.hideModal}
                className='mz-modal'
            >
                <Form onFinish={this.onFinish} ref={this.formRef} labelAlign='left'>
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
                                validator: this.checkNewPassword,
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
                                validator: this.checkConfirmPassword,
                            },
                        ]}
                    >
                        <Input type='password' maxLength='32' autoComplete='off' />
                    </FormItem>
                    <div className='mz-button-group center'>
                        <Button type='primary' htmlType='submit' loading={confirmLoading}>
                            确认
                        </Button>
                        <Button onClick={this.hideModal}>取消</Button>
                    </div>
                </Form>
            </Modal>
        );
    };

    render() {
        return (
            <>
                <AuthA authId='bmc.account.password.reset' onClick={this.showModal}>
                    重置密码
                </AuthA>

                {this.renderBody()}
            </>
        );
    }
}

export default ResetAccountPasswd;
