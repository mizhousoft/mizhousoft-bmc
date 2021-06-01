import React, { Component } from 'react';
import { message, Table, Popconfirm } from 'antd';
import { DEFAULT_DATA_PAGE, LOADING_FETCH_STATUS } from '@/constants/common';
import { getTableLocale } from '@/components/UIComponent';
import { fetchOnlineAccounts, logoffOnlineAccount } from '../redux/securityService';

class OnlineAccountList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            fetchStatus: LOADING_FETCH_STATUS,
            dataSource: DEFAULT_DATA_PAGE,
        };
    }

    logoff = (record) => {
        this.setState({ fetchStatus: LOADING_FETCH_STATUS });

        logoffOnlineAccount(record).then(({ fetchStatus }) => {
            if (fetchStatus.okey) {
                message.success('退出帐号成功。');
                this.refreshList();
            } else {
                message.error(fetchStatus.message);
                this.setState({ fetchStatus });
            }
        });
    };

    refreshList = () => {
        const { dataSource } = this.state;

        this.fetchList(dataSource.pageNumber, dataSource.pageSize);
    };

    fetchList = (pageNumber, pageSize) => {
        const body = {
            pageNumber,
            pageSize,
        };

        this.setState({ fetchStatus: LOADING_FETCH_STATUS });

        fetchOnlineAccounts(body).then(({ fetchStatus, dataPage }) => {
            this.setState({
                fetchStatus,
                dataSource: dataPage ?? DEFAULT_DATA_PAGE,
            });
        });
    };

    componentDidMount() {
        this.refreshList();
    }

    renderTable = () => {
        const { fetchStatus, dataSource } = this.state;

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
                width: 130,
                className: 'center-action-button',
                render: (text, record) => {
                    if (record.currentAccount) {
                        return null;
                    }

                    return (
                        <span>
                            <Popconfirm title='你确定要退出帐号吗？' onConfirm={() => this.logoff(record)}>
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
            pageSizeOptions: ['10', '20', '30', '40', '50'],
            showTotal: (total) => `总条数： ${total} `,
            onChange: (page, pageSize) => this.fetchList(page, pageSize),
            position: ['bottomLeft'],
        };

        const locale = getTableLocale(fetchStatus);

        return (
            <Table
                loading={fetchStatus.loading}
                columns={columns}
                dataSource={dataSource.content}
                pagination={pagination}
                rowKey={(record) => record.randomId}
                size='middle'
                bordered
                locale={locale}
            />
        );
    };

    render() {
        return (
            <>
                <div className='mz-page-head'>
                    <div className='title'>在线帐号</div>
                </div>

                <div className='mz-page-content'>
                    <div className='mz-page-content-body'>{this.renderTable()}</div>
                </div>
            </>
        );
    }
}

export default OnlineAccountList;
