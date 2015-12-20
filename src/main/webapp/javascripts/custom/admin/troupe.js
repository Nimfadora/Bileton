window.App = {
    Models: {},
    Views: {},
    Collections: {}
};

App.Models.Troupe = Backbone.Model.extend({
    initialize: function(){
    },
    defaults:{

    },
    urlRoot: "/bileton/admin/troupe/fetch"
});

App.Collections.TroupeCollection = Backbone.Collection.extend({
    model: App.Models.Troupe,
    initialize: function(){
    },
    url: '/bileton/admin/troupe/fetch'
});

App.Views.TroupeFormView = Backbone.View.extend({
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
        $("#troupe").val("");
        $(".identifier").attr("id", "-1");
    },
    create: function(){
        var model = new App.Models.Troupe({
            name: $("#troupe").val()
        });
        model.save();
    },
    update: function(){
        var model = new App.Models.Troupe({
            id: $(".identifier").attr("id"),
            name: $("#troupe").val()
        });
        model.save();
    },
    delete: function(){
        var selected = this.findChecked();
        _.each(_.clone(troupeCollection.models), function(model) {
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
    template: _.template($('#troupeFormTemplate').html()),
    initialize: function () {
        this.render();
    },
    render: function(){
        this.$el.html( this.template( {} ) );
        $('#form').append(this.el);
        return this;
    }
});

App.Views.TroupeFiltersView = Backbone.View.extend({
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
        troupeCollection.fetch({
            success: function(){
                troupeCollectionView.render();
                $('#table').append(troupeCollectionView.el);
            }
        });
    },
    search: function(){
        console.log("searching");
        var sort = $("#sort option:selected").attr('id');
        sort = (sort == 0)? "": (sort == 1) ? "ASC": "DESC";
        troupeCollection.fetch({
            data: {
                search: encodeURI($("#search").val().toUpperCase()),
                sort: sort
            },
            success: function(){
                troupeCollectionView.render();
                $('#table').append(troupeCollectionView.el);
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
        this.$el.html( this.template( {placeholder: "Введите название труппы..."} ) );
        $('#filters').append(this.el);
        return this;
    }
});

App.Views.TroupeView = Backbone.View.extend({
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

App.Views.TroupeCollectionView = Backbone.View.extend({

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
        var troupe = this.collection.get(id);
        $("#troupe").val(troupe.get("name"));
        $(".identifier").attr("id", id);
    },
    addOne: function(troupe) {
        var troupeView = new App.Views.TroupeView({ model: troupe });
        this.$el.append( troupeView.render().el );
    },
    render: function() {
        this.$el.empty();
        this.$el.append("<caption>Труппы</caption><tr><th>Название</th><th>Удалить</th></tr>");
        this.collection.each(this.addOne, this);
        return this;
    }

});
var troupeCollection = new App.Collections.TroupeCollection();
troupeCollection.fetch();

var troupeCollectionView = new App.Views.TroupeCollectionView({collection: troupeCollection});
troupeCollectionView.render();

var troupeFormView = new App.Views.TroupeFormView();
troupeFormView.render();

var troupeFiltersView = new App.Views.TroupeFiltersView();
troupeFiltersView.render();


$('#table').append(troupeCollectionView.el);