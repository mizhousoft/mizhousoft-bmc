import React, { PureComponent } from 'react';
import { Layout, Row, Col } from 'antd';
import Logo from '../Logo';

const { Header } = Layout;

class EmptyHeader extends PureComponent {
    render() {
        return (
            <Header className='header' style={{ width: '100%', backgroundColor: '#2a303c', padding: 0 }}>
                <Row>
                    <Col xxl={4} xl={5} lg={6} md={7}>
                        <Logo />
                    </Col>
                    <Col xxl={20} xl={19} lg={18} md={17} />
                </Row>
            </Header>
        );
    }
}

export default EmptyHeader;
