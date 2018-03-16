require(['jquery', 'vue', 'utils', 'messager'], function ($, Vue, utils, messager) {
    new Vue({
        el: '#content',
        data: {
            pageType: '输入手机号',
            username: null,
            password: null,
            verifyCode: null,
            appId: 'tonr',
            appSecret: 'secret',
            countDownSecond: 0
        },
        methods: {
            checkTelExist: function () {
                var self = this;
                if(!this.username) {
                    return;
                }
                $.ajax({
                    url: utils.patchUrlPrefixUrl('/user/mobile/' + this.username),
                    cache: false,
                    success: function (data) {
                        if(data) {
                            self.pageType = '使用手机号密码登录';
                        } else {
                            self.pageType = '注册用户';
                        }
                    }
                })
            },
            login: function () {
                $.ajax({
                    url: utils.patchUrlPrefixUrl('/login'),
                    data: {
                        appId: this.appId,
                        appSecret: this.appSecret,
                        username: this.username,
                        password: this.password
                    },
                    type: 'POST',
                    success: function(data) {
                        window.localStorage.accessToken = data['access_token'];
                        window.localStorage.refreshToken = data['refresh_token'];
                        window.localStorage.expiration = new Date().getTime() + ((data['expires_in'] / 2) * 1000);
                        setTimeout(function () {
                            window.location.href = document.referrer;
                        }, 500);
                    }
                })
            },
            register: function () {
                var self = this;
                $.ajax({
                    url: utils.patchUrlPrefixUrl('/register'),
                    data: {
                        mobile: this.username,
                        code: this.verifyCode,
                        password: this.password
                    },
                    type: 'POST',
                    success: function () {
                        messager.bubble('注册成功，前往登录');
                        self.pageType = '使用手机号密码登录';
                    }
                })
            },
            forgetPwd: function () {
                $.ajax({
                    url: utils.patchUrlPrefixUrl('/editPwd'),
                    data: {
                        mobile: this.username,
                        code: this.verifyCode,
                        password: this.password
                    },
                    type: 'POST',
                    success: function () {
                        messager.bubble('修改成功，前往登录');
                        self.pageType = '使用手机号密码登录';
                    }
                })
            },
            sendVerifyCode: function () {
                var self = this;
                disableVerifyButton();
                function disableVerifyButton() {
                    self.countDownSecond = 60;
                    var interval = setInterval(function () {
                        self.countDownSecond--;
                        if(self.countDownSecond === 0) {
                            clearInterval(interval);
                        }
                    }, 1000);
                }
                $.ajax({
                    url: utils.patchUrlPrefixUrl('/verifyCode'),
                    cache: false,
                    data: {
                        mobile: this.username
                    }
                })
            }
        },
        mounted: function () {
        }
    });
})