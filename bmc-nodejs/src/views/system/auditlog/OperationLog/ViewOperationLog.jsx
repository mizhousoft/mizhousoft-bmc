import { useState } from 'react';
import { Form, Modal } from 'antd';

const FormItem = Form.Item;

export default function ViewOperatorLog({ operationLog }) {
    const [visible, setVisible] = useState(false);

    return (
        <>
            <a onClick={() => setVisible(true)}>{operationLog.detail}</a>

            {visible && (
                <Modal
                    title='查看操作日志'
                    open
                    onOk={() => setVisible(false)}
                    onCancel={() => setVisible(false)}
                    width='600px'
                    closable={false}
                    destroyOnHidden
                    centered
                    className='mz-modal'
                >
                    <Form preserve={false} className='mz-view-form' labelAlign='left' labelCol={{ flex: '90px' }}>
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
