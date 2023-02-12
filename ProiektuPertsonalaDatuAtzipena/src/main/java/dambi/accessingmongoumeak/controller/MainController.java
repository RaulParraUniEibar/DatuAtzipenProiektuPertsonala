package dambi.accessingmongoumeak.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dambi.accessingmongoumeak.model.Entrenador;
import dambi.accessingmongoumeak.model.Estadios;
import dambi.accessingmongoumeak.model.EstadiosRepository;

@RestController // This means that this class is a Controller baina @Controller bakarrik
				// jarrita, PUT eta DELETEak ez dabiz
@RequestMapping(path = "/estadios") // This means URL's start with /demo (after Application path)
public class MainController {
	@Autowired // This means to get the bean called umeaRepository
				// Which is auto-generated by Spring, we will use it to handle the data
	private EstadiosRepository estadiosrepository;

	@GetMapping(path = "/id/{id}")
	public @ResponseBody Estadios getEstadiosPorId(@PathVariable int id) {
		// This returns a JSON or XML with the stadium of that Id
		return estadiosrepository.findById(id);
	}

	@GetMapping(path = "/allEstadios")
	public @ResponseBody Iterable<Estadios> getAllEstadios() {
		// Devuelve una lista de todos los estadios
		return estadiosrepository.findAll();
	}

	@GetMapping(path = "/{pais}")
	public @ResponseBody Iterable<Estadios> getEstadiosPais(@PathVariable String pais) {
		// Devuelve una lista de estadios del pais
		return estadiosrepository.findByPais(pais);
	}

	@GetMapping(path = "/capacidadMayor/{capacidad}")
	public @ResponseBody Iterable<Estadios> getEstadiosCapacidadMayor(@PathVariable int capacidad) {
		// Devuelve una lista de estadios cuya capacidad sea mayor a X
		return estadiosrepository.findByCapacidadMayor(capacidad);
	}

	@GetMapping(path = "/capacidadMenor/{capacidad}")
	public @ResponseBody Iterable<Estadios> getEstadiosCapacidadMenor(@PathVariable int capacidad) {
		// Devuelve una lista de estadios cuya capacidad sea menor a X
		return estadiosrepository.findByCapacidadMenor(capacidad);
	}

	@GetMapping(path = "/EstadiosEntrenadorEdad/{edad}")
	public @ResponseBody Iterable<Estadios> getEstadiosEntrenadorEdadMayor(@PathVariable int edad) {
		// Devuelve una lista de estadios cuyos entrenadores tengan mas edad de la dada
		return estadiosrepository.findByEntrenadorEdadMayor(edad);
	}

	@GetMapping(path = "/EntrenadoresEdad/{edad}")
	public @ResponseBody List<Entrenador> getEntrenadoresEdadMayor(@PathVariable int edad) {
		// Devuelve una lista de objeto entrenador con sus datos cuya edad sea mayor a
		// la dada
		return estadiosrepository.findEntrenadoresEdad(edad);
	}

	@GetMapping(path = "/Edad/{edad}")
	public @ResponseBody List<Entrenador> getNombreEdadEntrenador(@PathVariable int edad) {
		// Devuelve una lista con el nombre y la edad de los entrenadores con mayor edad
		// del dado
		return estadiosrepository.findByEntrenadorEdadMayores(edad);
	}

	@GetMapping(path = "/entrenadoresExperiencia/{experiencia}")
	public @ResponseBody List<Entrenador> getEntrenadoresConExperiencia(@PathVariable int experiencia) {
		// Devuelve una lista de los entrenadores que hayan entrenado a la cantidad de
		// equipos o mas de la expecificada
		return estadiosrepository.findEntrenadorConEquiposExperiencia(experiencia);
	}

	@DeleteMapping(path = "deleteEstadio/Id{id}")
	public @ResponseBody long deleteEstadio(@PathVariable int id) {
		try {
			long zenbat = estadiosrepository.deleteEstadioById(id);
			return zenbat;
		} catch (Exception ex) {
			System.out.println("Errorea " + id + " stadium ezabatzerakoan.");
		}
		return id;
		// elimina el estadio que tenga ese Id
	}

	@DeleteMapping(path = "deleteEntrenadorEstadio/Id{id}")
	public @ResponseBody long deleteEntrenadorEstadio(@PathVariable int id) {
		try {
			long zenbat = estadiosrepository.deleteCoachOfStadium(id);
			return zenbat;
		} catch (Exception ex) {
			System.out.println("Errorea " + id + " stadium-ko entrenatzailea ezabatzerakoan.");
		}
		return id;
		// elimina el entrenador del estadio que tenga ese Id
	}

	@DeleteMapping(path = "deleteExperienciaEntrenador/Id{id}")
	public @ResponseBody long deleteExperienciaEntrenador(@PathVariable int id) {
		try {
			long zenbat = estadiosrepository.deleteEntrenadorExperienceOfStadium(id);
			return zenbat;
		} catch (Exception ex) {
			System.out.println("Errorea " + id + " stadium-ko entrenatzailearen experientzia ezabatzerakoan.");
		}
		return id;
		// elimina la experiencia del entrenador que quieras
	}

	@PutMapping(value = "/editcapacidad")
	public Estadios editcapacidad(@Valid int id, int capacidad) {
		try {
			Estadios estadio = new Estadios();
			estadio = estadiosrepository.findById(id);
			estadio.setCapacidad(capacidad);

			estadiosrepository.updatecapacidad(estadio);
			return estadio;
		} catch (Exception ex) {
			return null;
		}

	}

	@PutMapping(value = "/editEstadio")
	public Estadios editEstadio(@Valid int id, String nombre_del_estadio, String ciudad, int capacidad) {
		try {
			Estadios estadio = new Estadios();
			estadio = estadiosrepository.findById(id);
			estadio.setNombre_del_estadio(nombre_del_estadio);
			estadio.setCiudad(ciudad);
			estadio.setCapacidad(capacidad);

			estadiosrepository.updateEstadio(estadio);
			return estadio;
		} catch (Exception ex) {
			return null;
		}

	}

	@PutMapping(value = "/editEntrenadorNombre")
	public Estadios editEntrenadorNombre(@Valid int id, String nombreEntrenador) {
		try {
			Estadios estadio = estadiosrepository.findById(id);
			estadio.getEntrenadores().setNombre(nombreEntrenador);
			estadiosrepository.updateEntrenadorNombre(estadio);
			return estadio;
		} catch (Exception ex) {
			return null;
		}
	}
	
}
