import React, { useEffect, useState } from 'react';
import { Button, Col, Form, Input, message, Row, Table } from 'antd';
import { useNavigate } from 'react-router-dom';

import AuthButton from '@/components-biz/AuthButton';
import AuthLink from '@/components-biz/AuthLink';
import AuthPopconfirm from '@/components-biz/AuthPopconfirm';
import PageComponent from '@/components/PageComponent';
import { DEFAULT_DATA_PAGE, LOADING_FETCH_STATUS } from '@/config/common';
import { getTableLocale } from '@/utils/antd-extension';
import httpRequest from '@/utils/http-request';

export default function RoleList() {
    const [form] = Form.useForm();

    const navigate = useNavigate();

    const [uFetchStatus, setFetchStatus] = useState(LOADING_FETCH_STATUS);
    const [dataSource, setDataSource] = useState([]);
    const [uFilter, setFilter] = useState({
        name: undefined,
    });

    const gotoNew = () => {
        navigate('/role/new');
    };

    const fetchList = (pageNumber, pageSize) => {
        setFilter(uFilter);
        setFetchStatus(LOADING_FETCH_STATUS);

        const requestBody = {
            url: '/role/fetchRoles.action',
            data: {
                pageNumber,
                pageSize,
                name: uFilter.name?.trim(),
            },
        };

        httpRequest.post(requestBody).then(({ fetchStatus, dataPage = DEFAULT_DATA_PAGE }) => {
            setDataSource(dataPage);
            setFetchStatus(fetchStatus);
        });
    };

    const refreshList = () => {
        fetchList(dataSource.pageNumber, dataSource.pageSize);
    };

    const search = () => {
        const fieldsValue = form.getFieldsValue();
        uFilter.name = fieldsValue.name;

        fetchList(1, dataSource.pageSize);
    };

    const deleteItem = (id) => {
        setFetchStatus(LOADING_FETCH_STATUS);

        const requestBody = {
            url: '/role/deleteRole.action',
            data: {
                id,
            },
        };

        httpRequest.post(requestBody).then(({ fetchStatus }) => {
            if (fetchStatus.okey) {
                message.success('删除角色成功。');
                refreshList();
            } else {
                message.error(fetchStatus.message);
                setFetchStatus(fetchStatus);
            }
        });
    };

    useEffect(() => {
        refreshList();
    }, []);

    const columns = [
        {
            title: '角色名',
            dataIndex: 'displayNameCN',
            key: 'displayNameCN',
            width: 250,
            render: (text, record, index) => (
                <AuthLink authId='bmc.role.view' childrenVisible to={`/role/view/${record.id}`}>
                    {text}
                </AuthLink>
            ),
        },
        {
            title: '描述',
            dataIndex: 'descriptionCN',
            key: 'descriptionCN',
        },
        {
            title: '操作',
            key: 'action',
            width: 120,
            className: 'mz-a-group',
            render: (text, record) => {
                if (record.type === 1) {
                    return null;
                }

                return (
                    <>
                        <AuthLink authId='bmc.role.edit' to={`/role/edit/${record.id}`}>
                            编辑
                        </AuthLink>

                        <AuthPopconfirm
                            authId='bmc.role.delete'
                            title='你确定要删除该角色吗？'
                            onConfirm={() => deleteItem(record.id)}
                            okText='确认'
                            cancelText='取消'
                            placement='left'
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
        onChange: (page, pageSize) => fetchList(page, pageSize),
    };

    const locale = getTableLocale(uFetchStatus);

    return (
        <PageComponent breadcrumbs={['角色']}>
            <Form
                form={form}
                labelAlign='left'
                initialValues={{
                    name: uFilter.name,
                }}
            >
                <Row className='mz-table-header'>
                    <Col span={12}>
                        <Form.Item name='name' noStyle>
                            <Input placeholder='输入名称' style={{ width: '300px' }} maxLength={10} />
                        </Form.Item>
                        &nbsp;&nbsp;
                        <Button onClick={search}>查询</Button>
                    </Col>
                    <Col span={12} style={{ textAlign: 'right' }}>
                        <AuthButton authId='bmc.role.new' onClick={gotoNew} type='primary'>
                            增加角色
                        </AuthButton>
                    </Col>
                </Row>
            </Form>
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
