const webpack = require("webpack");

module.exports = {
    entry: ["./src/Router.tsx"],
    output: {
        filename: "bundle.js",
        path: __dirname + "/target/classes/webroot"
    },
    devtool: "source-map",
    resolve: {
        extensions: [".ts", ".tsx", ".js", ".json", ".less"]
    },
    module: {
        rules: [
            {test: /\.js$/, enforce: "pre", loader: "source-map-loader"},
            {test: /\.tsx?$/, loader: "awesome-typescript-loader"},
            {test: /\.css$/, use: ['style-loader', 'css-loader']},
        ]
    },
    plugins: [
        new webpack.IgnorePlugin(/^\.\/locale$/, /moment$/),
    ],
    // externals: {
    //     "react": "React",
    //     "react-dom": "ReactDOM"
    // }
};