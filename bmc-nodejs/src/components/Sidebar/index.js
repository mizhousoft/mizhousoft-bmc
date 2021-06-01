import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Layout, Menu } from 'antd';
import FontIcon from '@/components/FontIcon';
import { findSiderMenuId, findOpenMenuKeys } from '@/utils/MenuUtils';

const { Sider } = Layout;
const { SubMenu } = Menu;

class Sidebar extends Component {
    constructor(props) {
        super(props);

        const { siderMenus, path } = props;
        let { selectedMenuId } = props;

        if (selectedMenuId === null) {
            selectedMenuId = findSiderMenuId(path, siderMenus);
        }

        const selectedKeys = selectedMenuId ? [selectedMenuId] : [];
        const openKeys = findOpenMenuKeys(selectedMenuId, siderMenus);

        this.state = {
            selectedKeys,
            openKeys,
        };
    }

    static getDerivedStateFromProps(nextProps, prevState) {
        const { selectedMenuId } = nextProps;
        const { selectedKeys } = prevState;

        if (!selectedKeys?.includes(selectedMenuId)) {
            const openKeys = findOpenMenuKeys(selectedMenuId, nextProps.siderMenus);
            return {
                selectedKeys: [selectedMenuId],
                openKeys,
            };
        }
    }

    renderMenuItem = (menu) => {
        if (menu.iconFont) {
            return (
                <Menu.Item key={menu.id}>
                    <Link to={menu.path} replace>
                        <FontIcon type={menu.iconFont} style={{ fontSize: '1.2em' }} /> {menu.name}
                    </Link>
                </Menu.Item>
            );
        }
        return (
            <Menu.Item key={menu.id}>
                <Link to={menu.path} replace>
                    {menu.name}
                </Link>
            </Menu.Item>
        );
    };

    renderSiderMenu = (siderMenu) => {
        if (undefined === siderMenu.subMenus) {
            return this.renderMenuItem(siderMenu);
        }
        if (siderMenu.iconFont) {
            return (
                <SubMenu
                    key={siderMenu.id}
                    title={
                        <span>
                            <FontIcon type={siderMenu.iconFont} style={{ fontSize: '1.2em' }} />
                            {siderMenu.name}
                        </span>
                    }
                >
                    {siderMenu.subMenus.map((subMenu, index) => this.renderSiderMenu(subMenu))}
                </SubMenu>
            );
        }
    };

    getParentMenuKeys = () => {
        const { siderMenus } = this.props;

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

    onOpenChange = (keys) => {
        const { openKeys } = this.state;
        const subMenuKeys = this.getParentMenuKeys();

        const latestOpenKey = keys.find((key) => openKeys.indexOf(key) === -1);
        if (subMenuKeys.indexOf(latestOpenKey) === -1) {
            this.setState({ openKeys: keys });
        } else if (undefined === latestOpenKey) {
            this.setState({ openKeys: [] });
        } else {
            const resultKeys = findOpenMenuKeys(latestOpenKey, this.props.siderMenus);
            this.setState({ openKeys: [...resultKeys, latestOpenKey] });
        }
    };

    onSelect = (item) => {
        const selectKey = item.key;
        const openKeys = findOpenMenuKeys(selectKey, this.props.siderMenus);

        this.setState({ selectedKeys: [selectKey], openKeys });
    };

    render() {
        const { siderMenus } = this.props;
        const { height = '100%' } = this.props;

        return (
            <Sider width={210} className='mz-layout-sider' theme='light'>
                <Menu
                    mode='inline'
                    selectedKeys={this.state.selectedKeys}
                    defaultSelectedKeys={this.state.selectedKeys}
                    openKeys={this.state.openKeys}
                    onOpenChange={this.onOpenChange}
                    onSelect={this.onSelect}
                    style={{ height, borderRight: 'none' }}
                >
                    {siderMenus.map((siderMenu, index) => this.renderSiderMenu(siderMenu))}
                </Menu>
            </Sider>
        );
    }
}

export default Sidebar;
