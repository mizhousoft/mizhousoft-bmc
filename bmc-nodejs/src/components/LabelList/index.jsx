import React, { useMemo, useState } from 'react';

import './index.less';

const LabelListContext = React.createContext({
    selectedValue: undefined,
    onClickLabel: undefined,
});

function ListItem({ value, title, number }) {
    return (
        <LabelListContext.Consumer>
            {({ selectedValue, onClickLabel }) => {
                const selectedClass = selectedValue === value ? 'selected' : '';

                return (
                    <li className={`${selectedClass}`} onClick={(e) => onClickLabel(value)}>
                        <span className='title'>{title}</span>
                        <span className='number'>（{number}）</span>
                    </li>
                );
            }}
        </LabelListContext.Consumer>
    );
}

function LabelList({ defaultValue, children, onChange }) {
    const [selectedValue, setSelectedValue] = useState(defaultValue);

    const clickLabelEvent = (value) => {
        setSelectedValue(value);

        if (onChange) {
            onChange(value);
        }
    };

    const value = useMemo(
        () => ({
            selectedValue,
            onClickLabel: clickLabelEvent,
        }),
        [selectedValue]
    );

    return (
        <ul className='mz-label-list'>
            <LabelListContext.Provider value={value}>{children}</LabelListContext.Provider>
        </ul>
    );
}

LabelList.Item = ListItem;

export default LabelList;
