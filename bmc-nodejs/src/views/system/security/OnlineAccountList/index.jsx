import { useEffect, useState } from 'react';
import { message, Popconfirm, Table } from 'antd';

import PageComponent from '@/components/PageComponent';
import { DEFAULT_DATA_PAGE, LOADING_FETCH_STATUS, SUCCEED_FETCH_STATUS } from '@/config/common';
import { getTableLocale } from '@/utils/antd-extension';
import httpRequest from '@/utils/http-request';

export default function OnlineAccountList() {
    const [uFetchStatus, setFetchStatus] = useState(LOADING_FETCH_STATUS);
    const [dataSource, setDataSource] = useState(DEFAULT_DATA_PAGE);

    const fetchList = (pageNumber, pageSize) => {
        const requestBody = {
            url: '/system/fetchOnlineAccounts.action',
            data: {
                pageNumber,
                pageSize,
            },
        };

        httpRequest.get(requestBody).then(({ fetchStatus, dataPage = DEFAULT_DATA_PAGE }) => {
            setDataSource(dataPage);
            setFetchStatus(fetchStatus);
        });
    };

    const refreshList = () => {
        setFetchStatus(LOADING_FETCH_STATUS);

        fetchList(dataSource.pageNumber, dataSource.pageSize);
    };

    const changeTablePage = (page, pageSize) => {
        setFetchStatus(LOADING_FETCH_STATUS);

        fetchList(page, pageSize);
    };

    const logoff = (record) => {
        setFetchStatus(LOADING_FETCH_STATUS);

        const requestBody = {
            url: '/system/logoffOnlineAccount.action',
            data: {
                ...record,
            },
        };

        httpRequest.post(requestBody).then(({ fetchStatus }) => {
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
        fetchList(dataSource.pageNumber, dataSource.pageSize);
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
        onChange: (page, pageSize) => changeTablePage(page, pageSize),
    };

    const locale = getTableLocale(uFetchStatus);

    return (
        <PageComponent breadcrumbs={['在线帐号']}>
            <Table
                loading={uFetchStatus.loading}
                columns={columns}
                dataSource={dataSource.content}
                pagination={pagination}
                rowKey={(record) => record.randomId}
                size='middle'
                locale={locale}
            />
        </PageComponent>
    );
}
