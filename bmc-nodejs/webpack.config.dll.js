const webpack = require('webpack')
var path = require("path");

const vendors = [
    '@ant-design/icons',
    'antd',
    'axios',
    'react',
    'react-dom',
    'react-redux',
    'react-router-dom',
    'redux',
    'redux-saga',
    'moment',
    '@ant-design/charts',
    'react-dnd',
    'react-dnd-html5-backend',
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
