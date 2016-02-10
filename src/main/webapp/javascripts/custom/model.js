window.App = {
    Models: {},
    Views: {},
    Collections: {}
};
App.Models.Play = Backbone.Model.extend({
    defaults: {
        name: 'play',
        date: "22/10/11",
        time: "19:00",
        picture: "1412317350.jpg",
        theatre: "театр",
        troupe: "труппа",
        prices: [0],
        starring: "актеры",
        summary: "о пьессе"
    }
});

App.Models.LastModalStep = Backbone.Model.extend({});

App.Models.Place = Backbone.Model.extend();


App.Models.Ticket = Backbone.Model.extend({
    defaults:{
        placesId: [],
        email: "email",
        discount: false, //"on" on true
        reserveType: -1
    },
    urlRoot: "bileton/admin/ticket/buy"
});

App.Collections.Audience = Backbone.Collection.extend({
    model: App.Models.Place,
    _baseUrl: 'bileton/admin/audience/fetch',
    constructor: function (dummy, params) {
        this.constructor.__super__.constructor.apply( this, arguments );
        if (params) {
            this.play_id = params.play_id;
        }
    },
    url: function () {
        return 'bileton/admin/audience/fetch?play_id=' + this.play_id
    }
});

App.Collections.PlayCollection = Backbone.Collection.extend({
    model: App.Models.Play,
    initialize: function () {
    },
    url: 'bileton/admin/playReport/fetch'
});

App.Models.Checkout = Backbone.Model.extend({
    validation: {
        email: [{
            required: true,
            msg: "Поле 'Email' обязательно для заполнения"
        }, {
            pattern: 'email',
            msg: "поле 'Email' должно содержать реальный email адрес"
        }, {
            rangeLength: [3, 30],
            msg: "Поле Email должно иметь не менее 3х и не более 30 символов"
        }],
        account: [{
            required: true,
            msg: "Поле 'Номер счёта' обязательно для заполнения"
        }, {
            pattern: /[0-9]{16}/,
            msg: "Поле 'Номер счёта' должно содержать 16 цифр без пробелов"
        }],
        cvv: [{
            required: true,
            msg: "Поле 'CVV' обязательно для заполнения"
        }, {
            pattern: /[0-9]{3}/,
            msg: "Поле 'CVV' должно содержать 3 цифры без пробела"
        }]
    },
    defaults: {
        discount: false,
        reserveType: 1
    }
});