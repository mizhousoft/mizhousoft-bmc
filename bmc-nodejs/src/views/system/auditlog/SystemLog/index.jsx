import React, { useState, useEffect } from 'react';
import { Table, Form, Input, Button, DatePicker, Row, Col } from 'antd';
import dayjs from 'dayjs';
import { DEFAULT_DATA_PAGE, LOADING_FETCH_STATUS } from '@/constants/common';
import { getTableLocale, PageComponent } from '@/components/UIComponent';
import ViewSystemLog from './ViewSystemLog';
import { fetchSystemLogs } from '../redux/auditLogService';

const FormItem = Form.Item;
const { RangePicker } = DatePicker;

export default function SystemLog() {
    const [form] = Form.useForm();

    const [uFetchStatus, setFetchStatus] = useState(LOADING_FETCH_STATUS);
    const [dataSource, setDataSource] = useState(DEFAULT_DATA_PAGE);
    const [uSearchFilter, setSearchFilter] = useState({
        beginTime: undefined,
        endTime: undefined,
        baseInfo: undefined,
        source: undefined,
    });
    const [uTableFilter, setTableFilter] = useState({
        logLevels: [],
        results: [],
    });

    const fetchList = (pageNumber, pageSize, searchFilter, tableFilter) => {
        const body = {
            pageNumber,
            pageSize,
            ...searchFilter,
            ...tableFilter,
        };

        setSearchFilter(searchFilter);
        setTableFilter(tableFilter);
        setFetchStatus(LOADING_FETCH_STATUS);

        fetchSystemLogs(body).then(({ fetchStatus, dataPage = DEFAULT_DATA_PAGE }) => {
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

        fetchList(pagination.current, pagination.pageSize, uSearchFilter, tableFilter);
    };

    const search = () => {
        const fieldsValue = form.getFieldsValue();

        const searchFilter = { ...fieldsValue };

        const [beginTime, endTime] = searchFilter.timePeriod ?? [];

        delete searchFilter.timePeriod;

        searchFilter.beginTime = beginTime?.format('YYYY-MM-DD HH:mm');
        searchFilter.endTime = endTime?.format('YYYY-MM-DD HH:mm');

        fetchList(dataSource.pageNumber, dataSource.pageSize, searchFilter, uTableFilter);
    };

    const clearFilterForm = () => {
        form.setFieldsValue({
            baseInfo: undefined,
            source: undefined,
            timePeriod: undefined,
        });

        const searchFilter = {
            beginTime: undefined,
            endTime: undefined,
            baseInfo: undefined,
            source: undefined,
        };

        const tableFilter = {
            logLevels: [],
            results: [],
        };

        fetchList(1, DEFAULT_DATA_PAGE.pageSize, searchFilter, tableFilter);
    };

    useEffect(() => {
        fetchList(dataSource.pageNumber, dataSource.pageSize, uSearchFilter, uTableFilter);
    }, []);

    const columns = [
        {
            title: '基本信息',
            dataIndex: 'baseInfo',
            key: 'baseInfo',
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
            filteredValue: uTableFilter.logLevels.length > 0 ? uTableFilter.logLevels : [],
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
            filteredValue: uTableFilter.results.length > 0 ? uTableFilter.results : [],
        },
        {
            title: '详细信息',
            dataIndex: 'detail',
            key: 'detail',
            render: (text, record, index) => <ViewSystemLog systemLog={record} />,
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
        position: ['bottomLeft'],
    };

    const locale = getTableLocale(uFetchStatus);

    let timePeriod = [undefined, undefined];
    if (uSearchFilter.beginTime !== undefined && uSearchFilter.endTime !== undefined) {
        const dateFormat = 'YYYY-MM-DD HH:mm';
        timePeriod = [dayjs(uSearchFilter.beginTime, dateFormat), dayjs(uSearchFilter.endTime, dateFormat)];
    }

    return (
        <PageComponent title='系统日志'>
            <Form
                form={form}
                layout='horizontal'
                labelAlign='left'
                className='mz-filter-form mz-table-header'
                initialValues={{
                    baseInfo: uSearchFilter.baseInfo,
                    source: uSearchFilter.source,
                    timePeriod,
                }}
                labelCol={{ flex: '85px' }}
                wrapperCol={{ style: { paddingRight: '30px' } }}
            >
                <Row>
                    <Col span={12}>
                        <FormItem name='baseInfo' label='基本信息'>
                            <Input autoComplete='off' maxLength='32' />
                        </FormItem>
                    </Col>
                    <Col span={12}>
                        <FormItem name='source' label='来源'>
                            <Input autoComplete='off' maxLength='32' />
                        </FormItem>
                    </Col>
                </Row>
                <Row style={{ marginTop: '10px' }}>
                    <Col span={12}>
                        <FormItem name='timePeriod' label='时间段'>
                            <RangePicker
                                format='YYYY-MM-DD HH:mm'
                                disabledDate={(current) => current && current >= dayjs().endOf('day')}
                            />
                        </FormItem>
                    </Col>
                    <Col span={8} className='mz-button-group'>
                        <Button onClick={search} className='mz-grey-button'>
                            查询
                        </Button>
                        <Button onClick={clearFilterForm}>清理过滤</Button>
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
                onChange={handleTableChange}
            />
        </PageComponent>
    );
}