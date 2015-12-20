window.App = {
    Models: {},
    Views: {},
    Collections: {}
};


App.Models.Spectacle = Backbone.Model.extend({
    initialize: function(){
    },
    defaults:{
        name: "spectacle",
        summary: "summary"
    },
    urlRoot: "/bileton/admin/spectacle/fetch"
});

App.Collections.SpectacleCollection = Backbone.Collection.extend({
    model: App.Models.Spectacle,
    initialize: function(){
    },
    url: '/bileton/admin/spectacle/fetch'
});

App.Views.SpectacleFormView = Backbone.View.extend({
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
        $("#spectacle").val("");
        $("#summary").val("");
        $(".identifier").attr("id", "-1");
    },
    create: function(){
        var model = new App.Models.Spectacle({
            name: $("#spectacle").val(),
            summary: $("#summary").val()
        });
        model.save();
    },
    update: function(){
        var model = new App.Models.Spectacle({
            id: $(".identifier").attr("id"),
            name: $("#spectacle").val(),
            summary: $("#summary").val()
        });
        model.save();
    },
    delete: function(){
        var selected = this.findChecked();
        _.each(_.clone(spectacleCollection.models), function(model) {
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
    template: _.template($('#spectacleFormTemplate').html()),
    initialize: function () {
        this.render();
    },
    render: function(){
        this.$el.html( this.template( {} ) );
        $('#form').append(this.el);
        return this;
    }
});

App.Views.SpectacleFiltersView = Backbone.View.extend({
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
        spectacleCollection.fetch({
            success: function(){
                spectacleCollectionView.render();
                $('#table').append(spectacleCollectionView.el);
            }
        });
    },
    search: function(){
        console.log("searching");
        var sort = $("#sort option:selected").attr('id');
        sort = (sort == 0)? "": (sort == 1) ? "ASC": "DESC";
        spectacleCollection.fetch({
            data: {
                search: encodeURI($("#search").val().toUpperCase()),
                sort: sort
            },
            success: function(){
                spectacleCollectionView.render();
                $('#table').append(spectacleCollectionView.el);
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
        this.$el.html( this.template( {placeholder: "Введите название спектакля..."} ) );
        $('#filters').append(this.el);
        return this;
    }
});

App.Views.SpectacleView = Backbone.View.extend({
    tagName: 'tr',
    initialize: function(){
        this.model.on('destroy', this.remove, this);
        this.model.on('change', this.render, this);
        this.render();
    },
    events: {
        //'click .more': 'fullInformation',
    },
    template: _.template('<td class="<%=category%>"><%=name%></td><td class="<%=category%>"><%=summary%></td><td class="<%=category%>"><input id="<%=id%>" type="checkbox" ></td>'),
    render: function(){
        this.$el.html( this.template( this.model.toJSON() ) );
        return this;
    },
    remove: function  () {
        this.$el.remove();
    }

});

App.Views.SpectacleCollectionView = Backbone.View.extend({

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
        var spectacle = this.collection.get(id);
        $("#spectacle").val(spectacle.get("name"));
        $("#summary").val(spectacle.get("summary"));
        $(".identifier").attr("id", id);
    },
    addOne: function(spectacle) {
        var spectacleView = new App.Views.SpectacleView({ model: spectacle });
        this.$el.append( spectacleView.render().el );
    },
    render: function() {
        this.$el.empty();
        this.$el.append("<caption>Спектакли</caption><tr><th>Название</th><th>Содержание</th><th>Удалить</th></tr>");
        this.collection.each(this.addOne, this);
        return this;
    }

});
var spectacleCollection = new App.Collections.SpectacleCollection();
spectacleCollection.fetch();

var spectacleCollectionView = new App.Views.SpectacleCollectionView({collection: spectacleCollection});
spectacleCollectionView.render();

var spectacleFormView = new App.Views.SpectacleFormView();
spectacleFormView.render();

var spectacleFiltersView = new App.Views.SpectacleFiltersView();
spectacleFiltersView.render();

$('#table').append(spectacleCollectionView.el);