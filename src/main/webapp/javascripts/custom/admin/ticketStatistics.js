window.App = {
    Models: {},
    Views: {},
    Collections: {}
};

App.Models.Ticket= Backbone.Model.extend({});

App.Collections.TicketsCollection = Backbone.Collection.extend({
    model: App.Models.Ticket,
    url: '/bileton/admin/ticketSt/fetch'
});

App.Views.TicketView = Backbone.View.extend({
    tagName: 'tr',
    initialize: function(){
        this.model.on('destroy', this.remove, this);
        this.model.on('change', this.render, this);
        this.render();
    },
    events: {
        //'click .more': 'fullInformation',
    },
    template: _.template('<td><%=playId%></td><td><%=spectacle%></td><td><%=ticketsSold%></td><td><%=total%></td>'),
    render: function(){
        this.$el.html( this.template( this.model.toJSON() ) );
        return this;
    },
    remove: function  () {
        this.$el.remove();
    }

});

App.Views.TicketsCollectionView = Backbone.View.extend({

    tagName: 'table',

    attributes: {
        'rules': 'all'
    },
    initialize: function() {
        console.log("rendering collections view");
        this.collection.on('add', this.addOne, this);
    },
    addOne: function(ticket) {
        var ticketView = new App.Views.TicketView({ model: ticket });
        this.$el.append( ticketView.render().el );
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
        this.$el.append("<caption>Статистика по билетам "+this.getDate()+"</caption><tr><th>id представления</th><th>Название спектакля</th><th>Билетов продано</th><th>Билетов в продаже</th></tr>");
        this.collection.each(this.addOne, this);
        return this;
    }

});
var ticketsCollection = new App.Collections.TicketsCollection();
ticketsCollection.fetch();

var ticketCollectionView = new App.Views.TicketsCollectionView({collection: ticketsCollection});
ticketCollectionView.render();

$('#table').append(ticketCollectionView.el);
