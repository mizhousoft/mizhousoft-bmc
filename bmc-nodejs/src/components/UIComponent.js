import React from 'react';
import { Link } from 'react-router-dom';
import { Empty, Button, Spin } from 'antd';
import Exception from '@/components/Exception';

export function getTableLocale(fetchStatus) {
    let locale = {};

    if (undefined !== fetchStatus && !fetchStatus.okey) {
        locale = { emptyText: <div style={{ color: '#f04134' }}>{fetchStatus.message}</div> };
    }

    return locale;
}

export function PageLoading({ tip = '数据加载中' }) {
    return (
        <div className='mz-page-loading'>
            <div className='spin'>
                <Spin size='large' tip={tip} />
            </div>
        </div>
    );
}

export function PageException({ fetchStatus, goBack }) {
    if (!fetchStatus.okey && fetchStatus.statusCode !== 200) {
        return <Exception type={fetchStatus.statusCode} />;
    }

    return (
        <Empty description={fetchStatus.message} className='mz-page-empty'>
            {undefined !== goBack && <Button onClick={goBack}>返回</Button>}
        </Empty>
    );
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

export function AButton({ title, onClick, style, children }) {
    const child = title ?? children;

    return (
        <a href='javascript:void(0)' rel='noopener noreferrer' onClick={onClick} style={style}>
            {child}
        </a>
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
