import React from 'react';
import { Menu } from 'antd';
import { Link } from 'react-router-dom';

export default function NavigationMenu({ activeKey, menus }) {
    const selectedKeys = [activeKey];

    const items = menus.map((menu) => ({
        key: menu.id,
        label: (
            <Link to={menu.path} replace>
                <span className={menu.iconClass} />
                {menu.name}
            </Link>
        ),
    }));

    return <Menu mode='horizontal' selectedKeys={selectedKeys} items={items} className='mz-navigation-menu' />;
}
