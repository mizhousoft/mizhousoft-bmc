import React, { useState } from 'react';
import { Form, Modal } from 'antd';
import FormFlex from '@/constants/flex';

const FormItem = Form.Item;

export default function ViewOperatorLog({ operationLog }) {
    const [visible, setVisible] = useState(false);

    return (
        <>
            <a onClick={() => setVisible(true)}>{operationLog.detail}</a>

            {visible && (
                <Modal
                    title='查看操作日志'
                    visible
                    onOk={() => setVisible(false)}
                    onCancel={() => setVisible(false)}
                    width='600px'
                    closable={false}
                    destroyOnClose
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
