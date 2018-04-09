require(['jquery', 'vue', 'utils', 'weui', 'messager'], function ($, Vue, utils, weui, messager) {
    new Vue({
        el: '#content',
        data: {
            orderForm: {
                deliverToAddress: {},
                items:[],
                coupon: {amount: 0}
            },
            account: {
                cash: 0,
                balance: 0,
                point: 0
            },
            payType: 'wechat'
        },
        filters: {
            coverPath: function (val) {
                return utils.patchUrl('/attachment/download/' + val);
            },
            price: function (val) {
                return utils.formatMoney(val);
            },
            addressName: function (val) {
                if(!val) {
                    return '';
                }
                var name = val.name;
                if(val.parent) {
                    name = val.parent.name + name;
                    if(val.parent.parent) {
                        name = val.parent.parent.name + name;
                    }
                }
                return name;
            }
        },
        methods: {
            openAddressSelector: function () {
                var self = this;
                weui.picker(utils.treeDataConverter(this.address), {
                    onConfirm: function (result) {
                        self.memberAddressForm.address = self.addressMap[result[result.length - 1]];
                    }
                });
            },
            getTotal: function () {
                var total = 0;
                $.each(this.orderForm.items, function () {
                    total += this.product.price * this.count;
                });
                return total;
            },
            getFinalTotal: function () {
                var total = this.getTotal();
                var point = 0;
                if(this.account.point) {
                    point = this.account.point / 100;
                }
                var balance = 0;
                if(this.account.balance) {
                    balance = this.account.balance;
                }
                return total - ((this.orderForm.coupon && this.orderForm.coupon.amount)||0) - point - balance;
            },
            loadOrderForm: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/orderForm/' + utils.getQueryString('id')),
                    success: function(data) {
                        if(data.status === 'PAYED') {
                            messager.bubble("支付成功", 'success');
                            setTimeout(function () {
                                window.location.href = utils.patchUrlPrefixUrl('/wechat/orderform/list?page=all');
                            }, 1000);
                        }
                        self.orderForm = data;
                        self.account.point = data.point;
                        self.account.balance = data.balance;
                    }
                })
            },
            pay: function () {
                var self = this;
                var items = [];
                $.each(self.orderForm.items, function () {
                    items.push({
                        count: this.count,
                        id: this.product.id
                    })
                });
                function pay() {
                    $.ajax({
                        url: utils.patchUrl('/api/orderForm/pay'),
                        contentType: 'application/json',
                        data: JSON.stringify($.extend(self.orderForm, {
                            cash: self.account.cash,
                            balance: self.account.balance,
                            point: self.account.point
                        })),
                        type: 'POST',
                        success: function() {
                            messager.bubble("支付成功", 'success');
                            setTimeout(function () {
                                window.location.href = utils.patchUrlPrefixUrl('/wechat/orderform/list?page=all');
                            }, 1000);
                        }
                    })
                }
                function wechatWebPay() {
                    window.location.href = utils.patchUrl('/api/orderForm/wechat/web/unified?orderNumber=' +
                        self.orderForm.orderNumber + '&cash=' + self.account.cash);
                }
                function aliWebPay() {
                    window.location.href = utils.patchUrl('/api/orderForm/ali/web/unified?orderNumber=' +
                        self.orderForm.orderNumber + '&cash=' + self.account.cash);
                }
                function wechatAppPay() {
                    $.ajax({
                        url: utils.patchUrl('/api/orderForm/wechat/unified'),
                        contentType: 'application/json',
                        type: 'post',
                        data: JSON.stringify($.extend(self.orderForm, {
                            cash: self.account.cash,
                            balance: self.account.balance,
                            point: self.account.point
                        })),
                        success: function (data) {

                            function setupWebViewJavascriptBridge(callback) {
                                var bridge = window.WebViewJavascriptBridge || window.WKWebViewJavascriptBridge;
                                if (bridge) { return callback(bridge); }
                                var callbacks = window.WVJBCallbacks || window.WKWVJBCallbacks;
                                if (callbacks) { return callbacks.push(callback); }
                                window.WVJBCallbacks = window.WKWVJBCallbacks = [callback];
                                if (window.WKWebViewJavascriptBridge) {
                                    //for https://github.com/Lision/WKWebViewJavascriptBridge
                                    window.webkit.messageHandlers.iOS_Native_InjectJavascript.postMessage(null);
                                } else {
                                    //for https://github.com/marcuswestin/WebViewJavascriptBridge
                                    var WVJBIframe = document.createElement('iframe');
                                    WVJBIframe.style.display = 'none';
                                    WVJBIframe.src = 'https://__bridge_loaded__';
                                    document.documentElement.appendChild(WVJBIframe);
                                    setTimeout(function() { document.documentElement.removeChild(WVJBIframe) }, 0);
                                }
                            }

                            setupWebViewJavascriptBridge(function(bridge) {

                                /* Initialize your app here */
                                bridge.callHandler('wechatpay', JSON.stringify(data), function responseCallback(responseData) {
                                    responseData = eval('('+responseData+')');
                                    if(responseData.result === 'ok') {
                                        pay();
                                    } else {
                                        messager.bubble("支付失败", 'error');
                                    }
                                });
                            });
                        }
                    });
                }

                function aliAppPay() {
                    $.ajax({
                        url: utils.patchUrl('/api/orderForm/ali/unified'),
                        contentType: 'application/json',
                        type: 'post',
                        data: JSON.stringify($.extend(self.orderForm, {
                            cash: self.account.cash,
                            balance: self.account.balance,
                            point: self.account.point
                        })),
                        success: function (data) {

                            function setupWebViewJavascriptBridge(callback) {
                                var bridge = window.WebViewJavascriptBridge || window.WKWebViewJavascriptBridge;
                                if (bridge) { return callback(bridge); }
                                var callbacks = window.WVJBCallbacks || window.WKWVJBCallbacks;
                                if (callbacks) { return callbacks.push(callback); }
                                window.WVJBCallbacks = window.WKWVJBCallbacks = [callback];
                                if (window.WKWebViewJavascriptBridge) {
                                    //for https://github.com/Lision/WKWebViewJavascriptBridge
                                    window.webkit.messageHandlers.iOS_Native_InjectJavascript.postMessage(null);
                                } else {
                                    //for https://github.com/marcuswestin/WebViewJavascriptBridge
                                    var WVJBIframe = document.createElement('iframe');
                                    WVJBIframe.style.display = 'none';
                                    WVJBIframe.src = 'https://__bridge_loaded__';
                                    document.documentElement.appendChild(WVJBIframe);
                                    setTimeout(function() { document.documentElement.removeChild(WVJBIframe) }, 0);
                                }
                            }

                            setupWebViewJavascriptBridge(function(bridge) {

                                /* Initialize your app here */
                                bridge.callHandler('alipay', JSON.stringify(data), function responseCallback(responseData) {
                                    responseData = eval('('+responseData+')');
                                    if(responseData.result === 'ok') {
                                        pay();
                                    } else {
                                        messager.bubble("支付失败", 'error');
                                    }
                                });
                            });
                        }
                    });
                }
                this.account.cash = this.getFinalTotal();
                var ua = navigator.userAgent;
                if(self.account.cash === 0) {
                    pay();
                } else {
                    if(this.payType === 'wechat') {
                        if(ua.indexOf('Android_WebView') > -1 || ua.indexOf('iPhone_WebView') > -1) {
                            wechatAppPay();
                        } else {
                            wechatWebPay();
                        }
                    } else {
                        if(ua.indexOf('Android_WebView') > -1 || ua.indexOf('iPhone_WebView') > -1) {
                            aliAppPay();
                        } else {
                            aliWebPay()
                        }
                    }
                }

            }
        },
        mounted: function () {
            this.loadOrderForm();
        }
    });
});
