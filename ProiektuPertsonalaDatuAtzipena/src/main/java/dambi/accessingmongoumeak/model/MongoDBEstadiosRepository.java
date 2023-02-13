package dambi.accessingmongoumeak.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

@Repository
public class MongoDBEstadiosRepository implements EstadiosRepository {

    @Autowired
    private MongoClient client;
    private MongoCollection<Estadios> estadiosCollection;

    @PostConstruct
    void init() {
        estadiosCollection = client.getDatabase("Futbol").getCollection("Estadios",
                Estadios.class);
    }

    // buscar todos los estadios
    @Override
    public List<Estadios> findAll() {
        return estadiosCollection.find().into(new ArrayList<>());
    }

    // buscar el estadio por la ID
    @Override
    public Estadios findById(int id) {
        return estadiosCollection.find(Filters.eq("_id", id)).first();
    }

    // buscar estadios del pais que nos dan
    @Override
    public List<Estadios> findByPais(String pais) {
        return estadiosCollection.find(Filters.eq("pais", pais)).into(new ArrayList<>());
    }

    // buscar estadios con mayor capacidad que la dada
    @Override
    public List<Estadios> findByCapacidadMayor(int capacidad) {
        return estadiosCollection.find(new Document("capacidad", new Document("$gt", capacidad)))
                .into(new ArrayList<>());
    }

    // buscar estadios con menor capacidad que la dada
    @Override
    public List<Estadios> findByCapacidadMenor(int capacidad) {
        return estadiosCollection.find(new Document("capacidad", new Document("$lt", capacidad)))
                .into(new ArrayList<>());
    }

    // buscar entrenadores con mayor edad
    @Override
    public List<Estadios> findByEntrenadorEdadMayor(int edad) {
        return estadiosCollection.find(new Document("entrenadores.edad", new Document("$gt", edad)))
                .into(new ArrayList<>());
    }

    // buscar entrenadores con mayor edad -Lista Objetos
    @Override
    public List<Entrenador> findEntrenadoresEdad(int edad) {
        return estadiosCollection.find(new Document("entrenadores.edad", new Document("$gt", edad)))
                .into(new ArrayList<>())
                .stream()
                .map(estadio -> estadio.getEntrenadores())
                .collect(Collectors.toList());
    }

    // buscar entrenadores con mayor edad pero solo con los datos que queramos
    @Override
    public List<Entrenador> findByEntrenadorEdadMayores(int edad) {
        List<Estadios> estadios = estadiosCollection.find(new Document("entrenadores.edad", new Document("$gt", edad)))
                .into(new ArrayList<>());
        List<Entrenador> entrenadores = new ArrayList<>();
        for (Estadios estadio : estadios) {
            Entrenador entrenador = estadio.getEntrenadores();
            entrenadores.add(new Entrenador(entrenador.getNombre(), entrenador.getEdad(), null));
        }
        return entrenadores;
    }

    // buscar entrenadores que tengan igual o mas equipos entrenados que la cantidad dada
    @Override
    public List<Entrenador> findEntrenadorConEquiposExperiencia(int entrenados) {
        List<Estadios> stadiums = estadiosCollection.find().into(new ArrayList<>());
        List<Entrenador> experienciaentrenador = new ArrayList<>();
        for (Estadios stadium : stadiums) {
            Entrenador entrenador = stadium.getEntrenadores();
            if (entrenador.getExperiencia().size() >= entrenados) {
                experienciaentrenador.add(entrenador);
            }
        }
        return experienciaentrenador;
    }

    // eliminar un estadio por la Id
    @Override
    public long deleteEstadioById(int id) {
        return estadiosCollection.deleteOne(new Document("_id", id)).getDeletedCount();
    }

    // eliminar el entrenador de un estadio sabiendo la Id
    @Override
    public long deleteCoachOfStadium(int id) {
        return estadiosCollection.updateOne(new Document("_id", id),
                new Document("$unset", new Document("entrenadores", ""))).getModifiedCount();

    }

    // eliminar la experiencia de un entreador sabiendo la Id
    @Override
    public long deleteEntrenadorExperienceOfStadium(int id) {
        return estadiosCollection.updateOne(new Document("_id", id),
                new Document("$unset", new Document("entrenadores.experiencia", ""))).getModifiedCount();
    }

    // actualizar la capacidad de un estadio
    @Override
    public Estadios updatecapacidad(Estadios estadio) {
        estadiosCollection.updateOne(Filters.eq("_id", estadio.getId()),
                new Document("$set", new Document("capacidad", estadio.getCapacidad())));
        return estadio;
    }

    // actualizar un estadio, el entrenador no
    @Override
    public Estadios updateEstadio(Estadios estadio) {
        estadiosCollection.updateOne(Filters.eq("_id", estadio.getId()),
                new Document("$set", new Document("capacidad", estadio.getCapacidad())
                        .append("nombre_del_estadio", estadio.getNombre_del_estadio())
                        .append("ciudad", estadio.getCiudad())));
        return estadio;
    }

    // actualizar el entrenador de un equipo/estadio
    @Override
    public Estadios updateEntrenador(Estadios estadio) {
        estadiosCollection.updateOne(Filters.eq("_id", estadio.getId()),
                new Document("$set", new Document("entrenadores.nombre", estadio.getEntrenadores().getNombre())
                .append("entrenadores.edad", estadio.getEntrenadores().getEdad())
                        .append("entrenadores.experiencia", estadio.getEntrenadores().getExperiencia())));
        return estadio;
    }

    // guardar un estadio completo
    @Override
    public Estadios save(Estadios estadio) {
        estadiosCollection.insertOne(estadio);
        return estadio;
    }

}