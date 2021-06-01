import React, { Component } from 'react';
import { Table, Button, Popconfirm } from 'antd';
import ButtonSelectRole from '@/views/system/role/ButtonSelectRole';
import { fetchRolesOnNew } from '../redux/accountService';

class SelectRoleForm extends Component {
    constructor(props) {
        super(props);

        this.state = {
            selectedRoles: props.formData.selectedRoles,
        };
    }

    next() {
        const { nextStep, formData } = this.props;

        const newFormData = { ...formData, selectedRoles: this.state.selectedRoles };
        nextStep(newFormData);
    }

    deleteRole = (id, name) => {
        this.setState((prevState) => ({ selectedRoles: prevState.selectedRoles.filter((item) => item.id !== id) }));
    };

    render() {
        const { prevStep, gotoList } = this.props;
        const { selectedRoles } = this.state;

        const columns = [
            {
                title: '角色名',
                dataIndex: 'displayNameCN',
                key: 'displayNameCN',
                width: '15%',
            },
            {
                title: '描述',
                dataIndex: 'descriptionCN',
                key: 'descriptionCN',
            },
            {
                title: '操作',
                key: 'action',
                width: 150,
                className: 'center-action-button',
                render: (text, record) => (
                    <Popconfirm
                        title='你确定要删除该角色吗？'
                        onConfirm={() => this.deleteRole(record.id, record.name)}
                        okText='确认'
                        cancelText='取消'
                        placement='left'
                    >
                        <a>删除</a>
                    </Popconfirm>
                ),
            },
        ];

        return (
            <div>
                <ButtonSelectRole
                    selectedRoles={selectedRoles}
                    onChange={(roles) => this.setState({ selectedRoles: roles })}
                    fetchAction={fetchRolesOnNew}
                />

                <Table
                    columns={columns}
                    dataSource={selectedRoles}
                    pagination={false}
                    rowKey={(record) => record.name}
                    size='middle'
                    bordered
                />

                <div className='mz-button-group' style={{ marginTop: '24px' }}>
                    <Button type='primary' onClick={() => this.next()}>
                        下一步
                    </Button>
                    <Button onClick={() => prevStep()}>上一步</Button>
                    <Button onClick={() => gotoList()}>取消</Button>
                </div>
            </div>
        );
    }
}

export default SelectRoleForm;
