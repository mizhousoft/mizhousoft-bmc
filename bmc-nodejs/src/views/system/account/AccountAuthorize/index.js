import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Form, Button, Popconfirm, message, Table } from 'antd';
import { LOADING_FETCH_STATUS } from '@/constants/common';
import { PageLoading, PageException, PageComponent } from '@/components/UIComponent';
import FormFlex from '@/constants/flex';
import ButtonSelectRole from '@/views/system/role/ButtonSelectRole';
import { fetchRolesOnAuthorize, fetchAccountRolesOnAuthorize, authorizeAccount } from '../redux/accountService';

const FormItem = Form.Item;

export default function AccountAuthorize() {
    const navigate = useNavigate();
    const { id } = useParams();

    const [uFetchStatus, setFetchStatus] = useState(LOADING_FETCH_STATUS);
    const [confirmLoading, setConfirmLoading] = useState(false);
    const [uAccount, setAccount] = useState(undefined);
    const [uSelectedRoles, setSelectedRoles] = useState([]);

    const gotoList = () => {
        navigate('/account/list');
    };

    const deleteRole = (rid, name) => {
        const roles = uSelectedRoles.filter((item) => item.id !== rid);
        setSelectedRoles(roles);
    };

    const onFinish = () => {
        const roleIds = uSelectedRoles.map((role, key, roles) => role.id);

        const body = {
            id: Number.parseInt(id, 10),
            roleIds,
        };

        setConfirmLoading(true);

        authorizeAccount(body).then(({ fetchStatus }) => {
            setConfirmLoading(false);

            if (fetchStatus.okey) {
                message.success('授权成功。');
                gotoList();
            } else {
                message.error(fetchStatus.message);
            }
        });
    };

    useEffect(() => {
        const body = {
            accountId: id,
        };

        fetchAccountRolesOnAuthorize(body).then(({ fetchStatus, account, selectedRoles = [] }) => {
            setAccount(account);
            setSelectedRoles(selectedRoles);
            setFetchStatus(fetchStatus);
        });
    }, []);

    const pageTitle = <>帐号 / 授权帐号</>;

    if (uFetchStatus.loading) {
        return <PageLoading title={pageTitle} />;
    }
    if (!uFetchStatus.okey) {
        return <PageException title={pageTitle} fetchStatus={uFetchStatus} goBack={gotoList} />;
    }

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
        <PageComponent title={pageTitle}>
            <Form labelAlign='left'>
                <FormItem {...FormFlex.w100_lg3_required} label='帐号名'>
                    {uAccount.name}
                </FormItem>
                <FormItem>
                    <ButtonSelectRole
                        selectedRoles={uSelectedRoles}
                        onChange={(roles) => setSelectedRoles(roles)}
                        fetchAction={fetchRolesOnAuthorize}
                    />

                    <Table
                        size='middle'
                        columns={columns}
                        dataSource={uSelectedRoles}
                        pagination={false}
                        rowKey={(record) => record.id}
                        bordered
                    />
                </FormItem>
                <FormItem className='mz-button-group'>
                    <Button type='primary' onClick={onFinish} confirmLoading={confirmLoading}>
                        确定
                    </Button>
                    <Button onClick={gotoList}>取消</Button>
                </FormItem>
            </Form>
        </PageComponent>
    );
}
