import { useEffect, useState } from 'react';
import { Button, Col, DatePicker, Form, Input, Row, Table } from 'antd';
import dayjs from 'dayjs';

import PageComponent from '@/components/PageComponent';
import { DEFAULT_DATA_PAGE, LOADING_FETCH_STATUS } from '@/config/common';
import { getTableLocale } from '@/utils/antd-extension';
import httpRequest from '@/utils/http-request';
import ViewSecurityLog from './ViewSecurityLog';

const FormItem = Form.Item;
const { RangePicker } = DatePicker;

export default function SecurityLog() {
    const [form] = Form.useForm();

    const [fetchStatus, setFetchStatus] = useState(LOADING_FETCH_STATUS);
    const [dataSource, setDataSource] = useState(DEFAULT_DATA_PAGE);
    const [searchFilter, setSearchFilter] = useState({
        beginTime: undefined,
        endTime: undefined,
        operation: undefined,
        accountName: undefined,
        terminal: undefined,
    });
    const [tableFilter, setTableFilter] = useState({
        logLevels: [],
        results: [],
    });

    const fetchList = (pageNumber, pageSize, searchFilter, tableFilter) => {
        const requestBody = {
            url: '/auditlog/fetchSecurityLogs.action',
            data: {
                pageNumber,
                pageSize,
                ...searchFilter,
                ...tableFilter,
            },
        };

        httpRequest.post(requestBody).then(({ fetchStatus, dataPage = DEFAULT_DATA_PAGE }) => {
            setSearchFilter(searchFilter);
            setTableFilter(tableFilter);
            setDataSource(dataPage);
            setFetchStatus(fetchStatus);
        });
    };

    const handleTableChange = (pagination, filters, sorter, extra) => {
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

        setFetchStatus(LOADING_FETCH_STATUS);

        fetchList(pagination.current, pagination.pageSize, searchFilter, tableFilter);
    };

    const search = () => {
        const fieldsValue = form.getFieldsValue();

        const searchFilter = { ...fieldsValue };

        const [beginTime, endTime] = searchFilter.timePeriod ?? [];

        delete searchFilter.timePeriod;

        searchFilter.beginTime = beginTime?.format('YYYY-MM-DD HH:mm');
        searchFilter.endTime = endTime?.format('YYYY-MM-DD HH:mm');

        setFetchStatus(LOADING_FETCH_STATUS);

        fetchList(dataSource.pageNumber, dataSource.pageSize, searchFilter, tableFilter);
    };

    const clearFilterForm = () => {
        form.setFieldsValue({
            operation: undefined,
            accountName: undefined,
            terminal: undefined,
            timePeriod: undefined,
        });

        const searchFilter = {
            beginTime: undefined,
            endTime: undefined,
            operation: undefined,
            accountName: undefined,
            terminal: undefined,
        };

        const tableFilter = {
            logLevels: [],
            results: [],
        };

        setFetchStatus(LOADING_FETCH_STATUS);

        fetchList(1, DEFAULT_DATA_PAGE.pageSize, searchFilter, tableFilter);
    };

    useEffect(() => {
        fetchList(dataSource.pageNumber, dataSource.pageSize, searchFilter, tableFilter);
    }, []);

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
            filteredValue: tableFilter.logLevels.length > 0 ? tableFilter.logLevels : [],
        },
        {
            title: '操作帐号',
            dataIndex: 'accountName',
            key: 'accountName',
        },
        {
            title: '操作时间',
            dataIndex: 'creationTimeStr',
            key: 'creationTimeStr',
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
            filteredValue: tableFilter.results.length > 0 ? tableFilter.results : [],
        },
        {
            title: '详细信息',
            dataIndex: 'detail',
            key: 'detail',
            render: (text, record, index) => <ViewSecurityLog securityLog={record} />,
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
        pageSizeOptions: ['10', '20', '30', '40', '50', '100'],
        showTotal: (total) => `总条数： ${total} `,
    };

    const locale = getTableLocale(fetchStatus);

    let timePeriod = [undefined, undefined];
    if (searchFilter.beginTime !== undefined && searchFilter.endTime !== undefined) {
        const dateFormat = 'YYYY-MM-DD HH:mm';
        timePeriod = [dayjs(searchFilter.beginTime, dateFormat), dayjs(searchFilter.endTime, dateFormat)];
    }

    return (
        <PageComponent breadcrumbs={[{ title: '安全日志' }]} lightBodyColor={false}>
            <Form
                form={form}
                layout='horizontal'
                labelAlign='left'
                className='mz-form-light'
                initialValues={{
                    operation: searchFilter.operation,
                    accountName: searchFilter.accountName,
                    terminal: searchFilter.terminal,
                    timePeriod,
                }}
                labelCol={{ flex: '85px' }}
                wrapperCol={{ style: { paddingRight: '30px' } }}
            >
                <Row>
                    <Col span={8}>
                        <FormItem name='operation' label='操作名称'>
                            <Input autoComplete='off' maxLength='32' />
                        </FormItem>
                    </Col>
                    <Col span={8}>
                        <FormItem name='accountName' label='操作帐号'>
                            <Input autoComplete='off' maxLength='32' />
                        </FormItem>
                    </Col>
                    <Col span={8}>
                        <FormItem name='terminal' label='操作终端'>
                            <Input autoComplete='off' maxLength='32' />
                        </FormItem>
                    </Col>
                </Row>
                <Row style={{ marginTop: '10px' }}>
                    <Col span={8}>
                        <FormItem name='timePeriod' label='时间段'>
                            <RangePicker format='YYYY-MM-DD HH:mm' disabledDate={(current) => current && current >= dayjs().endOf('day')} />
                        </FormItem>
                    </Col>
                    <Col span={8} />
                    <Col span={8} className='mz-button-group'>
                        <Button onClick={search}>查询</Button>
                        <Button onClick={clearFilterForm}>清理过滤</Button>
                    </Col>
                </Row>
            </Form>

            <Table
                loading={fetchStatus.loading}
                columns={columns}
                dataSource={dataSource.content}
                pagination={pagination}
                rowKey={(record) => record.id}
                size='middle'
                locale={locale}
                onChange={handleTableChange}
                className='mz-table-light'
            />
        </PageComponent>
    );
}
