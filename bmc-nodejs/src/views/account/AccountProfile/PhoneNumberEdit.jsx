import { useState } from 'react';
import { Button, Form, Input, message, Modal } from 'antd';

import httpRequest from '@/utils/http-request';

const FormItem = Form.Item;

export default function PhoneNumberEdit({ account, fetchPageData }) {
    const [confirmLoading, setConfirmLoading] = useState(false);
    const [visible, setVisible] = useState(false);

    const onFinish = (values) => {
        setConfirmLoading(true);

        const requestBody = {
            url: '/setting/account/modifyPhoneNumber.action',
            data: {},
        };

        if (values.phoneNumber && values.phoneNumber.length >= 1) {
            requestBody.data.phoneNumber = values.phoneNumber?.trim();
        }

        httpRequest.post(requestBody).then(({ fetchStatus }) => {
            setConfirmLoading(false);

            if (fetchStatus.okey) {
                setVisible(false);
                message.success('修改手机号成功。');
                fetchPageData();
            } else {
                message.error(fetchStatus.message);
            }
        });
    };

    return (
        <>
            <Button onClick={() => setVisible(true)}>修改</Button>

            {visible && (
                <Modal
                    title='修改手机号'
                    maskClosable={false}
                    footer={null}
                    open
                    destroyOnHidden
                    onCancel={() => setVisible(false)}
                    closable={false}
                    centered
                    className='mz-modal'
                >
                    <Form
                        preserve={false}
                        onFinish={onFinish}
                        labelAlign='left'
                        labelCol={{ flex: '80px' }}
                        initialValues={{
                            phoneNumber: account.phoneNumber,
                        }}
                    >
                        <FormItem
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
                            <Button type='primary' htmlType='submit' loading={confirmLoading}>
                                确认
                            </Button>
                            <Button onClick={() => setVisible(false)}>取消</Button>
                        </div>
                    </Form>
                </Modal>
            )}
        </>
    );
}
