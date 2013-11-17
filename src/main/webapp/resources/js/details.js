//console.log.log("In Details JS");

var CONST_SITES_URL = "histomonapi/site";

function loadSite ( id, callback ) {
	$.ajax
	({
	  type: "GET",
	  url: CONST_SITES_URL + "/" + id,
	  contentType: "application/json; charset=utf-8"
	})	
	.done(function(data) { 
		callback(data);
	})
	.fail(function(data) { /*console.log.log(data);*/ })
}

function trm ( data, data2 ) { if ( data == undefined || data == null )  {return ""; } else { return data + data2; } }
function trm2 ( data ) { if ( data == undefined || data == null )  {return ""; } else {return data;}}

function renderSite ( data ) {
	$("#fort_image").attr("src", data.imageUrl);
	$("#fort_location").text( trm(data.city, ",") + trm(data.state, ",") + data.country );
	$("#fort_elevation").text( trm(data.details.elevation, " m"));
	$("#hike_level").text(trm2(data.hike));
	$("#fort_name").text(data.name);
	$("#fort_desc").html(data.description);
	$("#fort_reach").html(trm2(data.details.hwtoreach));
	$("#fort_trekking").html(trm2(data.details.trektips));
	$("#fort_overnight").html(trm2(data.details.ostay));
	$("#fort_eat").html(trm2(data.details.pltoeat));
	
	var filename = data.imageUrl.substr(0, data.imageUrl.lastIndexOf('.'));
	var ext = data.imageUrl.substr(data.imageUrl.lastIndexOf('.') + 1);;
	var largeFile = filename + "_ls." + ext ;
	$("#lbox_link").attr("href", largeFile);
	plotSite(data.location);
	
}
function plotSite( location ) {
	var zoomval = 11;
	if ( location == undefined || location == null ) { location = "20.086889,75.695801" ; zoomval = 4;}
	var myLatlng = new google.maps.LatLng(location.split(",")[0],location.split(",")[1]);
	var mapOptions = {
		zoom: zoomval,
		center: myLatlng,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	}
	var map = new google.maps.Map(document.getElementById('maps-canvas'), mapOptions);

	var marker = new google.maps.Marker({
		position: myLatlng,
		map: map,
		title: name
	});
	//console.log.log("map done");
}	


$(document).ready(function() {
	var siteId = $.url().param("id");
	if ( siteId != undefined && siteId != null ) {
		loadSite( siteId, renderSite );
		$("#services").show();
	}
	$("#loading").hide();
});	

//console.log.log("Details JS Complete");