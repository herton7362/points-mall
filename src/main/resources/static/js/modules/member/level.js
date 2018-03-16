require(['jquery', 'vue', 'messager', 'utils'], function($, Vue, messager, utils) {
    new Vue({
        el: '#content',
        data: {
            crudgrid: {
                queryParams: {
                    name: ''
                },
                columns: [
                    {field:'name', title:'会员等级名称'},
                    {field:'needPoint', title:'需要积分'}
                ]
            },
            formData: {
                id: null,
                name: null,
                needPoint: null
            }
        },
        methods: {
        },
        mounted: function() {
            this.crudgrid.$instance.load();
        }
    });
});