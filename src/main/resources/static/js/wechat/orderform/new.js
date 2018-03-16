require(['jquery', 'vue', 'utils', 'weui', 'messager'], function ($, Vue, utils, weui, messager) {
    new Vue({
        el: '#content',
        data: {
            newAddressActionsheet: {
                $instance: {}
            },
            selectAddressActionsheet: {
                $instance: {}
            },
            address: [],
            addressMap: {},
            orderForm: {
                items: [],
                deliverToAddress: {},
                remark: null,
                member: null,
                coupon: {amount: 0}
            },
            member: {},
            memberAddresses: [],
            memberAddressForm: {
                id: null,
                name: null,
                tel: null,
                address: {},
                postalCode: null
            },
            coupons: [],
            couponSelector: {
                open: false
            },
            pointSelector: {
                open: false,
                other: false
            },
            balanceSelector: {
                open: false,
                other: false
            },
            account: {
                cash: 0,
                balance: null,
                point: null
            },
            hashchanged: false
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
            }
        },
        watch: {
            address: function (val) {
                $.each(val, function () {
                    this.label = this.name;
                    this.value = this.id;
                });
                return val;
            }
        },
        methods: {
            openAddressSelector: function () {
                var self = this;
                weui.picker(utils.treeDataConverter(this.address), {
                    onConfirm: function (result) {
                        self.memberAddressForm.address = self.addressMap[result[result.length - 1]];
                    }
                });
            },
            getTotal: function () {
                var total = 0;
                $.each(this.orderForm.items, function () {
                    total += this.product.price * this.count;
                });
                return total;
            },
            getFinalTotal: function () {
                var total = this.getTotal();
                var point = 0;
                if(this.account.point) {
                    point = this.account.point / 100;
                }
                var balance = 0;
                if(this.account.balance) {
                    balance = this.account.balance;
                }
                return total - this.orderForm.coupon.amount - point - balance;
            },
            editAddress: function (row) {
                this.newAddressActionsheet.$instance.open();
                this.memberAddressForm = row;
            },
            saveMemberAddress: function () {
                var self = this;
                if(!this.memberAddressForm.name) {
                    messager.bubble("请填写收货人");
                    return;
                }
                if(!this.memberAddressForm.tel) {
                    messager.bubble("请填写联系电话");
                    return;
                }
                if(!/^[1][3,4,5,7,8][0-9]{9}$/.test(this.memberAddressForm.tel)) {
                    messager.bubble("联系电话格式不正确");
                    return;
                }
                if(!this.memberAddressForm.address.id) {
                    messager.bubble("请选择地区");
                    return;
                }
                if(!this.memberAddressForm.detailAddress) {
                    messager.bubble("请填写详细地区");
                    return;
                }
                $.ajax({
                    url: utils.patchUrl('/api/memberAddress'),
                    contentType: 'application/json',
                    type: 'POST',
                    dataType: 'JSON',
                    data: JSON.stringify($.extend(this.memberAddressForm, {member: this.member})),
                    success: function(data) {
                        messager.bubble('保存成功！');
                        self.orderForm.deliverToAddress = data;
                        self.loadMemberAddress();
                        self.newAddressActionsheet.$instance.close();
                    }
                });
            },
            changeDefaultAddress: function (row) {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/memberAddress/defaultAddress/' + row.id),
                    type: 'POST',
                    success: function() {
                        self.selectAddressActionsheet.$instance.close();
                        self.loadMemberAddress();
                    }
                });
            },
            loadAddress: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/address'),
                    data: {
                        sort: 'sortNumber,updatedDate',
                        order: 'asc,desc'
                    },
                    success: function(data) {
                        self.address = data.content;
                        $.each(data.content, function () {
                            self.addressMap[this.id] = this;
                        })
                    }
                })
            },
            loadOrderForm: function () {
                var isCart = false; // 是否从购物车结算过来
                var self = this;
                if(utils.getQueryString('id')) {
                    isCart = true;
                }

                if(isCart) {
                    this.loadCarts(utils.getQueryString('id'));
                } else {
                    var productId = utils.getQueryString('productId');
                    var count = utils.getQueryString('count');
                    $.ajax({
                        url: utils.patchUrl('/product/' + productId),
                        success: function(data) {
                            self.orderForm.items.push({
                                product: data,
                                count: count
                            });
                        }
                    })

                }
            },
            loadCarts: function (id) {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/cart/' + id),
                    success: function(data) {
                        self.orderForm.items = data.items;
                    }
                })
            },
            loadMemberAddress: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/memberAddress'),
                    data: {
                        sort: 'createdDate',
                        order: 'desc',
                        'member.id': this.member.id
                    },
                    success: function(data) {
                        self.memberAddresses = data.content;
                        $.each(self.memberAddresses, function () {
                            if(this.defaultAddress) {
                                self.orderForm.deliverToAddress = this;
                            }
                        })
                    }
                })
            },
            submit: function () {
                var self = this;
                function makeOrder() {
                    $.each(self.orderForm.items,function () {
                        this.id = null;
                    });
                    self.account.cash = self.getFinalTotal();
                    $.ajax({
                        url: utils.patchUrl('/api/orderForm/makeOrder'),
                        contentType: 'application/json',
                        data: JSON.stringify($.extend(self.orderForm,{
                            status: 'UN_PAY', // 下单未支付
                            member: self.member,
                            cash: self.account.cash,
                            balance: self.account.balance || 0,
                            point: self.account.point || 0
                        })),
                        type: 'POST',
                        success: function(orderForm) {
                            messager.bubble("操作成功");
                            setTimeout(function () {
                                if(utils.getQueryString("id")) {
                                    $.ajax({
                                        url: utils.patchUrl('/api/cart/' + utils.getQueryString("id")),
                                        type: 'DELETE',
                                        success: function () {
                                            window.location.href = utils.patchUrlPrefixUrl('/wechat/orderform/un_pay?id=' + orderForm.id);
                                        }
                                    })
                                } else {
                                    window.location.href = utils.patchUrlPrefixUrl('/wechat/orderform/un_pay?id=' + orderForm.id);
                                }
                            }, 1000);
                        }
                    })
                }
                makeOrder();
            },
            loadCouponCount: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/coupon/member/' + self.member.id),
                    success: function (data) {
                        $.each(data, function () {
                            if(!self.ifExpired(this.coupon) && !this.used && this.coupon.minAmount <= self.getTotal()) {
                                self.coupons.push(this.coupon);
                            }
                        });
                    }
                })
            },
            ifExpired: function (row) {
                var now = new Date().getTime();
                return !(row.startDate <= now && now <= row.endDate);
            },
            priceFormatter: function (price) {
                var result = utils.formatMoney(price).split('.');
                return '<small>￥</small>' + result[0] + '<small>.'+result[1]+'</small>';
            },
            selectCoupon: function () {
                if(this.coupons.length > 0) {
                    this.couponSelector.open = true;
                }
            },
            useCoupon: function (coupon) {
                this.orderForm.coupon = coupon;
                window.history.go(-1);
            },
            selectPoint: function () {
                this.pointSelector.open = true;
            },
            usePoint:function (point) {
                var total = this.getFinalTotal();
                if(total < (point / 100)) {
                    messager.bubble("积分大于商品价格，请重新选择积分");
                    return;
                }
                this.account.point = point;
                window.history.go(-1);
            },
            otherPoint: function () {
                var self = this;
                this.pointSelector.other = true;
                setTimeout(function () {
                    $(self.$refs['pointSelectorInput']).focus();
                }, 200);
            },
            confirmPoint: function () {
                var total = this.getFinalTotal();
                if(total < (this.account.point / 100)) {
                    messager.bubble("积分大于商品价格，请重新填写积分");
                    this.account.point = null;
                    return;
                }
                if(this.account.point < 100) {
                    messager.bubble('积分最小不能小于100');
                    this.account.point = null;
                } else {
                    window.history.go(-1);
                }
            },
            selectBalance: function () {
                this.balanceSelector.open = true;
            },
            useBalance:function (balance) {
                if(balance > this.member.balance) {
                    messager.bubble('余额不足');
                    return;
                }
                if(balance > this.getTotal()) {
                    balance = this.getTotal();
                }
                this.account.balance = balance;
                window.history.go(-1);
            },
            otherBalance: function () {
                var self = this;
                this.balanceSelector.other = true;
                setTimeout(function () {
                    $(self.$refs['balanceSelectorInput']).focus();
                }, 200);
            },
            confirmBalance: function () {
                if(this.account.balance > this.member.balance) {
                    messager.bubble('余额不足');
                    this.account.balance = null;
                    return;
                }
                if(this.account.balance > this.getTotal()) {
                    this.account.balance = this.getTotal();
                }
                window.history.go(-1);
            },
            onActionsheetOpen: function () {
                $('body').css('position', 'fixed');
            },
            onActionsheetClose: function () {
                $('body').css('position', 'static');
            }
        },
        mounted: function () {
            var self = this;
            this.loadAddress();
            this.loadOrderForm();
            utils.getLoginMember(function (member) {
                self.member = member;
                self.loadMemberAddress();
                self.loadCouponCount();
            });
            $(window).on('hashchange', function () {
                var isBackward = location.hash.indexOf('#') < 0;
                if(isBackward) {
                    self.couponSelector.open = false;
                    self.pointSelector.open = false;
                    self.balanceSelector.open = false;
                    self.hashchanged = true;
                }
            });
        }
    });
});