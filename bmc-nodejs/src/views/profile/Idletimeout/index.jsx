import React, { useState, useEffect } from 'react';
import { Form, InputNumber, Button, message, Alert } from 'antd';
import { LOADING_FETCH_STATUS } from '@/constants/common';
import { PageComponent, PageLoading, PageException } from '@/components/UIComponent';
import { fetchIdletimeout, modifyIdletimeout } from '../profileService';

const FormItem = Form.Item;

export default function Idletimeout() {
    const [uFetchStatus, setFetchStatus] = useState(LOADING_FETCH_STATUS);
    const [confirmLoading, setConfirmLoading] = useState(false);
    const [uIdleTimeOut, setIdleTimeout] = useState(undefined);

    const onFinish = (values) => {
        setConfirmLoading(true);

        modifyIdletimeout(values).then(({ fetchStatus }) => {
            setConfirmLoading(false);

            if (fetchStatus.okey) {
                message.success('修改闲置超时时间成功。');
            } else {
                message.error(fetchStatus.message);
            }
        });
    };

    useEffect(() => {
        fetchIdletimeout().then(({ fetchStatus, idleTimeout }) => {
            setIdleTimeout(idleTimeout);
            setFetchStatus(fetchStatus);
        });
    }, []);

    const pageTitle = '闲时时间设置';

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
                initialValues={{ timeout: uIdleTimeOut.timeout }}
                labelAlign='left'
                labelCol={{ flex: '120px' }}
            >
                <Alert
                    message='当你长时间不使用系统，系统为保证你的帐号安全，将退出你的登录。'
                    type='info'
                    showIcon
                    style={{ marginBottom: '18px' }}
                />

                <Form.Item label='闲置超时时间'>
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

                <FormItem colon={false} label=' '>
                    <Button type='primary' htmlType='submit' loading={confirmLoading}>
                        确定
                    </Button>
                </FormItem>
            </Form>
        </PageComponent>
    );
}
