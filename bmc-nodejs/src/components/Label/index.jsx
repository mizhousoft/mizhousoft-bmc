import { useImperativeHandle, useState } from 'react';

import './index.less';

function LabelItem({ item, selected, clickItemEvent }) {
    const selectedClass = selected ? 'selected' : '';

    return (
        <span className={`label-item ${selectedClass}`} onClick={(e) => clickItemEvent(item.value)}>
            {item.title}
        </span>
    );
}

export default function Label({ defaultValue, title, extendMoreHidden = false, items = [], onChange, style = {}, required = false, ref }) {
    const [selectedValue, setSelectedValue] = useState(defaultValue);
    const [isExtendMore, setIsExtendMore] = useState(extendMoreHidden);

    const clickItemEvent = (value) => {
        setSelectedValue(value);

        if (onChange) {
            onChange(value);
        }
    };

    const extendMore = () => {
        setIsExtendMore(true);
    };

    useImperativeHandle(ref, () => ({
        setValue: (value) => setSelectedValue(value),
        getValue: () => selectedValue,
    }));

    const extendClass = isExtendMore ? 'expand-label' : '';
    let titleClass = title ? 'title ' : '';
    if (required) {
        titleClass += 'required';
    }

    return (
        <div className='mz-label' style={style}>
            <span className={titleClass}>{title}</span>
            <div className={`collection ${extendClass}`}>
                {items.map((item) => (
                    <LabelItem key={item.key} item={item} selected={item.value === selectedValue} clickItemEvent={clickItemEvent} />
                ))}
            </div>
            {!isExtendMore && (
                <span className='expand-all-text' onClick={extendMore}>
                    展开更多
                </span>
            )}
        </div>
    );
}
