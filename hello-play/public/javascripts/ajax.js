$( document ).ready(function() {
    $("#ajax-city").change(function() {
     	var filter = $("#ajax-city").val();
     	alert(filter);
		$.ajax({
			url: "",
		  	type: 'POST',
		  	async: true,
		  	data: { Filter: filter },
		  	success: function(response){
		  		$('#mfg_table').empty();
		  		
				/*$.each(response.responseText, function(){
					var button = $('<button/>',{
					    text: 'Div text',
					    'class': 'className'
					}).appendTo('#Id von der liste');
					
					var a = $('<a/>',{

					}).appendTo(button);
				});*/
			},
			error: function(){
				alert('error');
			}
		});
	});
});
