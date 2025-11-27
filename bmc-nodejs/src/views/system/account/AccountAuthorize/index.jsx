import { useEffect, useState } from 'react';
import { Button, Form, message, Popconfirm, Table } from 'antd';
import { useNavigate, useParams } from 'react-router';

import PageComponent from '@/components/PageComponent';
import PageException from '@/components/PageException';
import PageLoading from '@/components/PageLoading';
import { LOADING_FETCH_STATUS } from '@/config/common';
import httpRequest from '@/utils/http-request';
import ButtonSelectRole from '@/views/system/role/ButtonSelectRole';

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
        setConfirmLoading(true);

        const roleIds = uSelectedRoles.map((role, key, roles) => role.id);

        const requestBody = {
            url: '/account/authorizeAccount.action',
            data: {
                id: Number.parseInt(id, 10),
                roleIds,
            },
        };

        httpRequest.post(requestBody).then(({ fetchStatus }) => {
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
        const requestBody = {
            url: '/account/authorize/fetchAccountRoles.action',
            data: {
                accountId: id,
            },
        };

        httpRequest.get(requestBody).then(({ fetchStatus, account, selectedRoles = [] }) => {
            setAccount(account);
            setSelectedRoles(selectedRoles);
            setFetchStatus(fetchStatus);
        });
    }, []);

    const breadcrumbs = [{ title: '帐号' }, { title: '授权帐号' }];

    if (uFetchStatus.loading) {
        return <PageLoading breadcrumbs={breadcrumbs} />;
    }
    if (!uFetchStatus.okey) {
        return <PageException breadcrumbs={breadcrumbs} fetchStatus={uFetchStatus} goBack={gotoList} />;
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
        <PageComponent breadcrumbs={breadcrumbs}>
            <Form labelAlign='left' labelCol={{ flex: '80px' }}>
                <FormItem label='帐号名'>{uAccount.name}</FormItem>
                <FormItem>
                    <ButtonSelectRole
                        selectedRoles={uSelectedRoles}
                        onChange={(roles) => setSelectedRoles(roles)}
                        fetchRequestPath='/account/authorize/fetchRoles.action'
                    />

                    <Table size='middle' columns={columns} dataSource={uSelectedRoles} pagination={false} rowKey={(record) => record.id} />
                </FormItem>
                <FormItem className='mz-button-group'>
                    <Button type='primary' onClick={onFinish} loading={confirmLoading}>
                        确定
                    </Button>
                    <Button onClick={gotoList}>取消</Button>
                </FormItem>
            </Form>
        </PageComponent>
    );
}
