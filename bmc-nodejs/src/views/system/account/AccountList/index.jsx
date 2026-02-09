import { useEffect } from 'react';
import { Col, message, Radio, Row, Table } from 'antd';
import { useNavigate } from 'react-router';

import AuthButton from '@/components-auth/AuthButton';
import AuthLink from '@/components-auth/AuthLink';
import AuthPopconfirm from '@/components-auth/AuthPopconfirm';
import PageComponent from '@/components/PageComponent';
import { getTableLocale } from '@/utils/antd-extension';
import httpRequest from '@/utils/http-request';
import useAccountStore from '@/views/system/account/AccountList/accountStore';
import ResetAccountPasswd from '../ResetAccountPasswd';

export default function AccountList() {
    const navigate = useNavigate();

    const fetchStatus = useAccountStore((state) => state.fetchStatus);
    const dataSource = useAccountStore((state) => state.dataSource);
    const filter = useAccountStore((state) => state.filter);
    const setLoading = useAccountStore((state) => state.setLoading);
    const fetchList = useAccountStore((state) => state.fetchList);

    const gotoNew = () => {
        navigate('/account/new');
    };

    const refreshList = () => {
        fetchList(dataSource.pageNumber, dataSource.pageSize, filter);
    };

    const disableItem = (id) => {
        setLoading();

        const requestBody = {
            url: '/account/disableAccount.action',
            data: {
                id,
            },
        };

        httpRequest.post(requestBody).then(({ fetchStatus }) => {
            if (fetchStatus.okey) {
                message.success('禁用帐号成功。');
            } else {
                message.error(fetchStatus.message);
            }

            refreshList();
        });
    };

    const enableItem = (id) => {
        setLoading();

        const requestBody = {
            url: '/account/enableAccount.action',
            data: {
                id,
            },
        };

        httpRequest.post(requestBody).then(({ fetchStatus }) => {
            if (fetchStatus.okey) {
                message.success('启用帐号成功。');
            } else {
                message.error(fetchStatus.message);
            }

            refreshList();
        });
    };

    const unlockItem = (id) => {
        setLoading();

        const requestBody = {
            url: '/account/unlockAccount.action',
            data: {
                id,
            },
        };

        httpRequest.post(requestBody).then(({ fetchStatus }) => {
            if (fetchStatus.okey) {
                message.success('解锁帐号成功。');
            } else {
                message.error(fetchStatus.message);
            }
        });

        refreshList();
    };

    const deleteItem = (id) => {
        setLoading();

        const requestBody = {
            url: '/account/deleteAccount.action',
            data: {
                id,
            },
        };

        httpRequest.post(requestBody).then(({ fetchStatus }) => {
            if (fetchStatus.okey) {
                message.success('删除帐号成功。');
            } else {
                message.error(fetchStatus.message);
            }

            refreshList();
        });
    };

    const changeFilterStatus = (e) => {
        const filter = {
            status: e.target.value,
        };

        fetchList(dataSource.pageNumber, dataSource.pageSize, filter);
    };

    useEffect(() => {
        refreshList();
    }, []);

    const columns = [
        {
            title: '帐号',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: '手机号',
            dataIndex: 'phoneNumber',
            key: 'phoneNumber',
        },
        {
            title: '使用状态',
            dataIndex: 'statusText',
            key: 'statusText',
        },
        {
            title: '角色',
            dataIndex: 'roleNames',
            key: 'roleNames',
        },
        {
            title: '最后访问时间',
            dataIndex: 'lastAccessTimeText',
            key: 'lastAccessTimeText',
        },
        {
            title: '最后访问IP地址',
            dataIndex: 'lastAccessIpAddr',
            key: 'lastAccessIpAddr',
        },
        {
            title: '操作',
            key: 'action',
            width: 230,
            className: 'mz-a-group',
            render: (text, record) => {
                if (record.superAdmin) {
                    return null;
                }

                return (
                    <>
                        <AuthLink authId='bmc.account.authorize' to={`/account/authorize/${record.id}`}>
                            授权
                        </AuthLink>

                        {record.status === 2 && (
                            <AuthPopconfirm
                                authId='bmc.account.disable'
                                title='你确定要停用该帐号吗？'
                                onConfirm={() => disableItem(record.id)}
                                okText='确认'
                                cancelText='取消'
                            >
                                <a>停用</a>
                            </AuthPopconfirm>
                        )}
                        {record.status === 3 && (
                            <AuthPopconfirm
                                authId='bmc.account.enable'
                                title='你确定要启用该帐号吗？'
                                onConfirm={() => enableItem(record.id)}
                                okText='确认'
                                cancelText='取消'
                            >
                                <a>启用</a>
                            </AuthPopconfirm>
                        )}
                        {record.status === 4 && (
                            <AuthPopconfirm
                                authId='bmc.account.unlock'
                                title='你确定要解锁该帐号吗？'
                                onConfirm={() => unlockItem(record.id)}
                                okText='确认'
                                cancelText='取消'
                            >
                                <a>解锁</a>
                            </AuthPopconfirm>
                        )}

                        <ResetAccountPasswd accountId={record.id} />

                        <AuthPopconfirm
                            authId='bmc.account.delete'
                            title='你确定要删除该帐号吗？'
                            onConfirm={() => deleteItem(record.id)}
                            okText='确认'
                            cancelText='取消'
                        >
                            <a>删除</a>
                        </AuthPopconfirm>
                    </>
                );
            },
        },
    ];

    const pagination = {
        size: 'middle',
        total: dataSource.totalNumber,
        pageSize: dataSource.pageSize,
        current: dataSource.pageNumber,
        showQuickJumper: true,
        showSizeChanger: true,
        pageSizeOptions: ['10', '20', '30', '40', '50', '100'],
        showTotal: (total) => `总条数： ${total} `,
        onChange: (page, pageSize) => fetchList(page, pageSize, filter),
    };

    const locale = getTableLocale(fetchStatus);

    return (
        <PageComponent breadcrumbs={[{ title: '帐号' }]}>
            <Row className='mz-table-header'>
                <Col span={12}>
                    <Radio.Group
                        defaultValue={`${filter.status}`}
                        onChange={changeFilterStatus}
                        optionType='button'
                        options={[
                            { value: '0', label: '所有帐号' },
                            { value: '2', label: '启用帐号' },
                            { value: '3', label: '禁用帐号' },
                            { value: '4', label: '锁定帐号' },
                        ]}
                    />
                </Col>
                <Col span={12} style={{ textAlign: 'right' }}>
                    <AuthButton authId='bmc.account.new' onClick={gotoNew} type='primary'>
                        增加帐号
                    </AuthButton>
                </Col>
            </Row>
            <Table
                loading={fetchStatus.loading}
                columns={columns}
                dataSource={dataSource.content}
                pagination={pagination}
                rowKey={(record) => record.id}
                size='middle'
                locale={locale}
            />
        </PageComponent>
    );
}
