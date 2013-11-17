//console.log.log("In Details JS");

var CONST_SITES_URL = "histomonapi/site";
var CONST_SAVE_SITES_URL = "histomonapi/savesite";
var map;
var marker;

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
	$("#fort_city").val( trm2(data.city));
	$("#fort_district").val( trm2(data.district));
	$("#fort_elevation").val( trm2(data.details.elevation));
	$("#hike_level").val(trm2(data.hike));
	$("#fort_name").val(data.name);
	
	$("#fort_desc").val(data.description);
	$("#fort_reach").val(trm2(data.details.hwtoreach));
	$("#fort_trekking").val(trm2(data.details.trektips));
	$("#fort_overnight").val(trm2(data.details.ostay));
	$("#fort_eat").val(trm2(data.details.pltoeat));
	$("#fort_location_c").val ( data.location);
	$("#fort_id").val(data.id);
	$("#fort_nicename").val(data.nicename);
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
	map = new google.maps.Map(document.getElementById('maps-canvas'), mapOptions);

	marker = new google.maps.Marker({
		position: myLatlng,
		map: map,
		title: name
	});
	
	google.maps.event.addListener(map, 'dblclick', function(event) {
		placeMarker(event.latLng);
	});
  
	//console.log.log("map done");
}	

function placeMarker(location) {
  marker.setMap(null);
  marker = new google.maps.Marker({
      position: location,
      map: map
  });
  var lval = "" + location.lat() + "," + location.lng();
   $("#fort_location_c").attr("size", lval.length);
  $("#fort_location_c").val ( lval);
  event.preventDefault();
}

function saveSite ( dataStr ) {
	$.ajax
	({
	  type: "POST",
	  url: CONST_SAVE_SITES_URL,
	  contentType: "application/json; charset=utf-8",
	  data: dataStr
	 })	
	.done(function(data) { 
		if ( data == "Success") location.href = "details.html?id=" + $("#fort_nicename").val();
	})
	.fail(function(data) { /*console.log.log(data);*/ })
}


$("#savefort").on("click", function() {
		var data = {
			"name" : $("#fort_name").val(),
			"description" : $("#fort_desc").val(),
			"city" : $("#fort_city").val(),
			"district" : $("#fort_district").val(),
			"hike" : $("#hike_level").val(),
			"details" : {
				"elevation" : $("#fort_elevation").val(),
				"hwtoreach" : $("#fort_reach").val(),
				"trektips" : $("#fort_trekking").val(),
				"ostay" : $("#fort_overnight").val(),
				"pltoeat" : $("#fort_eat").val()
			},
			
			"id" : $("#fort_id").val(),
			"location" : $("#fort_location_c").val()
		};
		
		saveSite ( JSON.stringify( data) );
		
});

$("#resetfort").on("click", function() {
	location.reload();
});

$(document).ready(function() {
	$("#services").show();
	var siteId = $.url().param('id');
	if ( siteId != undefined && siteId != null ) {
		loadSite( siteId, renderSite );
	}
	$("#loading").hide();
});	

//console.log.log("Details JS Complete");