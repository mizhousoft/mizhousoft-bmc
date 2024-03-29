import React from 'react';
import { Breadcrumb, Col, Row } from 'antd';

export default function PageComponent({
    breadcrumbs = [],
    extra,
    children,
    headContent,
    headStyle = {},
    contentStyle = {},
    bodyStyle = {},
    bodyClass = '',
    lightBodyColor = true,
}) {
    const bodyTheme = lightBodyColor ? 'light-theme' : '';

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
                <div className={`mz-page-content-body ${bodyClass} ${bodyTheme}`} style={bodyStyle}>
                    {children}
                </div>
            </div>
        </>
    );
}
