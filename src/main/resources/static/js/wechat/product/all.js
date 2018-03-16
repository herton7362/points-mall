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
            productCategories: [],
            products: {},
            activeId: null,
            currentPage: 1,
            pageSize: 15,
            totalPage: 0
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
            loadProductCategory: function (callback) {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/productCategory'),
                    data: {
                        sort: 'sortNumber,updatedDate',
                        order: 'asc,desc'
                    },
                    success: function(data) {
                        self.productCategories = data.content;
                        if(callback instanceof Function) {
                            callback.call(self, data.content);
                        }
                    }
                })
            },
            tabClick: function (id) {
                var self = this;
                this.activeId = id;
                $.ajax({
                    url: utils.patchUrl('/product'),
                    data: {
                        sort: 'sortNumber,updatedDate',
                        order: 'asc,desc',
                        'productCategory.id': id,
                        currentPage: this.currentPage,
                        pageSize: this.pageSize
                    },
                    success: function(data) {
                        Vue.set(self.products, id, data.content);
                        self.totalPage = parseInt(data.totalElements / self.pageSize) + 1;
                    }
                });
            },
            prev: function () {
                this.currentPage = this.currentPage - 1;
                this.tabClick(this.activeId);
            },
            next: function () {
                this.currentPage = this.currentPage + 1;
                this.tabClick(this.activeId);
            },
            productDetail: function (row) {
                window.location.href = utils.patchUrlPrefixUrl('/wechat/product/detail?id=' + row.id);
            },
            addCart: function (product) {
                productsheet.open(product);
            }
        },
        mounted: function () {
            this.loadProductCategory(function(data) {
                this.tabClick(data[0].id);
            });
        }
    });
})