require(['jquery', 'vue', 'messager', 'utils', _appConf.ctx + '/static/js/modules/member/consume/consume.js'], function($, Vue, messager, utils, consume) {
    new Vue({
        el: '#content',
        data: {
            datagrid: {
                $instance: {},
                url: '/api/operationRecord',
                queryParams: {
                    businessType: 'DEDUCT_BALANCE',
                    member: {}
                },
                count: 0,
                columns: [
                    {field:'client.name', title:'客户端'},
                    {field:'content', title:'内容'},
                    {field:'updatedDate', title:'时间', formatter: function(val) {
                        return new Date(val).format("yyyy-MM-dd HH:mm:ss");
                    }}
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
            fastIncreasePointModal: {
                $instance: {},
                point: null
            },
            rechargeModal: {
                $instance: {},
                amount: null
            },
            deductBalanceModal: {
                $instance: {},
                amount: null
            }
        },
        methods: {
            openFastIncreasePointModal: function() {
                this.fastIncreasePointModal.$instance.open();
                var self = this;
                setTimeout(function() {
                    $(self.$refs.point).focus();
                }, 300);
            },
            // 快速积分
            fastIncreasePoint: function() {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/member/fastIncreasePoint'),
                    contentType: 'application/json',
                    type: 'POST',
                    data: JSON.stringify({
                        memberId: this.member.id,
                        point: this.fastIncreasePointModal.point
                    }),
                    success: function() {
                        messager.bubble('操作成功');
                        self.findMember();
                    }
                })
            },
            // 消费
            consume: function() {
                consume.open(this.member);
            },
            openRechargeModal: function() {
                this.rechargeModal.$instance.open();
                var self = this;
                setTimeout(function() {
                    $(self.$refs.recharge).focus();
                }, 300);
            },
            // 充值
            recharge: function() {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/recharge'),
                    contentType: 'application/json',
                    type: 'POST',
                    data: JSON.stringify({
                        memberId: this.member.id,
                        amount: this.rechargeModal.amount
                    }),
                    success: function() {
                        messager.bubble('操作成功');
                        self.findMember();
                    }
                })
            },
            openDeductBalanceModal: function () {
                this.deductBalanceModal.$instance.open();
                var self = this;
                setTimeout(function() {
                    $(self.$refs.deductBalance).focus();
                }, 300);
            },
            // 储值扣费
            deductBalance: function() {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/member/deductBalance'),
                    contentType: 'application/json',
                    type: 'POST',
                    data: JSON.stringify({
                        memberId: this.member.id,
                        amount: this.deductBalanceModal.amount
                    }),
                    success: function() {
                        messager.bubble('操作成功');
                        self.findMember();
                    }
                })
            },
            memberExist: function() {
                return !!this.member.id;
            },
            findMember: function() {
                var self = this;
                var memberId;
                $(self.$refs.cardNo).focus();
                $.ajax({
                    url: utils.patchUrl('/api/member/cardNo/' + this.member.cardNo),
                    success: function(data) {
                        // 如果获取到的对象为空则清空member对象中除了cardNo属性
                        if(!data) {
                            $.each(self.member, function(key) {
                                if('cardNo' !== key) {
                                    self.member[key] = null;
                                }
                            });
                            self.datagrid.$instance.loadData([], 0);
                           return;
                        }
                        // 如果获取到的对象比为空则给对象除了cardNo赋值
                        $.each(data, function(key, value) {
                            if('cardNo' !== key) {
                                self.member[key] = value;
                            }
                        });
                        // 加载消费/充值记录
                        memberId = data.id;
                        self.loadRecord(memberId);
                    }
                })
            },
            loadConsumeRecord: function() {
                this.datagrid.queryParams.businessType = 'CONSUME';
                this.loadRecord(this.member.id);
            },
            loadRechargeRecord: function() {
                this.datagrid.queryParams.businessType = 'RECHARGE';
                this.loadRecord(this.member.id);
            },
            loadDeductBalanceRecord: function() {
                this.datagrid.queryParams.businessType = 'DEDUCT_BALANCE';
                this.loadRecord(this.member.id);
            },
            loadFastIncreasePointRecord: function() {
                this.datagrid.queryParams.businessType = 'FAST_INCREASE_POINT';
                this.loadRecord(this.member.id);
            },
            loadRecord: function (memberId) {
                var self = this;
                if(!memberId) {
                    return;
                }
                this.datagrid.data = [];
                this.datagrid.queryParams.member.id = memberId;
                setTimeout(function () {
                    self.datagrid.$instance.load();
                }, 100)
            },
            tableTransformResponse: function (data) {
                data.client = {name: null};
                $.ajax({
                    url: utils.patchUrl('/api/oauthClient/' + data.clientId),
                    success: function (response) {
                        data.client = response;
                    }
                });
                return data;
            }
        },
        mounted: function() {
            var self = this;
            $(window).trigger('load');
            consume.onClose = function() {
                self.findMember();
            }
        }
    });
});