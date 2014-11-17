(function(scope) {

	'use strict';

	scope.dropbyke.ui.LocationSelector = Backbone.View
			.extend({

				events : {
					"click .modal .apply-location" : "applyLocation",
					"shown.bs.modal .modal" : "modalShown"
				},

				initialize : function() {
					this.modal = this.$('.modal');
					this.mapContainer = this.$('.modal .modal-body');
					this.lat = this.$('input[data-location-lat]');
					this.lon = this.$('input[data-location-lng]');
					this.address = this.$('input[data-location-address]');
					this.latLon = null;
				},

				applyLocation : function() {
					if (this.latLon) {
						this.lat.val(this.latLon.lat())
						this.lon.val(this.latLon.lng());
						this.marker.setMap(null);
						this.updateAddress();
						this.latLon = null;
					}
					this.modal.modal('hide');
				},

				modalShown : function() {
					this.mapApiReady().then(
							jQuery.proxy(function() {
								var mapOptions = {
									center : new google.maps.LatLng(-34.397,
											150.644),
									zoom : 8,
									mapTypeId : google.maps.MapTypeId.ROADMAP
								};
								var map = new google.maps.Map(this.mapContainer
										.get(0), mapOptions);
								this.initMap(map);
							}, this)).fail(function() {
						alert('Failed to load map api');
					})
				},

				initMap : function(map) {

					var lat = +this.lat.val(), lon = +this.lon.val();

					if (lat && lon) {
						this.latLon = new google.maps.LatLng(lat, lon);
						this.marker = new google.maps.Marker({
							position : this.latLon,
							map : map
						});

						map.panTo(this.latLon);
						map.setZoom(17);
					}
					else {
						if (navigator.geolocation) {
							navigator.geolocation.getCurrentPosition(jQuery.proxy(function(pos) {
								if(this.marker) {
									this.marker.setMap(null);
									this.marker = null;
								}
								this.latLon = new google.maps.LatLng(pos.coords.latitude, pos.coords.longitude);
								this.marker = new google.maps.Marker({
									position : this.latLon,
									map : map
								});

								map.panTo(this.latLon);
								map.setZoom(16);
								
							}, this), function() {});
						}
					}

					google.maps.event.addListener(map, 'click', jQuery.proxy(
							function(e) {
								if (this.marker) {
									this.marker.setMap(null);
								}
								this.latLon = e.latLng;
								this.marker = new google.maps.Marker({
									position : this.latLon,
									map : map
								});
							}, this));
					
					
				},
				
				updateAddress: function() {
					
					if(!this.latLon) return;
					
					var geocoder = new google.maps.Geocoder();
					geocoder.geocode({'latLng': this.latLon}, function(results, status) {
					    if (status == google.maps.GeocoderStatus.OK) {
					      if (results[0]) {
					    	  this.address.val(results[0].formatted_address);
					      } else {
					        alert('No address found');
					      }
					    } else {
					      alert('Getting address failed due to: ' + status);
					    }
					  }.bind(this));
				},

				checkMapLoaded : function() {
					return window['google'] && window['google']['maps']
							&& window['google']['maps']['Map']
				},

				mapApiReady : function() {
					var deferred = jQuery.Deferred();

					var apiKey = dropbyke.config.MAP_API_KEY;
					if (!this.checkMapLoaded()) {
						dropbyke.ui.LocationSelector.mapLoadedCallback = function() {
							deferred.resolve(true);
							dropbyke.ui.LocationSelector.mapLoadedCallback = null;
						};
						var headID = document.getElementsByTagName("head")[0];
						var newScript = document.createElement('script');
						newScript.type = 'text/javascript';
						newScript.src = "http://maps.googleapis.com/maps/api/js?key="
								+ apiKey
								+ "&sensor=false&&callback=dropbyke.ui.LocationSelector.mapLoadedCallback";
						headID.appendChild(newScript);

					} else {
						deferred.resolve(true);
					}

					return deferred.promise();
				}

			});

})(window);