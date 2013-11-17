//console.log.log("In JS");

var CONST_SITES_URL = "histomonapi/sites";

function populateSites ( data ) {
	if ( data.length == 0 ) {
		$("#loading").hide();
		$("#loadmorebtn").hide();
	} else {
		for (i = 0 ; i < data.length ; i ++ ) {
			var copy =  $("#fort_generic").clone();
			var h3obj = copy.find("h4");
			var imgobj = copy.find("img");
			var pobj = copy.find("p");
			h3obj.text ( data[i].name );
			
			var footnote = "&nbsp;&nbsp;<a class='small_link' href='details.html?id=" + data[i].nicename + "'>Details and directions>></a>";
			pobj.html ( "&nbsp;&nbsp;&nbsp;&nbsp;" + data[i].description + footnote );
			imgobj.attr ("src", data[i].imageUrl );
			
			var filename = data[i].imageUrl.substr(0, data[i].imageUrl.lastIndexOf('.'));
			var ext = data[i].imageUrl.substr(data[i].imageUrl.lastIndexOf('.') + 1);;
			var largeFile = filename + "_ls." + ext ;
			var aobj = copy.find("a[rel^='lightbox']");
			aobj.attr("href", largeFile);
			
			copy.removeClass("hiddenblock");
			copy.addClass("fort");
			$("#services").append(copy);
			$("#loading").hide();	
		}
		$("a[rel^='lightbox']").slimbox();
		var startIndexValue = parseInt($("#hid_startindex").attr("value")) + data.length;
		$("#hid_startindex").attr("value", startIndexValue);
		//console.log.log("Set - " + $("#hid_startindex").attr("value"));
	}
}

function loadSites ( params, callback ) {
	var result;
	$.ajax
	({
	  type: "GET",
	  url: CONST_SITES_URL,
	  contentType: "application/json; charset=utf-8",
	  /*beforeSend: function (xhr) {
        xhr.setRequestHeader("Authorization", "Basic cmVzdGFwaXVzZXI6Z3IzM25mMTNsZA==");
	  },*/
	  data:params,
	})	
	.done(function(list) { 
		callback(list);
	})
	.fail(function(data) { /*console.log.log(data);*/ })
}

$(document).ready(function() {
	loadSites ("numofentries=10", populateSites);
});	

$("#loadmorebtn").on("click", function() {
	var startIndexValue = $("#hid_startindex").attr("value");
	if ( parseInt(startIndexValue) > 0 ) {
		loadSites ("numofentries=10&startindex=" + startIndexValue , populateSites);
	}
});
//console.log.log("After JS");