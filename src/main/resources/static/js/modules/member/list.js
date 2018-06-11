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
            cards: {
                columns: [
                    {field:'cardNumber', title:'卡号'},
                    {field:'memberCardType', title:'类型', formatter: function(value) {
                        return value.name;
                    }},
                    {field:'balance', title:'储值余额'},
                    {field:'discount', title:'折扣'},
                    {field:'expireDate', title:'过期时间'}
                ],
                validator: {
                    $instance: {}
                },
                modal: {
                    $instance: {}
                },
                sidemodal: {
                    $instance: {},
                    form: {
                        memberCards: []
                    }
                },
                datagrid: {
                    $instance: {},
                    url: '/api/memberCard',
                    data: [],
                    queryParams: {}
                },
                form: {
                    cardNumber: null,
                    balance: 0,
                    discount: 1,
                    expireDate: null,
                    memberCardType: {}
                }
            },
            cardType: {
                list: []
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
            openAddMemberCardModal: function () {
                this.cards.validator.$instance.removeMark();
                this.cards.form = {
                    memberCardType: {id: null}
                };
                this.cards.modal.$instance.open();
            },
            saveMemberCard: function () {
                var self = this;
                var cardType = null;
                if(!this.cards.form.memberCardType.id) {
                    messager.bubble('请选择会员卡类型！');
                    return;
                }
                $.each(this.cardType.list, function () {
                    if(this.id === self.cards.form.memberCardType.id) {
                        cardType = this;
                    }
                });
                $.ajax({
                    url: utils.patchUrl('/api/memberCard'),
                    contentType: 'application/json',
                    type: 'POST',
                    dataType: 'JSON',
                    data: JSON.stringify($.extend(this.cards.form, {
                        member: self.cards.sidemodal.form,
                        memberCardType: cardType
                    })),
                    success: function() {
                        self.cards.modal.$instance.close();
                        messager.bubble('保存成功！');
                        $.ajax({
                            url: utils.patchUrl('/api/member/' + self.cards.sidemodal.form.id),
                            success: function(data) {
                                self.cards.sidemodal.form = data;
                            }
                        });
                    }
                })
            },
            findMemberCard: function (row) {
                var self = this;
                this.cards.validator.$instance.removeMark();
                $.ajax({
                    url: utils.patchUrl('/api/memberCard/' + row.id),
                    success: function(data) {
                        self.cards.form = data;
                        self.cards.modal.$instance.open();
                    }
                });
            },
            editMemberCard: function (row) {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/member/' + row.id),
                    success: function(data) {
                        self.cards.sidemodal.form = data;
                        self.cards.sidemodal.$instance.open();
                    }
                });
            },
            removeMemberCard: function(row, event) {
                var self = this;
                require(['messager'], function(messager) {
                    messager.alert('确定要删除吗？', event, function() {
                        $.ajax({
                            url: utils.patchUrl('/api/memberCard/' + row.id),
                            type: 'DELETE',
                            success: function() {
                                messager.bubble('删除成功');
                                $.ajax({
                                    url: utils.patchUrl('/api/member/' + self.cards.sidemodal.form.id),
                                    success: function(data) {
                                        self.cards.sidemodal.form = data;
                                    }
                                });
                            }
                        });
                    });
                });
            },
            loadCardTypes: function () {
                var self = this;
                $.ajax({
                    url: '/api/memberCardType',
                    success: function (data) {
                        self.cardType.list = data;
                    }
                })
            },
            cardTypeChange: function (val) {
                console.log(val)
            }
        },
        mounted: function() {
            this.crudgrid.$instance.load();
            this.loadCardTypes();
        },
        watch: {
            'cards.form.memberCardType.id': function (val) {
                var cardType = null;
                $.each(this.cardType.list, function () {
                    if(this.id === val) {
                        cardType = this;
                    }
                });
                if(cardType) {
                    this.cards.form.discount = cardType.discount;
                }
            }
        }
    });
});
