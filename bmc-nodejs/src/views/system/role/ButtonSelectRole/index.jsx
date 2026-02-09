import { useState } from 'react';
import { Button, Modal, Table } from 'antd';

import { DEFAULT_DATA_PAGE, LOADING_FETCH_STATUS } from '@/config/common';
import { getTableLocale } from '@/utils/antd-extension';
import httpRequest from '@/utils/http-request';

export default function ButtonSelectRole({ selectedRoles = [], fetchRequestPath, onChange }) {
    const [visible, setVisible] = useState(false);
    const [fetchStatus, setFetchStatus] = useState(LOADING_FETCH_STATUS);
    const [dataSource, setDataSource] = useState([]);
    const [selectedItems, setSelectedItems] = useState([]);

    const fetchList = (pageNumber, pageSize) => {
        setFetchStatus(LOADING_FETCH_STATUS);

        const requestBody = {
            url: fetchRequestPath,
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
        setSelectedItems(selectedRoles);
        setVisible(true);

        fetchList(dataSource.pageNumber, dataSource.pageSize);
    };

    const onFinish = () => {
        setVisible(false);

        onChange(selectedItems);
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

    const selectedRoleIds = selectedItems.map((role, key, roles) => role.id);

    const rowSelection = {
        onSelect: (record, selected, selectedRows) => {
            let roles = [...selectedItems];
            if (selected) {
                roles.push(record);
            } else {
                roles = roles.filter((item) => item.id !== record.id);
            }

            setSelectedItems(roles);
        },
        onSelectAll: (selected, selectedRows, changeRows) => {
            let roles = [...selectedItems];

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

            setSelectedItems(roles);
        },
        selectedRowKeys: selectedRoleIds,
    };

    const locale = getTableLocale(fetchStatus);

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
                styles={{
                    body: {
                        minHeight: '100px',
                    },
                }}
            >
                <Table
                    loading={fetchStatus.loading}
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
