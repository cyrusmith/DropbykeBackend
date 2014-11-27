(function (scope, $) {

    'use strict';

    scope.dropbyke.ui.Withdraw = Backbone.View
        .extend({

                    events: {
                        "click [data-submit]": "submit",
                        "click [data-toggle-form]": "toggle"
                    },

                    initialize: function () {
                        this.sum = +this.$('[data-sum]').attr('data-sum');
                        this.url = this.$el.attr('data-url');
                        this.userId = this.$el.attr('data-user');

                        this.$form = this.$('[data-widthdrawform-form]');
                        this.$input = this.$('[data-widthdraw-sum-input]');

                        this.$form.hide();
                        if(!this.sum) {
                            this.$('[data-toggle-form]').hide();
                        }
                    },

                    toggle: function () {
                        if (this.$form.is(':visible')) {
                            this.$input.val(0);
                            this.$form.hide();
                        }
                        else {
                            this.$form.show();
                            this.$input.val(this.sum / 100);
                        }
                    },

                    submit: function () {
                        if (this.inProgress) return;

                        this.inProgress = true;
                        this.$el.css("opacity", "0.3");
                        $.post(this.url, {
                            userId: this.userId,
                            sum: parseInt((+this.$input.val())*1000)/10
                        }, "json").done($.proxy(function (resp) {
                            if (resp) {
                                this.sum = +resp.sum;
                                this.$('[data-sum]').text(this.sum / 100);
                                if(!this.sum) {
                                    this.$('[data-toggle-form]').hide();
                                }
                                this.toggle();
                            }

                        }, this)).fail(function (error) {
                            if (error.responseJSON) {
                                alert(error.responseJSON.error);
                            }
                            else {
                                alert("Status: " + error.status + " " + error.statusText);
                            }

                        }).always($.proxy(function () {
                            this.$el.css("opacity", "1");
                            this.inProgress = false;
                        }, this));

                    }
                });

    $(function () {

        var wd = new scope.dropbyke.ui.Withdraw({
                                                    el: $('.widthdrawform')
                                                });

    });

})(window, jQuery);