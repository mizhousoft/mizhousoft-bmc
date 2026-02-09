import { useEffect, useState } from 'react';
import { Button, Col, Form, InputNumber, message, Radio, Row } from 'antd';

import PageComponent from '@/components/PageComponent';
import PageException from '@/components/PageException';
import PageLoading from '@/components/PageLoading';
import { LOADING_FETCH_STATUS } from '@/config/common';
import httpRequest from '@/utils/http-request';

const FormItem = Form.Item;

export default function AccountStrategy() {
    const [form] = Form.useForm();

    const [fetchStatus, setFetchStatus] = useState(LOADING_FETCH_STATUS);
    const [confirmLoading, setConfirmLoading] = useState(false);
    const [strategy, setStrategy] = useState(undefined);

    const changeLockTimeStrategy = (e) => {
        const newStrategy = { ...strategy };
        newStrategy.lockTimeStrategy = e.target.value;

        setStrategy(newStrategy);
    };

    const onFinish = (values) => {
        setConfirmLoading(true);

        const requestBody = {
            url: '/system/modifyAccountStrategy.action',
            data: {
                id: strategy.id,
                lockTimeStrategy: strategy.lockTimeStrategy,
                ...values,
            },
        };

        httpRequest.post(requestBody).then(({ fetchStatus }) => {
            setConfirmLoading(false);

            if (fetchStatus.okey) {
                message.success('修改帐号策略成功。');
            } else {
                message.error(fetchStatus.message);
            }
        });
    };

    useEffect(() => {
        const requestBody = {
            url: '/system/fetchAccountStrategy.action',
            data: {},
        };

        httpRequest.get(requestBody).then(({ fetchStatus, strategy }) => {
            setStrategy(strategy);
            setFetchStatus(fetchStatus);
        });
    }, []);

    const breadcrumbs = [{ title: '帐号策略' }];

    if (fetchStatus.loading) {
        return <PageLoading breadcrumbs={breadcrumbs} />;
    }
    if (!fetchStatus.okey) {
        return <PageException breadcrumbs={breadcrumbs} fetchStatus={fetchStatus} />;
    }

    const PermanentLockChecked = strategy.lockTimeStrategy === 2;
    const lockTimeChecked = !PermanentLockChecked;

    return (
        <PageComponent breadcrumbs={breadcrumbs}>
            <Form
                onFinish={onFinish}
                form={form}
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
