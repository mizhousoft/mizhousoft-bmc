import React, { Component } from 'react';
import { Table, Form, Button, message } from 'antd';
import FormFlex from '@/constants/flex';
import { addAccount } from '../redux/accountService';

const FormItem = Form.Item;

class ConfirmAccountInfoForm extends Component {
    constructor(props) {
        super(props);

        this.state = {
            confirmLoading: false,
        };
    }

    submitForm = () => {
        const { gotoList, formData } = this.props;

        const roleIds = formData.selectedRoles.map((role, key, roles) => role.id);

        const form = {
            name: formData.name,
            status: formData.status,
            phoneNumber: formData.phoneNumber.length === 11 ? formData.phoneNumber : undefined,
            password: formData.password,
            confirmPassword: formData.confirmPassword,
            roleIds,
        };

        this.setState({ confirmLoading: true });

        addAccount(form).then(({ fetchStatus }) => {
            this.setState({ confirmLoading: false });

            if (fetchStatus.okey) {
                message.success('新增帐号成功。');
                gotoList();
            } else {
                message.error(fetchStatus.message);
            }
        });
    };

    render() {
        const { formData, prevStep, gotoList } = this.props;

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
            <Form labelAlign='left'>
                <FormItem {...FormFlex.w100_lg5_required} label='帐号名'>
                    {formData.name}
                </FormItem>
                <FormItem {...FormFlex.w100_lg5_required} label='手机号'>
                    {formData.phoneNumber}
                </FormItem>
                <FormItem {...FormFlex.w100_lg5_required} label='帐号使用状态'>
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
                        bordered
                    />
                </FormItem>
                <FormItem className='mz-button-group'>
                    <Button type='primary' onClick={this.submitForm} loading={this.state.confirmLoading}>
                        完成
                    </Button>
                    <Button onClick={prevStep}>上一步</Button>
                    <Button onClick={gotoList}>取消</Button>
                </FormItem>
            </Form>
        );
    }
}

export default ConfirmAccountInfoForm;
