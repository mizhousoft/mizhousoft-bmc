const { rspack } = require('@rspack/core');
const path = require("path");
const { CleanWebpackPlugin } = require('clean-webpack-plugin');

module.exports = {
    mode: 'production',
    entry: './src/index.jsx',
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
    devtool: false,
    module: {
        rules: [
            {
                test: /\.css$/,
                use: [{
                    loader: rspack.CssExtractRspackPlugin.loader,
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
                    loader: rspack.CssExtractRspackPlugin.loader,
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
                        }
                    }
                }
                ]
            },
            {
                test: /\.(js|jsx)$/,
                use: {
                    loader: 'builtin:swc-loader',
                    options: {
                        jsc: {
                            parser: {
                                syntax: 'ecmascript',
                                jsx: true,
                            },
                            transform: {
                                react: {
                                    runtime: 'automatic', // 启用自动 JSX 转换
                                }
                            },
                        },
                    },
                },
                type: 'javascript/auto',
            },
            {
                test: /\.(gif|png|jpe?g|svg)$/i,
                type: 'asset/resource',
                exclude: /node_modules/
            }
        ]
    },
    optimization: {
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
        new rspack.DefinePlugin({
            ENV_TEST_ADMIN: JSON.stringify(''),
            ENV_TEST_PASSWORD: JSON.stringify(''),
        }),
        new CleanWebpackPlugin(),
        new rspack.CssExtractRspackPlugin({
            filename: '[name]-[contenthash].css',
        }),
        new rspack.HtmlRspackPlugin({
            filename: '../index.html',
            template: 'template/index.html',
        }),
        new rspack.CopyRspackPlugin(
            {
                patterns: [{
                    from: 'public/favicon.ico',
                    to: '../',
                    toType: 'dir'
                }
                ]
            })
    ]
}
