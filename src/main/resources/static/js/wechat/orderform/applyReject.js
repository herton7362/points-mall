require(['jquery', 'vue', 'utils', 'weui', 'messager'], function ($, Vue, utils, weui, messager) {
    new Vue({
        el: '#content',
        data: {
            orderForm: {
                deliverToAddress: {},
                items: []
            },
            applyRejectRemark: '',
            member: {},
            memberCards: []
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
                var memberCard = null;
                var self = this;
                var discount = 1;
                $.each(this.memberCards, function () {
                    if(this.id === self.orderForm.memberCardId)  {
                        memberCard = this;
                    }
                });
                if(memberCard) {
                    discount = memberCard.discount;
                }
                return total * discount;
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
            },
            loadMemberCards: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/memberCard'),
                    data: {
                        logicallyDeleted: 0,
                        'member.id': this.member.id
                    }
                }).then(function (memberCards) {
                    for (var i = 0; i < memberCards.length; i++) {
                        memberCards[i].name = memberCards[i].memberCardType.name;
                    }
                    self.memberCards = memberCards;
                });
            }
        },
        mounted: function () {
            var self = this;
            utils.getLoginMember(function (member) {
                self.member = member;
                self.loadMemberCards();
                self.loadOrderForm();
            });
        }
    });
});
