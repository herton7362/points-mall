require(['jquery', 'vue', 'utils', 'weui', 'messager'], function ($, Vue, utils, weui, messager) {
    new Vue({
        el: '#content',
        data: {
            member: {},
            formCount: {},
            memberLevel: {
                name: '普通会员'
            },
            couponCount: 0
        },
        filters: {
            coverPath: function (val) {
                return utils.patchUrl('/attachment/download/' + val);
            },
            price: function (val) {
                return utils.formatMoney(val) || 0;
            }
        },
        methods: {
            getHeadPhoto: function () {
                if(this.member.headPhoto) {
                    return utils.patchUrl('/attachment/download/' + this.member.headPhoto.id);
                } else {
                    return window._appConf.ctx + '/static/image/default_user.jpg';
                }
            },
            orderForm: function (page) {
                window.location.href = utils.patchUrlPrefixUrl('/wechat/orderform/list?page=' + page);
            },
            loadFormCount: function (memberId) {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/orderForm/count/' + memberId),
                    cache: false,
                    success: function (data) {
                        self.formCount = data;
                    }
                })
            },
            coupon: function () {
                window.location.href = utils.patchUrlPrefixUrl('/wechat/coupon/list');
            },
            loadCouponCount: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/coupon/count/' + this.member.id),
                    cache: false,
                    success: function (count) {
                        self.couponCount = count;
                    }
                })
            },
            loadMemberLevel: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/memberLevel/member/' + this.member.id),
                    cache: false,
                    success: function (data) {
                        if(data) {
                            self.memberLevel = data;
                        }
                    }
                })
            }
        },
        mounted: function () {
            var self = this;
            utils.getLoginMember(function (member) {
                self.member = member;
                self.loadFormCount(member.id);
                self.loadCouponCount();
                self.loadMemberLevel();
            }, true);

        }
    });
});