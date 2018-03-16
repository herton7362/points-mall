require(['jquery', 'vue', 'utils'], function($, Vue, utils) {
    new Vue({
        el: '.login-box',
        data: {
            username: null,
            password: null,
            msg: '用户登录',
            validator: null,
            error: false
        },
        methods: {
            validatorMounted: function(widget) {
                this.validator = widget;
            },
            submitForm: function() {
                var self = this;
                if(!this.validator.isFormValid()) {
                    return;
                }
                $.ajax({
                    url: utils.patchUrlPrefixUrl('/oauth/token'),
                    data: {
                        client_id: window._appConf.appId,
                        client_secret: window._appConf.appSecret,
                        grant_type: 'password',
                        username: this.username,
                        password: this.password
                    },
                    headers: {
                        Authorization: "Basic " +  btoa(window._appConf.appId + ':' + window._appConf.appSecret)
                    },
                    type: 'POST',
                    success: function(data) {
                        window.localStorage.accessToken = data['access_token'];
                        window.localStorage.refreshToken = data['refresh_token'];
                        window.localStorage.expiration = new Date().getTime() + ((data['expires_in'] / 2) * 1000);
                        window.location.href = utils.patchUrlPrefixUrl('/admin/index');
                        return false;
                    },
                    error: function(XMLHttpRequest) {
                        if(500=== XMLHttpRequest.status) {
                            self.msg = '系统内部错误，请联系系统管理员！';
                            self.error = true;
                        } else if(406 === XMLHttpRequest.status) {
                            self.msg = JSON.parse(XMLHttpRequest.responseText).message;
                            self.error = true;
                        } else if(400 === XMLHttpRequest.status) {
                            self.msg = '用户名或者密码错误';
                            self.error = true;
                        } else if(401 === XMLHttpRequest.status) {
                            self.msg = '用户名或密码错误';
                            self.error = true;
                        }
                        return false;
                    }
                });
            }
        }
    });
});