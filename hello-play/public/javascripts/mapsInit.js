
function s1(map, position1, position2){

	  var directionsDisplay;
	  var directionsService = new google.maps.DirectionsService();
	  
	  directionsDisplay = new google.maps.DirectionsRenderer();
	  directionsDisplay.setMap(map);


	/**
	Beispiel: route berechnen - mit optionalen waypoints wird die Route Ã¼ber die angegebenen Stationen berechnet
	*/
	
	  //var start = new google.maps.LatLng(47.67, 9.17); //document.getElementById("start").value;
	  //var end = new google.maps.LatLng(47.6745, 9.1708); //document.getElementById("end").value;
	  var request = {
	    origin:position1,
	    destination:position2,
	    
	    travelMode: google.maps.TravelMode.DRIVING
	  };
	  directionsService.route(request, function(result, status) {
	    if (status == google.maps.DirectionsStatus.OK) {
	      console.log("route. " +result);
	      directionsDisplay.setDirections(result);
	    }else{

	       console.log("route failed. status:" +status +" result:" +result);
	    }
	  });
}

	function initialize() {
		  var mapOptions = {
				  center: new google.maps.LatLng(47.66, 9.16), zoom: 14,
				  mapTypeId: google.maps.MapTypeId.ROADMAP
		  };
		  var map = new google.maps.Map(document.getElementById("map-canvas"),
		  mapOptions);
		  
		  var position2 = new google.maps.LatLng(47.659598,9.177358); 
		 var position1 = new google.maps.LatLng( 47.667431,9.171929);
		s1(map,position1,position2 );
		
	}
	google.maps.event.addDomListener(window, 'load', initialize);