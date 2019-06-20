package es.uji.ei1027.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.ei1027.dao.TipoActividadDao;
import es.uji.ei1027.model.TipoActividad;
import es.uji.ei1027.model.TiposdeActividad;
import es.uji.ei1027.model.TiposdeNiveles;

@Service
public class ActividadSvc implements ActividadService {

	@Autowired
	TipoActividadDao tipoActividaddao;
	
	@Override
	public List<String> getTiposActividad() {
		List<TipoActividad> tipoActividad = tipoActividaddao.getTipoActividad();
		 List<String> nombre = tipoActividad.stream()
		           .map(TipoActividad::getNombreCompleto)
		           .collect(Collectors.toList());
		 
		 
		 
		   return nombre;
	}

	@Override
	public List<String> getTiposdeActidad() {
		List<TiposdeActividad> tiposdeActividad = tipoActividaddao.getTiposdeActividad();
		 List<String> nombre = tiposdeActividad.stream()
		           .map(TiposdeActividad::getNombre)
		           .collect(Collectors.toList());
		return nombre;
	}

	@Override
	public List<String> getTiposdeNivel() {
		List<TiposdeNiveles> tiposdeNivel = tipoActividaddao.getTiposdeNivel();
		 List<String> nombre = tiposdeNivel.stream()
		           .map(TiposdeNiveles::getNombre)
		           .collect(Collectors.toList());
		return nombre;
	}

}
