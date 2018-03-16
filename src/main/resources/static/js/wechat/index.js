require([
    'jquery',
    'vue',
    'utils',
    'messager',
    _appConf.ctx + '/static/js/wechat/product/actionsheet/productsheet.js'
], function ($, Vue, utils, messager, productsheet) {
    new Vue({
        el: '#content',
        data: {
            products: []
        },
        filters: {
            coverPath: function (val) {
                return utils.patchUrl('/attachment/download/' + val);
            },
            price: function (val) {
                return utils.formatMoney(val);
            }
        },
        methods: {
            loadProducts: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/product'),
                    data: {
                        sort: 'sortNumber,updatedDate',
                        order: 'asc,desc',
                        currentPage: 1,
                        pageSize: 10
                    },
                    success: function(data) {
                        self.products = data.content
                    }
                });
            },
            productDetail: function (row) {
                window.location.href = utils.patchUrlPrefixUrl('/wechat/product/detail?id=' + row.id);
            },
            more: function () {
                window.location.href = utils.patchUrlPrefixUrl('/wechat/product/all');
            },
            addCart: function (product) {
                productsheet.open(product);
            }
        },
        mounted: function () {
            var username, token;
            token = utils.getQueryString("token");
            username = utils.getQueryString("username");
            if(token && username) {
                $.ajax({
                    url: utils.patchUrlPrefixUrl('/token/login'),
                    data: {
                        appId: 'tonr',
                        appSecret: 'secret',
                        username: username,
                        token: token
                    },
                    type: 'POST',
                    success: function(data) {
                        window.localStorage.accessToken = data['access_token'];
                        window.localStorage.refreshToken = data['refresh_token'];
                        window.localStorage.expiration = new Date().getTime() + ((data['expires_in'] / 2) * 1000);
                    }
                })
            }
            this.loadProducts();
        }
    });
})