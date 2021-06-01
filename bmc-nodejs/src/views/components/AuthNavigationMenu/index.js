import React, { PureComponent } from 'react';
import NavigationMenu from '@/components/NavigationMenu';
import SessionStore from '@/session/SessionStore';

class AuthNavigationMenu extends PureComponent {
    filterAuthMenus = (topMemus) => {
        const menus = JSON.parse(JSON.stringify(topMemus));

        const authMenus = [];
        for (let i = 0; i < menus.length; ++i) {
            const menu = menus[i];
            if (SessionStore.hasPermission(menu.id)) {
                authMenus.push(menu);
            }
        }

        return authMenus;
    };

    render() {
        const { topMemus, ...others } = this.props;

        const authTopMenus = this.filterAuthMenus(topMemus);

        return <NavigationMenu topMemus={authTopMenus} {...others} />;
    }
}

export default AuthNavigationMenu;
