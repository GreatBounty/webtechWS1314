$( document ).ready(function() {
    $(".showStrecken").click(function() {
    	
    	var id = $(this).attr('id');
        //alert("id: " + id);
        var value = $(this).attr('value');
        //alert("value: " + value);
     	
		$.ajax({
			url: "mapcoord/"+id,
		  	type: 'POST',
		  	async: true,
		  	dataType: 'json',
		  	data: { ID: id },
		  	success: function(response){
		  		
		  		var lonlatArray = $(jQuery.parseJSON(JSON.stringify(response)));

		  		var start = new Array();
		  		var ziel = new Array();
		  		lonlatArray.each(function(itemArray){
		  		
		  			var lonlat = lonlatArray[itemArray];
		  			
		  			lonlatArray.each(function(item){
		  				if(itemArray == 0){
		  					start[item] = lonlat[item];
		  				}else{
		  					ziel[item] = lonlat[item];
		  				}
			  		    //alert("heyhooooooo"+lonlat[item]);
			  		});
		  			
		  		   //alert("heyho");
		  		});
		  		
		  
		  	var divmap = $('<div/>',{
		  		}).appendTo("#"+id);
        
		  	var thStart = $('<div/>',{
		  		id: 'map-canvas'
		  		}).appendTo(divmap);
		  	
		  	var img = $('<img/>',{
		  		//&path=color:0x0000ff|weight:5|"+start[0]","+start[1]+"|"+ziel[0]+","+ziel[1]+""
		  		src: "http://maps.google.com/maps/api/staticmap?center="+start[0]+","+start[1]+"&zoom=13&size=800x500&maptype=roadmap&sensor=false&markers=color:green|label:none|"+start[0]+","+start[1]+"&markers=color:green|label:none|"+ziel[0]+","+ziel[1]+"&path=color:0x0000ff|weight:5|"+start[0]+","+start[1]+"|"+ziel[0]+","+ziel[1]
			}).appendTo(thStart);
		  	
			},
			error: function(){
				alert('error');
			}
		});
        
    	
	});
});
