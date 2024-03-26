import React from 'react';

import Sidebar from '@/components/Sidebar';
import SessionStore from '@/store/SessionStore';

export default function AuthSidebar({ siderMenus, path, ...others }) {
    const filterAuthMenus = (menus) => {
        const newMenus = JSON.parse(JSON.stringify(menus));

        const authMenus = [];
        for (let i = 0; i < newMenus.length; ++i) {
            const menu = newMenus[i];
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

    const authMenus = filterAuthMenus(siderMenus);

    return <Sidebar siderMenus={authMenus} path={path} {...others} />;
}
