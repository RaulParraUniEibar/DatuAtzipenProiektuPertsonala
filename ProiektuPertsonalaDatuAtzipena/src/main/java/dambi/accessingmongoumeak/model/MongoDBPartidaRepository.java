package dambi.accessingmongoumeak.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

@Repository
public class MongoDBPartidaRepository implements EstadiosRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY)
            .build();
    @Autowired
    private MongoClient client;
    private MongoCollection<Estadios> estadiosCollection;

    @PostConstruct
    void init() {
        estadiosCollection = client.getDatabase("Futbol").getCollection("Estadios",
                Estadios.class);
    }

    // ikusi stadium guztiak
    @Override
    public List<Estadios> findAll() {
        return estadiosCollection.find().into(new ArrayList<>());
    }

    // ikusi partida guztiak Id-a bilatzen
    @Override
    public Estadios findById(int id) {
        return estadiosCollection.find(Filters.eq("_id", id)).first();
    }

    // ikusi stadiums from that country
    @Override
    public List<Estadios> findByPais(String pais) {
        return estadiosCollection.find(Filters.eq("pais", pais)).into(new ArrayList<>());
    }

    // ikusi stadiums with more capacity than the given one
    @Override
    public List<Estadios> findByCapacidadMayor(int capacidad) {
        return estadiosCollection.find(new Document("capacidad", new Document("$gt", capacidad)))
                .into(new ArrayList<>());
    }


    // ikusi stadiums with less capacity than the given one
    @Override
    public List<Estadios> findByCapacidadMenor(int capacidad) {
        return estadiosCollection.find(new Document("capacidad", new Document("$lt", capacidad)))
                .into(new ArrayList<>());
    }

    // entrenadores con mayor edad 
    @Override
    public List<Estadios> findByEntrenadorEdadMayor(int edad) {
        return estadiosCollection.find(new Document("entrenadores.edad", new Document("$gt", edad)))
                .into(new ArrayList<>());
    }

    // entrenadores con mayor edad -Lista Objetos
    @Override
    public List<Entrenador> findEntrenadoresEdad(int edad) {
        return estadiosCollection.find(new Document("entrenadores.edad", new Document("$gt", edad)))
                .into(new ArrayList<>())
                .stream()
                .map(estadio -> estadio.getEntrenadores())
                .collect(Collectors.toList());
    }

    // entrenadores con mayor edad pero solo con los datos que queramos
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

    // entrenadores que tengan igual o mas equipos entrenados que la cantidad dada
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

}