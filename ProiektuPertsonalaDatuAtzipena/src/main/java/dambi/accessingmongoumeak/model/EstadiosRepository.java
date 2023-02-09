package dambi.accessingmongoumeak.model;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface EstadiosRepository {
    
    Estadios findById(int id);
    List<Estadios> findAll();
    List<Estadios> findByPais(String pais);
    List<Estadios> findByCapacidadMayor(int capacidad);
    List<Estadios> findByCapacidadMenor(int capacidad);
    List<Estadios> findByEntrenadorEdadMayor(int edad);
    List<Entrenador> findEntrenadoresEdad(int edad);
    List<Entrenador>    findByEntrenadorEdadMayores(int edad);
    List<Entrenador>findEntrenadorConEquiposExperiencia(int entrenados);

    
}
