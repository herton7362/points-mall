require(['jquery', 'vue', 'messager', 'utils'], function($, Vue, messager, utils) {
    new Vue({
        el: '#content',
        data: {
            crudgrid: {
                $instance: {},
                queryParams: {
                    name: ''
                },
                columns: [
                    {field:'name', title:'名称'},
                    {field:'remark', title:'备注'}
                ]
            },
            formData: {
                id: '',
                name: null,
                remark: null,
                items: []
            }
        },
        methods: {
            addItem: function () {
                this.crudgrid.$instance.getForm().items.push({});
            },
            itemSort: function (data) {
                $.each(data, function (i) {
                    this.sortNumber = i;
                })
            },
            removeItem: function (item, event) {
                var form = this.crudgrid.$instance.getForm();
                var items = form.items;
                if(!item.id) {
                    items.splice($.inArray(item, items), 1);
                } else {
                    messager.alert('确认删除'+form.name+'：['+item.name+']吗？', event, function () {
                        items.splice($.inArray(item, items), 1);
                    })
                }
            }
        },
        mounted: function() {
            this.crudgrid.$instance.load();
        }
    });
});