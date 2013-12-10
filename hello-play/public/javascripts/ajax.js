$( document ).ready(function() {

	var minlength = 1;
    $("#ajax-city").keyup(function() {
    	
    	var that = this,
    	value = $(this).val();
    	
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
			  		//$('#mfg_table').empty();
			  		
				},
				error: function(){
					alert('error');
				}
			});
		}
	});
});
