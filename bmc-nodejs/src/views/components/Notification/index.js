import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { Tabs, Popover, List, Badge } from 'antd';
import FontIcon from '@/components/FontIcon';
import NotificationStore from '@/store/notificationStore';
import { AButton } from '@/components/UIComponent';

const { TabPane } = Tabs;

class Notification extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visible: false,
            todos: NotificationStore.getTodos(),
        };
    }

    showPopover = () => {
        this.setState({ visible: true });
    };

    handleClickChange = (visible) => {
        this.setState({ visible });
    };

    rediectUrl = (entity) => {
        const { history } = this.props;

        this.setState({ visible: false }, () => {
            history.push(entity.viewPath);
        });
    };

    fetchNotifications = () => {
        const todos = NotificationStore.getTodos();
        this.setState({ todos });

        const pushTime = NotificationStore.getPushTime();
        const nowTs = new Date().getTime();

        if (nowTs - pushTime > 30 * 1000) {
            NotificationStore.asyncFetchData();
        }
    };

    componentDidMount() {
        this.interval = setInterval(this.fetchNotifications, 5000);
    }

    componentWillUnmount() {
        // 清除定时器
        clearInterval(this.interval);
    }

    render() {
        const { todos } = this.state;

        const content = (
            <Tabs defaultActiveKey='1' style={{ width: '320px' }}>
                <TabPane tab='待办事项' key='1'>
                    <List
                        itemLayout='horizontal'
                        dataSource={todos}
                        renderItem={(item) => (
                            <List.Item
                                actions={[
                                    <AButton title='审批' onClick={() => this.rediectUrl(item)} key={item.key} />,
                                ]}
                            >
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
        todos.forEach((item) => {
            badgeCount += item.count;
        });

        return (
            <>
                <Popover
                    content={content}
                    trigger='click'
                    placement='bottomLeft'
                    className='notification'
                    visible={this.state.visible}
                    onVisibleChange={this.handleClickChange}
                >
                    <Badge
                        count={badgeCount}
                        onClick={this.showPopover}
                        showZero
                        size='default'
                        style={{ backgroundColor: '#006eff', color: '#fff', cursor: 'pointer' }}
                        offset={[8, 0]}
                    >
                        <FontIcon
                            onClick={this.showPopover}
                            type='anticon-notification'
                            style={{ fontSize: '1.2em', cursor: 'pointer' }}
                        />
                    </Badge>
                </Popover>
            </>
        );
    }
}

export default withRouter(Notification);
