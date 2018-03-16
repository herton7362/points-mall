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
                    {field:'coverImage', title:'封面', formatter: function(value) {
                        return '<img width="50" src="'+utils.patchUrl('/attachment/download/' + value.id)+'">';
                     }},
                    {field:'productCategory.name', title:'分类'},
                    {field:'name', title:'名称'},
                    {field:'remark', title:'备注'},
                    {field:'points', title:'积分'},
                    {field:'price', title:'价格', formatter: function(value) {
                        return utils.formatMoney(value);
                    }}
                ]
            },
            sidebar: {
                $instance: {},
                root: {
                    id: null,
                    name: '所有类别',
                    open: true,
                    alwaysExpended: true
                },
                selectedId: null,
                data: []
            },
            productCategory: {
                data: []
            },
            formData: {
                id: '',
                name: null,
                remark: null,
                points: null,
                price: null,
                productCategory: {
                    productStandards: []
                },
                coverImage: null,
                styleImages: [],
                detailImages: []
            }
        },
        methods: {
            modalOpen: function() {
                var $form = this.crudgrid.$instance.getForm();
                if($form.id) {
                    return;
                }
                if(this.sidebar.$instance.getSelectedId()) {
                    $form.productCategory = {
                        id: this.sidebar.$instance.getSelectedId()
                    }
                } else {
                    $form.productCategory = {};
                }
            },
            loadSidebar: function(callback) {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/productCategory'),
                    data: {
                        sort:'sortNumber,updatedDate',
                        order: 'asc,desc'
                    },
                    success: function(data) {
                        self.sidebar.data = data.content;

                        if(callback instanceof Function) {
                            callback.call(self, data.content);
                        }
                    }
                });
            },
            sidebarClick: function(row) {
                var param = {};
                if(row.id !== null) {
                    param = {'productCategory.id': row.id};
                }
                this.crudgrid.$instance.load(param);
            },
            comboboxChange: function (val) {
            },
            hasStandard: function () {
                var productStandards = this.getSelectedProductStandards();
                return productStandards && productStandards.length > 0;
            },
            getSelectedProductStandards: function () {
                if(!this.crudgrid.$instance.getForm) {
                    return false;
                }
                var $form = this.crudgrid.$instance.getForm();
                var val = $form.productCategory.id;
                var productCategory = {};
                $.each(this.productCategory.data, function () {
                    if(this.id === val) {
                        productCategory = this;
                    }
                });
                return productCategory.productStandards;
            }
        },
        mounted: function() {
            var self = this;
            this.loadSidebar(function() {
                this.sidebarClick(this.sidebar.root);
            });
            $.ajax({
                url: utils.patchUrl('/api/productCategory'),
                data: {
                    sort: 'sortNumber,updatedDate',
                    order: 'asc,desc'
                },
                success: function(data) {
                    self.productCategory.data = data.content;
                }
            })
        }
    });
});