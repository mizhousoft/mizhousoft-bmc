import React, { Component } from 'react';
import { Form, Button, message, Input, Modal } from 'antd';
import FormFlex from '@/constants/flex';
import { modifyPhoneNumber } from '../profileService';

const FormItem = Form.Item;

class PhoneNumberEdit extends Component {
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

    onFinish = (values) => {
        const { fetchPageData } = this.props;

        this.setState({ confirmLoading: true });

        const body = {};
        if (values.phoneNumber && values.phoneNumber.length >= 1) {
            body.phoneNumber = values.phoneNumber;
        }

        modifyPhoneNumber(body).then(({ fetchStatus }) => {
            if (fetchStatus.okey) {
                this.setState({ confirmLoading: false, visible: false });
                message.success('修改手机号成功。');
                fetchPageData();
            } else {
                this.setState({ confirmLoading: false });
                message.error(fetchStatus.message);
            }
        });
    };

    render() {
        const { account } = this.props;

        return (
            <>
                <Button onClick={this.showModal}>修改</Button>

                {this.state.visible && (
                    <Modal
                        title='修改手机号'
                        maskClosable={false}
                        footer={null}
                        visible
                        destroyOnClose
                        onCancel={this.hideModal}
                        closable={false}
                        centered
                        className='mz-modal'
                    >
                        <Form
                            onFinish={this.onFinish}
                            labelAlign='left'
                            initialValues={{
                                phoneNumber: account.phoneNumber,
                            }}
                        >
                            <FormItem
                                {...FormFlex.w100_lg5_required}
                                label='手机号'
                                name='phoneNumber'
                                rules={[
                                    {
                                        min: 11,
                                        message: '手机号最小长度是11。',
                                    },
                                ]}
                            >
                                <Input autoComplete='off' maxLength='11' />
                            </FormItem>
                            <div className='mz-button-group center'>
                                <Button type='primary' htmlType='submit' loading={this.state.confirmLoading}>
                                    确认
                                </Button>
                                <Button onClick={this.hideModal}>取消</Button>
                            </div>
                        </Form>
                    </Modal>
                )}
            </>
        );
    }
}

export default PhoneNumberEdit;
