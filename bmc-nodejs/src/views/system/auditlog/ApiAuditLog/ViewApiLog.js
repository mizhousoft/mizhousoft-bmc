import React, { useState } from 'react';
import { Form, Modal } from 'antd';
import FormFlex from '@/constants/flex';

const FormItem = Form.Item;

export default function ViewApiLog({ apiLog }) {
    const [visible, setVisible] = useState(false);

    return (
        <>
            <a onClick={() => setVisible(true)}>{apiLog.detail}</a>

            {visible && (
                <Modal
                    title='查看接口日志'
                    visible
                    width='600px'
                    onOk={() => setVisible(false)}
                    onCancel={() => setVisible(false)}
                    closable={false}
                    destroyOnClose
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
