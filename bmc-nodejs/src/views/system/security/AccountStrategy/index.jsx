import React, { useState, useEffect } from 'react';
import { Form, InputNumber, Button, Radio, Row, Col, message } from 'antd';
import { LOADING_FETCH_STATUS } from '@/constants/common';
import { PageLoading, PageException, PageComponent } from '@/components/UIComponent';
import { fetchAccountStrategy, modifyAccountStrategy } from '../redux/securityService';

const FormItem = Form.Item;

export default function AccountStrategy() {
    const [form] = Form.useForm();

    const [uFetchStatus, setFetchStatus] = useState(LOADING_FETCH_STATUS);
    const [confirmLoading, setConfirmLoading] = useState(false);
    const [uStrategy, setStrategy] = useState(undefined);

    const changeLockTimeStrategy = (e) => {
        uStrategy.lockTimeStrategy = e.target.value;

        const newStrategy = { ...uStrategy };
        setStrategy(newStrategy);
    };

    const onFinish = (values) => {
        setConfirmLoading(true);

        const body = { id: uStrategy.id, lockTimeStrategy: uStrategy.lockTimeStrategy, ...values };

        modifyAccountStrategy(body).then(({ fetchStatus }) => {
            setConfirmLoading(false);

            if (fetchStatus.okey) {
                message.success('修改帐号策略成功。');
            } else {
                message.error(fetchStatus.message);
            }
        });
    };

    useEffect(() => {
        fetchAccountStrategy().then(({ fetchStatus, strategy }) => {
            setStrategy(strategy);
            setFetchStatus(fetchStatus);
        });
    }, []);

    const breadcrumbs = ['帐号策略'];

    if (uFetchStatus.loading) {
        return <PageLoading breadcrumbs={breadcrumbs} />;
    }
    if (!uFetchStatus.okey) {
        return <PageException breadcrumbs={breadcrumbs} fetchStatus={uFetchStatus} />;
    }

    const PermanentLockChecked = uStrategy.lockTimeStrategy === 2;
    const lockTimeChecked = !PermanentLockChecked;

    return (
        <PageComponent breadcrumbs={breadcrumbs}>
            <Form
                onFinish={onFinish}
                form={form}
                initialValues={{
                    accountUnusedDay: uStrategy.accountUnusedDay,
                    timeLimitPeriod: uStrategy.timeLimitPeriod,
                    loginLimitNumber: uStrategy.loginLimitNumber,
                    lockTime: 1,
                    accountLockTime: uStrategy.accountLockTime,
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
                            <Radio checked={lockTimeChecked} onChange={changeLockTimeStrategy}>
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
                            <Radio checked={PermanentLockChecked} onChange={changeLockTimeStrategy}>
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
        </PageComponent>
    );
}
