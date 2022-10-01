import React from 'react';

import './index.css';

export default function Panel({ title, children, bodyStyle }) {
    return (
        <>
            <div className='mz-panel-title'>{title}</div>
            <div className='mz-panel-body' style={bodyStyle}>
                {children}
            </div>
        </>
    );
}
