//Este exemplo exibe um formulário de endereço, usando o recurso de preenchimento automático
// da API do Google Places para ajudar os usuários a preencher as informações.

// Este exemplo requer a biblioteca Places. Inclua as bibliotecas = locais
// parâmetro quando você carrega pela primeira vez a API. Por exemplo:
// <script src = "https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY&libraries=places">
var placeSearch,
	autocomplete;
var componentForm = {
		street_number : 'short_name',
		administrative_area_level_2 : 'long_name',
		administrative_area_level_1 : 'short_name',
		postal_code : 'short_name',
		route : 'long_name'
	};

function initAutocomplete() {
	// Create the autocomplete object, restricting the search to geographical
	// location types.
	autocomplete = new google.maps.places.Autocomplete(
		/** @type {!HTMLInputElement} */ (document.getElementById('route')),
		{
			types : [ 'geocode' ]
		});


	// When the user selects an address from the dropdown, populate the address
	// fields in the form.
	autocomplete.addListener('place_changed', fillInAddress);
}

function fillInAddress() {
	// Get the place details from the autocomplete object.
	var place = autocomplete.getPlace();

	for (var component in componentForm) {
		document.getElementById(component).value = '';
		document.getElementById(component).disabled = false;
	}

	// Get each component of the address from the place details
	// and fill the corresponding field on the form.
	for (var i = 0; i < place.address_components.length; i++) {
		var addressType = place.address_components[i].types[0];
		if (componentForm[addressType]) {
			var val = place.address_components[i][componentForm[addressType]];
			document.getElementById(addressType).value = val;
		}
	}
}

// Bias the autocomplete object to the user's geographical location,
// as supplied by the browser's 'navigator.geolocation' object.
function geolocate() {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			var geolocation = {
				lat : position.coords.latitude,
				lng : position.coords.longitude
			};
			var circle = new google.maps.Circle({
				center : geolocation,
				radius : position.coords.accuracy
			});
			autocomplete.setBounds(circle.getBounds());
		});
	}
}