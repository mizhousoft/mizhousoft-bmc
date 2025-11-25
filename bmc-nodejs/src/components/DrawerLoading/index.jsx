import { Spin } from 'antd';

export default function DrawerLoading({ tip = '数据加载中', height = 'calc(100vh - 100px)' }) {
    return (
        <div className='mz-drawer-loading' style={{ height }}>
            <div className='spin'>
                <Spin size='large' tip={tip}>
                    <div></div>
                </Spin>
            </div>
        </div>
    );
}
