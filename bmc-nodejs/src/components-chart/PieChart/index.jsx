import React from 'react';
import { Pie } from '@ant-design/plots';

export default function PieChart({ data = [], angleField = 'percent', colorField = 'label', valueField = 'value', height = 270 }) {
    return (
        <Pie
            appendPadding={20}
            data={data}
            angleField={angleField}
            colorField={colorField}
            radius={0.9}
            height={height}
            autoFit={false}
            label={{
                text: (dataItem) => `${dataItem[colorField]}: ${dataItem[angleField]}%`,
                position: 'outside',
            }}
            legend={false}
            tooltip={(
                dataItem // 每一个数据项
            ) => ({
                name: dataItem[colorField],
                value: dataItem[valueField],
            })}
        />
    );
}
