import React from 'react';
import { Empty, Result } from 'antd';

import FontIcon from '@/components/FontIcon';

export function getTableLocale(fetchStatus) {
    let locale = {};

    if (undefined !== fetchStatus && !fetchStatus.okey) {
        locale = { emptyText: <div style={{ color: '#f04134' }}>{fetchStatus.message}</div> };
    }

    return locale;
}

export function getListLocale(fetchStatus) {
    let locale = { emptyText: <Empty image={Empty.PRESENTED_IMAGE_SIMPLE} /> };

    if (undefined !== fetchStatus && !fetchStatus.okey) {
        locale = {
            emptyText: <Result status='error' subTitle={fetchStatus.message} />,
        };
    }

    return locale;
}

export function decorateTreeNodeIcon(node) {
    node.icon = <FontIcon type={node.icon} style={{ fontSize: '1.3em', verticalAlign: 'text-bottom' }} />;

    if (node.children) {
        node.children.forEach((child) => {
            decorateTreeNodeIcon(child);
        });
    }
}

export function disableTreeNode(node, disabledIds) {
    for (let i = 0; i < disabledIds.length; ++i) {
        const disabledId = `${disabledIds[i]}`;
        if (node.key === disabledId) {
            node.disabled = true;
            break;
        }
    }

    if (node.children) {
        node.children.forEach((child) => {
            disableTreeNode(child, disabledIds);
        });
    }
}

export function mergeTableRowSpan(list, attribute) {
    list.forEach((item) => {
        item.rowSpan = undefined;
    });

    for (let i = 1; i < list.length; ++i) {
        for (let j = i + 1; j < list.length; ++j) {
            const a = list[i];
            const b = list[j];

            if (a[attribute] === b[attribute]) {
                if (list[i].rowSpan) {
                    list[i].rowSpan += 1;
                } else {
                    list[i].rowSpan = 2;
                }

                list[j].rowSpan = 0;

                if (j === list.length - 1) {
                    i = j;
                    break;
                }
            } else {
                i = j - 1;
                break;
            }
        }
    }
}
