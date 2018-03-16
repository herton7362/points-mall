require(['jquery', 'vue', 'messager', 'utils'], function($, Vue, messager, utils) {
    new Vue({
        el: '#content',
        data: {
            crudgrid: {
                queryParams: {
                    name: ''
                },
                columns: [
                    {field:'cardNo', title:'会员卡'},
                    {field:'loginName', title:'登录名'},
                    {field:'name', title:'姓名'},
                    {field:'mobile', title:'手机'},
                    {field:'gender', title:'性别', formatter: function(value) {
                        if(value === '0') {
                            return '女士';
                        } else if(value === '1') {
                            return '先生';
                        }
                        return '未知';
                    }},
                    {field:'birthday', title:'生日', formatter: function(value) {
                        if(!value) {
                            return '';
                        }
                        return new Date(value).format("yyyy-MM-dd");
                    }},
                    {field:'idCard', title:'身份证'},
                    {field:'address', title:'所在地区'}
                ]
            },
            formData: {
                id: null,
                cardNo: null,
                name: null,
                mobile: null,
                gender: '1',
                birthday: null,
                idCard: null,
                address: null
            }
        },
        methods: {
        },
        mounted: function() {
            this.crudgrid.$instance.load();
        }
    });
});