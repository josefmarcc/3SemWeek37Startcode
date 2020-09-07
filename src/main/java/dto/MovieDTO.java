package dto;

import entities.Movie;

/**
 *
 * @author josef
 */
public class MovieDTO {
    
    private Long id;
    private int year;
    private String title;
    private String[] actors;
    
    public MovieDTO(Movie movie){
        this.id = movie.getId();
        this.year = movie.getYear();
        this.title = movie.getTitle();
        this.actors = movie.getActors();
         
    }
    
    
    
}
