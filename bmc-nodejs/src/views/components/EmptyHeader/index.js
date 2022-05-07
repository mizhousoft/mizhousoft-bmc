import React from 'react';
import { Layout, Row, Col } from 'antd';
import Logo from '../Logo';

const { Header } = Layout;

export default function EmptyHeader() {
    return (
        <Header className='mz-empty-header'>
            <Row>
                <Col xxl={4} xl={5} lg={6} md={7}>
                    <Logo />
                </Col>
                <Col xxl={20} xl={19} lg={18} md={17} />
            </Row>
        </Header>
    );
}
