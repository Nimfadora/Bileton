
var collectionOfPlays = new App.Collections.PlayCollection();
collectionOfPlays.fetch();

var playCollectionView = new App.Views.PlayCollectionView({collection: collectionOfPlays});
playCollectionView.render();

var ticket = new App.Models.Ticket();

$('#plot').append(playCollectionView.el);