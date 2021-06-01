import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { Button, message, Row, Col, Input, Table, Form } from 'antd';
import { DEFAULT_DATA_PAGE, LOADING_FETCH_STATUS } from '@/constants/common';
import { getTableLocale } from '@/components/UIComponent';
import AuthButton from '@/views/components/AuthButton';
import AuthLink from '@/views/components/AuthLink';
import AuthPopconfirm from '@/views/components/AuthPopconfirm';
import { fetchRoles, deleteRole } from '../redux/roleService';

class RoleList extends Component {
    formRef = React.createRef();

    constructor(props) {
        super(props);
        this.state = {
            fetchStatus: LOADING_FETCH_STATUS,
            dataSource: DEFAULT_DATA_PAGE,

            filter: {
                name: undefined,
            },
        };
    }

    gotoNew = () => {
        const { history } = this.props;
        history.push('/role/new');
    };

    deleteRole = (id) => {
        this.setState({ fetchStatus: LOADING_FETCH_STATUS });

        const body = { id };

        deleteRole(body).then(({ fetchStatus }) => {
            if (fetchStatus.okey) {
                message.success('删除角色成功。');
                this.refreshList();
            } else {
                message.error(fetchStatus.message);
                this.setState({ fetchStatus });
            }
        });
    };

    refreshList = () => {
        const { dataSource } = this.state;

        this.fetchList(dataSource.pageNumber, dataSource.pageSize, undefined);
    };

    fetchList = (pageNumber, pageSize, filter) => {
        filter = filter ?? this.state.filter;

        const body = {
            pageNumber,
            pageSize,
            name: filter.name,
        };

        this.setState({ fetchStatus: LOADING_FETCH_STATUS, filter });

        fetchRoles(body).then(({ fetchStatus, dataPage }) => {
            this.setState({
                fetchStatus,
                dataSource: dataPage ?? DEFAULT_DATA_PAGE,
            });
        });
    };

    search = () => {
        const { dataSource, filter } = this.state;

        const fieldsValue = this.formRef.current.getFieldsValue();
        filter.name = fieldsValue.name;

        this.fetchList(1, dataSource.pageSize, filter);
    };

    componentDidMount() {
        this.refreshList();
    }

    renderHeader = () => (
        <Form
            ref={this.formRef}
            labelAlign='left'
            initialValues={{
                name: this.state.filter.name,
            }}
        >
            <Row className='mz-table-header'>
                <Col span={12}>
                    <Form.Item name='name' noStyle>
                        <Input placeholder='输入名称' style={{ width: '300px' }} maxLength={10} />
                    </Form.Item>
                    &nbsp;&nbsp;
                    <Button onClick={this.search} className='mz-grey-button'>
                        查询
                    </Button>
                </Col>
                <Col span={12} style={{ textAlign: 'right' }}>
                    <AuthButton authId='bmc.role.new' onClick={this.gotoNew} type='primary'>
                        增加角色
                    </AuthButton>
                </Col>
            </Row>
        </Form>
    );

    renderTable = () => {
        const { fetchStatus, dataSource } = this.state;

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
                className: 'column-ellipsis column-width-2',
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
                                onConfirm={() => this.deleteRole(record.id)}
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
                    <Row>
                        <Col span={24} className='title'>
                            角色
                        </Col>
                    </Row>
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

export default withRouter(RoleList);
