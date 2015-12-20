window.App = {
    Models: {},
    Views: {},
    Collections: {}
};
App.Models.Row = Backbone.Model.extend({
    initialize: function(){
      console.log(this);
    }
});

App.Collections.Table = Backbone.Collection.extend({
    model: App.Models.Row,
    initialize: function(){
        console.log(this);
    },
    url: '/bileton/admin/editor'
});

App.Views.EditorView = Backbone.View.extend({
    tagName: 'form',
    attributes: {
        'role': 'form'
    },
    events: {
        'click #perform': 'perform'
    },
    perform: function(){
        $("#table").empty();
        var table  = new App.Collections.Table();
        //table.fetch({traditional: true, data: $("#query").val(), type: 'POST', success: function(user) {
        //    // fetch successfully completed
        //    console.log(user.toJson());
        //},
        //    error: function() {
        //        console.log('Failed to fetch!');
        //    }});
        var tableView = new App.Views.TableView({collection : table});
        table.fetch({
            data:{query: $("#query").val()},
            success: function(){
                tableView.render();
            },
            error: function(){
                console.log("error");
            }
        });


        $("#table").append(tableView.el);
    },


    template: '<div class="form-group"><label for="spectacle">Запрос:</label><textarea class="form-control" id="query" placeholder="Введите запрос"></textarea></div><div class="form-group"><button type="button" id="perform" class="btn btn-default">Perform</button>',
    initialize: function () {
        this.render();
    },

    render: function(){
        this.$el.html( this.template );
        $('#form').append(this.el);
    }
});

App.Views.RowView = Backbone.View.extend({
    tagName: 'tr',
    initialize: function(){
        this.model.on('destroy', this.remove, this);
        this.model.on('change', this.render, this);
        var res = this.setTemplate();
        this.template = _.template(res);
        this.render();

    },

    events: {
        //'click .more': 'fullInformation',
    },
    setTemplate: function(){
        var res = "";
        var arr = _.keys(this.model.attributes);
        for(var i = 0; i<arr.length; i++){
            res+="<td><%="+arr[i]+"%></td>";
        }
        return res;
    },
    template: "",
    render: function(){
        this.$el.html( this.template( this.model.toJSON() ) );
        return this;
    },
    remove: function  () {
        this.$el.remove();
    }
});

App.Views.TableView = Backbone.View.extend({
    tagName: 'table',
    className: 'table',

    attributes: {
        'rules': 'all'
    },
    initialize: function(){
        this.collection.on('add', this.addOne, this);
    },
    addOne: function(model){
        var rowView = new App.Views.RowView({ model: model });
        this.$el.append( rowView.render().el );
    },
    render: function(){
        this.$el.empty();
        var arr = _.keys(this.collection.models[0].attributes);
        var res = "<caption>Результат</caption><tr>";
        for(var i=0; i<arr.length; i++)
            res += "<th>"+arr[i]+"</th>"
        res +="</tr>";
        this.$el.append(res);
        this.collection.each(this.addOne, this);
        return this;
    }
});


var form = new App.Views.EditorView();
form.render();

$("#form").append(form.el);

