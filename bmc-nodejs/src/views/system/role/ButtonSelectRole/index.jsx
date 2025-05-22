import { useState } from 'react';
import { Button, Modal, Table } from 'antd';

import { DEFAULT_DATA_PAGE, LOADING_FETCH_STATUS } from '@/config/common';
import { getTableLocale } from '@/utils/antd-extension';
import httpRequest from '@/utils/http-request';

export default function ButtonSelectRole({ selectedRoles = [], fetchAction, onChange }) {
    const [visible, setVisible] = useState(false);
    const [uFetchStatus, setFetchStatus] = useState(LOADING_FETCH_STATUS);
    const [dataSource, setDataSource] = useState([]);
    const [uSelectedRoles, setSelectedRoles] = useState([]);

    const fetchList = (pageNumber, pageSize) => {
        setFetchStatus(LOADING_FETCH_STATUS);

        const requestBody = {
            url: fetchAction,
            data: {
                pageSize,
                pageNumber,
            },
        };

        httpRequest.get(requestBody).then(({ fetchStatus, dataPage = DEFAULT_DATA_PAGE }) => {
            setDataSource(dataPage);
            setFetchStatus(fetchStatus);
        });
    };

    const showModal = () => {
        setSelectedRoles(selectedRoles);
        setVisible(true);

        fetchList(dataSource.pageNumber, dataSource.pageSize);
    };

    const onFinish = () => {
        setVisible(false);

        onChange(uSelectedRoles);
    };

    const columns = [
        {
            title: '角色名',
            dataIndex: 'displayNameCN',
            key: 'displayNameCN',
            width: 200,
        },
        {
            title: '描述',
            dataIndex: 'descriptionCN',
            key: 'descriptionCN',
        },
    ];

    const pagination = {
        size: 'middle',
        total: dataSource.totalNumber,
        pageSize: dataSource.pageSize,
        current: dataSource.pageNumber,
        showQuickJumper: true,
        showTotal: (total) => `总条数： ${total} `,
        onChange: (page, pageSize) => fetchList(page, pageSize),
    };

    const selectedRoleIds = uSelectedRoles.map((role, key, roles) => role.id);

    const rowSelection = {
        onSelect: (record, selected, selectedRows) => {
            let roles = [...uSelectedRoles];
            if (selected) {
                roles.push(record);
            } else {
                roles = roles.filter((item) => item.id !== record.id);
            }

            setSelectedRoles(roles);
        },
        onSelectAll: (selected, selectedRows, changeRows) => {
            let roles = [...uSelectedRoles];

            if (selected) {
                changeRows.forEach((row) => {
                    const matchList = roles.filter((item) => item.id === row.id);
                    if (matchList.length === 0) {
                        roles.push(row);
                    }
                });
            } else {
                changeRows.forEach((row) => {
                    const matchList = roles.filter((item) => item.id === row.id);
                    if (matchList.length > 0) {
                        roles = roles.filter((item) => item.id !== row.id);
                    }
                });
            }

            setSelectedRoles(roles);
        },
        selectedRowKeys: selectedRoleIds,
    };

    const locale = getTableLocale(uFetchStatus);

    return (
        <>
            <div style={{ marginBottom: '15px' }}>
                <Button onClick={showModal}>选择所属角色</Button>
            </div>

            <Modal
                title='选择角色'
                open={visible}
                closable={false}
                maskClosable={false}
                onCancel={() => setVisible(false)}
                width='50%'
                footer={null}
                centered
                destroyOnHidden
                className='mz-modal'
                bodyStyle={{ minHeight: '100px' }}
            >
                <Table
                    loading={uFetchStatus.loading}
                    columns={columns}
                    dataSource={dataSource.content}
                    pagination={pagination}
                    rowKey={(record) => record.id}
                    rowSelection={rowSelection}
                    size='middle'
                    locale={locale}
                />
                <div className='mz-button-group center'>
                    <Button type='primary' onClick={onFinish}>
                        确认
                    </Button>
                    <Button onClick={() => setVisible(false)}>取消</Button>
                </div>
            </Modal>
        </>
    );
}
