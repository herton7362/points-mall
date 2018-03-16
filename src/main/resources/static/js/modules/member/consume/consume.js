define([
    'jquery',
    'vue',
    'messager',
    'utils',
    'text!'+_appConf.ctx+'/static/js/modules/member/consume/consume.html'
], function($, Vue, messager, utils, html) {
    $(html).appendTo('body');
    return new Vue({
        el: '#consume',
        data: {
            modal: {
                $instance: {}
            },
            consumeModal: {
                $instance: {}
            },
            datagrid: {
                $instance: {},
                summary: function(data) {
                    var total = 0;
                    $.each(data, function () {
                        total += this.price * this.count;
                    });
                    return '合计：' + utils.formatMoney(total) + ' 元';
                },
                queryParams: {
                    name: ''
                },
                columns: [
                    {field:'name', title:'名称'},
                    {field:'price', title:'单价', formatter: function(value) {
                        return utils.formatMoney(value);
                    }},
                    {field:'count', title:'数量', summary: 'max', align: 'center', width: 50, editor: {
                        type: 'number'
                    }},
                    {field:'points', title:'获得积分'},
                    {field:'remark', title:'备注'}

                ],
                data: []
            },
            member: {
                name: null,
                cardNo: null,
                point: null,
                salePoint: null,
                address: null,
                balance: null
            },
            payType: [],
            account: {
                cash: 0,
                balance: 0,
                point: 0
            },
            sidebar: {
                $instance: {},
                selectedId: null,
                root: {
                    id: 'isNull',
                    name: '所有类别',
                    open: true,
                    alwaysExpended: true
                },
                data: []
            }
        },
        filters: {
            formatMoney: function(val) {
                return utils.formatMoney(val);
            }
        },
        watch: {
            'account.balance': function(val, oldVal) {
                this.account.balance = val || 0;
                if(parseFloat(val) > this.member.balance) {
                    messager.bubble("余额不足");
                    this.account.balance = oldVal;
                }
                this._checkPayType('balance');
                var summary = this._calculateNeedPayCash();
                if(summary < 0) {
                    messager.bubble("金额错误，超出商品价格");
                    this.account.balance = oldVal;
                }
            },
            'account.cash': function(val) {
                this.account.cash = val || 0;
            },
            'account.point': function(val, oldVal) {
                this.account.point = val || 0;
                if(parseFloat(val) > this.member.salePoint) {
                    messager.bubble("积分不足");
                    this.account.point = oldVal;
                }
                this._checkPayType('point');
                var summary = this._calculateNeedPayCash();
                if(summary < 0) {
                    messager.bubble("金额错误，超出商品价格");
                    this.account.point = oldVal;
                }
            },
            payType: function(val) {
                var payTypes = ['balance', 'point'];
                var self = this;

                $.each(payTypes, function() {
                    if(val.indexOf(this + '') < 0){
                        self.account[this] = 0;
                    }
                });
            },
            deep:true
        },
        methods: {
            open: function(member) {
                var self = this;
                this.member = member;
                this.modal.$instance.open();
                this.datagrid.data.splice(0);
                setTimeout(function() {
                    $(self.$refs.searchFiled).focus();
                }, 300)
            },
            load: function() {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/productCategory'),
                    data: $.extend(self.datagrid.queryParams, {
                        sort: 'sortNumber,updatedDate',
                        order: 'asc,desc'
                    })
                }).then(function(category) {
                    return $.ajax({
                        url: utils.patchUrl('/api/product'),
                        data: $.extend(self.datagrid.queryParams, {
                            sort: 'sortNumber,updatedDate',
                            order: 'asc,desc'
                        }),
                        success: function (product) {
                            $.each(product.content, function() {
                                if(this.productCategory) {
                                    this.parent = this.productCategory;
                                    this.name = this.name + '（' + utils.formatMoney(this.price) + '元）';
                                    this.count = 1;
                                }
                            });
                            category.content = category.content || [];
                            self.sidebar.data = category.content.concat(product.content);
                        }
                    });
                });
            },
            add: function() {
                var self = this;
                var index = -1;
                $.ajax({
                    url: utils.patchUrl('/api/product/' + this.sidebar.$instance.getSelectedId()),
                    success: function (product) {
                        if(product) {
                            product.count = 1;
                            $.each(self.datagrid.data, function(i) {
                                if(this.id === product.id) {
                                    index = i;
                                }
                            });
                            if(index >= 0) {
                                self.datagrid.data[index].count ++;
                            } else {
                                self.datagrid.data.push(product);
                            }
                        }
                    }
                });
            },
            remove: function (row) {
                this.datagrid.data.splice(this.datagrid.data.indexOf(row), 1);
            },
            getSummary: function() {
                var data = this.datagrid.data;
                var total = 0;
                $.each(data, function () {
                    total += this.price * this.count;
                });
                return total;
            },
            consumeOpen: function() {
                var data = this.datagrid.data;
                var total = this.getSummary();
                if(data.length <= 0) {
                    messager.bubble("请选择消费项目");
                    return;
                }
                this.account.balance = 0;
                this.account.point = 0;
                this.account.cash = total;
                this._checkPayType('balance');
                this._checkPayType('point');
                this.consumeModal.$instance.open()
            },
            _checkPayType: function(payType) {
                var index = this.payType.indexOf(payType);
                var val = this.account[payType];
                if(val > 0 && index < 0) {
                    this.payType.push(payType);
                } else if (val <= 0 && index >= 0) {
                    this.payType.splice(index, 1);
                }
            },
            _calculateNeedPayCash: function() {
                return this.account.cash = this.getSummary() - this.account.balance - (parseInt(this.account.point) / 100);
            },
            consume: function() {
                var self = this;
                var items = [];
                $.each(this.datagrid.data, function () {
                    items.push({
                        count: this.count,
                        product: this
                    })
                });
                $.ajax({
                    url: utils.patchUrl('/api/orderForm/makeOrder'),
                    contentType: 'application/json',
                    data: JSON.stringify({
                        status: 'PAYED', // 完成支付
                        member: this.member,
                        items: items,
                        cash: this.account.cash,
                        balance: this.account.balance,
                        point: this.account.point
                    }),
                    type: 'POST',
                    success: function() {
                        messager.bubble("操作成功");
                        self.consumeModal.$instance.close();
                        self.modal.$instance.close();
                        if(self.onClose) {
                            self.onClose.call(self);
                        }
                    }
                })
            }
        },
        mounted: function() {
            this.load();
            $(window).trigger('load');
        }
    });
});