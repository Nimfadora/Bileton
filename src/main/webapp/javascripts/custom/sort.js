App.Models.Theatre = Backbone.Model.extend({});

App.Collections.TheatreCollection = Backbone.Collection.extend({
    model: App.Models.Theatre,
    url: "/bileton/admin/theatre/fetch"
});

App.Views.SortView = Backbone.View.extend({
    tagName: 'div',
    className: 'sorttab',
    initialize: function(){
        this.collection.on('add', this.addTheatreOption, this);
        this.render();
        this.renderDatepicker();
    },
    events:{
        'click #search': 'search',
        'click #clear': 'clear',
        'keypress #gogol': function(e){
            if (e.keyCode == 13) {
                $("#search").trigger('click');
            }
        }
    },

    template: _.template('<div id="firstRow"><div class="width"><label for="gogol" class="label">Поиск:</label><input id="gogol" class="form-control input-sm" type="search" style="width: 290px" placeholder="Введите название представления..." ></div><label for="theatre" class="label">Театр:</label><select id="theatre" class="form-control input-sm" style="width: 200px; display: inline"><option>Выберите театр</option></select><label for="from" class="label">Дата от:</label><input id="from" type="text" class="form-control input-sm" placeholder="кликните" style="display: inline; width: 140px" /><label for="to"" class="label">Дата до:</label><input id="to" type="text" class="form-control input-sm" placeholder="кликните" style="display: inline; width: 140px"/></div><br><div id="secondRow"><label for="period" class="label">Период:</label><select id="period" class="input-sm form-control" style="display: inline; width: 100px"><option id="1">Сегодня</option><option id="2">Неделя</option><option selected="selected" id="3">Месяц</option></select><label for="sort" class="label">Сортировать:</label><select id="sort" class="input-sm form-control" style="display: inline; width: 225px"><option value="0">Укажите параметр сортировки</option><option id="1">по возрастанию цены</option><option id="2">по убыванию цены</option><option id="3">по дате</option><option id="4">по алфавиту</option></select><button id="search" class="btn">Найти</button><button id="clear" class="btn">Очистить</button></div>'),

    addTheatreOption: function(theatre){
        $('#theatre').append("<option id='"+theatre.get('id')+"'>"+theatre.get('name')+"</option>");
    },

    clear: function(){
        $("#gogol").val("");
        $("#theatre :first").attr('selected', 'selected');
        $("#sort :first").attr('selected', 'selected');
        $('#from').val("").datepicker( "destroy" );
        $('#to').val("").datepicker( "destroy" );
        this.renderDatepicker();
        $('#plot').empty();
        collectionOfPlays.fetch();
        playCollectionView.render();
        $('#plot').append(playCollectionView.el);
    },

    search: function() {
        collectionOfPlays.fetch({
            data: {
                search: encodeURI($("#gogol").val().toUpperCase()),
                theatre: $("#theatre option:selected").attr("id"),
                dateFrom: $("#from").val(),
                dateTo: $("#to").val(),
                period: $("#period option:selected").attr("id"),
                sort: $("#sort option:selected").attr("id")
            },
            success: function(){
                playCollectionView.render();
                $('#plot').append(playCollectionView.el);
                if(collectionOfPlays.models.length == 0) {
                    $('#plot').append('<img src="/bileton/images/OSCAR.jpg" style="height: 70vh; width: 70%; margin: 0 15% 0 15%;">');
                    $('#plot').append('<h3 style="color: red" align="center">Поиск не дал результатов :)</br>Попробуйте еще раз</h3>');
                }
            },
            error: function(){
                console.log("sort error");
                $('#plot').append('<img src="/bileton/images/OSCAR.jpg" style="height: 70vh; width: 70%; margin: 0 15% 0 15%;">');
                $('#plot').append('<h3 style="color: red" align="center">Поиск не дал результатов :)</br>Попробуйте еще раз</h3>');
            }
        });
    },
    renderDatepicker: function(){
        //$('#from').datepicker({ minDate: 0 });
        var dateToday = new Date();
        var dates = $("#from, #to").datepicker({
            showOtherMonths: true,
            selectOtherMonths: true,
            showButtonPanel: true,
            defaultDate: "+1w",
            changeMonth: true,
            numberOfMonths: 1,
            minDate: dateToday,
            onSelect: function(selectedDate) {
                var option = this.id == "from" ? "minDate" : "maxDate",
                    instance = $(this).data("datepicker"),
                    date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
                dates.not(this).datepicker("option", option, date);
            }
        });
    },
    render: function(){
        this.$el.html(this.template({}));
        $('.sortMenu').append(this.el);
        return this;
    }
});

var theatres = new App.Collections.TheatreCollection();
theatres.fetch();

var sortView = new App.Views.SortView({collection: theatres});