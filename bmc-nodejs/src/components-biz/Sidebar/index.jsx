import React, { useEffect, useState } from 'react';
import { Affix, Layout, Menu } from 'antd';
import { Link } from 'react-router-dom';

import FontIcon from '@/components/FontIcon';
import menuUtils from '@/utils/menu-utils';

import './index.css';

const { Sider } = Layout;

export default function Sidebar({ header, footer, siderMenus, path, activeKey, height = '100%' }) {
    if (activeKey === null) {
        activeKey = findSiderMenuId(path, siderMenus);
    }

    const [selectedKeys, setSelectedKeys] = useState(() => (activeKey ? [activeKey] : []));
    const [openKeys, setOpenKeys] = useState(() => menuUtils.findOpenMenuKeys(activeKey, siderMenus));

    const buildMenuItem = (menu) => {
        if (menu.iconFont) {
            return {
                key: menu.id,
                label: (
                    <Link to={menu.path} replace>
                        <FontIcon type={menu.iconFont} style={{ fontSize: '1.2em', verticalAlign: 'text-bottom' }} />
                        {menu.name}
                    </Link>
                ),
            };
        }

        return {
            key: menu.id,
            style: { paddingLeft: '54px' },
            label: (
                <Link to={menu.path} replace>
                    {menu.name}
                </Link>
            ),
        };
    };

    const buildSiderMenuItems = (siderMenu) => {
        if (undefined === siderMenu.children) {
            return buildMenuItem(siderMenu);
        }
        if (siderMenu.iconFont) {
            const childrenItems = siderMenu.children.map((childMenu) => buildSiderMenuItems(childMenu));

            return {
                key: siderMenu.id,
                label: (
                    <>
                        <FontIcon type={siderMenu.iconFont} style={{ fontSize: '1.2em', verticalAlign: 'text-bottom' }} />
                        {siderMenu.name}
                    </>
                ),
                children: childrenItems,
            };
        }
    };

    const getParentMenuKeys = () => {
        const subMenuKeys = [];
        siderMenus.forEach((item) => {
            subMenuKeys.push(item.id);

            if (undefined !== item.children) {
                const list = item.children;
                list.forEach((el) => {
                    if (undefined !== el.children) {
                        subMenuKeys.push(el.id);
                    }
                });
            }
        });

        return subMenuKeys;
    };

    const onOpenChange = (keys) => {
        const subMenuKeys = getParentMenuKeys();

        const latestOpenKey = keys.find((key) => openKeys.indexOf(key) === -1);
        if (subMenuKeys.indexOf(latestOpenKey) === -1) {
            setOpenKeys(keys);
        } else if (undefined === latestOpenKey) {
            setOpenKeys([]);
        } else {
            const resultKeys = menuUtils.findOpenMenuKeys(latestOpenKey, siderMenus);
            setOpenKeys([...resultKeys, latestOpenKey]);
        }
    };

    const onSelect = (item) => {
        const selectKey = item.key;
        const openMenuKeys = menuUtils.findOpenMenuKeys(selectKey, siderMenus);

        setSelectedKeys([selectKey]);
        setOpenKeys(openMenuKeys);
    };

    useEffect(() => {
        const menuIds = activeKey ? [activeKey] : [];
        setSelectedKeys(menuIds);

        const keys = menuUtils.findOpenMenuKeys(activeKey, siderMenus);
        setOpenKeys(keys);
    }, [activeKey]);

    const menuItems = siderMenus.map((siderMenu) => buildSiderMenuItems(siderMenu));

    return (
        <Sider width={206} className='mz-sider' theme='light'>
            {undefined !== header && <div className='mz-sider-header'>{header}</div>}
            <div className='mz-sider-body'>
                <Menu
                    mode='inline'
                    selectedKeys={selectedKeys}
                    defaultSelectedKeys={selectedKeys}
                    openKeys={openKeys}
                    onOpenChange={onOpenChange}
                    onSelect={onSelect}
                    style={{ height, borderRight: 'none' }}
                    items={menuItems}
                />
            </div>
            {undefined !== footer && (
                <Affix offsetBottom={0}>
                    <div className='mz-sider-footer'>{footer}</div>
                </Affix>
            )}
        </Sider>
    );
}
