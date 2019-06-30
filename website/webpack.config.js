const webpack = require("webpack");

module.exports = {
    entry: {
        people: ["./src/pages/people/PeopleRouter.tsx"],
        places: ["./src/pages/places/PlacesRouter.tsx"],
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
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                use: [
                    {loader: 'cache-loader'},
                    {loader: 'thread-loader', options: {workers: require('os').cpus().length - 2}},
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
            }
        ]
    },
    plugins: [
        new webpack.IgnorePlugin(/^\.\/locale$/, /moment$/),
    ],
    externals: {
        "react": "React",
        "react-dom": "ReactDOM"
    }
};