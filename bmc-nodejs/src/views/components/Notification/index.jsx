import React, { useEffect, useState } from 'react';
import { Badge, List, Popover, Tabs } from 'antd';
import { useNavigate } from 'react-router-dom';

import FontIcon from '@/components/FontIcon';
import { AButton } from '@/components/UIComponent';
import { BASENAME } from '@/config/application';
import DefaultUserStore from '@/store/DefaultUserStore';
import { addEventListener, removeEventListener } from '@/utils/eventBus';
import { asyncFetch } from '@/utils/request';

let interval = 0;
const PUSHTIME_KEY = 'PUSHTIME_KEY';

export default function Notification() {
    const navigate = useNavigate();

    const [visible, setVisible] = useState(false);
    const [uTodos, setTodos] = useState([]);

    const rediectUrl = (entity) => {
        setVisible(false);
        navigate(entity.viewPath);
    };

    const asyncFetchData = () => {
        asyncFetch({
            url: `${BASENAME}/fetchNotifications.action`,
        }).then(({ todos = [], pushTime, fetchStatus }) => {
            setTodos(todos);

            const time = undefined !== pushTime ? pushTime : new Date().getTime();
            DefaultUserStore.setItem(PUSHTIME_KEY, `${time}`);

            if (fetchStatus.statusCode === 401) {
                clearInterval(interval);
            }
        });
    };

    const fetchNotifications = () => {
        const nowTs = new Date().getTime();

        let pushTime = 0;
        const text = DefaultUserStore.getItem(PUSHTIME_KEY);
        if (text) {
            pushTime = parseInt(text, 10);
        }

        if (nowTs - pushTime > 30 * 1000) {
            asyncFetchData();
        }
    };

    const listenEvent = (e) => {
        asyncFetchData();
    };

    useEffect(() => {
        interval = setInterval(fetchNotifications, 5000);

        addEventListener('notification', listenEvent);

        return () => {
            clearInterval(interval);
            removeEventListener('notification', listenEvent);
        };
    }, []);

    const tabItems = [
        {
            key: '1',
            label: '待办事项',
            children: (
                <List
                    itemLayout='horizontal'
                    dataSource={uTodos}
                    renderItem={(item) => (
                        <List.Item
                            actions={[
                                <AButton title={item.buttonName} onClick={() => rediectUrl(item)} key={item.key} />,
                            ]}
                        >
                            <List.Item.Meta
                                title={
                                    <>
                                        {item.title} <span className='number'>({item.count})</span>
                                    </>
                                }
                                description={item.description}
                            />
                        </List.Item>
                    )}
                />
            ),
        },
    ];

    const content = <Tabs defaultActiveKey='1' className='mz-notification-list' items={tabItems} />;

    let badgeCount = 0;
    uTodos.forEach((item) => {
        badgeCount += item.count;
    });

    return (
        <Popover
            content={content}
            trigger='click'
            placement='bottomLeft'
            className='mz-notification'
            open={visible}
            onOpenChange={(show) => setVisible(show)}
        >
            <Badge count={badgeCount} onClick={() => setVisible(true)} showZero size='default' offset={[8, 0]}>
                <FontIcon
                    onClick={() => setVisible(true)}
                    type='anticon-notification'
                    style={{ fontSize: '1.15em', cursor: 'pointer' }}
                />
            </Badge>
        </Popover>
    );
}
