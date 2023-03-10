package dambi.accessingmongoumeak.model;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface EstadiosRepository {
    
    //Todos los metodos que usamos en el controller

    Estadios findById(int id);
    List<Estadios> findAll();
    List<Estadios> findByPais(String pais);
    List<Estadios> findByCapacidadMayor(int capacidad);
    List<Estadios> findByCapacidadMenor(int capacidad);
    List<Estadios> findByEntrenadorEdadMayor(int edad);
    List<Entrenador> findEntrenadoresEdad(int edad);
    List<Entrenador>    findByEntrenadorEdadMayores(int edad);
    List<Entrenador>findEntrenadorConEquiposExperiencia(int entrenados);
    
    public long deleteEstadioById(int id);
    public long deleteCoachOfStadium(int id);
    public long deleteEntrenadorExperienceOfStadium(int id);
    
    public Estadios updatecapacidad(Estadios estadio);
    public Estadios updateEstadio(Estadios estadio);
    public Estadios updateEntrenador(Estadios estadio);

    public Estadios save(Estadios estadio);
}
