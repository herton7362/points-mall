require(['jquery', 'vue', 'messager', 'utils'], function($, Vue, messager, utils) {
    new Vue({
        el: '#content',
        data: {
            coupons: [],
            member: {
                coupons: []
            }
        },
        filters: {
            price: function (val) {
                return utils.formatMoney(val);
            },
            date: function (val) {
                return new Date(val).format('yyyy-MM-dd');
            }
        },
        methods: {
            loadCoupons: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/coupon/unClaimed'),
                    success: function (data) {
                        $.each(data, function () {
                            this.expired = self.ifExpired(this);
                        });
                        self.coupons = data;
                    }
                })
            },
            loadMemberCoupons: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/coupon/member/' + self.member.id),
                    success: function (data) {
                        $.each(data, function () {
                            this.expired = self.ifExpired(this.coupon);
                        });
                        Vue.set(self.member, 'coupons', data);
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
            claim: function (coupon) {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/coupon/claim'),
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(coupon),
                    success: function () {
                        messager.bubble('领取成功', 'success');
                        self.loadCoupons();
                        self.loadMemberCoupons();
                    }
                })
            }
        },
        mounted: function() {
            var self = this;
            utils.getLoginMember(function (member) {
                self.member = member;
                self.loadMemberCoupons(member.id);
            }, true);
            this.loadCoupons();
        }
    });
});