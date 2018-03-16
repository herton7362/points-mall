require(['jquery', 'vue', 'messager', 'utils'], function($, Vue, messager, utils) {
    var vue = new Vue({
        el: '#content',
        data: {
            data: [],
            queryParams: {
                createdDate: [new Date(new Date().format('yyyy-MM-dd')).getTime(), new Date().getTime()],
                createdDateRadio: 1
            },
            currentPage: 1,
            pagerSize: 7,
            pageSize: 15,
            count: 0,
            checkAll: false,
            selectedRows: [],
            orderStatus: [],
            shippingInfo: {
                modal: {
                    $instance: {}
                },
                form: {},
                validator: {
                    $instance: {}
                }
            },
            returnMoney: {
                modal: {
                    $instance: {}
                },
                form: {},
                validator: {
                    $instance: {}
                }
            },
            formDetail: {
                modal: {
                    $instance: {}
                },
                form: {
                    deliverToAddress: {},
                    member: {},
                    items: []
                }
            }
        },
        watch: {
            'queryParams.createdDateRadio': function (val) {
                if(val === 0) {
                    this.queryParams.createdDate = [];
                } else if(val === 1) {
                    this.queryParams.createdDate = [new Date(new Date().format('yyyy-MM-dd')).getTime(), new Date().getTime()];
                } else if(val === 2) {
                    this.queryParams.createdDate = [new Date().getTime() -24*60*60*1000*7, new Date().getTime()];
                } else if(val === 3) {
                    this.queryParams.createdDate = [new Date().getTime() -24*60*60*1000*30, new Date().getTime()];
                }
            }
        },
        filters: {
            coverPath: function (val) {
                return utils.patchUrl('/attachment/download/' + val);
            },
            price: function (val) {
                if(!val) {
                    return '￥0.00';
                }
                return '￥' + utils.formatMoney(val);
            },
            date: function (val) {
                return new Date(val).format("yyyy-MM-dd HH:mm:ss");
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
            status: function (val) {
                var result = '';
                if(!val) {
                    return '';
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
            getStatusClass: function (status) {
                if('UN_PAY' === status) {
                    return 'label-info';
                } else if('APPLY_REJECTED' === status) {
                    return 'label-danger';
                } else {
                    return 'label-success';
                }
            },
            load: function () {
                var self = this;
                if(!this.queryParams.status) {
                    delete this.queryParams.status;
                }
                $.ajax({
                    url: utils.patchUrl('/api/orderForm'),
                    data: $.extend({
                        sort: 'updatedDate',
                        order: 'desc',
                        currentPage: this.currentPage,
                        pageSize: this.pageSize
                    }, this.queryParams),
                    success: function(data) {
                        self.data = data.content;
                        self.count = data.totalElements;
                        self.clearSelected();
                    }
                })
            },
            goToPage: function (page) {
                this.currentPage = page;
                this.load();
            },
            expend: function (row) {
                Vue.set(row, 'expended', !row.expended);
            },
            getProductTotal: function (row) {
                var total = 0;
                $.each(row.items, function () {
                    total += this.count;
                });
                return total;
            },
            getProductPoint: function (row) {
                var total = 0;
                $.each(row.items, function () {
                    total += this.count * this.product.points;
                });
                return total;
            },
            getReceivable: function (row) {
                var total = 0;
                $.each(row.items, function () {
                    total += this.count * this.product.price;
                });
                return total;
            },
            clearSelected: function () {
                this.selectedRows.splice(0);
            },
            selectAll: function(event) {
                var self = this;
                if($(event.target).is(':checked')) {
                    $.each(this.data, function () {
                        if($.inArray(this, this.selectedRows) < 0) {
                            self.selectedRows.push(this);
                        }
                    });
                } else {
                    this.clearSelected();
                }
            },
            shipModalOpen: function (row) {
                this.shippingInfo.form = {
                    id: row.id
                };
                this.shippingInfo.modal.$instance.open();
            },
            shippingInfoSave: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/orderForm/sendOut'),
                    contentType: 'application/json',
                    type: 'POST',
                    dataType: 'JSON',
                    data: JSON.stringify(this.shippingInfo.form),
                    success: function () {
                        messager.bubble('操作成功');
                        self.shippingInfo.modal.$instance.close();
                        self.load();
                    }
                })
            },
            rejectModalOpen: function (row) {
                this.returnMoney.form = {
                    id: row.id,
                    returnedMoney: row.cash,
                    returnedBalance: row.balance,
                    returnedPoint: row.point
                };
                this.returnMoney.modal.$instance.open();
            },
            returnMoneySave: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/orderForm/reject'),
                    contentType: 'application/json',
                    type: 'POST',
                    dataType: 'JSON',
                    data: JSON.stringify(this.returnMoney.form),
                    success: function () {
                        messager.bubble('操作成功');
                        self.returnMoney.modal.$instance.close();
                        self.load();
                    }
                })
            },
            detailModalOpen: function (row) {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/orderForm/' + row.id),
                    cache: false,
                    success: function (data) {
                        self.formDetail.form = data;
                        self.formDetail.modal.$instance.open();
                    }
                })
            }
        },
        mounted: function() {
            var self = this;
            this.load();
            $.ajax({
                url: utils.patchUrl('/api/orderForm/status'),
                success: function (data) {
                    self.orderStatus = data;
                    $.each(self.orderStatus, function () {
                        this.id = this.id.toUpperCase()
                    })
                }
            })
        }
    });
});