import React, { useState, useImperativeHandle } from 'react';
import './index.less';

function LabelItem({ item, selected, clickItemEvent }) {
    const selectedClass = selected ? 'selected' : '';

    return (
        <span className={`label-item ${selectedClass}`} onClick={(e) => clickItemEvent(item.value)}>
            {item.title}
        </span>
    );
}

function Label(props, ref) {
    const { defaultValue, title, extendMoreHidden = false, items = [], onChange, style = {}, required = false } = props;

    const [selectedValue, setSelectedValue] = useState(defaultValue);
    const [isExtendMore, setExtendMore] = useState(extendMoreHidden);

    const clickItemEvent = (value) => {
        setSelectedValue(value);

        if (onChange) {
            onChange(value);
        }
    };

    const extendMore = () => {
        setExtendMore(true);
    };

    useImperativeHandle(ref, () => ({
        setValue: (value) => setSelectedValue(value),
        getValue: () => selectedValue,
    }));

    const extendClass = isExtendMore ? 'expand-label' : '';

    return (
        <div className='mz-label' style={style}>
            <span className={required ? 'title required' : 'title'}>{title}</span>
            <div className={`collection ${extendClass}`}>
                {items.map((item) => (
                    <LabelItem
                        key={item.key}
                        item={item}
                        selected={item.value === selectedValue}
                        clickItemEvent={clickItemEvent}
                    />
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

export default React.forwardRef(Label);
