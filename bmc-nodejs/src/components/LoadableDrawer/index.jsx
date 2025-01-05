import { Drawer } from 'antd';

import DrawerLoading from '@/components/DrawerLoading';
import PageException from '@/components/PageException';

export default function LoadableDrawer({ fetchStatus, title, open, width, placement, onClose, children }) {
    if (fetchStatus.loading) {
        return (
            <Drawer title={title} width={width} placement={placement} destroyOnClose onClose={onClose} open={open} className='mz-drawer'>
                <DrawerLoading />
            </Drawer>
        );
    }

    if (!fetchStatus.okey) {
        return (
            <Drawer title={title} width={width} placement={placement} destroyOnClose onClose={onClose} open={open} className='mz-drawer'>
                <PageException fetchStatus={fetchStatus} goBack={onClose} />
            </Drawer>
        );
    }

    return (
        <Drawer title={title} width={width} placement={placement} destroyOnClose onClose={onClose} open={open} className='mz-drawer'>
            {children}
        </Drawer>
    );
}
