import React from 'react';
import { Line } from '@ant-design/plots';

export default function LineChart({ data = [], xField = 'xValue', yField = 'yValue', colorField = 'label', height = 320, slider }) {
    let maxValue = 0;

    const yValues = data.map((item) => item[yField] ?? 0);
    if (yValues.length > 0) {
        maxValue = Math.max(...yValues);
        if (maxValue > 0) {
            let value = Math.floor(maxValue / 10, 10);
            if (value === 0) {
                value = 5;
            }

            maxValue += value;
        }
    }
    if (maxValue === 0) {
        maxValue = 10;
    }

    const config = {
        height,
        data,
        xField,
        yField,
        colorField,
        legend: {
            color: {
                position: 'bottom',
                colPadding: 25,
                layout: {
                    justifyContent: 'center',
                    alignItems: 'center',
                    flexDirection: 'column',
                },
            },
        },
        tooltip: {
            title: xField,
            items: [{ channel: 'y' }],
        },
        point: {
            sizeField: 2,
            shapeField: 'circle',
        },
        scale: {
            y: {
                domainMax: maxValue,
            },
        },
        axis: {
            x: {
                labelLineWidth: 10,
                labelSpacing: 0,
                line: true,
                labelAutoHide: true,
                labelAutoRotate: false,
                labelAutoEllipsis: false,
            },
            y: {
                tick: false,
                grid: true,
                gridLineDash: [8, 8],
                gridStrokeOpacity: 0.3,
            },
        },
        slider,
    };

    return <Line autoFit {...config} />;
}
