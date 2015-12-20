window.App = {
    Models: {},
    Views: {},
    Collections: {}
};
App.Models.Performance = Backbone.Model.extend({
    defaults:{
        troupe_id: -1,
        spectacle_id: -1,
        troupe: "troupe",
        spectacle: "spectacle"
    },
    urlRoot: "/bileton/admin/performance/fetch"
});

App.Models.Troupe = Backbone.Model.extend({
    initialize: function(){
    },
    defaults:{
        name: "troupe"
    }
});

App.Models.Spectacle = Backbone.Model.extend({
    initialize: function(){
    },
    defaults:{
        name: "spectacle",
        summary: "summary"
    }
});

App.Collections.PerfCollection = Backbone.Collection.extend({
    model: App.Models.Performance,
    initialize: function(){
    },
    url: '/bileton/admin/performance/fetch'
});

App.Collections.TroupeCollection = Backbone.Collection.extend({
    model: App.Models.Troupe,
    initialize: function(){
    },
    url: '/bileton/admin/troupe/fetch'
});

App.Collections.SpectacleCollection = Backbone.Collection.extend({
    model: App.Models.Spectacle,
    initialize: function(){
    },
    url: '/bileton/admin/spectacle/fetch'
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
        $("#spectacle :contains(\"Выберите спектакль\")").attr("selected", "selected");
        $("#troupe :contains(\"Выберите труппу\")").attr("selected", "selected");
        $(".identifier").attr("id", "-1");
    },
    create: function(){
        var model = new App.Models.Performance({
            troupe_id: $("#troupe :selected").attr("id"),
            spectacle_id: $("#spectacle :selected").attr("id")
        });
        model.save();
    },
    update: function(){
        var model = new App.Models.Performance({
            id: $(".identifier").attr("id"),
            troupe_id: $("#troupe :selected").attr("id"),
            spectacle_id: $("#spectacle :selected").attr("id")
        });
        model.save();
    },
    delete: function(){
        var selected = this.findChecked();
        _.each(_.clone(collectionOfPerfs.models), function(model) {
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
    template: _.template($('#perfFormTemplate').html()),
    initialize: function () {
        this.collection.on('add', this.addTroupeOption, this);
        spectacleCollection.on('add', this.addSpectacleOption, this );
        this.render();
    },
    addTroupeOption: function(troupe){
        $('#troupe').append("<option id='"+troupe.get('id')+"'>"+troupe.get('name')+"</option>");
    },
    addSpectacleOption: function(spectacle){
        $('#spectacle').append("<option id='"+spectacle.get("id")+"'>"+spectacle.get('name')+"</option>");
    },
    render: function(){
        this.$el.html( this.template( {} ) );
        $('#form').append(this.el);
        //this.collection.each(this.addTroupeOption, this);
        //spectacleCollection.each(this.addSpectacleOption, this)
    }
});

App.Views.PerfView = Backbone.View.extend({
    tagName: 'tr',
    initialize: function(){
        this.model.on('destroy', this.remove, this);
        this.model.on('change', this.render, this);
        this.render();
    },
    events: {
        //'click .more': 'fullInformation',
    },
    template: _.template('<td><%=troupe%></td><td><%=spectacle%></td><td><input id="<%=id%>" type="checkbox" ></td>'),
    render: function(){
        this.$el.html( this.template( this.model.toJSON() ) );
        return this;
    },
    remove: function  () {
        this.$el.remove();
    }

});

App.Views.PerfCollectionView = Backbone.View.extend({

    tagName: 'table',
    className: 'table',

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
        var perf = this.collection.get(id);
        $("#spectacle :contains(\""+perf.get("spectacle")+"\")").attr("selected", "selected");
        $("#troupe :contains(\""+perf.get("troupe")+"\")").attr("selected", "selected");
        $(".identifier").attr("id", id);
    },
    addOne: function(perf) {
        var perfView = new App.Views.PerfView({ model: perf });
        this.$el.append( perfView.render().el );
    },
    render: function() {
        this.$el.empty();
        this.$el.append("<caption>Представления</caption><tr><th>Труппа</th><th>Спектакль</th><th>Удалить</th></tr>");
        //this.collection.each(this.addOne, this);
        return this;
    }

});

var collectionOfPerfs = new App.Collections.PerfCollection();
collectionOfPerfs.fetch();

var perfCollectionView = new App.Views.PerfCollectionView({collection: collectionOfPerfs});
perfCollectionView.render();

var troupeCollection = new App.Collections.TroupeCollection();
troupeCollection.fetch();

var spectacleCollection = new App.Collections.SpectacleCollection();
spectacleCollection.fetch();

var perfFormView = new App.Views.FormView({collection: troupeCollection});
perfFormView.render();

$('#table').append(perfCollectionView.el);
