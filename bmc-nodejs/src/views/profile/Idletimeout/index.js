import React, { useState, useEffect, useRef } from 'react';
import { Form, InputNumber, Button, message, Alert } from 'antd';
import FormFlex from '@/constants/flex';
import { LOADING_FETCH_STATUS } from '@/constants/common';
import { PageComponent, PageLoading, PageException } from '@/components/UIComponent';
import { fetchIdletimeout, modifyIdletimeout } from '../profileService';

const FormItem = Form.Item;

export default function Idletimeout() {
    const formRef = useRef();

    const [pageStatus, setPageStatus] = useState(LOADING_FETCH_STATUS);
    const [confirmLoading, setConfirmLoading] = useState(false);
    const [idleTimeoutObj, setIdleTimeout] = useState(undefined);

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
            setPageStatus(fetchStatus);
        });
    }, []);

    if (pageStatus.loading) {
        return <PageLoading />;
    }
    if (!pageStatus.okey) {
        return <PageException fetchStatus={pageStatus} />;
    }

    return (
        <PageComponent title='闲时时间设置'>
            <Form onFinish={onFinish} ref={formRef} initialValues={{ timeout: idleTimeoutObj.timeout }}>
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
        </PageComponent>
    );
}
