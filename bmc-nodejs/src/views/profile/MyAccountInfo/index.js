import React, { useState, useEffect } from 'react';
import { Form, Table, Space } from 'antd';
import { LOADING_FETCH_STATUS } from '@/constants/common';
import { PageLoading, PageException, PageComponent } from '@/components/UIComponent';
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

export default function MyAccountInfo() {
    const [uFetchStatus, setFetchStatus] = useState(LOADING_FETCH_STATUS);
    const [uAccount, setAccount] = useState(undefined);
    const [uRoles, setRoles] = useState([]);

    const fetchPageData = () => {
        setFetchStatus(LOADING_FETCH_STATUS);

        fetchMyAccountInfo().then(({ fetchStatus, account, roles = [] }) => {
            setRoles(roles);
            setAccount(account);
            setFetchStatus(fetchStatus);
        });
    };

    useEffect(() => {
        fetchPageData();
    }, []);

    const pageTitle = '我的帐号';

    if (uFetchStatus.loading) {
        return <PageLoading title={pageTitle} />;
    }
    if (!uFetchStatus.okey) {
        return <PageException title={pageTitle} fetchStatus={uFetchStatus} />;
    }

    return (
        <PageComponent title={pageTitle}>
            <Form labelAlign='left'>
                <FormItem {...FormFlex.w100_lg4_required} label='帐号名'>
                    {uAccount.name}
                </FormItem>
                <FormItem {...FormFlex.w100_lg4_required} label='手机号'>
                    <Space>
                        <span>{uAccount.phoneNumber}</span>

                        <PhoneNumberEdit account={uAccount} fetchPageData={fetchPageData} />
                    </Space>
                </FormItem>

                <FormItem>
                    <div style={{ marginBottom: '13px' }}>所属角色：</div>
                    <Table
                        size='middle'
                        columns={columns}
                        dataSource={uRoles}
                        rowKey={(record) => record.id}
                        pagination={false}
                        bordered
                    />
                </FormItem>
            </Form>
        </PageComponent>
    );
}
