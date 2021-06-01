import React, { Component } from 'react';
import { Form, Modal } from 'antd';
import FormFlex from '@/constants/flex';

const FormItem = Form.Item;

class ViewApiLog extends Component {
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
        const { apiLog } = this.props;

        return (
            <>
                <a onClick={this.showModal}>{apiLog.detail}</a>

                {this.state.visible && (
                    <Modal
                        title='查看接口日志'
                        visible
                        width='600px'
                        onOk={this.hideModal}
                        onCancel={this.hideModal}
                        closable={false}
                        centered
                        className='mz-modal'
                    >
                        <Form className='mz-view-form' labelAlign='left' {...FormFlex.w100_lg5_required}>
                            <FormItem label='操作名称'>{apiLog.operation}</FormItem>
                            <FormItem label='级别'>{apiLog.logLevel}</FormItem>
                            <FormItem label='操作时间'>{apiLog.creationTimeStr}</FormItem>
                            <FormItem label='来源'>{apiLog.source}</FormItem>
                            <FormItem label='操作终端'>{apiLog.terminal}</FormItem>
                            <FormItem label='操作结果'>{apiLog.resultStr}</FormItem>
                            <FormItem label='详细信息'>{apiLog.detail}</FormItem>
                            <FormItem label='附加信息'>{apiLog.addInfo}</FormItem>
                        </Form>
                    </Modal>
                )}
            </>
        );
    }
}

export default ViewApiLog;
