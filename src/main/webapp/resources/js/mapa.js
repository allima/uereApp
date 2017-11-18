var map; // recebera o para
var infoWindow; //outras informaçoes
// serviços para gerar rota
var directionsService;
var directionRenderer;
//localização do sistema
var localizacao;
//icone do entregador
var image = 'imagens/delivery.png';
//ponto do entregador
var marker;
var waypts = []; // pontos para entrega

function entregador(xhr, status, args) {
	var latlng = new google.maps.LatLng(args.coord.latitude, args.coord.longitude);
	marker.setPosition(latlng);

}


function initMap(xhr, status, args) {
	
	
	var qtd_entregas = args.coord.length;

	for (var i = 0; i < args.coord.length; i++) {
		waypts.push({
			location : args.coord[i].latitude + ', ' + args.coord[i].longitude,
			stopover : true,
		});
	}
	// verifica se existe o id na pagina
	if (document.getElementById('map')) {
		// cria o mapa
		map = new google.maps.Map(document.getElementById('map'), {
			center : {
				lat : -20.452458,
				lng : -45.438858
			},
			zoom : 12
		});
	} 

	

	// pega a localização ( IFMG )
	localizacao = {
		lat : -20.452559,
		lng : -45.438597
	};

	// onde o icone será colocado inicialmente
	marker = new google.maps.Marker({
		position : localizacao,
		map : map,
		draggable : false,
		animation : google.maps.Animation.DROP,
		icon : image,
		title : 'Entregador'
	});
	// gerando a rota
	rota(waypts);
	function rota(waypts) {
		var objConfigDR = {
			map : map, // recebe o mapa q sera renderizado a rota
		}

		// configuração da rota (saída-destinos-chegada)
		var objConfigDS = {
			origin : localizacao, //saída
			destination : localizacao, // chegada
			waypoints : waypts, // entregas
			optimizeWaypoints : true, // otimiza a rota
			travelMode : google.maps.TravelMode.DRIVING //modelo de entrega (veículo)
		}
		// chama o serviço da google que retorna a rota
		directionsService = new google.maps.DirectionsService();
		// renderiza no mapa
		directionRenderer = new google.maps.DirectionsRenderer(objConfigDR);

		directionsService.route(objConfigDS, fnRutear);
		// verifica se a resposta foi aceita
		function fnRutear(resultados, status) {
			if (status == 'OK') {
				directionRenderer.setDirections(resultados);
				var ordem = resultados.routes[0].waypoint_order;
				document.getElementById('frmRota:ordemRota').value = ordem;
			} else {
				alert('Nenhuma rota gerada');
			}
		}

	}


	// centraliza o mapa
	// map.setCenter(localizacao); 
	

}

function handleLocationError(browserHasGeolocation, infoWindow, localizacao) {
	infoWindow.setPosition(localizacao);
	infoWindow.setContent(browserHasGeolocation ?
		'Error: The Geolocation service failed.' :
		'Error: Your browser doesn\'t support geolocation.');
}