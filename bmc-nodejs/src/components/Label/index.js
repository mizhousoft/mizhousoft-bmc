import React, { useState } from 'react';
import './index.less';

const LabelContext = React.createContext({
    selectedValue: undefined,
    onClickLabel: undefined,
});

function LabelItem({ value, children }) {
    return (
        <LabelContext.Consumer>
            {({ selectedValue, onClickLabel }) => {
                const selectedClass = selectedValue === value ? 'selected' : '';

                return (
                    <span className={`label-item ${selectedClass}`} onClick={(e) => onClickLabel(value)}>
                        {children}
                    </span>
                );
            }}
        </LabelContext.Consumer>
    );
}

function Label({ defaultValue, title, extendMoreHidden = false, children, onChange }) {
    const [selectedValue, setSelectedValue] = useState(defaultValue);
    const [isExtendMore, setExtendMore] = useState(extendMoreHidden);

    const clickLabelEvent = (value) => {
        setSelectedValue(value);

        if (onChange) {
            onChange(value);
        }
    };

    const extendMore = () => {
        setExtendMore(true);
    };

    const value = {
        selectedValue,
        onClickLabel: clickLabelEvent,
    };

    const extendClass = isExtendMore ? 'expand-label' : '';

    return (
        <div className='mz-label'>
            <span className='title'>{title}</span>
            <div className={`collection ${extendClass}`}>
                <LabelContext.Provider value={value}>{children}</LabelContext.Provider>
            </div>
            {!isExtendMore && (
                <span className='expand-all-text' onClick={extendMore}>
                    展开更多
                </span>
            )}
        </div>
    );
}

Label.Item = LabelItem;

export default Label;
