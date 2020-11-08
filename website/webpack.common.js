const webpack = require("webpack");
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
    entry: {
        home: ["./src/pages/home/HomeRouter.tsx"],
        people: ["./src/pages/people/PeopleRouter.tsx"],
        places: ["./src/pages/places/PlacesRouter.tsx"],
        records: ["./src/pages/records/RecordsRouter.tsx"],
        community: ["./src/pages/community/CommunityRouter.tsx"]
    },
    output: {
        path: __dirname + "/target/classes/js",
        filename: "[name].bundle.js", //filename: "[name].[contenthash].bundle.js",
        chunkFilename: "[name].bundle.js", //chunkFilename: "[name].[contenthash].bundle.js"
    },
    resolve: {
        extensions: [".ts", ".tsx", ".js", ".json", ".less"],
        fallback: {"path": require.resolve("path-browserify")}
    },
    optimization: {
        splitChunks: {
            chunks: "all",
            maxInitialRequests: Infinity,
            minSize: 0,
            maxSize: 1000000,
            cacheGroups: {
                vendor: {
                    name: "vendors",
                    test: /node_modules/,
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
                    {loader: 'ts-loader'}
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
                    {loader: 'less-loader', options: {lessOptions: {javascriptEnabled: true}}}
                ]
            },
            {
                //Used by leaflet
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
        //new Chunks2JsonPlugin({outputDir: 'target/classes/js/'}), //Outputs manifest
        new ForkTsCheckerWebpackPlugin(), //Type checks
        new HtmlWebpackPlugin({
            inject: false,
            chunks: ["home"],
            publicPath: "/js",
            template: "src/main/resources/router.html.template",
            filename: "home.html"
        }),
        new HtmlWebpackPlugin({
            inject: false,
            chunks: ["people"],
            publicPath: "/js",
            template: "src/main/resources/router.html.template",
            filename: "people.html"
        }),
        new HtmlWebpackPlugin({
            inject: false,
            chunks: ["places"],
            publicPath: "/js",
            template: "src/main/resources/router.html.template",
            filename: "places.html"
        }),
        new HtmlWebpackPlugin({
            inject: false,
            chunks: ["records"],
            publicPath: "/js",
            template: "src/main/resources/router.html.template",
            filename: "records.html"
        }),
        new HtmlWebpackPlugin({
            inject: false,
            chunks: ["community"],
            publicPath: "/js",
            template: "src/main/resources/router.html.template",
            filename: "community.html"
        })
    ]
};