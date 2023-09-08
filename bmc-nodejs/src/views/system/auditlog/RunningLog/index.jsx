import React, { useEffect, useState } from 'react';
import { Button, Modal, Spin, Table, Tabs } from 'antd';

import { fetchRunningLogFileNames, fetchRunningLogNames } from '../redux/auditLogService';
import { PageComponent, PageException, PageLoading } from '@/components/UIComponent';
import { BASENAME } from '@/config/application';
import { LOADING_FETCH_STATUS } from '@/constants/common';
import { downloadFile } from '@/utils/request';

export default function RunningLog() {
    const [uFetchStatus, setFetchStatus] = useState(LOADING_FETCH_STATUS);
    const [uActiveLogname, setActiveLogname] = useState(undefined);
    const [uLognames, setLognames] = useState([]);
    const [uLogFiles, setLogFiles] = useState([]);

    const fetchRunningLogFiles = (logname) => {
        const body = {
            logname,
        };

        fetchRunningLogFileNames(body).then(({ fetchStatus, logFiles = [] }) => {
            setLogFiles(logFiles);
            setFetchStatus(fetchStatus);
        });
    };

    const onChange = (value) => {
        setActiveLogname(value);
        setLogFiles([]);
        setFetchStatus(LOADING_FETCH_STATUS);

        fetchRunningLogFiles(value);
    };

    const downloadFileAction = (logname) => {
        const modal = Modal.info({
            content: (
                <Spin tip='正在下载中...'>
                    <div />
                </Spin>
            ),
            footer: null,
            className: 'mz-modal-confirm-loading',
            centered: true,
        });

        downloadFile(
            `${BASENAME}/runninglog/downloadRunningLogFile.action?logname=${logname}`,
            logname,
            () => {
                modal.destroy();
            },
            () => {
                modal.destroy();
                message.error('下载文件失败');
            }
        );
    };

    useEffect(() => {
        fetchRunningLogNames().then(({ fetchStatus, activeLogname, lognames = [], logFiles = [] }) => {
            setActiveLogname(activeLogname);
            setLognames(lognames);
            setLogFiles(logFiles);
            setFetchStatus(fetchStatus);
        });
    }, []);

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
                <Button type='link' onClick={() => downloadFileAction(record.name)}>
                    下载
                </Button>
            ),
        },
    ];

    const breadcrumbs = ['本地日志'];

    if (uFetchStatus.loading) {
        return <PageLoading breadcrumbs={breadcrumbs} />;
    }
    if (!uFetchStatus.okey) {
        return <PageException breadcrumbs={breadcrumbs} fetchStatus={uFetchStatus} />;
    }

    const tabItems = [];
    uLognames.forEach((logname) => {
        tabItems.push({
            label: logname,
            key: logname,
            children: (
                <Table
                    columns={columns}
                    dataSource={uLogFiles}
                    rowKey={(record) => record.name}
                    size='middle'
                    pagination={false}
                />
            ),
        });
    });

    return (
        <PageComponent breadcrumbs={breadcrumbs}>
            <Tabs hideAdd onChange={onChange} activeKey={uActiveLogname} items={tabItems} />
        </PageComponent>
    );
}
