//console.log.log("In Maps JS");

var CONST_SITES_URL = "histomonapi/sites";

function plotSites ( data ) {
	if ( data.length == 0 ) {
		$("#loading").html("No more Results Found.");
	} else {
	
		var myLatlng = new google.maps.LatLng(20.086889,75.695801);
		var mapOptions = {
		zoom: 7,
		center: myLatlng,
		mapTypeId: google.maps.MapTypeId.ROADMAP
		}
		var map = new google.maps.Map(document.getElementById('maps-canvas'), mapOptions);
  
		infoWindows = Array();
		markers = Array();
		var validLocationIndex = 0;
		for (i = 0 ; i < data.length ; i ++ ) {
			var name = data[i].name;
			var location = data[i].location;
			var desc;
			if ( data[i].description != null && data[i].description.length > 200 ) {
				desc = data[i].description.substring(0, 199) + "...";
			} else {
				desc = data[i].description;
			}
			
			desc += "<br/><a class='small_link' href='details.html?id=" + data[i].nicename + "'>More information>></a>";
			
			if ( location != null ) {
				var lat  = location.split(",")[0];
				var longt = location.split(",")[1];
				myLatlng = new google.maps.LatLng(lat,longt);
				markers[i] = new google.maps.Marker({
					position: myLatlng,
					map: map,
					title: name,
					infoWindowIndex : validLocationIndex
				});
				
				var contentString = "<h3>"+ name + "</h3>"+ desc;
				
				var infowindow = new google.maps.InfoWindow({
					content: contentString
				});
				google.maps.event.addListener(markers[i], 'click', function(event) {
					for ( k=0;k<infoWindows.length;k++) {
						infoWindows[k].close();
					}
					map.panTo(event.latLng);
					infoWindows[this.infoWindowIndex].open(map, this);
				});
				infoWindows.push(infowindow);
				validLocationIndex++;
			}
		}
		////console.log.log("map done");
	}
}

function loadSites ( params, callback ) {
	var result;
	$.ajax
	({
	  type: "GET",
	  url: CONST_SITES_URL,
	  contentType: "application/json; charset=utf-8",
	  data:params
	  
	})	
	.done(function(list) { 
		callback(list);
	})
	.fail(function(data) { /*console.log.log(data);*/ })
}

$(document).ready(function() {
	loadSites ("", plotSites);
});	

//console.log.log("After JS");