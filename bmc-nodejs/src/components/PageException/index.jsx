import React from 'react';
import { Button, Result } from 'antd';

import Exception from '@/components/Exception';
import PageComponent from '@/components/PageComponent';

function InnerException({ fetchStatus, goBack, height }) {
    if (!fetchStatus.okey && fetchStatus.statusCode !== 200) {
        return <Exception type={fetchStatus.statusCode} goBack={goBack} height={height} />;
    }

    return (
        <Result
            status='500'
            title='500'
            subTitle={fetchStatus.message}
            extra={
                undefined !== goBack && (
                    <Button type='primary' onClick={goBack}>
                        返回
                    </Button>
                )
            }
        />
    );
}

export default function PageException({ breadcrumbs = [], fetchStatus, goBack, height = 'calc(100vh - 180px)' }) {
    if (breadcrumbs.length > 0) {
        return (
            <PageComponent breadcrumbs={breadcrumbs}>
                <InnerException fetchStatus={fetchStatus} goBack={goBack} height={height} />
            </PageComponent>
        );
    }

    return <InnerException fetchStatus={fetchStatus} goBack={goBack} height={height} />;
}
