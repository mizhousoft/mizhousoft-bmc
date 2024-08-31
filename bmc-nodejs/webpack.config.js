var webpack = require('webpack')
var path = require("path")

module.exports = {
    mode: 'development',
    entry: {
        bundle: './src/index.jsx'
    },
    output: {
        path: path.join(__dirname, './dist/assets'),
        publicPath: "/assets/",
        filename: '[name].js',
        chunkFilename: '[name]-[contenthash:10].js'
    },
    resolve: {
        alias: {
            '@': path.resolve(__dirname, "./src")
        },
        extensions: [".js", ".jsx", ".json"],
        mainFiles: ["index"],
    },
    devtool: 'source-map',
    module: {
        rules: [
            {
                test: /\.css$/,
                use: [
                    {
                        loader: 'style-loader' // creates style nodes from JS strings
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
                use: [
                    {
                        loader: 'style-loader' // creates style nodes from JS strings
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
                type: 'asset/resource',
                exclude: /node_modules/
            }
        ]
    },
    devServer: {
        host: "0.0.0.0",
        port: 8080,
        hot: true,
        compress: true,
        open: false,
        allowedHosts: "all",
        historyApiFallback: {
            disableDotRule: false
        },
        proxy: [
            {
                context: ["**/*.action"],
                target: 'http://127.0.0.1:18080',
                //target: 'https://dev.mizhousoft.com',
                //secure: false
                changeOrigin: true,
            },
        ],
    },
    plugins: [
        new webpack.DefinePlugin({
            ENV_TEST_ADMIN: JSON.stringify('admin'),
            ENV_TEST_PASSWORD: JSON.stringify('Bmc@123456789'),
        }),
        new webpack.DllReferencePlugin({
            context: __dirname,
            manifest: require('./manifest.json')
        })
    ]
}
