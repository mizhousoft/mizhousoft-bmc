import React, { useState } from 'react';
import { Button, Popconfirm, Table } from 'antd';

import { fetchRolesOnNew } from '../redux/accountService';
import ButtonSelectRole from '@/views/system/role/ButtonSelectRole';

export default function SelectRoleForm({ nextStep, prevStep, gotoList, formData }) {
    const [uSelectedRoles, setSelectedRoles] = useState(formData.selectedRoles);

    const next = () => {
        const newFormData = { ...formData, selectedRoles: uSelectedRoles };
        nextStep(newFormData);
    };

    const deleteRole = (id, name) => {
        const selectedRoles = uSelectedRoles.filter((item) => item.id !== id);
        setSelectedRoles(selectedRoles);
    };

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
                    onConfirm={() => deleteRole(record.id, record.name)}
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
                selectedRoles={uSelectedRoles}
                onChange={(roles) => setSelectedRoles(roles)}
                fetchAction={fetchRolesOnNew}
            />

            <Table
                columns={columns}
                dataSource={uSelectedRoles}
                pagination={false}
                rowKey={(record) => record.name}
                size='middle'
            />

            <div className='mz-button-group' style={{ marginTop: '24px' }}>
                <Button type='primary' onClick={() => next()}>
                    下一步
                </Button>
                <Button onClick={() => prevStep()}>上一步</Button>
                <Button onClick={() => gotoList()}>取消</Button>
            </div>
        </div>
    );
}
