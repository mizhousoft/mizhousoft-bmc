import { useImperativeHandle, useState } from 'react';
import { DndContext, PointerSensor, useSensor, useSensors } from '@dnd-kit/core';
import { restrictToVerticalAxis } from '@dnd-kit/modifiers';
import { arrayMove, SortableContext, useSortable, verticalListSortingStrategy } from '@dnd-kit/sortable';
import { CSS } from '@dnd-kit/utilities';
import { Table } from 'antd';

const DragableBodyRow = (props) => {
    const { attributes, listeners, setNodeRef, transform, transition, isDragging } = useSortable({
        id: props['data-row-key'],
    });
    const style = {
        ...props.style,
        transform: CSS.Translate.toString(transform),
        transition,
        cursor: 'move',
        ...(isDragging
            ? {
                  position: 'relative',
                  zIndex: 9999,
              }
            : {}),
    };
    return <tr {...props} ref={setNodeRef} style={style} {...attributes} {...listeners} />;
};

export default function SortableTable({ columns, title, dataSource, ref }) {
    const [uDataSource, setDataSource] = useState(dataSource);

    const sensors = useSensors(
        useSensor(PointerSensor, {
            activationConstraint: {
                distance: 1,
            },
        })
    );

    const onDragEnd = ({ active, over }) => {
        if (active.id !== over?.id) {
            setDataSource((prev) => {
                const activeIndex = prev.findIndex((i) => i.id === active.id);
                const overIndex = prev.findIndex((i) => i.id === over?.id);
                return arrayMove(prev, activeIndex, overIndex);
            });
        }
    };

    useImperativeHandle(ref, () => ({
        getDataSource: () => uDataSource,
    }));

    return (
        <DndContext sensors={sensors} modifiers={[restrictToVerticalAxis]} onDragEnd={onDragEnd}>
            <SortableContext items={uDataSource.map((i) => i.id)} strategy={verticalListSortingStrategy}>
                <Table
                    title={title}
                    columns={columns}
                    dataSource={uDataSource}
                    rowKey='id'
                    pagination={false}
                    components={{
                        body: {
                            row: DragableBodyRow,
                        },
                    }}
                />
            </SortableContext>
        </DndContext>
    );
}
