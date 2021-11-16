import * as React from 'react';
import './index.less';

const LabelContext = React.createContext({
    selectedValue: undefined,
    onClickLabel: undefined,
});

class LabelItem extends React.PureComponent {
    render() {
        const { value } = this.props;

        return (
            <LabelContext.Consumer>
                {({ selectedValue, onClickLabel }) => {
                    const selectedClass = selectedValue === value ? 'selected' : '';

                    return (
                        <span className={`label-item ${selectedClass}`} onClick={(e) => onClickLabel(value)}>
                            {this.props.children}
                        </span>
                    );
                }}
            </LabelContext.Consumer>
        );
    }
}

class Label extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            selectedValue: props.defaultValue,

            isExtendMore: props.extendMoreHidden === undefined ? false : props.extendMoreHidden,
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

    extendMore = () => {
        this.setState({ isExtendMore: true });
    };

    render() {
        const { title } = this.props;

        const value = {
            selectedValue: this.state.selectedValue,
            onClickLabel: this.clickLabelEvent,
        };

        const extendClass = this.state.isExtendMore ? 'expand-label' : '';

        return (
            <div className='mz-label'>
                <span className='title'>{title}</span>
                <div className={`collection ${extendClass}`}>
                    <LabelContext.Provider value={value}>{this.props.children}</LabelContext.Provider>
                </div>
                {!this.state.isExtendMore && (
                    <span className='expand-all-text' onClick={this.extendMore}>
                        展开更多
                    </span>
                )}
            </div>
        );
    }
}

Label.Item = LabelItem;

export default Label;
