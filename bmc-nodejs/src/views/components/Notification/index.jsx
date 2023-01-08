import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Tabs, Popover, List, Badge } from 'antd';
import FontIcon from '@/components/FontIcon';
import NotificationStore from '@/store/notificationStore';
import { AButton } from '@/components/UIComponent';

export default function Notification() {
    const navigate = useNavigate();

    const [visible, setVisible] = useState(false);
    const [uTodos, setTodos] = useState(() => NotificationStore.getTodos());

    const rediectUrl = (entity) => {
        setVisible(false);
        navigate(entity.viewPath);
    };

    const fetchNotifications = () => {
        const todos = NotificationStore.getTodos();
        setTodos(todos);

        const pushTime = NotificationStore.getPushTime();
        const nowTs = new Date().getTime();

        if (nowTs - pushTime > 30 * 1000) {
            NotificationStore.asyncFetchData();
        }
    };

    useEffect(() => {
        const interval = setInterval(fetchNotifications, 5000);

        return () => clearInterval(interval);
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
            <Badge count={badgeCount} onClick={() => setVisible(true)} showZero={false} size='default' offset={[8, 0]}>
                <FontIcon
                    onClick={() => setVisible(true)}
                    type='anticon-notification'
                    style={{ fontSize: '1.15em', cursor: 'pointer' }}
                />
            </Badge>
        </Popover>
    );
}
