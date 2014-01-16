$(document).ready(function() {
	
	$('a[href*=#]').click(function() {
	    if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'')
	    && location.hostname == this.hostname) {
	      var $target = $(this.hash);
	      $target = $target.length && $target
	      || $('[name=' + this.hash.slice(1) +']');
	      if ($target.length) {
	        var targetOffset = $target.offset().top;
	        $('html,body')
	        .animate({scrollTop: targetOffset}, {duration: 1000, easing: 'easeInOutExpo'});
	       return false;
	      }
	    }
	  });
	  
	  
	  
	  
	
	
	/* lock form on scroll */
	$('.down-tab, .reasons-wrap .webtechnik-blurb h3 span').waypoint(function(event, direction) {
	   if (direction === 'down') {
	      $('.lock-header').animate({
		    top: '0'
		  }, 200, function() {
		    // Animation complete.
		  });
	   }
	   else {
	      $('.lock-header').animate({
		    top: '-70px'
		  }, 200, function() {
		    // Animation complete.
		  });
	   }
	});
});