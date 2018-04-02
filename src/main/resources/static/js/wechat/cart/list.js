require(['jquery', 'vue', 'utils', 'weui', 'messager'], function ($, Vue, utils, weui, messager) {
    new Vue({
        el: '#content',
        data: {
            cart: {},
            cartItems: [],
            selectedItems: [],
            checkedAll: true,
            member: null,
            editing: false,
            loading: true
        },
        filters: {
            coverPath: function (val) {
                return utils.patchUrl('/attachment/download/' + val);
            },
            price: function (val) {
                return utils.formatMoney(val);
            }
        },
        methods: {
            productDetail: function (product) {
                window.location.href = utils.patchUrlPrefixUrl('/wechat/product/detail?id=' + product.id);
            },
            selectItem: function () {
                var self = this;
                setTimeout(function () {
                    if(self.selectedItems.length < self.cartItems.length) {
                        self.checkedAll = false;
                    } else if (self.selectedItems.length === self.cartItems.length) {
                        self.checkedAll = true;
                    }
                }, 50);
            },
            checkAll: function () {
                var self = this;
                setTimeout(function () {
                    if(self.checkedAll) {
                        self.selectedItems =  self.cartItems;
                    } else {
                        self.selectedItems = [];
                    }
                }, 50);
            },
            edit: function () {
                this.editing = !this.editing;
                if(this.editing) {
                    this.checkedAll = false;
                    this.checkAll();
                } else {
                    this.checkedAll = true;
                    this.checkAll();
                }
            },
            _remove: function (items) {
                var ids = [];
                var self = this;
                $.each(items, function() {
                    ids.push(this.id);
                });
                $.ajax({
                    url: utils.patchUrl('/api/cart/item/' + ids.join(',')),
                    type: 'DELETE',
                    success: function() {
                        $.each(items, function () {
                            self.cartItems.splice($.inArray(this, self.cartItems), 1);
                        })
                    }
                });

            },
            remove: function (item) {
                var self = this;
                messager.alert('确定要删除该商品吗？', function () {
                    self._remove([item]);
                });
            },
            removeSelected: function () {
                var self = this;
                messager.alert('确定将所选 ' + this.selectedItems.length + ' 个商品删除？', function () {
                    self._remove(self.selectedItems);
                });
            },
            increase: function (item) {
                $.ajax({
                    url: utils.patchUrl('/api/cart/item/increase/' + item.id),
                    type: 'POST',
                    success: function() {
                        item.count ++
                    }
                });
            },
            reduce: function (item) {
                $.ajax({
                    url: utils.patchUrl('/api/cart/item/reduce/' + item.id),
                    type: 'POST',
                    success: function() {
                        item.count --
                    }
                });
            },
            settlement: function () {
                var selectedIds = [];
                $.each(this.selectedItems, function() {
                    selectedIds.push(this.id);
                });
                window.location.href = utils.patchUrlPrefixUrl('/wechat/orderform/new?id=' + this.cart.id +
                    '&items=' + selectedIds.join(','));
            },
            getTotal: function () {
                var total = 0;
                $.each(this.selectedItems, function () {
                    total += this.product.price * this.count;
                });
                return total;
            },
            loadCarts: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/cart'),
                    data: {
                        sort: 'sortNumber,updatedDate',
                        order: 'asc,desc',
                        'member.id': this.member.id
                    },
                    success: function(data) {
                        if(data.content && data.content.length > 0) {
                            self.cart = data.content[0];
                            self.cartItems = data.content[0].items;
                            self.selectedItems = data.content[0].items;
                        }
                        self.loading = false;
                    }
                })
            }
        },
        mounted: function () {
            var self = this;
            utils.getLoginMember(function (member) {
                self.member = member;
                self.loadCarts();
            }, true);
        }
    });
});