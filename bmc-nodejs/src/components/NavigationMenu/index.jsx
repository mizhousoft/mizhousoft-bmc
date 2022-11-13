import React from 'react';
import { Link } from 'react-router-dom';
import { Menu } from 'antd';
import SessionStore from '@/session/SessionStore';

export default function NavigationMenu({ selectedTopMenuId, topMemus }) {
    const getTopMenuFirstPath = (topMenu) => {
        if (topMenu.path) {
            return topMenu.path;
        }
        if (topMenu.subMenus) {
            for (let j = 0; j < topMenu.subMenus.length; ++j) {
                const subMenu = topMenu.subMenus[j];
                if (!SessionStore.hasPermission(subMenu.id)) {
                    continue;
                }

                if (subMenu.path) {
                    return subMenu.path;
                }

                if (!subMenu.subMenus) {
                    continue;
                }

                for (let k = 0; k < subMenu.subMenus.length; ++k) {
                    const childMenu = subMenu.subMenus[k];
                    if (!SessionStore.hasPermission(childMenu.id)) {
                        continue;
                    }

                    if (childMenu.path) {
                        return childMenu.path;
                    }
                }
            }
        }

        return null;
    };

    const renderMenu = (topMenu) => {
        const path = getTopMenuFirstPath(topMenu);

        return (
            <Menu.Item key={topMenu.id}>
                <Link to={path} replace>
                    <span className={topMenu.iconClass} />
                    {topMenu.name}
                </Link>
            </Menu.Item>
        );
    };

    const selectedKeys = [selectedTopMenuId];

    return (
        <Menu mode='horizontal' selectedKeys={selectedKeys} className='mz-navigation-menu'>
            {topMemus.map((topMemu, index) => renderMenu(topMemu))}
        </Menu>
    );
}
