import React, { PureComponent } from 'react';
import { Layout, Row, Col } from 'antd';
import { GLOBAL_MENUS } from '@/config/globalMenu';
import Logo from '../Logo';
import Logout from '../Logout';
import AccountAvatar from '../AccountAvatar';
import AuthNavigationMenu from '../AuthNavigationMenu';

const { Header } = Layout;

class MainHeader extends PureComponent {
    render() {
        const { selectedTopMenuId } = this.props;

        return (
            <Header className='header' style={{ width: '100%', backgroundColor: '#051E37', padding: 0 }}>
                <Row>
                    <Col xxl={3} xl={4} lg={5} md={6}>
                        <Logo />
                    </Col>
                    <Col xxl={17} xl={15} lg={13} md={11}>
                        <AuthNavigationMenu selectedTopMenuId={selectedTopMenuId} topMemus={GLOBAL_MENUS} />
                    </Col>
                    <Col xxl={4} xl={5} lg={6} md={7} style={{ textAlign: 'right' }}>
                        <AccountAvatar isActive={selectedTopMenuId === ''} />
                        <Logout />
                    </Col>
                </Row>
            </Header>
        );
    }
}

export default MainHeader;
