window.App = {
    Models: {},
    Views: {},
    Collections: {}
};

App.Models.Theatre = Backbone.Model.extend({
    initialize: function(){
    },
    defaults:{

    },
    urlRoot: "/bileton/admin/theatre/fetch"
});

App.Collections.TheatreCollection = Backbone.Collection.extend({
    model: App.Models.Theatre,
    initialize: function(){
    },
    url: '/bileton/admin/theatre/fetch'
});

App.Views.TheatreFormView = Backbone.View.extend({
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
        $("#theatre").val("");
        $(".identifier").attr("id", "-1");
    },
    create: function(){
        var model = new App.Models.Theatre({
            name: $("#theatre").val()
        });
        model.save();
    },
    update: function(){
        var model = new App.Models.Theatre({
            id: $(".identifier").attr("id"),
            name: $("#theatre").val()
        });
        model.save();
    },
    delete: function(){
        var selected = this.findChecked();
        _.each(_.clone(theatreCollection.models), function(model) {
            if( _.contains(selected, model.get("id")))
                model.destroy({remove: true});
        });
    },
    findChecked: function(){
        var selected = [];
        $('table input:checked').each(function() {
            selected.push(parseInt($(this).attr('id')));
        });
        return selected;
    },
    template: _.template($('#theatreFormTemplate').html()),
    initialize: function () {
        this.render();
    },
    render: function(){
        this.$el.html( this.template( {} ) );
        $('#form').append(this.el);
        return this;
    }
});

App.Views.TheatreFiltersView = Backbone.View.extend({
    tagName: 'form',
    attributes: {
        'role': 'form'
    },
    events: {
        'click #startSearch': 'search',
        'click #clearSearch': 'clear',
        'keypress #search': function(e){
            if (e.keyCode == 13) {
                this.search();
            }
        }
    },
    clear: function () {
        $("#search").val("");
        $("#sort :first").attr('selected', 'selected');
        theatreCollection.fetch({
            success: function(){
                theatreCollectionView.render();
                $('#table').append(theatreCollectionView.el);
            }
        });
    },
    search: function(){
        console.log("searching");
        var sort = $("#sort option:selected").attr('id');
        sort = (sort == 0)? "": (sort == 1) ? "ASC": "DESC";
        theatreCollection.fetch({
            data: {
                search: encodeURI($("#search").val().toUpperCase()),
                sort: sort
            },
            success: function(){
                theatreCollectionView.render();
                $('#table').append(theatreCollectionView.el);
            },
            error: function(){
                console.log("Value Not Found");
            }
        });
    },
    template: _.template($('#searchFilterTemplate').html()),
    initialize: function () {
        this.render();
    },
    render: function(){
        this.$el.html( this.template( {placeholder: "Введите название театра..."} ) );
        $('#filters').append(this.el);
        return this;
    }
});

App.Views.TheatreView = Backbone.View.extend({
    tagName: 'tr',
    initialize: function(){
        this.model.on('destroy', this.remove, this);
        this.model.on('change', this.render, this);
        this.render();
    },
    events: {
        //'click .more': 'fullInformation',
    },
    template: _.template('<td class="<%=category%>"><%=name%></td><td class="<%=category%>"><input id="<%=id%>" type="checkbox" ></td>'),
    render: function(){
        this.$el.html( this.template( this.model.toJSON() ) );
        return this;
    },
    remove: function  () {
        this.$el.remove();
    }

});

App.Views.TheatreCollectionView = Backbone.View.extend({

    tagName: 'table',

    attributes: {
        'rules': 'all'
    },
    initialize: function() {
        console.log("rendering collections view");
        this.collection.on('add', this.addOne, this);
    },
    events: {
        'click tr': 'click'
    },
    click: function(e){
        var id = $(e.currentTarget).children().last().children().attr('id');
        var theatre = this.collection.get(id);
        $("#theatre").val(theatre.get("name"));
        $(".identifier").attr("id", id);
    },
    addOne: function(theatre) {
        var theatreView = new App.Views.TheatreView({ model: theatre });
        this.$el.append( theatreView.render().el );
    },
    render: function() {
        this.$el.empty();
        this.$el.append("<caption>Театры</caption><tr><th>Название</th><th>Удалить</th></tr>");
        this.collection.each(this.addOne, this);
        return this;
    }

});
var theatreCollection = new App.Collections.TheatreCollection();
theatreCollection.fetch();

var theatreCollectionView = new App.Views.TheatreCollectionView({collection: theatreCollection});
theatreCollectionView.render();

var theatreFormView = new App.Views.TheatreFormView();
theatreFormView.render();

var theatreFiltersView = new App.Views.TheatreFiltersView();
theatreFiltersView.render();


$('#table').append(theatreCollectionView.el);