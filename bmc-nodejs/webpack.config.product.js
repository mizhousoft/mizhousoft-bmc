var webpack = require('webpack')
var path = require("path")
var theme = require("./theme.js")
const {
    CleanWebpackPlugin
} = require('clean-webpack-plugin')
const TerserPlugin = require('terser-webpack-plugin')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const CopyWebpackPlugin = require('copy-webpack-plugin')
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const CssMinimizerPlugin = require('css-minimizer-webpack-plugin');

module.exports = {
    mode: 'production',
    entry: {
        bundle: './src/index.jsx'
    },
    output: {
        path: path.join(__dirname, './dist/assets'),
        publicPath: "/bmc/assets/",
        filename: '[id]-[chunkhash].js',
        chunkFilename: '[id]-[chunkhash].js'
    },
    resolve: {
        alias: {
            '@': path.resolve(__dirname, "./src"),
        },
        extensions: [".js", ".jsx", ".json"],
        mainFiles: ["index"],
    },
    module: {
        rules: [
            {
                test: /\.css$/,
                use: [{
                    loader: MiniCssExtractPlugin.loader,
                },
                {
                    loader: 'css-loader',
                    options: {
                        importLoaders: 1
                    }
                }
                ]
            },
            {
                test: /\.less$/,
                use: [{
                    loader: MiniCssExtractPlugin.loader,
                },
                {
                    loader: 'css-loader',
                    options: {
                        importLoaders: 2
                    }
                },
                {
                    loader: 'postcss-loader'
                },
                {
                    loader: 'less-loader',
                    options: {
                        lessOptions: {
                            javascriptEnabled: true,
                            "modifyVars": theme()
                        }
                    }
                }
                ]
            },
            {
                test: /\.(js|jsx)$/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        cacheDirectory: true
                    }
                }],
                include: [
                    path.resolve(__dirname, "src")
                ],
                exclude: /node_modules/
            },
            {
                test: /\.(gif|png|jpe?g|svg)$/i,
                type: 'asset/inline',
                exclude: /node_modules/
            }
        ]
    },
    optimization: {
        nodeEnv: 'production',
        moduleIds: 'deterministic',
        chunkIds: 'deterministic',
        minimize: true,
        minimizer: [
            new TerserPlugin({
                parallel: true,
                terserOptions: {
                    format: {
                        comments: false,
                    },
                },
                extractComments: false,
            }),
            new CssMinimizerPlugin({
                parallel: true,
            }),
        ],
        splitChunks: {
            cacheGroups: {
                styles: {
                    name: 'styles',
                    type: 'css/mini-extract',
                    chunks: 'all',
                    enforce: true,
                },
            },
        },
    },
    plugins: [
        new webpack.DefinePlugin({
            ENV_TEST_ADMIN: JSON.stringify(''),
            ENV_TEST_PASSWORD: JSON.stringify(''),
        }),
        new CleanWebpackPlugin(),
        new MiniCssExtractPlugin({
            filename: '[name]-[contenthash].css',
        }),
        new HtmlWebpackPlugin({
            filename: '../index.html',
            template: 'template/index.html'
        }),
        new CopyWebpackPlugin(
            {
                patterns: [{
                    from: 'public/favicon.ico',
                    to: '../',
                    toType: 'dir'
                },
                {
                    from: 'public/polyfill.min.js',
                    to: './',
                    toType: 'dir'
                }
                ]
            })
    ]
}
