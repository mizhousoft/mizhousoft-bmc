import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { message, Row, Col, Radio, Table } from 'antd';
import { getTableLocale, PageComponent } from '@/components/UIComponent';
import { DEFAULT_DATA_PAGE } from '@/constants/common';
import AuthButton from '@/views/components/AuthButton';
import AuthLink from '@/views/components/AuthLink';
import AuthPopconfirm from '@/views/components/AuthPopconfirm';
import { fetchEvent, fetchResultEvent, actionEvent, actionResultEvent } from '../redux/accountSlice';
import ResetAccountPasswd from '../ResetAccountPasswd';
import {
    fetchAccountInfoList,
    disableAccount,
    enableAccount,
    unlockAccount,
    deleteAccount,
} from '../redux/accountService';

export default function AccountList() {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const uFetchStatus = useSelector((state) => state.accounts.fetchStatus);
    const dataSource = useSelector((state) => state.accounts.dataSource);
    const uFilter = useSelector((state) => state.accounts.filter);

    const gotoNew = () => {
        navigate('/account/new');
    };

    const fetchList = (pageNumber, pageSize, filter) => {
        const body = {
            pageNumber,
            pageSize,
            status: filter.status,
        };

        dispatch(fetchEvent({ filter }));

        fetchAccountInfoList(body).then(({ fetchStatus, dataPage = DEFAULT_DATA_PAGE }) => {
            dispatch(
                fetchResultEvent({
                    fetchStatus,
                    dataSource: dataPage,
                })
            );
        });
    };

    const refreshList = () => {
        fetchList(dataSource.pageNumber, dataSource.pageSize, uFilter);
    };

    const disableItem = (id) => {
        dispatch(actionEvent());

        const body = { id };

        disableAccount(body).then(({ fetchStatus }) => {
            if (fetchStatus.okey) {
                message.success('禁用帐号成功。');
                refreshList();
            } else {
                message.error(fetchStatus.message);
                dispatch(actionResultEvent());
            }
        });
    };

    const enableItem = (id) => {
        dispatch(actionEvent());

        const body = { id };

        enableAccount(body).then(({ fetchStatus }) => {
            if (fetchStatus.okey) {
                message.success('启用帐号成功。');
                refreshList();
            } else {
                message.error(fetchStatus.message);
                dispatch(actionResultEvent());
            }
        });
    };

    const unlockItem = (id) => {
        dispatch(actionEvent());

        const body = { id };

        unlockAccount(body).then(({ fetchStatus }) => {
            if (fetchStatus.okey) {
                message.success('解锁帐号成功。');
                refreshList();
            } else {
                message.error(fetchStatus.message);
                dispatch(actionResultEvent());
            }
        });
    };

    const deleteItem = (id) => {
        dispatch(actionEvent());

        const body = { id };

        deleteAccount(body).then(({ fetchStatus }) => {
            if (fetchStatus.okey) {
                message.success('删除帐号成功。');
                refreshList();
            } else {
                message.error(fetchStatus.message);
                dispatch(actionResultEvent());
            }
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
        onChange: (page, pageSize) => fetchList(page, pageSize, uFilter),
        position: ['bottomLeft'],
    };

    const locale = getTableLocale(uFetchStatus);

    return (
        <PageComponent title='帐号'>
            <Row className='mz-table-header'>
                <Col span={12}>
                    <Radio.Group defaultValue={`${uFilter.status}`} onChange={changeFilterStatus}>
                        <Radio.Button value='0'>所有帐号</Radio.Button>
                        <Radio.Button value='2'>启用帐号</Radio.Button>
                        <Radio.Button value='3'>禁用帐号</Radio.Button>
                        <Radio.Button value='4'>锁定帐号</Radio.Button>
                    </Radio.Group>
                </Col>
                <Col span={12} style={{ textAlign: 'right' }}>
                    <AuthButton authId='bmc.account.new' onClick={gotoNew} type='primary'>
                        增加帐号
                    </AuthButton>
                </Col>
            </Row>
            <Table
                loading={uFetchStatus.loading}
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
