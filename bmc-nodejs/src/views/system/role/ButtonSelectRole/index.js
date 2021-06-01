import React, { Component } from 'react';
import { Table, Button, Modal } from 'antd';
import { DEFAULT_DATA_PAGE, LOADING_FETCH_STATUS } from '@/constants/common';
import { getTableLocale } from '@/components/UIComponent';

class ButtonSelectRole extends Component {
    constructor(props) {
        super(props);

        this.state = {
            fetchStatus: LOADING_FETCH_STATUS,
            dataSource: [],

            visible: false,

            selectedRoles: [],
        };
    }

    showModal = () => {
        const { selectedRoles } = this.props;

        this.setState({ visible: true, selectedRoles: selectedRoles ?? [] });

        const { dataSource } = this.state;
        this.fetchList(dataSource.pageNumber, dataSource.pageSize);
    };

    hideModal = () => {
        this.setState({ visible: false });
    };

    fetchList = (pageNumber, pageSize) => {
        const { fetchAction } = this.props;

        const body = {
            pageSize,
            pageNumber,
        };

        this.setState({ fetchStatus: LOADING_FETCH_STATUS });

        fetchAction(body).then(({ fetchStatus, dataPage }) => {
            this.setState({
                fetchStatus,
                dataSource: dataPage ?? DEFAULT_DATA_PAGE,
            });
        });
    };

    onFinish = () => {
        const { onChange } = this.props;

        this.hideModal();

        onChange(this.state.selectedRoles);
    };

    renderModal = () => {
        const { fetchStatus, dataSource, visible, selectedRoles } = this.state;

        if (!visible) {
            return null;
        }

        const selectedRoleIds = selectedRoles.map((role, key, roles) => role.id);

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
            onChange: (page, pageSize) => this.fetchList(page, pageSize),
            position: ['bottomLeft'],
        };

        const rowSelection = {
            onSelect: (record, selected, selectedRows) => {
                let roles = [...selectedRoles];
                if (selected) {
                    roles.push(record);
                } else {
                    roles = roles.filter((item) => item.id !== record.id);
                }

                this.setState({ selectedRoles: roles });
            },
            onSelectAll: (selected, selectedRows, changeRows) => {
                let roles = [...selectedRoles];

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

                this.setState({ selectedRoles: roles });
            },
            selectedRowKeys: selectedRoleIds,
        };

        const locale = getTableLocale(fetchStatus);

        return (
            <Modal
                title='选择角色'
                visible
                closable={false}
                maskClosable={false}
                onCancel={this.hideModal}
                width='50%'
                footer={null}
                centered
                className='mz-modal'
            >
                <Table
                    loading={fetchStatus.loading}
                    columns={columns}
                    dataSource={dataSource.content}
                    pagination={pagination}
                    rowKey={(record) => record.id}
                    rowSelection={rowSelection}
                    size='middle'
                    bordered
                    locale={locale}
                />
                <div className='mz-button-group center'>
                    <Button type='primary' onClick={this.onFinish}>
                        确认
                    </Button>
                    <Button onClick={this.hideModal}>取消</Button>
                </div>
            </Modal>
        );
    };

    render() {
        return (
            <>
                <div style={{ marginBottom: '15px' }}>
                    <Button onClick={this.showModal}>选择所属角色</Button>
                </div>

                {this.renderModal()}
            </>
        );
    }
}

export default ButtonSelectRole;
