window.App = {
    Models: {},
    Views: {},
    Collections: {}
};
App.Models.Play = Backbone.Model.extend({
    defaults:{
        spectacle: "spectacle",
        spId: 1,
        perfId: 1
    }
});

App.Models.Theatre = Backbone.Model.extend({
    initialize: function(){
    },
    defaults:{
        name: "theatre"
    }
});


App.Collections.TheatreCollection = Backbone.Collection.extend({
    model: App.Models.Theatre,
    initialize: function(){
    },
    url: '/bileton/admin/theatre/fetch'
});

App.Collections.PlayCollection = Backbone.Collection.extend({
    model: App.Models.Play,
    initialize: function(){
    },
    url: '/bileton/admin/playAuto/fetch'
});

App.Views.FormView = Backbone.View.extend({
    tagName: 'form',
    attributes: {
        'role': 'form'
    },
    events: {
        'click #clear': 'clear',
        'click #create': 'create',
        'click #update': 'update',
        'click #delete': 'delete'
    },
    clear: function(){
        $("#play :first").attr("selected", "selected");
        $("#theatre :first").attr("selected", "selected");
        $("#time :first").attr("selected", "selected");
        $("#date").val("");
    },
    create: function(){
        var model = new App.Models.Performance({
            perfId: $("#play :selected").attr("id"),
            spId: $("#play :selected").attr("data"),
            theatre: $("#theatre :selected").attr("id"),
            time: $("#time :selected").val(),
            date: $("#date").val()
        });
        model.save();
    },

    template: _.template($('#automatisationFormTemplate').html()),
    initialize: function () {
        this.collection.on('add', this.addPlayOption, this);
        theatreCollection.on('add', this.addTheatreOption, this ); //TODO:
        this.render();
    },
    addPlayOption: function(play){
        $('#troupe').append("<option id='"+play.get('perfId')+"' data='"+play.get("spId")+">"+play.get('spectacle')+"</option>");
    },
    addTheatreOption: function(theatre){
        $('#spectacle').append("<option id='"+theatre.get("id")+"'>"+theatre.get('name')+"</option>");
    },
    render: function(){
        this.$el.html( this.template( {} ) );
        $('#form').append(this.el);
    }
});

var collectionOfPlays = new App.Collections.PlayCollection();
collectionOfPlays.fetch();

var theatreCollection = new App.Collections.TheatreCollection();
theatreCollection.fetch();

var formView = new App.Views.FormView({collection: collectionOfPlays});
formView.render();

