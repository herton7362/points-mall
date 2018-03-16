require(['jquery', 'vue', 'utils', 'morris'], function($, Vue, utils, Morris) {
    new Vue({
        el: '#content',
        data: {
            counts: {
                todaySale: 0,
                monthSale: 0,
                product: 0,
                member: 0
            }
        },
        filters: {
            price: function (val) {
                return utils.formatMoney(val);
            }
        },
        methods: {
            loadTodaySaleCount: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/orderForm/todaySale'),
                    cache: false,
                    success: function (data) {
                        self.counts.todaySale = data;
                    }
                })
            },
            loadMonthSaleCount: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/orderForm/monthSale'),
                    cache: false,
                    success: function (data) {
                        self.counts.monthSale = data;
                    }
                })
            },
            loadProductCount: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/product/count'),
                    cache: false,
                    success: function (data) {
                        self.counts.product = data;
                    }
                })
            },
            loadMemberCount: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/member/count'),
                    cache: false,
                    success: function (data) {
                        self.counts.member = data;
                    }
                })
            },
            loadEverydaySale: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/orderForm/everydaySale'),
                    cache: false,
                    success: function (data) {
                        Morris.Area({
                            element   : 'revenue-chart',
                            resize    : true,
                            data      : data,
                            xkey      : 'y',
                            ykeys     : ['item1'],
                            labels    : ['销售额'],
                            lineColors: ['#3c8dbc'],
                            hideHover : 'auto'
                        });
                    }
                })
            }
        },
        mounted: function () {
            this.loadTodaySaleCount();
            this.loadMonthSaleCount();
            this.loadProductCount();
            this.loadMemberCount();
            this.loadEverydaySale();
        }
    });

});