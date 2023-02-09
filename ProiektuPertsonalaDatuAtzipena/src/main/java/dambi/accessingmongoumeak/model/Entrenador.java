package dambi.accessingmongoumeak.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Entrenador {

    String nombre;
    int edad;

    List<String> experiencia;
    

}
