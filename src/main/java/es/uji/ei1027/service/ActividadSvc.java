package es.uji.ei1027.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.ei1027.dao.TipoActividadDao;
import es.uji.ei1027.model.TipoActividad;

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

}
