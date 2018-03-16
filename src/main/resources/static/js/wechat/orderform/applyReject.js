require(['jquery', 'vue', 'utils', 'weui', 'messager'], function ($, Vue, utils, weui, messager) {
    new Vue({
        el: '#content',
        data: {
            orderForm: {
                deliverToAddress: {},
                items: []
            },
            applyRejectRemark: ''
        },
        filters: {
            coverPath: function (val) {
                return utils.patchUrl('/attachment/download/' + val);
            },
            price: function (val) {
                return utils.formatMoney(val);
            },
            date: function (val) {
                return new Date(val).format("yyyy-MM-dd HH:mm:ss");
            }
        },
        methods: {
            getTotal: function () {
                var total = 0;
                $.each(this.orderForm.items, function () {
                    total += this.product.price * this.count;
                });
                return total;
            },
            loadOrderForm: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/orderForm/' + utils.getQueryString('id')),
                    success: function(data) {
                        self.orderForm = data;
                    }
                })
            },
            applyReject: function () {
                if(!this.applyRejectRemark) {
                    messager.bubble('请输入申请退货原因');
                    return;
                }
                $.ajax({
                    url: utils.patchUrl('/api/orderForm/applyReject'),
                    contentType: 'application/json',
                    type: 'POST',
                    data: JSON.stringify({
                        id: this.orderForm.id,
                        applyRejectRemark: this.applyRejectRemark
                    }),
                    success: function() {
                        messager.bubble("操作成功");
                        setTimeout(function () {
                            window.location.href = utils.patchUrlPrefixUrl('/wechat/orderform/list?page=all');
                        }, 1000);
                    }
                })
            }
        },
        mounted: function () {
            this.loadOrderForm();
        }
    });
});