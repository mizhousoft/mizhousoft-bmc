import React, { Component } from 'react';
import { Form, InputNumber, Button, Row, Col, message } from 'antd';
import { LOADING_FETCH_STATUS } from '@/constants/common';
import { PageLoading, PageException } from '@/components/UIComponent';
import { fetchPasswordStrategy, modifyPasswordStrategy } from '../redux/securityService';

const FormItem = Form.Item;

class PasswordStrategy extends Component {
    formRef = React.createRef();

    constructor(props) {
        super(props);
        this.state = {
            fetchStatus: LOADING_FETCH_STATUS,
            confirmLoading: false,

            strategy: undefined,
        };
    }

    onFinish = (values) => {
        this.setState({ confirmLoading: true });

        const body = {
            id: this.state.id,
            ...values,
        };

        modifyPasswordStrategy(body).then(({ fetchStatus }) => {
            this.setState({ confirmLoading: false });

            if (fetchStatus.okey) {
                message.success('修改密码策略成功。');
            } else {
                message.error(fetchStatus.message);
            }
        });
    };

    componentDidMount() {
        fetchPasswordStrategy().then(({ fetchStatus, strategy }) => {
            this.setState({
                fetchStatus,
                strategy,
            });
        });
    }

    renderBody = () => {
        const { fetchStatus, strategy, confirmLoading } = this.state;

        if (fetchStatus.loading) {
            return <PageLoading />;
        }
        if (!fetchStatus.okey) {
            return <PageException fetchStatus={fetchStatus} />;
        }

        return (
            <Form
                onFinish={this.onFinish}
                ref={this.formRef}
                initialValues={{
                    historyRepeatSize: strategy.historyRepeatSize,
                    charAppearSize: strategy.charAppearSize,
                    modifyTimeInterval: strategy.modifyTimeInterval,
                    validDay: strategy.validDay,
                    reminderModifyDay: strategy.reminderModifyDay,
                }}
            >
                <FormItem>
                    <Row>
                        <Col xxl={4} xl={6} lg={7}>
                            密码不能与历史密码重复次数
                        </Col>
                        <Col xxl={5} xl={5}>
                            <FormItem
                                name='historyRepeatSize'
                                noStyle
                                rules={[
                                    {
                                        required: true,
                                        message: '请输入密码不能与历史密码重复次数。',
                                    },
                                ]}
                            >
                                <InputNumber min={1} max={10} />
                            </FormItem>
                        </Col>
                    </Row>
                </FormItem>
                <FormItem>
                    <Row>
                        <Col xxl={4} xl={6} lg={7}>
                            密码中允许同一个字符出现的次数
                        </Col>
                        <Col xxl={5} xl={5}>
                            <FormItem
                                noStyle
                                name='charAppearSize'
                                rules={[
                                    {
                                        required: true,
                                        message: '请输入密码中允许同一个字符出现的次数。',
                                    },
                                ]}
                            >
                                <InputNumber min={1} max={4} />
                            </FormItem>
                        </Col>
                    </Row>
                </FormItem>
                <FormItem>
                    <Row>
                        <Col xxl={4} xl={6} lg={7}>
                            密码修改最短时间间隔
                        </Col>
                        <Col span={5}>
                            <FormItem
                                name='modifyTimeInterval'
                                noStyle
                                rules={[
                                    {
                                        required: true,
                                        message: '请输入密码修改最短时间间隔。',
                                    },
                                ]}
                            >
                                <InputNumber min={5} max={60} />
                            </FormItem>
                            &nbsp;分钟
                        </Col>
                    </Row>
                </FormItem>
                <FormItem>密码过期强制修改策略</FormItem>
                <FormItem>
                    <Row>
                        <Col xxl={4} xl={6} lg={7} style={{ textAlign: 'right', paddingRight: '5px' }}>
                            密码有效期
                        </Col>
                        <Col span={5}>
                            <FormItem
                                name='validDay'
                                noStyle
                                rules={[
                                    {
                                        required: true,
                                        message: '请输入密码有效期。',
                                    },
                                ]}
                            >
                                <InputNumber min={60} max={360} />
                            </FormItem>
                            &nbsp;天
                        </Col>
                    </Row>
                </FormItem>
                <FormItem>
                    <Row>
                        <Col xxl={4} xl={6} lg={7} style={{ textAlign: 'right', paddingRight: '5px' }}>
                            距离密码到期，提醒用户修改的天数
                        </Col>
                        <Col span={5}>
                            <FormItem
                                name='reminderModifyDay'
                                rules={[
                                    {
                                        required: true,
                                        message: '请输入提醒用户修改密码的天数。',
                                    },
                                ]}
                            >
                                <InputNumber min={5} max={15} />
                            </FormItem>
                        </Col>
                    </Row>
                </FormItem>
                <FormItem>
                    <Row>
                        <Col xxl={4} xl={6} lg={7} />
                        <Col span={5}>
                            <Button type='primary' htmlType='submit' loading={confirmLoading}>
                                确定
                            </Button>
                        </Col>
                    </Row>
                </FormItem>
            </Form>
        );
    };

    render() {
        return (
            <>
                <div className='mz-page-head'>
                    <div className='title'>密码策略</div>
                </div>

                <div className='mz-page-content'>
                    <div className='mz-page-content-body'>{this.renderBody()}</div>
                </div>
            </>
        );
    }
}

export default PasswordStrategy;
