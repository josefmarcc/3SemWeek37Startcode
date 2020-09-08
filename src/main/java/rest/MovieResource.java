package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.MovieDTO;
import entities.Movie;
import utils.EMF_Creator;
import facades.MovieFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("movie")
public class MovieResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final MovieFacade FACADE = MovieFacade.getMovieFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        long count = FACADE.getMovieCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllEmployees() {
        EntityManager em = EMF.createEntityManager();
        try {
            List<Movie> movies = FACADE.getAllMovies();
            return GSON.toJson(movies);
        } finally {
            em.close();
        }
    }
    
    @Path("id/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMovieById(@PathParam("id") long id) {
        EntityManager em = EMF.createEntityManager();
        try {
            Movie movie = FACADE.getMovieById(id);
            MovieDTO movieDTO = new MovieDTO(movie);
            return GSON.toJson(movieDTO);
        } finally {
            em.close();
        }
    }
    
    @Path("title/{title}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMovieByTitle(@PathParam("title") String title) {
        EntityManager em = EMF.createEntityManager();
        try {
            Movie movie = FACADE.getMovieByTitle(title);
            MovieDTO movieDTO = new MovieDTO(movie);
            return GSON.toJson(movieDTO);
        } finally {
            em.close();
        }
    }
    
    @GET
    @Path("/populate")
    @Produces({MediaType.APPLICATION_JSON})
    public String populate() {
        FACADE.populateDB();
        return "{\"msg\":\"3 rows added\"}";
    }
    
    @GET
    @Path("/unpopulate")
    @Produces({MediaType.APPLICATION_JSON})
    public String unpopulate() {
        FACADE.deleteAllMovies();
        return "{\"msg\":\"All rows deleted\"}";
    }
    
    

}
