const webpack = require("webpack");
const Chunks2JsonPlugin = require('chunks-2-json-webpack-plugin');
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');

module.exports = {
    entry: {
        people: ["./src/pages/people/PeopleRootPage.tsx"],
        places: ["./src/pages/places/PlacesRootPage.tsx"],
        records: ["./src/pages/records/RecordsRootPage.tsx"],
    },
    output: {
        path: __dirname + "/target/classes/js",
        filename: "[name].bundle.js", //filename: "[name].[contenthash].bundle.js",
        chunkFilename: "[name].bundle.js",
    },
    devtool: "source-map",
    resolve: {
        extensions: [".ts", ".tsx", ".js", ".json", ".less"]
    },
    optimization: {
        splitChunks: {
            chunks: "all",
            maxInitialRequests: Infinity,
            minSize: 0,
            cacheGroups: {
                vendor: {
                    name: "vendors",
                    test: /node_modules/
                }
            }
        }
    },
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                use: [
                    {loader: 'cache-loader'},
                    {loader: 'thread-loader'},
                    {loader: 'ts-loader', options: {happyPackMode: true, transpileOnly: true}}
                ]
            },
            {
                test: /\.css$/,
                use: ["style-loader", "css-loader"]
            },
            {
                test: /\.less$/,
                use: [
                    {loader: 'style-loader'},
                    {loader: 'css-loader'},
                    {loader: 'less-loader', options: {javascriptEnabled: true}}
                ]
            },
            {
                test: /\.(gif|png|jpe?g|svg)$/i,
                use: [
                    "file-loader",
                    {
                        loader: "image-webpack-loader",
                    }
                ]

            }
        ]
    },
    plugins: [
        new webpack.IgnorePlugin(/^\.\/locale$/, /moment$/),
        new Chunks2JsonPlugin({outputDir: 'target/classes/js/'}), //Outputs manifest
        new ForkTsCheckerWebpackPlugin() //Type checks
    ],
    externals: {
        "react": "React",
        "react-dom": "ReactDOM"
    }
};