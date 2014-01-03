$( document ).ready(function() {

	var minlength = 1;
    $("#ajax-city").keyup(function() {
    	
    	var that = this,
    	value = $(this).val();
    	$("#city").empty();
    	if(value.length >= minlength){
    
	     	var filter = $("#ajax-city").val();
	     	//alert(filter);
			$.ajax({
				url: "city/"+filter,
			  	type: 'POST',
			  	async: true,
			  	dataType: 'json',
			  	data: { Filter: filter },
			  	success: function(response){
			  		//alert('test');
			  		//response liefert alle städte
			  		var counter = 0;
			  		
			  		var listStart = $('<ul/>',{
					}).appendTo('#city');
		  			
			  		$(jQuery.parseJSON(JSON.stringify(response))).each(function() {  
			  		if(counter < 5){
			  			//alert(this.name);
			  			
			  			var thead = $('<li/>',{
						    text: this.name
						}).appendTo(listStart);
			  		}
			  		counter++;
			  		});
				},
				error: function(){
					alert('error');
				}
			});
		}
	});
});

