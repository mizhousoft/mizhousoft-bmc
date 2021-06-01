import React, { Component } from 'react';
import { connect } from 'react-redux';
import { withRouter } from 'react-router-dom';
import { message, Row, Col, Radio, Table } from 'antd';
import { getTableLocale } from '@/components/UIComponent';
import AuthButton from '@/views/components/AuthButton';
import AuthLink from '@/views/components/AuthLink';
import AuthPopconfirm from '@/views/components/AuthPopconfirm';
import ACCOUNT from '../redux/accountActionType';
import ResetAccountPasswd from '../ResetAccountPasswd';
import { disableAccount, enableAccount, unlockAccount, deleteAccount } from '../redux/accountService';

class AccountList extends Component {
    gotoNew = () => {
        const { history } = this.props;
        history.push('/account/new');
    };

    disableAccount = (id) => {
        const { dispatch } = this.props;

        dispatch({ type: ACCOUNT.ACTION });

        const body = { id };

        disableAccount(body).then(({ fetchStatus }) => {
            if (fetchStatus.okey) {
                message.success('禁用帐号成功。');
                this.refreshList();
            } else {
                message.error(fetchStatus.message);
                dispatch({ type: ACCOUNT.ACTION_FAILURE, payload: { fetchStatus } });
            }
        });
    };

    enableAccount = (id) => {
        const { dispatch } = this.props;

        dispatch({ type: ACCOUNT.ACTION });

        const body = { id };

        enableAccount(body).then(({ fetchStatus }) => {
            if (fetchStatus.okey) {
                message.success('启用帐号成功。');
                this.refreshList();
            } else {
                message.error(fetchStatus.message);
                dispatch({ type: ACCOUNT.ACTION_FAILURE, payload: { fetchStatus } });
            }
        });
    };

    unlockAccount = (id) => {
        const { dispatch } = this.props;

        dispatch({ type: ACCOUNT.ACTION });

        const body = { id };

        unlockAccount(body).then(({ fetchStatus }) => {
            if (fetchStatus.okey) {
                message.success('解锁帐号成功。');
                this.refreshList();
            } else {
                message.error(fetchStatus.message);
                dispatch({ type: ACCOUNT.ACTION_FAILURE, payload: { fetchStatus } });
            }
        });
    };

    deleteAccount = (id) => {
        const { dispatch } = this.props;

        dispatch({ type: ACCOUNT.ACTION });

        const body = { id };

        deleteAccount(body).then(({ fetchStatus }) => {
            if (fetchStatus.okey) {
                message.success('删除帐号成功。');
                this.refreshList();
            } else {
                message.error(fetchStatus.message);
                dispatch({ type: ACCOUNT.ACTION_FAILURE, payload: { fetchStatus } });
            }
        });
    };

    refreshList = () => {
        const { dataSource, filter } = this.props;

        this.fetchList(dataSource.pageNumber, dataSource.pageSize, filter);
    };

    fetchList = (pageNumber, pageSize, filter) => {
        const { dispatch } = this.props;

        filter = filter ?? this.props.filter;

        const payload = {
            pageNumber,
            pageSize,
            status: filter.status,
        };

        dispatch({ type: ACCOUNT.FETCH_LIST, filter, payload });
    };

    changeFilterStatus = (e) => {
        const { dataSource } = this.props;

        const filter = {
            status: e.target.value,
        };

        this.fetchList(dataSource.pageNumber, dataSource.pageSize, filter);
    };

    componentDidMount() {
        this.refreshList();
    }

    renderHeader = () => (
        <Row className='mz-table-header'>
            <Col span={12}>
                <Radio.Group defaultValue={`${this.props.filter.status}`} onChange={this.changeFilterStatus}>
                    <Radio.Button value='0'>所有帐号</Radio.Button>
                    <Radio.Button value='2'>启用帐号</Radio.Button>
                    <Radio.Button value='3'>禁用帐号</Radio.Button>
                    <Radio.Button value='4'>锁定帐号</Radio.Button>
                </Radio.Group>
            </Col>
            <Col span={12} style={{ textAlign: 'right' }}>
                <AuthButton authId='bmc.account.new' onClick={this.gotoNew} type='primary'>
                    增加帐号
                </AuthButton>
            </Col>
        </Row>
    );

    renderTable = () => {
        const { fetchStatus, dataSource } = this.props;

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
                                    onConfirm={() => this.disableAccount(record.id)}
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
                                    onConfirm={() => this.enableAccount(record.id)}
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
                                    onConfirm={() => this.unlockAccount(record.id)}
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
                                onConfirm={() => this.deleteAccount(record.id)}
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
            pageSizeOptions: ['10', '20', '30', '40', '50'],
            showTotal: (total) => `总条数： ${total} `,
            onChange: (page, pageSize) => this.fetchList(page, pageSize, undefined),
            position: ['bottomLeft'],
        };

        const locale = getTableLocale(fetchStatus);

        return (
            <Table
                loading={fetchStatus.loading}
                columns={columns}
                dataSource={dataSource.content}
                pagination={pagination}
                rowKey={(record) => record.id}
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
                    <div className='title'>帐号</div>
                </div>

                <div className='mz-page-content'>
                    <div className='mz-page-content-body'>
                        {this.renderHeader()}
                        {this.renderTable()}
                    </div>
                </div>
            </>
        );
    }
}

const mapStateToProps = (state) => ({
    fetchStatus: state.accounts.fetchStatus,
    dataSource: state.accounts.dataSource,
    filter: state.accounts.filter,
});

export default withRouter(connect(mapStateToProps)(AccountList));
