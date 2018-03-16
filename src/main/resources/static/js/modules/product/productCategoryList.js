require(['jquery', 'vue', 'messager', 'utils'], function($, Vue, messager, utils) {
    new Vue({
        el: '#content',
        data: {
            crudgrid: {
                columns: [
                    {field:'name', title:'名称'},
                    {field:'remark', title:'备注'}
                ]
            },
            productStandards: [],
            sidebar: {
                root: {
                    id: 'isNull',
                    name: '所有类别',
                    open: true,
                    alwaysExpended: true
                }
            },
            parent: {
                data: []
            },
            formData: {
                id: null,
                parent: {},
                name: null,
                remark: null,
                productStandards: []
            }
        },
        methods: {
            refresh: function () {
                this.loadCombobox();
            },
            loadCombobox: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/productCategory'),
                    data: {
                        sort: 'sortNumber',
                        order: 'asc'
                    },
                    success: function(data) {
                        self.parent.data = data.content;
                    }
                })
            },
            loadProductStandard: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/productStandard'),
                    data: {
                        sort: 'sortNumber',
                        order: 'asc'
                    },
                    success: function (data) {
                        self.productStandards = data.content;
                    }
                })
            }
        },
        mounted: function() {
            this.loadCombobox();
            this.loadProductStandard();
        }
    });
});