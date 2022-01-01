import React from 'react';
import NavigationMenu from '@/components/NavigationMenu';
import SessionStore from '@/session/SessionStore';

export default function AuthNavigationMenu({ topMemus, ...others }) {
    const filterAuthMenus = (memus) => {
        const newMenus = JSON.parse(JSON.stringify(memus));

        const authMenus = [];
        for (let i = 0; i < newMenus.length; ++i) {
            const menu = newMenus[i];
            if (SessionStore.hasPermission(menu.id)) {
                authMenus.push(menu);
            }
        }

        return authMenus;
    };

    const authTopMenus = filterAuthMenus(topMemus);

    return <NavigationMenu topMemus={authTopMenus} {...others} />;
}
