package co.com.ingeneo.api.service;

import org.springframework.hateoas.PagedModel;

import co.com.ingeneo.api.controller.request.OrdenMaritimaRequest;
import co.com.ingeneo.api.controller.request.OrdenTerrestreRequest;
import co.com.ingeneo.api.controller.response.OrdenModel;

public interface OrdenService {

	PagedModel<OrdenModel> getAllOrdenesFiltered(final Integer page, final Integer size, String filter, String sort);
	
	OrdenModel getOrdenById(final Long ordenId);
	
	OrdenModel saveOrdenMaritima(final OrdenMaritimaRequest ordenMaritimaRequest);
	
	OrdenModel saveOrdenTerrestre(final OrdenTerrestreRequest ordenTerrestreRequest);
	
	OrdenModel updateOrdenMaritima(final Long ordenId,final OrdenMaritimaRequest ordenMaritimaRequest);
	
	OrdenModel updateOrdenTerrestre(final Long ordenId,final OrdenTerrestreRequest ordenTerrestreRequest);
	
	void deleteById(final Long ordenId);
	
	void ordenEnviada(final Long ordenId);
	
	void ordenFinalizada(final Long ordenId);

	OrdenModel getOrdenByNumGuia(String numeroGuia);
}