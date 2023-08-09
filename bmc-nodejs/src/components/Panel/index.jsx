import React from 'react';
import { Row, Col } from 'antd';

import './index.css';

export default function Panel({ title, extra, children, bodyStyle = {}, style = {} }) {
    return (
        <div className='mz-panel' style={style}>
            <Row className='mz-panel-header'>
                <Col span={12} className='mz-panel-title'>
                    {title}
                </Col>
                <Col span={12} className='mz-panel-extra'>
                    {extra}
                </Col>
            </Row>
            <div className='mz-panel-body' style={bodyStyle}>
                {children}
            </div>
        </div>
    );
}
