import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Layout, Menu } from 'antd';
import FontIcon from '@/components/FontIcon';
import { findSiderMenuId, findOpenMenuKeys } from '@/utils/MenuUtils';

const { Sider } = Layout;
const { SubMenu } = Menu;

export default function Sidebar({ header, footer, siderMenus, path, selectedMenuId, height = '100%' }) {
    if (selectedMenuId === null) {
        selectedMenuId = findSiderMenuId(path, siderMenus);
    }

    const [selectedKeys, setSelectedKeys] = useState(() => (selectedMenuId ? [selectedMenuId] : []));
    const [openKeys, setOpenKeys] = useState(() => findOpenMenuKeys(selectedMenuId, siderMenus));

    const renderMenuItem = (menu) => {
        if (menu.iconFont) {
            return (
                <Menu.Item key={menu.id}>
                    <Link to={menu.path} replace>
                        <FontIcon type={menu.iconFont} style={{ fontSize: '1.2em', verticalAlign: 'text-bottom' }} />
                        {menu.name}
                    </Link>
                </Menu.Item>
            );
        }
        return (
            <Menu.Item key={menu.id} style={{ paddingLeft: '54px' }}>
                <Link to={menu.path} replace>
                    {menu.name}
                </Link>
            </Menu.Item>
        );
    };

    const renderSiderMenu = (siderMenu) => {
        if (undefined === siderMenu.subMenus) {
            return renderMenuItem(siderMenu);
        }
        if (siderMenu.iconFont) {
            return (
                <SubMenu
                    key={siderMenu.id}
                    title={
                        <>
                            <FontIcon
                                type={siderMenu.iconFont}
                                style={{ fontSize: '1.2em', verticalAlign: 'text-bottom' }}
                            />
                            {siderMenu.name}
                        </>
                    }
                >
                    {siderMenu.subMenus.map((subMenu, index) => renderSiderMenu(subMenu))}
                </SubMenu>
            );
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

    return (
        <Sider width={210} className='mz-layout-sider' theme='light'>
            {undefined !== header && <div className='mz-sider-header'>{header}</div>}
            <div style={{ flex: '1 1 0%', overflow: 'hidden auto' }}>
                <Menu
                    mode='inline'
                    selectedKeys={selectedKeys}
                    defaultSelectedKeys={selectedKeys}
                    openKeys={openKeys}
                    onOpenChange={onOpenChange}
                    onSelect={onSelect}
                    style={{ height, borderRight: 'none' }}
                >
                    {siderMenus.map((siderMenu, index) => renderSiderMenu(siderMenu))}
                </Menu>
            </div>
            {undefined !== footer && <div className='mz-sider-footer'>{footer}</div>}
        </Sider>
    );
}
