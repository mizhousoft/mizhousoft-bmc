import React, { Component } from 'react';
import { Table } from 'antd';
import { DndProvider, useDrag, useDrop } from 'react-dnd';
import { HTML5Backend } from 'react-dnd-html5-backend';

const type = 'DragableBodyRow';

const DragableBodyRow = ({ index, moveRow, className, style, ...restProps }) => {
    const ref = React.useRef();
    const [{ isOver, dropClassName }, drop] = useDrop(
        () => ({
            accept: type,
            collect: (monitor) => {
                const { index: dragIndex } = monitor.getItem() || {};
                if (dragIndex === index) {
                    return {};
                }
                return {
                    isOver: monitor.isOver(),
                    dropClassName: dragIndex < index ? ' drop-over-downward' : ' drop-over-upward',
                };
            },
            drop: (item) => {
                moveRow(item.index, index);
            },
        }),
        [index]
    );
    const [, drag] = useDrag(
        () => ({
            type,
            item: { index },
            collect: (monitor) => ({
                isDragging: monitor.isDragging(),
            }),
        }),
        [index]
    );
    drop(drag(ref));

    return (
        <tr
            ref={ref}
            className={`${className}${isOver ? dropClassName : ''}`}
            style={{ cursor: 'move', ...style }}
            {...restProps}
        />
    );
};

class SortableTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            dataSource: props.dataSource,
        };
    }

    moveRow = (dragIndex, hoverIndex) => {
        const { dataSource } = this.state;

        const newValues = [...dataSource];

        const items = newValues.splice(dragIndex, 1);
        newValues.splice(hoverIndex, 0, items[0]);

        this.setState({ dataSource: newValues });
    };

    getDataSource = () => {
        const { dataSource } = this.state;

        return dataSource;
    };

    render() {
        const { columns, title } = this.props;
        const { dataSource } = this.state;

        return (
            <DndProvider backend={HTML5Backend}>
                <Table
                    title={title}
                    id='table-drag-sorting'
                    size='middle'
                    columns={columns}
                    dataSource={dataSource}
                    rowKey={(record) => `index-${record.id}`}
                    pagination={false}
                    bordered
                    components={{
                        body: {
                            row: DragableBodyRow,
                        },
                    }}
                    onRow={(record, index) => ({
                        index,
                        moveRow: this.moveRow,
                    })}
                />
            </DndProvider>
        );
    }
}

export default SortableTable;
