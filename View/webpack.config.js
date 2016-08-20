var config = {
    entry : {
        index : './src/js/index.js',
        main : './src/js/main.js'
    },

    // send to distribution
    output: {
        path: './',
        filename: '[name].js'
    },

    devServer: {
        inline: true,
        port: 7070
    },

    module: {
        loaders: [
            {
                test: /\.jsx?$/,
                exclude: /node_modules/,
                loader: 'babel',

                query: {
                    presets: ['es2015', 'react']
                }
            }
        ]
    }
}

module.exports = config;
