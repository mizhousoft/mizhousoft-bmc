import { Modal, Spin } from 'antd';

import PageException from '@/components/PageException';

import './index.css';

export default function LoadableModal({ fetchStatus, className, title, open, width, loadingHeight = '200px', style, onCancel, children }) {
    if (fetchStatus.loading) {
        return (
            <Modal
                className={className}
                title={title}
                footer={null}
                open={open}
                destroyOnHidden
                width={width}
                onCancel={onCancel}
                style={style}
                centered
            >
                <div className='mz-modal-loading' style={{ height: loadingHeight }}>
                    <Spin size='default' />
                </div>
            </Modal>
        );
    }

    if (!fetchStatus.okey) {
        return (
            <Modal
                className={className}
                title={title}
                footer={null}
                open={open}
                destroyOnHidden
                width={width}
                onCancel={onCancel}
                style={style}
                centered
            >
                <PageException fetchStatus={fetchStatus} goBack={onCancel} />
            </Modal>
        );
    }

    return (
        <Modal
            className={className}
            title={title}
            footer={null}
            open={open}
            destroyOnHidden
            width={width}
            onCancel={onCancel}
            style={style}
            centered
        >
            {children}
        </Modal>
    );
}
