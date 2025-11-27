import { useEffect, useState } from 'react';
import { Button, message, Modal, Spin, Table } from 'antd';

import PageComponent from '@/components/PageComponent';
import { LOADING_FETCH_STATUS } from '@/config/common';
import { getTableLocale } from '@/utils/antd-extension';
import httpRequest from '@/utils/http-request';

export default function RunningLog() {
    const [uFetchStatus, setFetchStatus] = useState(LOADING_FETCH_STATUS);
    const [uLogFiles, setLogFiles] = useState([]);

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

        httpRequest.download(
            `/runninglog/downloadRunningLogFile.action?logname=${logname}`,
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
        const requestBody = {
            url: '/runninglog/fetchRunningLogNames.action',
            data: {},
        };

        httpRequest.get(requestBody).then(({ fetchStatus, logFiles = [] }) => {
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
            title: '最后修改时间',
            dataIndex: 'lastModified',
            key: 'lastModified',
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

    const locale = getTableLocale(uFetchStatus);

    return (
        <PageComponent breadcrumbs={[{ title: '本地日志' }]}>
            <Table
                columns={columns}
                loading={uFetchStatus.loading}
                locale={locale}
                dataSource={uLogFiles}
                rowKey={(record) => record.name}
                size='middle'
                pagination={false}
            />
        </PageComponent>
    );
}
