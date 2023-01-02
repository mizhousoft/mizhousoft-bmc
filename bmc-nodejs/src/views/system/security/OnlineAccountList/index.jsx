import React, { useState, useEffect } from 'react';
import { message, Table, Popconfirm } from 'antd';
import { DEFAULT_DATA_PAGE, LOADING_FETCH_STATUS, SUCCEED_FETCH_STATUS } from '@/constants/common';
import { getTableLocale, PageComponent } from '@/components/UIComponent';
import { fetchOnlineAccounts, logoffOnlineAccount } from '../redux/securityService';

export default function OnlineAccountList() {
    const [uFetchStatus, setFetchStatus] = useState(LOADING_FETCH_STATUS);
    const [dataSource, setDataSource] = useState(DEFAULT_DATA_PAGE);

    const fetchList = (pageNumber, pageSize) => {
        const body = {
            pageNumber,
            pageSize,
        };

        setFetchStatus(LOADING_FETCH_STATUS);

        fetchOnlineAccounts(body).then(({ fetchStatus, dataPage = DEFAULT_DATA_PAGE }) => {
            setDataSource(dataPage);
            setFetchStatus(fetchStatus);
        });
    };

    const refreshList = () => {
        fetchList(dataSource.pageNumber, dataSource.pageSize);
    };

    const logoff = (record) => {
        setFetchStatus(LOADING_FETCH_STATUS);

        logoffOnlineAccount(record).then(({ fetchStatus }) => {
            if (fetchStatus.okey) {
                message.success('退出帐号成功。');
                refreshList();
            } else {
                setFetchStatus(SUCCEED_FETCH_STATUS);
                message.error(fetchStatus.message);
            }
        });
    };

    useEffect(() => {
        refreshList();
    }, []);

    const columns = [
        {
            title: '帐号名',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: 'IP地址',
            dataIndex: 'ipAddress',
            key: 'ipAddress',
        },
        {
            title: '登录时间',
            dataIndex: 'loginTime',
            key: 'loginTime',
        },
        {
            title: '角色',
            dataIndex: 'role',
            key: 'role',
        },
        {
            title: '操作',
            key: 'action',
            width: 110,
            className: 'mz-a-group',
            render: (text, record) => {
                if (record.currentAccount) {
                    return null;
                }

                return (
                    <span>
                        <Popconfirm title='你确定要退出帐号吗？' onConfirm={() => logoff(record)}>
                            <a>退出帐号</a>
                        </Popconfirm>
                    </span>
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
        onChange: (page, pageSize) => fetchList(page, pageSize),
        position: ['bottomLeft'],
    };

    const locale = getTableLocale(uFetchStatus);

    return (
        <PageComponent title='在线帐号'>
            <Table
                loading={uFetchStatus.loading}
                columns={columns}
                dataSource={dataSource.content}
                pagination={pagination}
                rowKey={(record) => record.randomId}
                size='middle'
                bordered
                locale={locale}
            />
        </PageComponent>
    );
}
