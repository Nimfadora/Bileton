App.Views.PlayView = Backbone.View.extend({
    tagName: 'div',
    className: 'play',
    initialize: function () {
        this.model.on('destroy', this.remove, this);
        this.model.on('change', this.render, this);
        this.render();
    },
    templateMin: _.template($('#minPlayTemplate').html()),
    templateMax: _.template($('#maxPlayTemplate').html()),
    events: {
        'click .more': 'fullInformation',
        'click .less': 'briefInformation',
        'click .btn-primary': 'buyTicket',
        'mouseover .btn-primary': 'prompt'
    },

    prompt: function () {
        nhpup.popup('Купить', {'width': 60});
    },
    briefInformation: function () {
        this.$el.html(this.templateMin(this.model.toJSON()));
        this.$el.css("height", "300px");
        this.$(".right").css("height", "300px");
        if (this.$el.offset().left > 500)
            this.$el.css("float", "left");

    },

    fullInformation: function () {
        this.$el.html(this.templateMax(this.model.toJSON()));
        if (this.$el.offset().left > 500)
            this.$el.css("float", "right");
        this.$el.css("height", "630px");
        this.$(".right").css("height", "630px");
    },
    buyTicket: function (e) {
        var popUpView = new App.Views.PopUpView();
        var audience = new App.Collections.Audience(null, {
            play_id: e.target.id
        });
        audience.fetch({
            success: function () {
                var audienceView = new App.Views.AudienceView({collection: audience});
                $('.modal-body').append(audienceView.render().$el);
            }
        });

    },
    render: function () {
        this.$el.html(this.templateMin(this.model.toJSON()));
        return this;
    },
    remove: function () {
        this.$el.remove();
    }
});


App.Views.PlaceView = Backbone.View.extend({
    initialize: function () {
        this.model.on('destroy', this.remove, this);
        this.model.on('change', this.render, this);
        this.render();
    },
    render: function () {
        //this.$el.html( this.template( this.model.toJSON() ) );
        var el = this.template(this.model.toJSON());
        return el;
    },
    remove: function () {
        this.$el.remove();
    }

});

App.Views.AudienceView = Backbone.View.extend({
    tagName: 'div',
    className: "row",
    attributes: {
        'data-step': "1",
        'data-title': "Выбор мест"
    },
    arrOfPrices: [],
    arrOfPlaces: [],
    initialize: function () {
        this.arrOfPrices = [];
        this.arrOfPlaces = [];
    },
    events: {
        'click .place': 'choose'
    },
    next: function () {
        $("#next").removeAttr('disabled');
    },
    disableNext: function () {
        $("#next").attr('disabled', 'disabled');
    },
    choose: function (e) {
        var placeClass = parseInt(e.target.classList[1].charAt(1))-1;
        if($(e.target).hasClass("chosen_place")) {
            this.arrOfPlaces.splice(this.arrOfPlaces.indexOf(e.target.id),1);
            $(e.target).removeClass("chosen_place");
            $('#r_' + e.target.id).remove();
            $('#p_' + e.target.id).remove();

            this.arrOfPrices.splice(_.indexOf(collectionOfPlays.get(this.collection.play_id).get("prices")[placeClass]), 1);
            if (this.arrOfPrices.length == 0)
                this.disableNext();
            $('.ticketPrice').remove();
            //TODO: rerender view
            $(".priceContainer").append('<p class="ticketPrice">Сумма:&nbsp&nbsp&nbsp&nbsp&nbsp' + _.reduce(this.arrOfPrices, function (memo, num) {
                    return memo + num;
                }, 0) + ' грн</p>');
        }
        else {
            this.arrOfPlaces.push(e.target.id);
            $(e.target).addClass("chosen_place");
            $(".choosenContainer").append('<p class="chosen_row" id="r_' + e.target.id + '">Ряд: ' + $(e.target).data("row") + '</p><p class="ch_place" id="p_' + e.target.id + '">Место: ' +  $(e.target).text() + '</p>');
            this.arrOfPrices.push(collectionOfPlays.get(this.collection.play_id).get("prices")[placeClass]);
            $('.ticketPrice').remove();
            $(".priceContainer").append('<p class="ticketPrice">Сумма:&nbsp&nbsp&nbsp&nbsp&nbsp' + _.reduce(this.arrOfPrices, function (memo, num) {
                    return memo + num;
                }, 0) + ' грн</p>');
            this.next();

        }
        ticket.set("placesId", this.arrOfPlaces);
    },
    template: _.template("<div class=\"popUpContent\"><div id=\"placeholder\"><div id=\"stage\"><p>Сцена</p></div><div class=\"audience\">" +
        "<%var margin = 24; " +
        " _.each(places, function(el, idx, places){" +
        "if(el.get(\"category\")!=category){" +
        "categoryCounter++; " +
        "category = el.get(\"category\");" +
        "} if(el.get(\"placeNum\")==1){%>" +
        "<div class='row'><div class=\"audienceRow\">р <%=el.get(\"rowNum\")%></div><div class=\"placesContainer\">" +
        "<%}if(el.get(\"rowNum\")>11 && el.get(\"placeNum\")==1){" +
        "lim= lim - 2;%>" +
        "<div class='place c<%=el.get(\"category\")%>' data-state='<%=el.get(\"state\")%>' data-row='<%=el.get(\"rowNum\")%>' id='<%=el.id%>' style=\"margin-left:<%=margin%> \"><%=el.get(\"placeNum\")%></div>" +
        "<%margin = margin + 22;}else if(el.get(\"placeNum\") < lim){%>" +
        "<div class='place c<%=el.get(\"category\")%>' data-state='<%=el.get(\"state\")%>' data-row='<%=el.get(\"rowNum\")%>' id='<%=el.id%>'><%=el.get(\"placeNum\")%></div>"+
        "<%}else{%>" +
        "<div class='place c<%=el.get(\"category\")%>' data-state='<%=el.get(\"state\")%>' data-row='<%=el.get(\"rowNum\")%>' id='<%=el.id%>'><%=el.get(\"placeNum\")%></div></div></div>" +
        "<%} })%>" +
        "</div></div>" +
        "<div class='choosenContainer'></div><div class='legend'>" +
        "<% for(var i=0; i<categoryCounter; i++){%>" +
        "<div class='place c<%=i+1%>'></div><p><%=prices[i]%>грн</p>" +
        "<%}%></div><div class='priceContainer'></div></div>"),
    render: function () {
        this.$el.html(this.template({places: this.collection.models, rows: this.collection.models[this.collection.models.length -1].get("rowNum"), lim: 18 , category: -1, categoryCounter: 0, prices: collectionOfPlays.get(this.collection.play_id).get("prices")}));
        return this;
    }
});

App.Views.PlayCollectionView = Backbone.View.extend({
    tagName: 'div',

    initialize: function () {
        console.log("rendering collections view");
        this.collection.on('add', this.addOne, this);
        console.log(this.el);
    },
    addOne: function (play) {
        var playView = new App.Views.PlayView({model: play});
        this.$el.append(playView.render().el);
    },
    render: function () {
        $('#plot').empty();
        this.$el.empty();
        this.collection.each(this.addOne, this);
        return this;
    }

});

App.Views.PopUpView = Backbone.View.extend({
    tagName: 'div',
    className: 'modal fade',
    id: 'myModal',
    flag: false,
    attributes: {
        'role': 'dialog',
        'aria-labelledby': 'myModalLabel',
        'aria-hidden': 'true',
        'tabindex': '1'
    },
    template: _.template($('#myModalTemplate').html()),
    initialize: function () {
        $('#myModal').remove();
        this.render();
        this.checkoutModel = new App.Models.Checkout();
        ticket = new App.Models.Ticket();
        this.checkoutView = new App.Views.CheckoutView({model: this.checkoutModel});
    },
    events: {
        'keydown .modal-dialog': function (e) {
            if (e.keyCode == 13) {
                $("#next").trigger('click');
            }
        },
        'click #next': function () {
            if ($('#actual-step').attr('value') == "2") {
                if (this.flag)
                    this.checkoutView.signUp();
                else
                    this.flag = true;
            }
        }
    },
    render: function () {
        this.$el.html(this.template({}));
        $('#plot').append(this.el);
        this.initializeModal();

    },
    disableNext: function () {
        $("#next").attr('disabled', 'disabled');
    },
    disablePrevious: function () {
        $("#previous").attr('disabled', 'disabled');
    },
    initializeModal: function () {
        var thisRef = this;
        this.$el.modalSteps({
            btnCancelHtml: 'Отмена',
            btnPreviousHtml: 'Назад',
            btnNextHtml: 'Далее',
            btnLastStepHtml: 'Завершить',
            disableNextButton: false,
            completeCallback: function () {
                var query = "";
                var arr = ticket.get("placesId");
                for(var i = 0; i< arr.length; i++){
                    query += (arr[i] + ",");
                }
                ticket.fetch({
                    data: {
                        tickets: query.substr(0, query.length - 1)
                    }
                });
            },
            callbacks: {
                '1': function () {
                    thisRef.disableNext();
                },
                '3': function () {
                    ticket.set('email', thisRef.checkoutView.model.get('email'));
                    ticket.set('discount', thisRef.checkoutView.model.get('discount'));
                    ticket.set('reserveType', thisRef.checkoutView.model.get('reserveType'));
                    thisRef.disablePrevious();
                    ticket.save(null,{
                        success: function(model, response){
                            ticket.set("pdfId", response);
                            $(".getTicket").attr("href", "/bileton/reportTemplates/"+response+".pdf")
                        }
                    });
                }
            },
            nextValidationCallback: function () {
                return !($('#actual-step').attr('value') == "2" && !thisRef.checkoutView.signUp());
            }
        });
    }
});


App.Views.CheckoutView = Backbone.View.extend({
    tagName: 'form',
    className: "form-horizontal",
    attributes: {
        'role': "form"
    },
    template: _.template($('#formTemplate').html()),
    initialize: function () {
        this.render();
        Backbone.Validation.bind(this);
    },
    events: {
        'blur #email': 'emailIsValid',
        'blur #account': 'accountIsValid',
        'blur #cvv': 'cvvIsValid',
        'click .orderType[value=book]': function () {
            this.model.set("reserveType", "2");
            $('.orderType[value=book]').attr("checked", "true");
            $("input[name=account]").attr('disabled', 'disabled');
            $("input[name=cvv]").attr('disabled', 'disabled');
            $('#account').val("").closest('.form-group').removeClass('has-error').removeClass('has-success').find('.help-block').html('').addClass('hidden');
            $('#cvv').val("").closest('.form-group').removeClass('has-error').removeClass('has-success').find('.help-block').html('').addClass('hidden');
        },
        'click .orderType[value=buy]': function () {
            this.model.set("reserveType", "1");
            $('.orderType[value=book]').removeAttr("checked");
            $("input[name=account]").removeAttr('disabled');
            $("input[name=cvv]").removeAttr('disabled');
            this.signUp();
        },
        'click #discount': function(e){
            if($(e.target).is(":checked")){
                this.model.set("discount", "true");
            }else{
                this.model.set("discount", "false");
            }
        }
    },
    emailIsValid: function () {
        var data = this.$el.serializeObject();
        this.model.set(data);
        this.model.isValid('email');
    },
    accountIsValid: function () {
        var data = this.$el.serializeObject();
        this.model.set(data);
        this.model.isValid('account');
    },
    cvvIsValid: function () {
        var data = this.$el.serializeObject();
        this.model.set(data);
        this.model.isValid('cvv');
    },

    signUp: function () {
        var data = this.$el.serializeObject();
        this.model.set(data);
        if ($('.orderType[value=book]').attr("checked") == "checked")
            return this.model.isValid('email');
        else
            return this.model.isValid(true);
    },

    remove: function () {
        Backbone.Validation.unbind(this);
        return Backbone.View.prototype.remove.apply(this, arguments);
    },

    render: function () {
        this.$el.html(this.template({}));
        $('.checkoutForm').append(this.el);
    }
});

_.extend(Backbone.Validation.callbacks, {
    valid: function (view, attr, selector) {
        var $el = view.$('[name=' + attr + ']'),
            $group = $el.closest('.form-group');
        $group.removeClass('has-error');
        $group.addClass('has-success');
        $group.find('.help-block').html('').addClass('hidden');
    },
    invalid: function (view, attr, error, selector) {
        var $el = view.$('[name=' + attr + ']'),
            $group = $el.closest('.form-group');
        if (($el.attr('name') == "account" || $el.attr('name') == "cvv") && $('.orderType[value=book]').attr("checked") == "checked") {
            $group.removeClass('has-error');
            $group.find('.help-block').html('').addClass('hidden');
        }
        else {
            $group.addClass('has-error');
            $group.find('.help-block').html(error).removeClass('hidden');
        }
    }
});

$.fn.serializeObject = function () {
    "use strict";
    var a = {}, b = function (b, c) {
        var d = a[c.name];
        "undefined" != typeof d && d !== null ? $.isArray(d) ? d.push(c.value) : a[c.name] = [d, c.value] : a[c.name] = c.value
    };
    return $.each(this.serializeArray(), b), a
};