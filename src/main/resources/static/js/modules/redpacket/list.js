require(['jquery', 'vue', 'utils'], function($, Vue, utils) {
    new Vue({
        el: '#content',
        data: {
            crudgrid: {
                queryParams: {
                    name: ''
                },
                columns: [
                    {field:'amount', title:'赠送金额', formatter: function(value) {
                        return utils.formatMoney(value);
                    }},
                    {field:'minAmount', title:'使用条件： 起送金额', formatter: function(value) {
                        return utils.formatMoney(value);
                    }},
                    {field:'startDate', title:'开始时间', formatter: function(value) {
                        if(!value) {
                            return '';
                        }
                        return new Date(value).format("yyyy-MM-dd");
                    }},
                    {field:'endDate', title:'结束时间', formatter: function(value) {
                        if(!value) {
                            return '';
                        }
                        return new Date(value).format("yyyy-MM-dd");
                    }},
                    {field:'id', title: '状态', align: 'center', formatter: function (value, row) {
                        var now = new Date().getTime();
                        if(row.startDate < now && row.endDate > now) {
                            return '<span class="label label-success"> 进行中 </span>';
                        } else if(row.startDate > now) {
                            return '<span class="label label-default"> 未开始 </span>';
                        } else if(row.endDate < now) {
                            return '<span class="label label-danger"> 已过期 </span>';
                        }
                    }}
                ]
            },
            formData: {
                id: null,
                amount: null,
                minAmount: null,
                startDate: null,
                endDate: null,
                remark: null
            }
        },
        methods: {
        },
        mounted: function() {
            this.crudgrid.$instance.load();
        }
    });
});