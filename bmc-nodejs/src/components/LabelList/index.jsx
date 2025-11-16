import { useState } from 'react';

import './index.less';

function ListItem({ item, active, onChange }) {
    const selectedClass = active ? 'selected' : '';

    return (
        <li className={`${selectedClass}`} onClick={(e) => onChange(item.value)}>
            <span className='title'>{item.title}</span>
            <span className='number'>（{item.number}）</span>
        </li>
    );
}

function LabelList({ defaultValue, items = [], onChange }) {
    const [selectedKey, setSelectedKey] = useState(defaultValue);

    const clickListItem = (value) => {
        setSelectedKey(value);

        if (onChange) {
            onChange(value);
        }
    };

    return (
        <ul className='mz-label-list'>
            {items.map((item) => (
                <ListItem key={item.value} item={item} active={selectedKey === item.value} onChange={clickListItem} />
            ))}
        </ul>
    );
}

export default LabelList;
