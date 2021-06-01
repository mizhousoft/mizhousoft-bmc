import React, { Component } from 'react';
import { Form, Modal } from 'antd';
import FormFlex from '@/constants/flex';

const FormItem = Form.Item;

class ViewOperatorLog extends Component {
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
        const { operationLog } = this.props;

        return (
            <>
                <a onClick={this.showModal}>{operationLog.detail}</a>

                {this.state.visible && (
                    <Modal
                        title='查看操作日志'
                        visible
                        onOk={this.hideModal}
                        onCancel={this.hideModal}
                        width='600px'
                        closable={false}
                        centered
                        className='mz-modal'
                    >
                        <Form className='mz-view-form' labelAlign='left' {...FormFlex.w100_lg5_required}>
                            <FormItem label='操作名称'>{operationLog.operation}</FormItem>
                            <FormItem label='级别'>{operationLog.logLevel}</FormItem>
                            <FormItem label='操作帐号'>{operationLog.accountName}</FormItem>
                            <FormItem label='操作时间'>{operationLog.creationTimeStr}</FormItem>
                            <FormItem label='来源'>{operationLog.source}</FormItem>
                            <FormItem label='操作终端'>{operationLog.terminal}</FormItem>
                            <FormItem label='操作结果'>{operationLog.resultStr}</FormItem>
                            <FormItem label='详细信息'>{operationLog.detail}</FormItem>
                            <FormItem label='附加信息'>{operationLog.addInfo}</FormItem>
                        </Form>
                    </Modal>
                )}
            </>
        );
    }
}

export default ViewOperatorLog;
