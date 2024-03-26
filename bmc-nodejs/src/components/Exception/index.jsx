import React, { createElement } from 'react';
import { Button } from 'antd';

import { BASENAME } from '@/config/application';
import SessionStore from '@/store/SessionStore';

import './index.less';

import config from './typeConfig';

export default function Exception({ img, type, title, desc, actions }) {
    let statusCode = 404;
    if (type === 401) {
        statusCode = 401;
    } else if (type === 403) {
        statusCode = 403;
    } else if (type <= 504 && type >= 500) {
        statusCode = 500;
    } else if (type >= 404 && type < 422) {
        statusCode = 404;
    }

    const pageType = statusCode in config ? statusCode : '404';
    const toUrl = BASENAME + SessionStore.getHomePath();

    return (
        <div className='exception'>
            <div className='imgBlock'>
                <div className='imgEle' style={{ backgroundImage: `url(${img || config[pageType].img})` }} />
            </div>
            <div className='content'>
                <h1>{title || config[pageType].title}</h1>
                <div className='desc'>{desc || config[pageType].desc}</div>
                <div className='actions'>
                    {actions ||
                        createElement(
                            'a',
                            {
                                to: toUrl,
                                href: toUrl,
                            },
                            <Button type='primary'>返回首页</Button>
                        )}
                </div>
            </div>
        </div>
    );
}
