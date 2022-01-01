import React from 'react';
import { Layout, Row, Col } from 'antd';
import { GLOBAL_MENUS } from '@/config/globalMenu';
import Logo from '../Logo';
import Logout from '../Logout';
import AccountAvatar from '../AccountAvatar';
import AuthNavigationMenu from '../AuthNavigationMenu';

const { Header } = Layout;

export default function MainHeader({ selectedTopMenuId }) {
    return (
        <Header style={{ width: '100%', backgroundColor: '#051E37', padding: 0, height: '60px', lineHeight: '60px' }}>
            <Row>
                <Col xxl={3} xl={4} lg={5} md={6}>
                    <Logo />
                </Col>
                <Col xxl={18} xl={16} lg={14} md={12}>
                    <AuthNavigationMenu selectedTopMenuId={selectedTopMenuId} topMemus={GLOBAL_MENUS} />
                </Col>
                <Col xxl={3} xl={4} lg={5} md={6} style={{ textAlign: 'right' }}>
                    <AccountAvatar isActive={selectedTopMenuId === ''} />
                    <Logout />
                </Col>
            </Row>
        </Header>
    );
}
