import React, { Component } from 'react';
import { Form, Modal } from 'antd';
import FormFlex from '@/constants/flex';

const FormItem = Form.Item;

class ViewSystemLog extends Component {
    constructor(props) {
        super(props);

        this.state = {
            visible: false,
        };
    }

    showModal = () => {
        this.setState({ visible: true });
    };

    hideModal = () => {
        this.setState({ visible: false });
    };

    render() {
        const { systemLog } = this.props;

        return (
            <>
                <a onClick={this.showModal}>{systemLog.detail}</a>

                {this.state.visible && (
                    <Modal
                        title='查看系统日志'
                        width='600px'
                        visible
                        onOk={this.hideModal}
                        onCancel={this.hideModal}
                        closable={false}
                        centered
                        className='mz-modal'
                    >
                        <Form className='mz-view-form' labelAlign='left' {...FormFlex.w100_lg5_required}>
                            <FormItem label='基本信息'>{systemLog.baseInfo}</FormItem>
                            <FormItem label='级别'>{systemLog.logLevel}</FormItem>
                            <FormItem label='操作时间'>{systemLog.creationTimeStr}</FormItem>
                            <FormItem label='来源'>{systemLog.source}</FormItem>
                            <FormItem label='操作结果'>{systemLog.resultStr}</FormItem>
                            <FormItem label='详细信息'>{systemLog.detail}</FormItem>
                            <FormItem label='附加信息'>{systemLog.addInfo}</FormItem>
                        </Form>
                    </Modal>
                )}
            </>
        );
    }
}

export default ViewSystemLog;