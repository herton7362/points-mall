require(['jquery', 'vue', 'messager', 'utils'], function($, Vue, messager, utils) {
    new Vue({
        el: '#content',
        data: {
            crudgrid: {
                queryParams: {
                    name: ''
                },
                columns: [
                    {field:'name', title:'类型名称'},
                    {field:'discount', title:'折扣'},
                    {field:'remark', title:'备注'}
                ]
            },
            formData: {
                id: null,
                name: null,
                discount: null,
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
