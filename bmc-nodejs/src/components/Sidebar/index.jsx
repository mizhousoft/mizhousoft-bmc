import React, { useEffect, useState } from 'react';
import { Affix, Layout, Menu } from 'antd';
import { Link } from 'react-router-dom';

import FontIcon from '@/components/FontIcon';
import { findOpenMenuKeys, findSiderMenuId } from '@/utils/MenuUtils';

const { Sider } = Layout;

export default function Sidebar({ header, footer, siderMenus, path, selectedMenuId, height = '100%' }) {
    if (selectedMenuId === null) {
        selectedMenuId = findSiderMenuId(path, siderMenus);
    }

    const [selectedKeys, setSelectedKeys] = useState(() => (selectedMenuId ? [selectedMenuId] : []));
    const [openKeys, setOpenKeys] = useState(() => findOpenMenuKeys(selectedMenuId, siderMenus));

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
        if (undefined === siderMenu.subMenus) {
            return buildMenuItem(siderMenu);
        }
        if (siderMenu.iconFont) {
            const children = siderMenu.subMenus.map((subMenu) => buildSiderMenuItems(subMenu));

            return {
                key: siderMenu.id,
                label: (
                    <>
                        <FontIcon
                            type={siderMenu.iconFont}
                            style={{ fontSize: '1.2em', verticalAlign: 'text-bottom' }}
                        />
                        {siderMenu.name}
                    </>
                ),
                children,
            };
        }
    };

    const getParentMenuKeys = () => {
        const subMenuKeys = [];
        siderMenus.forEach((item) => {
            subMenuKeys.push(item.id);

            if (undefined !== item.subMenus) {
                const list = item.subMenus;
                list.forEach((el) => {
                    if (undefined !== el.subMenus) {
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
            const resultKeys = findOpenMenuKeys(latestOpenKey, siderMenus);
            setOpenKeys([...resultKeys, latestOpenKey]);
        }
    };

    const onSelect = (item) => {
        const selectKey = item.key;
        const openMenuKeys = findOpenMenuKeys(selectKey, siderMenus);

        setSelectedKeys([selectKey]);
        setOpenKeys(openMenuKeys);
    };

    useEffect(() => {
        const menuIds = selectedMenuId ? [selectedMenuId] : [];
        setSelectedKeys(menuIds);

        const keys = findOpenMenuKeys(selectedMenuId, siderMenus);
        setOpenKeys(keys);
    }, [selectedMenuId]);

    const menuItems = siderMenus.map((siderMenu) => buildSiderMenuItems(siderMenu));

    return (
        <Sider width={210} className='mz-sider' theme='light'>
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
