import React from 'react';
import { Col, Layout, Row } from 'antd';

import AccountAvatar from '@/components-biz/AccountAvatar';
import Logo from '@/components-biz/Logo';
import Logout from '@/components-biz/Logout';
import NavigationMenu from '@/components/NavigationMenu';

const { Header } = Layout;

export default function NavigationHeader({ activeKey, menus = [] }) {
    return (
        <Header className='mz-main-header'>
            <Row>
                <Col xxl={3} xl={4} lg={5} md={6}>
                    <Logo />
                </Col>
                <Col xxl={18} xl={16} lg={14} md={12}>
                    <NavigationMenu activeKey={activeKey} menus={menus} />
                </Col>
                <Col xxl={3} xl={4} lg={5} md={6} className='right'>
                    <AccountAvatar isActive={activeKey === ''} />
                    <Logout />
                </Col>
            </Row>
        </Header>
    );
}
