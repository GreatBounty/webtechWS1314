$( document ).ready(function() {
    $(".strecken").change(function() {
    	
     	var filter = $(".strecken").val();
     	//alert(filter);
     	var map = new google.maps.Map(document.getElementById("map-canvas"));
     	
     	var position2 = new google.maps.LatLng(47.659598,9.177358); //Konstanz BHF
     	var position1 = new google.maps.LatLng(47.667431,9.171929); //Konstanz HTWG 
     	
     	if(filter == "s2"){
     		var position2 = new google.maps.LatLng(47.678696,8.974753); //Gaienhofen
	  		var position1 = new google.maps.LatLng(47.694292,8.993289);	//Horn
     	}
     	
     	if(filter == "s3"){
     		var position2 = new google.maps.LatLng(47.694292,8.993289); //Konstanz HTWG
	  		var position1 = new google.maps.LatLng(47.667431,9.171929); //Gaienhofen
     	}
     	if(filter == "s4"){
     		var position2 = new google.maps.LatLng(47.764064,8.853396); //Singen
	  		var position1 = new google.maps.LatLng(47.667431,9.171929);	//Konstanz HTWG
     	}
    
     	document.getElementById("hidden_startlon").value = position1.nb;
     	document.getElementById("hidden_startlat").value = position1.ob;
     	
     	document.getElementById("hidden_ziellon").value = position2.nb;
     	document.getElementById("hidden_ziellat").value = position2.ob;
     	
     	//alert(document.getElementById("hidden_startlon").value);
     	
     	s1(map,position1,position2 );	

	});
});

$( document ).ready(function() {
    $(".streckenClick").click(function() {
    	
     	var filter = $(this).attr('value');
     	//alert(filter);
     	var map = new google.maps.Map(document.getElementById("map-canvas"));
     	
     	var position2 = new google.maps.LatLng(47.659598,9.177358); //Konstanz BHF
     	var position1 = new google.maps.LatLng(47.667431,9.171929); //Konstanz HTWG 
     	
     	if(filter == "s2"){
     		var position2 = new google.maps.LatLng(47.678696,8.974753); //Gaienhofen
	  		var position1 = new google.maps.LatLng(47.694292,8.993289);	//Horn
     	}
     	
     	if(filter == "s3"){
     		var position2 = new google.maps.LatLng(47.694292,8.993289); //Konstanz HTWG
	  		var position1 = new google.maps.LatLng(47.667431,9.171929); //Gaienhofen
     	}
     	if(filter == "s4"){
     		var position2 = new google.maps.LatLng(47.764064,8.853396); //Singen
	  		var position1 = new google.maps.LatLng(47.667431,9.171929);	//Konstanz HTWG
     	}
    
     	document.getElementById("hidden_startlon").value = position1.nb;
     	document.getElementById("hidden_startlat").value = position1.ob;
     	
     	document.getElementById("hidden_ziellon").value = position2.nb;
     	document.getElementById("hidden_ziellat").value = position2.ob;
     	
     	//alert(document.getElementById("hidden_startlon").value);
     	
     	s1(map,position1,position2 );	

	});
});
