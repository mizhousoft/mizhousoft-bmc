export function findSiderMenuId(path, siderMenus) {
    for (let i = 0; i < siderMenus.length; ++i) {
        const siderMenu = siderMenus[i];
        if (undefined === siderMenu.subMenus) {
            if (path === siderMenu.path) {
                return siderMenu.id;
            }
        } else {
            for (let j = 0; j < siderMenu.subMenus.length; ++j) {
                const subMenu = siderMenu.subMenus[j];
                if (path === subMenu.path) {
                    return subMenu.id;
                }

                if (subMenu.subMenus) {
                    for (let k = 0; k < subMenu.subMenus.length; ++k) {
                        const childMenu = subMenu.subMenus[k];
                        if (childMenu.path === path) {
                            return childMenu.id;
                        }
                    }
                }
            }
        }
    }

    return null;
}

export function findOpenMenuKeys(selectMenuId, siderMenus) {
    for (let i = 0; i < siderMenus.length; ++i) {
        const siderMenu = siderMenus[i];

        if (siderMenu.subMenus) {
            for (let j = 0; j < siderMenu.subMenus.length; ++j) {
                const subMenu = siderMenu.subMenus[j];
                if (selectMenuId === subMenu.id) {
                    return [siderMenu.id];
                }

                if (subMenu.subMenus) {
                    for (let k = 0; k < subMenu.subMenus.length; ++k) {
                        const childMenu = subMenu.subMenus[k];
                        if (childMenu.id === selectMenuId) {
                            return [siderMenu.id, subMenu.id];
                        }
                    }
                }
            }
        }
    }

    return [];
}
