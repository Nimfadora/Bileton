window.App = {
    Models: {},
    Views: {},
    Collections: {}
};

App.Models.TroupeSt = Backbone.Model.extend({});

App.Collections.TroupesStCollection = Backbone.Collection.extend({
    model: App.Models.TroupeSt,
    url: '/bileton/admin/playsInTheatres/fetch'
});

App.Views.TroupeStView = Backbone.View.extend({
    tagName: 'tr',
    initialize: function(){
        this.model.on('destroy', this.remove, this);
        this.model.on('change', this.render, this);
        this.render();
    },
    events: {
        //'click .more': 'fullInformation',
    },
    template: _.template('<td><%=name%></td><td><%=count%></td>'),
    render: function(){
        this.$el.html( this.template( this.model.toJSON() ) );
        return this;
    },
    remove: function  () {
        this.$el.remove();
    }

});

App.Views.PlaysInTheatresCollectionView = Backbone.View.extend({

    tagName: 'table',

    attributes: {
        'rules': 'all'
    },
    initialize: function() {
        console.log("rendering collections view");
        this.collection.on('add', this.addOne, this);
    },
    addOne: function(theatre) {
        var pitView = new App.Views.TroupeStView({ model: theatre });
        this.$el.append( pitView.render().el );
    },
    getDate: function(){
        var d = new Date();

        var month = d.getMonth()+1;
        var day = d.getDate();

        var output = d.getFullYear() + '-' +
            (month<10 ? '0' : '') + month + '-' +
            (day<10 ? '0' : '') + day;
        return output;
    },
    render: function() {
        this.$el.empty();
        this.$el.append("<caption>Статистика по театру "+this.getDate()+"</caption><tr><th>Театр</th><th>Количество постановок</th></tr>");
        this.collection.each(this.addOne, this);
        return this;
    }

});
var pitCollection = new App.Collections.TroupesStCollection();
pitCollection.fetch();

var pitCollectionView = new App.Views.PlaysInTheatresCollectionView({collection: pitCollection});
pitCollectionView.render();

$('#table').append(pitCollectionView.el);
