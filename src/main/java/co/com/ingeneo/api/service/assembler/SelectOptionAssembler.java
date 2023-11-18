package co.com.ingeneo.api.service.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;

import co.com.ingeneo.api.controller.response.SelectOptionGeneric;

public class SelectOptionAssembler {
	
	public static <T extends Serializable> CollectionModel<SelectOptionGeneric> toCollectionModelGeneric(List<T> list,
			Function<T, SelectOptionGeneric> mapper, Class<?> controller) {

		List<SelectOptionGeneric> lista = list.stream().map(mapper).collect(Collectors.toList());
		Link link;

		try {
			Method methodSelect = controller.getMethod("selectOptionGeneric");
			link = linkTo(methodSelect).withSelfRel();
		} catch (NoSuchMethodException | SecurityException e) {
			link = linkTo(controller).withSelfRel();
			e.printStackTrace();
		}

		return CollectionModel.of(lista, link);

	}
	
	public static <T extends Serializable> CollectionModel<SelectOptionGeneric> toCollectionModelGeneric(List<T> list,
			Function<T, SelectOptionGeneric> mapper,Class<?> controller,String method) {

		List<SelectOptionGeneric> lista = list.stream().map(mapper).collect(Collectors.toList());
		Link link;

		try {
			Method methodSelect = controller.getMethod(method);
			link = linkTo(methodSelect).withSelfRel();
		} catch (NoSuchMethodException | SecurityException e) {
			link = linkTo(controller).withSelfRel();
			e.printStackTrace();
		}

		return CollectionModel.of(lista, link);

	}

}