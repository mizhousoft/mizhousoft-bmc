import React, { Component } from 'react';
import { Form, Table, Space } from 'antd';
import { LOADING_FETCH_STATUS } from '@/constants/common';
import { PageLoading, PageException } from '@/components/UIComponent';
import FormFlex from '@/constants/flex';
import PhoneNumberEdit from './PhoneNumberEdit';
import { fetchMyAccountInfo } from '../profileService';

const FormItem = Form.Item;

const columns = [
    {
        title: '角色名',
        dataIndex: 'displayNameCN',
        key: 'displayNameCN',
        width: '25%',
    },
    {
        title: '描述',
        dataIndex: 'descriptionCN',
        key: 'descriptionCN',
    },
];

class MyAccountInfo extends Component {
    constructor(props) {
        super(props);
        this.state = {
            fetchStatus: LOADING_FETCH_STATUS,

            account: undefined,
            roles: [],
        };
    }

    fetchPageData = () => {
        this.setState({ fetchStatus: LOADING_FETCH_STATUS });

        fetchMyAccountInfo().then(({ fetchStatus, account, roles }) => {
            this.setState({
                fetchStatus,
                account,
                roles: roles ?? [],
            });
        });
    };

    componentDidMount() {
        this.fetchPageData();
    }

    renderBody() {
        const { fetchStatus, account, roles } = this.state;

        if (fetchStatus.loading) {
            return <PageLoading />;
        }
        if (!fetchStatus.okey) {
            return <PageException fetchStatus={fetchStatus} />;
        }

        return (
            <Form labelAlign='left'>
                <FormItem {...FormFlex.w100_lg4_required} label='帐号名'>
                    {account.name}
                </FormItem>
                <FormItem {...FormFlex.w100_lg4_required} label='手机号'>
                    <Space>
                        <span>{account.phoneNumber}</span>

                        <PhoneNumberEdit account={account} fetchPageData={this.fetchPageData} />
                    </Space>
                </FormItem>

                <FormItem>
                    <div style={{ marginBottom: '13px' }}>所属角色：</div>
                    <Table
                        size='middle'
                        columns={columns}
                        dataSource={roles}
                        rowKey={(record) => record.id}
                        pagination={false}
                        bordered
                    />
                </FormItem>
            </Form>
        );
    }

    render() {
        return (
            <>
                <div className='mz-page-head'>
                    <div className='title'>我的帐号</div>
                </div>

                <div className='mz-page-content'>
                    <div className='mz-page-content-body'>{this.renderBody()}</div>
                </div>
            </>
        );
    }
}

export default MyAccountInfo;
