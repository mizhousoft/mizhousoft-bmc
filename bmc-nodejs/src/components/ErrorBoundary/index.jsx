import { Result } from 'antd';

export default function ErrorBoundary({ title = '500', subTitle = '对不起，页面崩溃了' }) {
    return <Result status='500' title={title} subTitle={subTitle} style={{ backgroundColor: 'white' }} />;
}
