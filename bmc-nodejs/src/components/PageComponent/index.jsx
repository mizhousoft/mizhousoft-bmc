import { Breadcrumb, Col, Row } from 'antd';
import { Link } from 'react-router';

function itemRender(currentItem, params, items, paths) {
    return currentItem.path ? <Link to={currentItem?.path}>{currentItem.title}</Link> : <span>{currentItem.title}</span>;
}

export default function PageComponent({
    breadcrumbs = [],
    extra,
    children,
    headContent,
    headStyle = {},
    contentStyle = {},
    bodyStyle = {},
    bodyClass = '',
    lightBodyColor = true,
}) {
    const bodyTheme = lightBodyColor ? 'light-theme' : '';
    const leftSpan = extra ? 20 : 24;

    return (
        <>
            {breadcrumbs.length > 0 || headContent ? (
                <div className='mz-page-head' style={headStyle}>
                    <Row>
                        <Col span={leftSpan}>
                            <Breadcrumb items={breadcrumbs} itemRender={itemRender} />
                        </Col>
                        {extra ? (
                            <Col span={4} className='extra'>
                                {extra}
                            </Col>
                        ) : null}
                    </Row>
                    {headContent}
                </div>
            ) : null}

            <div className='mz-page-content' style={contentStyle}>
                <div className={`mz-page-content-body ${bodyClass} ${bodyTheme}`} style={bodyStyle}>
                    {children}
                </div>
            </div>
        </>
    );
}
