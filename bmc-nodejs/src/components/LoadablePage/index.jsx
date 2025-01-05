import PageComponent from '@/components/PageComponent';
import PageException from '@/components/PageException';
import PageLoading from '@/components/PageLoading';

export default function LoadablePage({ fetchStatus, title, pageStyle = {}, pageClass = '', bodyStyle = {}, children, goBack }) {
    if (fetchStatus.loading) {
        return <PageLoading />;
    }

    if (!fetchStatus.okey) {
        return <PageException fetchStatus={fetchStatus} goBack={goBack} />;
    }

    return (
        <PageComponent title={title} pageStyle={pageStyle} pageClass={pageClass} bodyStyle={bodyStyle}>
            {children}
        </PageComponent>
    );
}
