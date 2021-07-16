import React, { Component } from 'react';
import { Form, InputNumber, Button, message, Alert } from 'antd';
import FormFlex from '@/constants/flex';
import { LOADING_FETCH_STATUS } from '@/constants/common';
import { PageLoading, PageException } from '@/components/UIComponent';
import { fetchIdletimeout, modifyIdletimeout } from '../profileService';

const FormItem = Form.Item;

class Idletimeout extends Component {
    formRef = React.createRef();

    constructor(props) {
        super(props);
        this.state = {
            fetchStatus: LOADING_FETCH_STATUS,

            idleTimeout: undefined,
            confirmLoading: false,
        };
    }

    onFinish = (values) => {
        this.setState({ confirmLoading: true });

        modifyIdletimeout(values).then(({ fetchStatus }) => {
            this.setState({ confirmLoading: false });

            if (fetchStatus.okey) {
                message.success('修改闲置超时时间成功。');
            } else {
                message.error(fetchStatus.message);
            }
        });
    };

    componentDidMount() {
        fetchIdletimeout().then(({ fetchStatus, idleTimeout }) => {
            this.setState({
                fetchStatus,
                idleTimeout,
            });
        });
    }

    renderBody = () => {
        const { fetchStatus, idleTimeout, confirmLoading } = this.state;

        if (fetchStatus.loading) {
            return <PageLoading />;
        }
        if (!fetchStatus.okey) {
            return <PageException fetchStatus={fetchStatus} />;
        }

        return (
            <Form onFinish={this.onFinish} ref={this.formRef} initialValues={{ timeout: idleTimeout.timeout }}>
                <Alert
                    message='当你长时间不使用系统，系统为保证你的帐号安全，将退出你的登录。'
                    type='info'
                    showIcon
                    style={{ marginBottom: '18px' }}
                />

                <Form.Item {...FormFlex.w50_lg4} label='闲置超时时间' labelAlign='left'>
                    <FormItem
                        name='timeout'
                        rules={[
                            {
                                required: true,
                                message: '请输入闲置超时时间。',
                            },
                        ]}
                        noStyle
                    >
                        <InputNumber min={1} max={1440} />
                    </FormItem>
                    <span>&nbsp; 分钟</span>
                </Form.Item>

                <FormItem {...FormFlex.w50_lg4} colon={false} label=' '>
                    <Button type='primary' htmlType='submit' loading={confirmLoading}>
                        确定
                    </Button>
                </FormItem>
            </Form>
        );
    };

    render() {
        return (
            <>
                <div className='mz-page-head'>
                    <div className='title'>闲时时间设置</div>
                </div>

                <div className='mz-page-content'>
                    <div className='mz-page-content-body'>{this.renderBody()}</div>
                </div>
            </>
        );
    }
}

export default Idletimeout;
