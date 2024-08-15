package br.com.lecom.oauth.config.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ApiImplicitParams({
		@ApiImplicitParam(name = "page", dataType = "int", paramType = "query", value = "Página de resultados que deseja recuperar (0..N)"),
		@ApiImplicitParam(name = "size", dataType = "int", paramType = "query", value = "Número de registros por página"),
		@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Critério de ordenação no formato: nome-do-campo,asc|desc") })
public @interface ApiPageable {

}
