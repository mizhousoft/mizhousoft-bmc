import React, { Component } from 'react';
import { Tabs, Table } from 'antd';
import { LOADING_FETCH_STATUS } from '@/constants/common';
import { PageLoading, PageException, UnsafeALink } from '@/components/UIComponent';
import { BASENAME } from '@/config/application';
import { fetchRunningLogNames, fetchRunningLogFileNames } from '../redux/auditLogService';

const { TabPane } = Tabs;

class RunningLog extends Component {
    constructor(props) {
        super(props);
        this.state = {
            fetchStatus: LOADING_FETCH_STATUS,

            activeLogname: undefined,
            lognames: [],
            logFiles: [],
        };
    }

    onChange = (value) => {
        this.setState({ fetchStatus: LOADING_FETCH_STATUS, activeLogname: value, logFiles: [] });

        this.fetchRunningLogFiles(value);
    };

    fetchRunningLogFiles = (logname) => {
        const body = {
            logname,
        };

        fetchRunningLogFileNames(body).then(({ fetchStatus, logFiles = [] }) => {
            this.setState({
                fetchStatus,
                logFiles,
            });
        });
    };

    componentDidMount() {
        fetchRunningLogNames().then(({ fetchStatus, activeLogname, lognames = [], logFiles = [] }) => {
            this.setState({
                fetchStatus,
                activeLogname,
                lognames,
                logFiles,
            });
        });
    }

    renderBody = () => {
        const { fetchStatus, lognames, activeLogname, logFiles } = this.state;

        if (fetchStatus.loading) {
            return <PageLoading />;
        }
        if (!fetchStatus.okey) {
            return <PageException fetchStatus={fetchStatus} />;
        }

        const columns = [
            {
                title: '日志文件名称',
                dataIndex: 'name',
                key: 'name',
            },
            {
                title: '文件大小',
                dataIndex: 'size',
                key: 'size',
            },
            {
                title: '操作',
                key: 'action',
                width: '120px',
                className: 'center-action-button',
                render: (text, record) => (
                    <UnsafeALink href={`${BASENAME}/runninglog/downloadRunningLogFile.action?logname=${record.name}`}>
                        下载
                    </UnsafeALink>
                ),
            },
        ];

        const extraActions = <></>;

        return (
            <Tabs hideAdd onChange={this.onChange} activeKey={activeLogname} tabBarExtraContent={extraActions}>
                {lognames.map((logname) => (
                    <TabPane tab={logname} key={logname}>
                        <Table
                            columns={columns}
                            dataSource={logFiles}
                            rowKey={(record) => record.name}
                            size='middle'
                            bordered
                            pagination={false}
                        />
                    </TabPane>
                ))}
            </Tabs>
        );
    };

    render() {
        return (
            <>
                <div className='mz-page-head'>
                    <div className='title'>本地日志</div>
                </div>

                <div className='mz-page-content'>
                    <div className='mz-page-content-body'>{this.renderBody()}</div>
                </div>
            </>
        );
    }
}

export default RunningLog;
