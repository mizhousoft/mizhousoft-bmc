import * as React from 'react';
import './index.less';

const LabelListContext = React.createContext({
    selectedValue: undefined,
    onClickLabel: undefined,
});

class ListItem extends React.PureComponent {
    render() {
        const { value, title, number } = this.props;

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
}

class LabelList extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            selectedValue: props.defaultValue,
        };
    }

    static getDerivedStateFromProps(nextProps, prevState) {
        const { defaultValue } = nextProps;
        const { selectedValue } = prevState;

        if (defaultValue !== selectedValue) {
            return {
                selectedValue: defaultValue,
            };
        }

        return null;
    }

    clickLabelEvent = (value) => {
        const { onChange } = this.props;

        this.setState({ selectedValue: value });

        if (onChange) {
            onChange(value);
        }
    };

    render() {
        const value = {
            selectedValue: this.state.selectedValue,
            onClickLabel: this.clickLabelEvent,
        };

        return (
            <ul className='mz-label-list'>
                <LabelListContext.Provider value={value}>{this.props.children}</LabelListContext.Provider>
            </ul>
        );
    }
}

LabelList.Item = ListItem;

export default LabelList;
