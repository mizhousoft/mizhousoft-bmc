import React, { useState, useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import { Tabs, Popover, List, Badge } from 'antd';
import FontIcon from '@/components/FontIcon';
import NotificationStore from '@/store/notificationStore';
import { AButton } from '@/components/UIComponent';

const { TabPane } = Tabs;

export default function Notification() {
    const history = useHistory();

    const [visible, setVisible] = useState(false);
    const [uTodos, setTodos] = useState(() => NotificationStore.getTodos());

    const rediectUrl = (entity) => {
        setVisible(false);
        history.push(entity.viewPath);
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

    const content = (
        <Tabs defaultActiveKey='1' style={{ width: '320px' }}>
            <TabPane tab='待办事项' key='1'>
                <List
                    itemLayout='horizontal'
                    dataSource={uTodos}
                    renderItem={(item) => (
                        <List.Item actions={[<AButton title='审批' onClick={() => rediectUrl(item)} key={item.key} />]}>
                            <List.Item.Meta
                                title={
                                    <>
                                        {item.title} <span style={{ color: '#108ee9' }}>({item.count})</span>
                                    </>
                                }
                                description={item.description}
                            />
                        </List.Item>
                    )}
                />
            </TabPane>
        </Tabs>
    );

    let badgeCount = 0;
    uTodos.forEach((item) => {
        badgeCount += item.count;
    });

    return (
        <Popover
            content={content}
            trigger='click'
            placement='bottomLeft'
            className='notification'
            visible={visible}
            onVisibleChange={(show) => setVisible(show)}
        >
            <Badge
                count={badgeCount}
                onClick={() => setVisible(true)}
                showZero
                size='default'
                style={{ backgroundColor: '#006eff', color: '#fff', cursor: 'pointer' }}
                offset={[8, 0]}
            >
                <FontIcon
                    onClick={() => setVisible(true)}
                    type='anticon-notification'
                    style={{ fontSize: '1.2em', cursor: 'pointer' }}
                />
            </Badge>
        </Popover>
    );
}
