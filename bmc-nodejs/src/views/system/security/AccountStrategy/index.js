import React, { Component } from 'react';
import { Form, InputNumber, Button, Radio, Row, Col, message } from 'antd';
import { LOADING_FETCH_STATUS } from '@/constants/common';
import { PageLoading, PageException } from '@/components/UIComponent';
import { fetchAccountStrategy, modifyAccountStrategy } from '../redux/securityService';

const FormItem = Form.Item;

class AccountStrategy extends Component {
    formRef = React.createRef();

    constructor(props) {
        super(props);
        this.state = {
            fetchStatus: LOADING_FETCH_STATUS,
            confirmLoading: false,

            strategy: undefined,
        };
    }

    changeLockTimeStrategy = (e) => {
        const { strategy } = this.state;

        strategy.lockTimeStrategy = e.target.value;

        this.setState({ strategy });
    };

    onFinish = (values) => {
        const { strategy } = this.state;

        this.setState({ confirmLoading: true });

        const body = { id: strategy.id, lockTimeStrategy: strategy.lockTimeStrategy, ...values };

        modifyAccountStrategy(body).then(({ fetchStatus }) => {
            this.setState({ confirmLoading: false });

            if (fetchStatus.okey) {
                message.success('修改帐号策略成功。');
            } else {
                message.error(fetchStatus.message);
            }
        });
    };

    componentDidMount() {
        fetchAccountStrategy().then(({ fetchStatus, strategy }) => {
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

        const PermanentLockChecked = strategy.lockTimeStrategy === 2;
        const lockTimeChecked = !PermanentLockChecked;

        return (
            <Form
                onFinish={this.onFinish}
                ref={this.formRef}
                initialValues={{
                    accountUnusedDay: strategy.accountUnusedDay,
                    timeLimitPeriod: strategy.timeLimitPeriod,
                    loginLimitNumber: strategy.loginLimitNumber,
                    lockTime: 1,
                    accountLockTime: strategy.accountLockTime,
                    permanentLock: 2,
                }}
            >
                <FormItem>
                    <Row>
                        <Col xxl={3} xl={4} lg={5}>
                            帐号连续
                        </Col>
                        <Col span={12}>
                            <FormItem
                                name='accountUnusedDay'
                                noStyle
                                rules={[
                                    {
                                        required: true,
                                        message: '请输入帐号连续未使用天数。',
                                    },
                                ]}
                            >
                                <InputNumber min={60} max={180} />
                            </FormItem>
                            &nbsp;天未使用，停用帐号。
                        </Col>
                    </Row>
                </FormItem>
                <FormItem>帐号锁定条件设置</FormItem>
                <FormItem>
                    <Row>
                        <Col xxl={3} xl={4} lg={5} style={{ textAlign: 'right', paddingRight: '5px' }}>
                            限定时间段
                        </Col>
                        <Col span={12}>
                            <FormItem
                                name='timeLimitPeriod'
                                noStyle
                                rules={[
                                    {
                                        required: true,
                                        message: '请输入限定时间段长度。',
                                    },
                                ]}
                            >
                                <InputNumber min={5} max={720} />
                            </FormItem>
                            &nbsp;分钟
                        </Col>
                    </Row>
                </FormItem>
                <FormItem>
                    <Row>
                        <Col xxl={3} xl={4} lg={5} style={{ textAlign: 'right', paddingRight: '10px' }}>
                            限定时间段内连续登录
                        </Col>
                        <Col span={12}>
                            <FormItem
                                name='loginLimitNumber'
                                noStyle
                                rules={[
                                    {
                                        required: true,
                                        message: '请输入限定时间段内连续登录失败次数。',
                                    },
                                ]}
                            >
                                <InputNumber min={5} max={30} />
                            </FormItem>
                            &nbsp;次失败，锁定帐号。
                        </Col>
                    </Row>
                </FormItem>
                <FormItem>帐号锁定设置</FormItem>
                <Row>
                    <Col xxl={3} xl={4} lg={5} style={{ textAlign: 'right', paddingLeft: '10px' }}>
                        <FormItem name='lockTime' noStyle>
                            <Radio checked={lockTimeChecked} onChange={this.changeLockTimeStrategy}>
                                锁定时长（分钟）
                            </Radio>
                        </FormItem>
                    </Col>
                    <Col span={12}>
                        <FormItem name='accountLockTime'>
                            <InputNumber min={5} max={60} disabled={!lockTimeChecked} />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col xxl={3} xl={4} lg={5} style={{ textAlign: 'right', paddingRight: '55px' }}>
                        <FormItem name='permanentLock' noStyle>
                            <Radio checked={PermanentLockChecked} onChange={this.changeLockTimeStrategy}>
                                永久锁定
                            </Radio>
                        </FormItem>
                    </Col>
                </Row>
                <FormItem>
                    <Row>
                        <Col xxl={3} xl={4} lg={5} />
                        <Col span={12}>
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
                    <div className='title'>帐号策略</div>
                </div>

                <div className='mz-page-content'>
                    <div className='mz-page-content-body'>{this.renderBody()}</div>
                </div>
            </>
        );
    }
}

export default AccountStrategy;
