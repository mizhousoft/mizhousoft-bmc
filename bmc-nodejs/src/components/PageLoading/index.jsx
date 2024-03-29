import React from 'react';
import { Spin } from 'antd';

import PageComponent from '@/components/PageComponent';

function InnerLoading({ tip = '数据加载中', height = 'calc(100vh - 60px)' }) {
    return (
        <div className='mz-page-loading' style={{ height }}>
            <div className='spin'>
                <Spin size='large' tip={tip} />
            </div>
        </div>
    );
}

export default function PageLoading({ tip = '数据加载中', breadcrumbs = [], height = 'calc(100vh - 60px)' }) {
    if (breadcrumbs.length > 0) {
        return (
            <PageComponent breadcrumbs={breadcrumbs}>
                <InnerLoading tip={tip} height={height} />
            </PageComponent>
        );
    }

    return <InnerLoading tip={tip} height={height} />;
}
