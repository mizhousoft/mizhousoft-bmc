import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { Form, Button, Popconfirm, message, Table } from 'antd';
import { LOADING_FETCH_STATUS } from '@/constants/common';
import { PageLoading, PageException } from '@/components/UIComponent';
import FormFlex from '@/constants/flex';
import ButtonSelectRole from '@/views/system/role/ButtonSelectRole';
import { fetchRolesOnAuthorize, fetchAccountRolesOnAuthorize, authorizeAccount } from '../redux/accountService';

const FormItem = Form.Item;

class AccountAuthorize extends Component {
    constructor(props) {
        super(props);
        this.state = {
            fetchStatus: LOADING_FETCH_STATUS,
            confirmLoading: false,

            accountId: props.match.params.id,

            account: undefined,
            selectedRoles: [],
        };
    }

    gotoList = () => {
        const { history } = this.props;
        history.push('/account/list');
    };

    deleteRole = (id, name) => {
        this.setState((prevState) => ({ selectedRoles: prevState.selectedRoles.filter((item) => item.id !== id) }));
    };

    onFinish = () => {
        const { accountId, selectedRoles } = this.state;

        const roleIds = selectedRoles.map((role, key, roles) => role.id);

        const body = {
            id: Number.parseInt(accountId, 10),
            roleIds,
        };

        this.setState({ confirmLoading: true });

        authorizeAccount(body).then(({ fetchStatus }) => {
            this.setState({ confirmLoading: false });

            if (fetchStatus.okey) {
                message.success('授权成功。');
                this.gotoList();
            } else {
                message.error(fetchStatus.message);
            }
        });
    };

    componentDidMount() {
        const body = {
            accountId: this.state.accountId,
        };

        fetchAccountRolesOnAuthorize(body).then(({ fetchStatus, account, selectedRoles }) => {
            this.setState({
                fetchStatus,
                account,
                selectedRoles: selectedRoles ?? [],
            });
        });
    }

    renderBody = () => {
        const { fetchStatus, account, selectedRoles, confirmLoading } = this.state;

        if (fetchStatus.loading) {
            return <PageLoading />;
        }
        if (!fetchStatus.okey) {
            return <PageException fetchStatus={fetchStatus} goBack={this.gotoList} />;
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
            <Form labelAlign='left'>
                <FormItem {...FormFlex.w100_lg5_required} label='帐号名'>
                    {account.name}
                </FormItem>
                <FormItem>
                    <ButtonSelectRole
                        selectedRoles={selectedRoles}
                        onChange={(roles) => this.setState({ selectedRoles: roles })}
                        fetchAction={fetchRolesOnAuthorize}
                    />

                    <Table
                        size='middle'
                        columns={columns}
                        dataSource={selectedRoles}
                        pagination={false}
                        rowKey={(record) => record.id}
                        bordered
                    />
                </FormItem>
                <FormItem className='mz-button-group'>
                    <Button type='primary' onClick={this.onFinish} confirmLoading={confirmLoading}>
                        确定
                    </Button>
                    <Button
                        onClick={() => {
                            this.gotoList();
                        }}
                    >
                        取消
                    </Button>
                </FormItem>
            </Form>
        );
    };

    render() {
        return (
            <>
                <div className='mz-page-head'>
                    <div className='title'>帐号 / 授权帐号</div>
                </div>

                <div className='mz-page-content'>
                    <div className='mz-page-content-body'>{this.renderBody()}</div>
                </div>
            </>
        );
    }
}

export default withRouter(AccountAuthorize);
