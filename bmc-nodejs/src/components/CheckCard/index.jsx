import { useState } from 'react';

import './index.less';

function CheckCardItem({ item, checked = false, onClick }) {
    const checkedClass = checked ? 'mz-checkcard-checked' : '';

    return (
        <div onClick={onClick} className={`mz-checkcard mz-checkcard-bordered ${checkedClass}`} style={item.style}>
            <div className='mz-checkcard-content'>
                <div className='mz-checkcard-detail'>
                    <div className='mz-checkcard-header'>
                        <div className='mz-checkcard-header-left'>
                            <div className='mz-checkcard-title mz-checkcard-title-with-ellipsis'>{item.title}</div>
                        </div>
                    </div>
                    <div className='mz-checkcard-description'>{item.description}</div>
                </div>
            </div>
        </div>
    );
}

export default function CheckCard({ defaultValue, items = [], style = {}, onChange }) {
    const [selectedValue, setSelectedValue] = useState(defaultValue);

    const clickItem = (value) => {
        setSelectedValue(value);

        onChange(value);
    };

    return (
        <>
            <div className='mz-checkcard-group' style={{ ...style }}>
                {items.map((item) => (
                    <CheckCardItem
                        key={item.value}
                        item={item}
                        checked={item.value === selectedValue}
                        onClick={() => clickItem(item.value)}
                    />
                ))}
            </div>
        </>
    );
}
