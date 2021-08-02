module.exports = {
    devServer: {
        proxy: {
            "/api": {
                target: "http://localhost:8080",
                changeOrigin: true,
                pathRewrite: {
                    "^/api": ""
                }
            }
        },
        disableHostCheck: true
    },
    chainWebpack: config => {
        config.resolve.alias.set("@", resolve("src"));
    },
    configureWebpack: {
        resolve: {
            alias: {
                'assets': '@/assets',
                'common': '@/common',
                'components': '@/components',
                'api': '@/api',
                'views': '@/views',
                'plugins': '@/plugins'
            }
        }
    }
};
const path = require("path");
function resolve(dir) {
    return path.join(__dirname, dir);
}
