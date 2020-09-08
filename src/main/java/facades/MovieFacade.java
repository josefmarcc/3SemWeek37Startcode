package facades;

import entities.Movie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private MovieFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getMovieFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    //TODO Remove/Change this before use
    public long getMovieCount(){
        EntityManager em = emf.createEntityManager();
        try{
            long renameMeCount = (long)em.createQuery("SELECT COUNT(r) FROM Movie r").getSingleResult();
            return renameMeCount;
        }finally{  
            em.close();
        }
        
    }
    public Movie createMovie(int year, String title, String[] actors) {
        Movie movie = new Movie(year, title, actors);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
            return movie;
        } finally {
            em.close();
        }
    }

    public Movie getMovieById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Movie movie = em.find(Movie.class, id);
            return movie;
        } finally {
            em.close();
        }
    }

    public Movie getMovieByTitle(String title) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("Select m FROM Movie m WHERE m.title = :title");
            query.setParameter("title", title);
            Movie movie = (Movie) query.getSingleResult();
            return movie;
        } finally {
            em.close();
        }
    }

    public List<Movie> getAllMovies() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Movie> query
                    = em.createQuery("Select m from Movie m", Movie.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    
     public void populateDB(){
            EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new Movie(1986, "Topgun", new String[]{"Tom Cruise", "Val Kilmer","Kelly McGills"}));
            em.persist(new Movie(2003, "Kill Bill", new String[]{"Uma Thurman", "Daryl Hannah"}));
            em.persist(new Movie(1991, "Point Break", new String[]{"Patrick Swayze", "Keanu Reeves"}));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
     
     public void deleteAllMovies(){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
}
