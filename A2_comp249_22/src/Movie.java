// -----------------------------------------------------
// Assignment #2
// COMP 249
// Written by: Mobina Gerayely Moghadam 40258626 , Rojan Nessari 40255637
// -----------------------------------------------------
/**
 * The Movie class implements the Serializable interface to support serialization.
 * @author Mobina Gerayely Moghadam 40258626 , Rojan Nessari 40255637
 * @see java.io.Serializable
 */

import java.io.Serializable;
import java.util.Objects;

public class Movie implements Serializable {
    private  int year;
    private int duration;
    private double score;
    private String title;

    private String genre;
    private String rating;

    private String director;
    private String actor1;
    private String actor2;
    private String actor3;

    public Movie() {
	// TODO Auto-generated constructor stub
    }  
    
    /**
     * Parameterized constructor for the Movie class.
     * Initializes movie attributes with provided values.
     *
     * @param year The year of the movie's release.
     * @param title The title of the movie.
     * @param duration The duration of the movie in minutes.
     * @param genre The genre of the movie.
     * @param rating The rating of the movie.
     * @param score The score or rating of the movie.
     * @param director The director of the movie.
     * @param actor1 The first actor of the movie.
     * @param actor2 The second actor of the movie.
     * @param actor3 The third actor of the movie.
     */
	public Movie(int year, String title, int duration, String genre, String rating, double score, String director,
			String actor1, String actor2, String actor3) {
		this.year=year;
		this.title=title;
		this.duration= duration;
		this.genre=genre;
		this.rating=rating;
		this.score=score;
		this.director=director;
		this.actor1=actor1;
		this.actor2=actor2;
		this.actor3=actor3;
	}

	// Getters and Setters for all attributes
	public int getYear() {
        return year;
    }

    public int getDuration() {
        return duration;
    }

    public double getScore() {
        return score;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getRating() {
        return rating;
    }

    public String getDirector() {
        return director;
    }

    public String getActor1() {
        return actor1;
    }

    public String getActor2() {
        return actor2;
    }

    public String getActor3() {
        return actor3;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setActor1(String actor1) {
        this.actor1 = actor1;
    }

    public void setActor2(String actor2) {
        this.actor2 = actor2;
    }

    public void setActor3(String actor3) {
        this.actor3 = actor3;
    }


    /**
     * Overrides the equals method to compare Movie objects based on their attributes.
     * 
     * @param obj The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Movie movie = (Movie) obj;
        return (year == movie.getYear() && duration == movie.getDuration() && score== movie.getScore() &&
               title.equals(movie.getTitle()) &&genre.equals(movie.getGenre())&&rating.equals(movie.getRating())&&
                director.equals(movie.getDirector())&&actor1.equals(movie.getActor1())&&actor2.equals(movie.getActor2())&&
                actor3.equals(movie.getActor3()));
    }
    
    /**
     * Overrides the toString method to provide a string representation of the Movie object.
     * 
     * @return A string representation of the Movie object.
     */
    @Override
    public String toString() {
        return "Movie{" +
                "year=" + year +
                ", duration=" + duration +
                ", score=" + score +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", rating='" + rating + '\'' +
                ", director='" + director + '\'' +
                ", actor1='" + actor1 + '\'' +
                ", actor2='" + actor2 + '\'' +
                ", actor3='" + actor3 + '\'' +
                '}';
    }

}
