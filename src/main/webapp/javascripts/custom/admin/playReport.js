window.App = {
    Models: {},
    Views: {},
    Collections: {}
};
App.Models.Performance = Backbone.Model.extend({
    defaults:{
        name: "name",
        theatre: "theatre",
        troupe: "troupe",
        date: "date",
        time: "time",
        summary: "summary",
        starring: "actors",
        prices: []
    },
    urlRoot: "/bileton/admin/playReport/fetch"
});

App.Collections.PlayReportCollection = Backbone.Collection.extend({
    model: App.Models.Performance,
    initialize: function(){
    },
    url: '/bileton/admin/playReport/fetch'
});

App.Views.PlayReportView = Backbone.View.extend({
    tagName: 'tr',
    initialize: function(){
        this.model.on('destroy', this.remove, this);
        this.model.on('change', this.render, this);
        this.render();
    },
    events: {
        //'click .more': 'fullInformation',
    },
    template: _.template('<td><%=name%></td><td><%=theatre%></td><td><%=troupe%></td><td><%=date%></td><td><%=time%></td><td><%=starring%></td><td><%=prices%></td>'),
    render: function(){
        this.$el.html( this.template( this.model.toJSON() ) );
        return this;
    },
    remove: function  () {
        this.$el.remove();
    }

});

App.Views.PlayReportCollectionView = Backbone.View.extend({

    tagName: 'table',
    className: 'table table-bordered',

    //attributes: {
    //    'rules': 'all'
    //},
    initialize: function() {
        console.log("rendering collections view");
        this.collection.on('add', this.addOne, this);
    },
    addOne: function(play) {
        var playView = new App.Views.PlayReportView({ model: play });
        this.$el.append( playView.render().el );
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
        this.$el.append("<caption>Постановки Отчёт "+this.getDate()+"</caption><thead class='thead-inverse'><tr><th>Постановка</th><th>Театр</th><th>Труппа</th><th>Дата</th><th>Время</th><th>Актеры</th><th>Цены</th></tr></thead>");
        this.collection.each(this.addOne, this);
        return this;
    }

});

var collectionOfPlays = new App.Collections.PlayReportCollection();
collectionOfPlays.fetch();

var playReportCollectionView = new App.Views.PlayReportCollectionView ({collection: collectionOfPlays});
playReportCollectionView.render();

$('#table').append(playReportCollectionView.el);