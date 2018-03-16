require(['jquery', 'vue', 'utils', 'weui', 'messager'], function ($, Vue, utils, weui, messager) {
    var vue = new Vue({
        el: '#content',
        data: {
            orderItems: [],
            deliverToAddress: {},
            orderForm: {
                deliverToAddress: {}
            },
            orderStatus: []
        },
        filters: {
            coverPath: function (val) {
                return utils.patchUrl('/attachment/download/' + val);
            },
            price: function (val) {
                return utils.formatMoney(val);
            },
            addressName: function (val) {
                if(!val) {
                    return '';
                }
                var name = val.name;
                if(val.parent) {
                    name = val.parent.name + name;
                    if(val.parent.parent) {
                        name = val.parent.parent.name + name;
                    }
                }
                return name;
            },
            date: function (val) {
                return new Date(val).format("yyyy-MM-dd HH:mm:ss");
            },
            status: function (val) {
                var result = '';
                if(!val) {
                    return null;
                }
                $.each(vue.orderStatus, function () {
                    if(this.id.toUpperCase() === val) {
                        result = this.text;
                    }
                });
                return result;
            }
        },
        methods: {
            getTotal: function () {
                var total = 0;
                $.each(this.orderItems, function () {
                    total += this.product.price * this.count;
                });
                return total;
            },
            loadOrderForm: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/orderForm/' + utils.getQueryString('id')),
                    success: function(data) {
                        self.orderItems = data.items;
                        self.orderForm = data;
                    }
                })
            }
        },
        mounted: function () {
            var self = this;
            this.loadOrderForm();
            $.ajax({
                url: utils.patchUrl('/api/orderForm/status'),
                success: function (data) {
                    self.orderStatus = data;
                }
            })
        }
    });
});