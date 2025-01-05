import { useState } from 'react';
import { Button, Form, message, Table } from 'antd';

import httpRequest from '@/utils/http-request';

const FormItem = Form.Item;

export default function ConfirmAccountInfoForm({ prevStep, gotoList, formData }) {
    const [confirmLoading, setConfirmLoading] = useState(false);

    const submitForm = () => {
        const roleIds = formData.selectedRoles.map((role, key, roles) => role.id);

        setConfirmLoading(true);

        const requestBody = {
            url: '/account/addAccount.action',
            data: {
                name: formData.name?.trim(),
                status: formData.status,
                phoneNumber: formData.phoneNumber.length === 11 ? formData.phoneNumber?.trim() : undefined,
                password: formData.password?.trim(),
                confirmPassword: formData.confirmPassword?.trim(),
                roleIds,
            },
        };

        httpRequest.post(requestBody).then(({ fetchStatus }) => {
            setConfirmLoading(false);

            if (fetchStatus.okey) {
                message.success('新增帐号成功。');
                gotoList();
            } else {
                message.error(fetchStatus.message);
            }
        });
    };

    const columns = [
        {
            title: '角色名',
            dataIndex: 'displayNameCN',
            key: 'displayNameCN',
            width: '25%',
        },
        {
            title: '描述',
            dataIndex: 'descriptionCN',
            key: 'descriptionCN',
        },
    ];

    return (
        <Form labelAlign='left' labelCol={{ flex: '120px' }}>
            <FormItem label='帐号名'>{formData.name}</FormItem>
            <FormItem label='手机号'>{formData.phoneNumber}</FormItem>
            <FormItem label='帐号使用状态'>
                {formData.status === 2 && '启用'}
                {formData.status === 3 && '停用'}
            </FormItem>
            <FormItem>
                <div style={{ marginBottom: '13px' }}> 已选择的所属角色： </div>
                <Table
                    columns={columns}
                    dataSource={formData.selectedRoles}
                    pagination={false}
                    rowKey={(record) => record.id}
                    size='middle'
                />
            </FormItem>
            <FormItem className='mz-button-group'>
                <Button type='primary' onClick={submitForm} loading={confirmLoading}>
                    完成
                </Button>
                <Button onClick={prevStep}>上一步</Button>
                <Button onClick={gotoList}>取消</Button>
            </FormItem>
        </Form>
    );
}
