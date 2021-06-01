import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Menu } from 'antd';
import SessionStore from '@/session/SessionStore';

class NavigationMenu extends Component {
    getTopMenuFirstPath = (topMenu) => {
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

    renderMenu = (topMenu) => {
        const path = this.getTopMenuFirstPath(topMenu);

        return (
            <Menu.Item key={topMenu.id}>
                <Link to={path} replace>
                    {topMenu.name}
                </Link>
            </Menu.Item>
        );
    };

    render() {
        const { selectedTopMenuId, topMemus } = this.props;
        const selectedKeys = [selectedTopMenuId];

        return (
            <Menu
                mode='horizontal'
                defaultSelectedKeys={selectedKeys}
                style={{ backgroundColor: '#051E37' }}
                className='header-menu'
            >
                {topMemus.map((topMemu, index) => this.renderMenu(topMemu))}
            </Menu>
        );
    }
}

export default NavigationMenu;
