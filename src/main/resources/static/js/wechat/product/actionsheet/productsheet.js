define([
    'jquery',
    'vue',
    'messager',
    'utils',
    'text!'+_appConf.ctx+'/static/js/wechat/product/actionsheet/productsheet.html'
], function($, Vue, messager, utils, html) {
    $(html).appendTo('body');
    return new Vue({
        el: '#product-sheet',
        data: {
            actionsheet: {
                $instance: {}
            },
            count: 1,
            product: {
                coverImage: {}
            },
            button: {
                text: '确定',
                callback: function () {
                    this.close();
                }
            }
        },
        filters: {
            coverPath: function (val) {
                if(!val) {
                    return '';
                }
                return utils.patchUrl('/attachment/download/' + val);
            },
            price: function (val) {
                return utils.formatMoney(val);
            }
        },
        methods: {
            open: function (product) {
                if(product) {
                    this.product = product;
                }
                this.actionsheet.$instance.open();
            },
            close: function () {
                this.actionsheet.$instance.close();
            },
            increase: function () {
                this.count ++;
            },
            reduce: function () {
                this.count --;
            },
            buttonClick: function () {
                this.button.callback.call(this);
            },
            addCart: function () {
                var self = this;
                utils.getLoginMember(function(member) {
                    $.ajax({
                        url: utils.patchUrl('/api/cart/addProduct'),
                        contentType: 'application/json',
                        type: 'POST',
                        data: JSON.stringify({
                            member: member,
                            items: [{
                                product: self.product,
                                count: self.count
                            }]
                        }),
                        success: function() {
                            messager.bubble('成功加入购物车', 'success');
                            self.close();
                        }
                    });
                });
            },
            justBuy: function () {
                window.location.href = utils.patchUrlPrefixUrl('/wechat/orderform/new?productId=' + this.product.id + '&count=' + this.count)
            }
        }
    });
});