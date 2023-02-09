package dambi.accessingmongoumeak.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estadios {

    String nombre_del_estadio;
    int capacidad;
    int id;
    String pais;
    String ciudad;
    String equipo;

    Entrenador entrenadores;



}