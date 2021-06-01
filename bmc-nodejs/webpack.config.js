var webpack = require('webpack')
var path = require("path")
var theme = require("./theme.js")

module.exports = {
    mode: 'development',
    entry: {
        bundle: './src/index.js'
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
        }
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
                                "modifyVars": theme()
                            }
                        }
                    }
                ]
            },
            {
                test: /\.js$/,
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
    devServer: {
        contentBase: path.join(__dirname, "public"),
        host: "0.0.0.0",
        port: 8080,
        hot: true,
        inline: true,
        compress: true,
        disableHostCheck: true,
        publicPath: "/assets/",
        historyApiFallback: {
            disableDotRule: false
        },
        proxy: {
            '**/*.action': {
                target: 'http://127.0.0.1:18080'
                //target: 'https://dev.mizhousoft.com',
                //secure: false
            }
        }
    },
    plugins: [
        new webpack.DllReferencePlugin({
            context: __dirname,
            manifest: require('./manifest.json')
        })
    ]
}
