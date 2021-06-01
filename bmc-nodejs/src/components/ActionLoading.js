import React, { PureComponent } from 'react';
import { Modal, Spin } from 'antd';
import { LoadingOutlined } from '@ant-design/icons';

const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;

class ActionLoading extends PureComponent {
    render() {
        const { content } = this.props;

        return (
            <Modal
                width='300px'
                centered
                maskClosable={false}
                footer={null}
                visible={this.props.visible}
                destroyOnClose
                closable={false}
            >
                <div style={{ textAlign: 'center' }}>
                    <Spin indicator={antIcon} style={{ marginRight: '30px' }} />
                    {content}
                </div>
            </Modal>
        );
    }
}

export default ActionLoading;
