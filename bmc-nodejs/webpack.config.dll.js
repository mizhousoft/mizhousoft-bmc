const webpack = require('webpack')
var path = require("path");

const vendors = [
    '@ant-design/icons',
    '@ant-design/plots',
    'antd',
    'axios',
    'react',
    'react-dom',
    'react-redux',
    'react-router',
    'redux',
    '@reduxjs/toolkit',
    'dayjs',
]

module.exports = {
    output: {
        path: __dirname,
        filename: '[name].[fullhash].js',
        library: '[name]_[fullhash]',
    },
    entry: {
        vendor: vendors,
    },
    plugins: [
        new webpack.DllPlugin({
            path: path.join(__dirname, 'manifest.json'),
            name: '[name]_[fullhash]',
            context: __dirname,
        }),
    ],
}
