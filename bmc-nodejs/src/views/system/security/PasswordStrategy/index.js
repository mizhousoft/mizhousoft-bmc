import React, { useState, useEffect } from 'react';
import { Form, InputNumber, Button, Row, Col, message } from 'antd';
import { LOADING_FETCH_STATUS } from '@/constants/common';
import { PageLoading, PageException, PageComponent } from '@/components/UIComponent';
import { fetchPasswordStrategy, modifyPasswordStrategy } from '../redux/securityService';

const FormItem = Form.Item;

export default function PasswordStrategy() {
    const [form] = Form.useForm();

    const [uFetchStatus, setFetchStatus] = useState(LOADING_FETCH_STATUS);
    const [confirmLoading, setConfirmLoading] = useState(false);
    const [uStrategy, setStrategy] = useState(undefined);

    const onFinish = (values) => {
        setConfirmLoading(true);

        const body = {
            id: uStrategy.id,
            ...values,
        };

        modifyPasswordStrategy(body).then(({ fetchStatus }) => {
            setConfirmLoading(false);

            if (fetchStatus.okey) {
                message.success('修改密码策略成功。');
            } else {
                message.error(fetchStatus.message);
            }
        });
    };

    useEffect(() => {
        fetchPasswordStrategy().then(({ fetchStatus, strategy }) => {
            setStrategy(strategy);
            setFetchStatus(fetchStatus);
        });
    }, []);

    const pageTitle = '密码策略';

    if (uFetchStatus.loading) {
        return <PageLoading title={pageTitle} />;
    }
    if (!uFetchStatus.okey) {
        return <PageException title={pageTitle} fetchStatus={uFetchStatus} />;
    }

    return (
        <PageComponent title={pageTitle}>
            <Form
                onFinish={onFinish}
                form={form}
                initialValues={{
                    historyRepeatSize: uStrategy.historyRepeatSize,
                    charAppearSize: uStrategy.charAppearSize,
                    modifyTimeInterval: uStrategy.modifyTimeInterval,
                    validDay: uStrategy.validDay,
                    reminderModifyDay: uStrategy.reminderModifyDay,
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
                            距离密码到期，提醒修改的天数
                        </Col>
                        <Col span={5}>
                            <FormItem
                                name='reminderModifyDay'
                                rules={[
                                    {
                                        required: true,
                                        message: '请输入提醒修改密码的天数。',
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
        </PageComponent>
    );
}
