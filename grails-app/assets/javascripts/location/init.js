(function($) {
	'use strict';

	$(function() {
		var selectorStart = new dropbyke.ui.LocationSelector({
			el: $(".location-start")
		});
		
		var selectorStop = new dropbyke.ui.LocationSelector({
			el: $(".location-stop")
		});
	});
	
})(jQuery);