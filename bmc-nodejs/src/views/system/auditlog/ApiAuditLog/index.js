import React, { Component } from 'react';
import { Table, Form, Input, Button, DatePicker, Row, Col } from 'antd';
import moment from 'moment';
import { DEFAULT_DATA_PAGE, LOADING_FETCH_STATUS } from '@/constants/common';
import { getTableLocale } from '@/components/UIComponent';
import FormFlex from '@/constants/flex';
import ViewApiLog from './ViewApiLog';
import { fetchApiAuditLogs } from '../redux/auditLogService';

const FormItem = Form.Item;
const { RangePicker } = DatePicker;

class ApiAuditLog extends Component {
    formRef = React.createRef();

    constructor(props) {
        super(props);
        this.state = {
            fetchStatus: LOADING_FETCH_STATUS,
            dataSource: DEFAULT_DATA_PAGE,

            searchFilter: {
                beginTime: undefined,
                endTime: undefined,
                operation: undefined,
                terminal: undefined,
                source: undefined,
            },

            tableFilter: {
                logLevels: [],
                results: [],
            },
        };
    }

    fetchList = (page, searchFilter, tableFilter) => {
        page = page ?? {
            pageNumber: this.state.dataSource.pageNumber,
            pageSize: this.state.dataSource.pageSize,
        };
        searchFilter = searchFilter ?? this.state.searchFilter;
        tableFilter = tableFilter ?? this.state.tableFilter;

        const body = { ...page, ...searchFilter, ...tableFilter };

        this.setState({ fetchStatus: LOADING_FETCH_STATUS, searchFilter, tableFilter });

        fetchApiAuditLogs(body).then(({ fetchStatus, dataPage }) => {
            this.setState({
                fetchStatus,
                dataSource: dataPage ?? DEFAULT_DATA_PAGE,
            });
        });
    };

    handleTableChange = (pagination, filters, sorter, extra) => {
        const tableFilter = {
            logLevels: [],
            results: [],
        };

        if (filters.logLevels) {
            tableFilter.logLevels = filters.logLevels;
        }

        if (filters.results) {
            tableFilter.results = filters.results;
        }

        const page = {
            pageNumber: pagination.current,
            pageSize: pagination.pageSize,
        };

        this.fetchList(page, undefined, tableFilter);
    };

    search = () => {
        const fieldsValue = this.formRef.current.getFieldsValue();

        const searchFilter = { ...fieldsValue };

        const [beginTime, endTime] = searchFilter.timePeriod ?? [];

        delete searchFilter.timePeriod;

        searchFilter.beginTime = beginTime?.format('YYYY-MM-DD HH:mm');
        searchFilter.endTime = endTime?.format('YYYY-MM-DD HH:mm');

        this.fetchList(undefined, searchFilter, undefined);
    };

    clearFilterForm = () => {
        this.formRef.current.setFieldsValue({
            operation: undefined,
            terminal: undefined,
            source: undefined,
            timePeriod: undefined,
        });

        const page = {
            pageNumber: DEFAULT_DATA_PAGE.pageNumber,
            pageSize: DEFAULT_DATA_PAGE.pageSize,
        };

        const searchFilter = {
            beginTime: undefined,
            endTime: undefined,
            operation: undefined,
            terminal: undefined,
            source: undefined,
        };

        const tableFilter = {
            logLevels: [],
            results: [],
        };

        this.fetchList(page, searchFilter, tableFilter);
    };

    componentDidMount() {
        this.fetchList(undefined, undefined, undefined);
    }

    renderHeader = () => {
        const { searchFilter } = this.state;

        let timePeriod = [undefined, undefined];
        if (searchFilter.beginTime !== undefined && searchFilter.endTime !== undefined) {
            const dateFormat = 'YYYY-MM-DD HH:mm';
            timePeriod = [moment(searchFilter.beginTime, dateFormat), moment(searchFilter.endTime, dateFormat)];
        }

        return (
            <Form
                ref={this.formRef}
                layout='horizontal'
                labelAlign='left'
                className='mz-filter-form mz-table-header'
                initialValues={{
                    operation: searchFilter.operation,
                    terminal: searchFilter.terminal,
                    source: searchFilter.source,
                    timePeriod,
                }}
                {...FormFlex.w100_md6_required}
            >
                <Row>
                    <Col span={8}>
                        <FormItem name='operation' label='操作名称'>
                            <Input autoComplete='off' maxLength='32' />
                        </FormItem>
                    </Col>
                    <Col span={8}>
                        <FormItem name='terminal' label='操作终端'>
                            <Input autoComplete='off' maxLength='32' />
                        </FormItem>
                    </Col>
                    <Col span={8} />
                </Row>
                <Row style={{ marginTop: '10px' }}>
                    <Col span={8}>
                        <FormItem name='source' label='来源'>
                            <Input autoComplete='off' maxLength='32' />
                        </FormItem>
                    </Col>
                    <Col span={8}>
                        <FormItem name='timePeriod' label='操作时间'>
                            <RangePicker format='YYYY-MM-DD HH:mm' />
                        </FormItem>
                    </Col>
                    <Col span={8} className='mz-button-group'>
                        <Button onClick={this.search} className='mz-grey-button'>
                            查询
                        </Button>
                        <Button onClick={this.clearFilterForm}>清理过滤</Button>
                    </Col>
                </Row>
            </Form>
        );
    };

    renderTable = () => {
        const { fetchStatus, dataSource, tableFilter } = this.state;

        const columns = [
            {
                title: '操作名称',
                dataIndex: 'operation',
                key: 'operation',
            },
            {
                title: '级别',
                dataIndex: 'logLevel',
                key: 'logLevels',
                filters: [
                    {
                        text: '一般',
                        value: 'Info',
                    },
                    {
                        text: '警告',
                        value: 'Warn',
                    },
                    {
                        text: '危险',
                        value: 'Risk',
                    },
                ],
                filteredValue: tableFilter.logLevels.length > 0 ? tableFilter.logLevels : undefined,
            },
            {
                title: '操作时间',
                dataIndex: 'creationTimeStr',
                key: 'creationTimeStr',
                width: '150px',
            },
            {
                title: '来源',
                dataIndex: 'source',
                key: 'source',
            },
            {
                title: '操作终端',
                dataIndex: 'terminal',
                key: 'terminal',
            },
            {
                title: '操作结果',
                dataIndex: 'resultStr',
                key: 'results',
                filters: [
                    {
                        text: '成功',
                        value: '1',
                    },
                    {
                        text: '失败',
                        value: '2',
                    },
                    {
                        text: '部分成功',
                        value: '3',
                    },
                ],
                filteredValue: tableFilter.results.length > 0 ? tableFilter.results : undefined,
            },
            {
                title: '详细信息',
                dataIndex: 'detail',
                key: 'detail',
                render: (text, record, index) => <ViewApiLog apiLog={record} />,
                width: '20%',
                ellipsis: true,
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
                onChange={this.handleTableChange}
            />
        );
    };

    render() {
        return (
            <>
                <div className='mz-page-head'>
                    <div className='title'>系统日志</div>
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

export default ApiAuditLog;
