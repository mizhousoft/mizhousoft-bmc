import React from 'react';
import { Link } from 'react-router-dom';
import { Empty, Button, Spin, Modal, Statistic, Breadcrumb, Row, Col } from 'antd';
import { LoadingOutlined } from '@ant-design/icons';
import Exception from '@/components/Exception';

const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;

export function getTableLocale(fetchStatus) {
    let locale = {};

    if (undefined !== fetchStatus && !fetchStatus.okey) {
        locale = { emptyText: <div className='mz-table-error'>{fetchStatus.message}</div> };
    }

    return locale;
}

export function FullPageLoading({ tip = '数据加载中' }) {
    return (
        <div className='mz-page-loading'>
            <div className='spin'>
                <Spin size='large' tip={tip} />
            </div>
        </div>
    );
}

export function FullPageException({ fetchStatus, goBack }) {
    if (!fetchStatus.okey && fetchStatus.statusCode !== 200) {
        return <Exception type={fetchStatus.statusCode} />;
    }

    return (
        <Empty description={fetchStatus.message} className='mz-page-empty'>
            {undefined !== goBack && <Button onClick={goBack}>返回</Button>}
        </Empty>
    );
}

export function PageComponent({
    breadcrumbs = [],
    extra,
    children,
    headContent,
    headStyle = {},
    contentStyle = {},
    bodyStyle = {},
    bodyClass = '',
}) {
    return (
        <>
            {breadcrumbs.length > 0 || headContent ? (
                <div className='mz-page-head' style={headStyle}>
                    <Row>
                        <Col span={20}>
                            <Breadcrumb>
                                {breadcrumbs.map((value) => (
                                    <Breadcrumb.Item key={value}>{value}</Breadcrumb.Item>
                                ))}
                            </Breadcrumb>
                        </Col>
                        <Col span={4} className='extra'>
                            {extra}
                        </Col>
                    </Row>
                    {headContent}
                </div>
            ) : null}

            <div className='mz-page-content' style={contentStyle}>
                <div className={`mz-page-content-body ${bodyClass}`} style={bodyStyle}>
                    {children}
                </div>
            </div>
        </>
    );
}

export function PageLoading({ breadcrumbs = [], tip = '数据加载中' }) {
    if (breadcrumbs.length > 0) {
        return (
            <PageComponent breadcrumbs={breadcrumbs}>
                <FullPageLoading tip={tip} />
            </PageComponent>
        );
    }
    return <FullPageLoading tip={tip} />;
}

export function PageException({ breadcrumbs = [], fetchStatus, goBack }) {
    if (breadcrumbs.length > 0) {
        return (
            <PageComponent breadcrumbs={breadcrumbs}>
                <FullPageException fetchStatus={fetchStatus} goBack={goBack} />
            </PageComponent>
        );
    }
    return <FullPageException fetchStatus={fetchStatus} goBack={goBack} />;
}

export function ModalLoading({ tip }) {
    return (
        <div className='mz-modal-loading'>
            <div className='spin'>
                <Spin size='large' tip={tip} />
            </div>
        </div>
    );
}

export function ModalException({ fetchStatus, goBack }) {
    if (!fetchStatus.okey && fetchStatus.statusCode !== 200) {
        return <Exception type={fetchStatus.statusCode} />;
    }

    return (
        <Empty description={fetchStatus.message} className='mz-page-empty'>
            {undefined !== goBack && <Button onClick={goBack}>返回</Button>}
        </Empty>
    );
}

export function AButton({ title, onClick, style = {}, children }) {
    const child = title ?? children;

    return (
        <Button onClick={onClick} type='link' style={{ padding: '0px', ...style }}>
            {child}
        </Button>
    );
}

export function UnsafeALink({ href, style, children }) {
    return (
        <a href={href} rel='noopener noreferrer' style={style} target='_blank'>
            {children}
        </a>
    );
}

export function SafeLink({ to, style, children }) {
    return (
        <Link to={to} rel='opener' target='_blank' style={style}>
            {children}
        </Link>
    );
}

export function ActionLoading({ visible, content }) {
    return (
        <Modal width='300px' centered maskClosable={false} footer={null} open={visible} destroyOnClose closable={false}>
            <div style={{ textAlign: 'center' }}>
                <Spin indicator={antIcon} style={{ marginRight: '30px' }} />
                {content}
            </div>
        </Modal>
    );
}

export function LinkStatistic({ to, title, value, groupSeparator, suffix }) {
    const valueNode = (number) => <Link to={to}>{number}</Link>;

    return (
        <Statistic title={title} groupSeparator={groupSeparator} suffix={suffix} valueRender={() => valueNode(value)} />
    );
}
