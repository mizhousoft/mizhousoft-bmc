import React, { PureComponent } from 'react';
import Sidebar from '@/components/Sidebar';
import SessionStore from '@/session/SessionStore';

class AuthSidebar extends PureComponent {
    filterAuthMenus = (siderMenus) => {
        const menus = JSON.parse(JSON.stringify(siderMenus));

        const authMenus = [];
        for (let i = 0; i < menus.length; ++i) {
            const menu = menus[i];
            if (!SessionStore.hasPermission(menu.id)) {
                continue;
            }

            if (menu.subMenus) {
                const authSubMenus = menu.subMenus.filter((subMenu) => SessionStore.hasPermission(subMenu.id));

                if (authSubMenus.length > 0) {
                    menu.subMenus = authSubMenus;
                    authMenus.push(menu);
                }
            } else {
                authMenus.push(menu);
            }
        }

        return authMenus;
    };

    render() {
        const { siderMenus, path, ...others } = this.props;

        const authMenus = this.filterAuthMenus(siderMenus);

        return <Sidebar siderMenus={authMenus} path={path} {...others} />;
    }
}

export default AuthSidebar;
