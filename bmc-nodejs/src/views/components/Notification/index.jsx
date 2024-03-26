import React, { useEffect, useState } from 'react';
import { Badge, List, Popover, Tabs } from 'antd';
import { useNavigate } from 'react-router-dom';

import FontIcon from '@/components/FontIcon';
import { AButton } from '@/components/UIComponent';
import { addEventListener, removeEventListener } from '@/utils/event-bus';
import httpRequest from '@/utils/http-request';

let interval = 0;
let fetchTime = 0;

export default function Notification() {
    const navigate = useNavigate();

    const [visible, setVisible] = useState(false);
    const [uTodos, setTodos] = useState([]);

    const rediectUrl = (entity) => {
        setVisible(false);
        navigate(entity.viewPath);
    };

    const asyncFetchData = () => {
        const requestBody = {
            url: '/fetchNotifications.action',
            data: {},
        };

        httpRequest.get(requestBody).then(({ todos = [], pushTime, fetchStatus }) => {
            setTodos(todos);

            fetchTime = undefined !== pushTime ? pushTime : new Date().getTime();

            if (fetchStatus.statusCode === 401) {
                clearInterval(interval);
            }
        });
    };

    const fetchNotifications = () => {
        const nowTs = new Date().getTime();

        if (nowTs - fetchTime > 30 * 1000) {
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
                        <List.Item actions={[<AButton title={item.buttonName} onClick={() => rediectUrl(item)} key={item.key} />]}>
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
                <FontIcon onClick={() => setVisible(true)} type='anticon-notification' style={{ fontSize: '1.15em', cursor: 'pointer' }} />
            </Badge>
        </Popover>
    );
}
