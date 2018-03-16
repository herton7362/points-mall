require(['jquery', 'vue', 'utils'], function($, Vue, utils) {
    var vue = new Vue({
        el: '#content',
        data: {
            crudgrid: {
                queryParams: {
                    name: ''
                },
                columns: [
                    {field: 'clientId', title: '所属系统', formatter: function(value) {
                            var result;
                            $.each(vue.client.data, function () {
                                if(value === this.id) {
                                    result = this.name;
                                }
                            });
                            return result;
                        }},
                    {field: 'name', title: '名称'},
                    {field:'amount', title:'面额', formatter: function(value) {
                        return utils.formatMoney(value);
                    }},
                    {field:'obtainType', title:'获取方式', formatter: function(value) {
                        if('LOGIN' === value) {
                            return '发放';
                        }
                        return '未知';
                    }},
                    {field:'minAmount', title:'使用条件： 满减金额', formatter: function(value) {
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
            client: {
                data: []
            },
            formData: {
                id: null,
                name: null,
                clientId: null,
                amount: null,
                minAmount: null,
                startDate: null,
                endDate: null,
                obtainType: null,
                remark: null
            }
        },
        methods: {
        },
        mounted: function() {
            this.crudgrid.$instance.load();
            var self = this;
            $.ajax({
                url: utils.patchUrl('/api/oauthClient'),
                data: {
                    sort: 'sortNumber',
                    order: 'asc'
                },
                success: function(data) {
                    self.client.data = data.content;
                }
            })
        }
    });
});