package edu.psgv.sweng861;

import se.michaelthelin.spotify.model_objects.special.SearchResult;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

/**
 * SpotifyArtistData stores the data from the API requests
 * meant to be displayed later on by the JFrame window
 * in the GUI. It stores each artist's name, genres they play
 * in, number of followers, popularity rating, names of the
 * artists related to them, their top tracks, and the first
 * image of them that is in the search result.
 * @author Matthew Bollinger
 */
public class SpotifyArtistData {
	
	// Max number to be inserted into array list
	private int maxInserts; 

	// Contains the name of each artist
	private final ArrayList<String> artistNames = new ArrayList<>();
	// Contains the multiple genres that each artist plays in
	private final ArrayList<ArrayList<String>> artistGenres = new ArrayList<>();
	// Contains the number of followers that each artist has
	private final ArrayList<Integer> artistFollowers = new ArrayList<>();
	// Contains the image of each artist in the form of a BufferedImage object
	private final ArrayList<BufferedImage> artistImages = new ArrayList<>();
	// Contains the simplified popularity of each artist
	private final ArrayList<String> artistPopularities = new ArrayList<>();
	// Contains the names of the related artists of each artist
	private final ArrayList<ArrayList<String>> artistRelatedArtists = new ArrayList<>();
	// Contains the top track of each artist
	private final ArrayList<String> artistTopTracks = new ArrayList<>();

	/**
	 * SpotifyArtistData() constructor takes three arguments and
	 * sets the number of maximum number of artists to get 
	 * information about. Afterwards, it sets the various values
	 * of each artist in different ArrayLists.
	 * @param data is a SearchResult and is obtained from SpotifyRestRequester
	 * in order to get the data to be put into the ArrayLists.
	 * @param relatedArtists is a double ArrayList that holds the related 
	 * artists of each artist in order to obtain the names of said artists.
	 * @param topTracks is a double ArrayList that holds the top tracks
	 * of each artist. However, only one track will be obtained and used.
	 */
	public SpotifyArtistData(SearchResult data, ArrayList<ArrayList<Artist>> relatedArtists, ArrayList<ArrayList<Track>> topTracks) {
		setMaxInserts(data);
		setArtistValues(data, relatedArtists, topTracks);
	}

	/**
	 * setMaxInserts() sets the maximum number of artists
	 * to obtain their information in order to be displayed
	 * by the GUI.
	 * @param data
	 */
	private void setMaxInserts(SearchResult data) {
		int size = data.getArtists().getTotal();
		// Max number of artists that will be used is five
		maxInserts = Math.min(size, 5);
	}

	/**
	 * setArtistValues() sets the various values of each artist
	 * into multiple ArrayLists as well as double ArrayLists.
	 * @param data
	 * @param relatedArtists
	 * @param topTracks
	 */
	private void setArtistValues(SearchResult data, ArrayList<ArrayList<Artist>> relatedArtists, ArrayList<ArrayList<Track>> topTracks) {
		setArtistNames(data);
		setArtistGenres(data);
		setArtistFollowers(data);
		setArtistPopularities(data);
		setArtistRelatedArtists(relatedArtists);
		setArtistTopTracks(topTracks);
		setArtistImages(data);
	}

	/**
	 * setArtistNames() sets the names of the artists
	 * using the getName() method in the Artist class.
	 * @param data
	 */
	private void setArtistNames(SearchResult data) {
		for (int i = 0; i < maxInserts; i++) {
			artistNames.add(data.getArtists().getItems()[i].getName());
		}
	}

	/**
	 * getArtistNames() returns the names of the artists.
	 * @return an ArrayList of the names of the artists
	 */
	public ArrayList<String> getArtistNames() {
		return artistNames;
	}

	/**
	 * setArtistGenres() sets the various genres that
	 * each artist plays in using the getGenres() method
	 * from the Artist class.
	 * @param data
	 */
	private void setArtistGenres(SearchResult data) {
		for (int i = 0; i < maxInserts; i++) {
			// Get an array of the genres an artist plays in
			String[] genres = data.getArtists().getItems()[i].getGenres();
			ArrayList<String> singleArtistGenre = new ArrayList<>(List.of(genres));
			artistGenres.add(singleArtistGenre);
		}
	}

	/**
	 * getArtistGenres() gets the genres that each artist plays in.
	 * @return an double ArrayList of Strings that holds the genres
	 * of the artists
	 */
	public ArrayList<ArrayList<String>> getArtistGenres() {
		return artistGenres;
	}

	/**
	 * setArtistFollowers() sets the number of followers
	 * that each artist has using the getFollowers().getTotal()
	 * methods from the Artist class.
	 * @param data
	 */
	private void setArtistFollowers(SearchResult data) {
		for (int i = 0; i < maxInserts; i++) {
			artistFollowers.add(data.getArtists().getItems()[i].getFollowers().getTotal());
		}
	}

	/**
	 * getArtistFollowers() gets the number of followers of each artist.
	 * @return an ArrayList of integers that stores the number of followers
	 * of each artist
	 */
	public ArrayList<Integer> getArtistFollowers() {
		return artistFollowers;
	}
	
	/**
	 * setArtistPopularities() sets the popularity of each artist
	 * based on the number obtained from the getPopularity() method
	 * from the Artist class. The popularity number is used to
	 * specify one of five different strings that simplifies
	 * the number to something more readable by a user.
	 * @param data
	 */
	private void setArtistPopularities(SearchResult data) {
		for (int i = 0; i < maxInserts; i++) {
			// Obtains the popularity number (0..100)
			int pop = data.getArtists().getItems()[i].getPopularity();
			// Uses popularity number to split into five different
			// Possible popularity ratings
			if (pop <= 20)
				artistPopularities.add("Not popular");
			else if (pop <= 40)
				artistPopularities.add("Not very popular");
			else if (pop <= 60)
				artistPopularities.add("Somewhat popular");
			else if (pop <= 80)
				artistPopularities.add("Popular");
			else
				artistPopularities.add("Very popular");
		}
	}
	
	/**
	 * getArtistPopularities() gets the popularity of each artist.
	 * @return an ArrayList of the popularity of each artist
	 */
	public ArrayList<String> getArtistPopularities() {
		return artistPopularities;
	}
	
	/**
	 * setArtistRelatedArtists() sets the names of the related artists
	 * of each artist in a double ArrayList. This is done through the
	 * getName() method of the Artist class.
	 * @param relatedArtists is a double ArrayList of Artist objects
	 */
	private void setArtistRelatedArtists(ArrayList<ArrayList<Artist>> relatedArtists) {
		for (int i = 0; i < maxInserts; i++) {
			// Obtain an ArrayList of the names of the related artists of one artist
			// Before adding that into a double ArrayList
			ArrayList<String> relatedArtistNames = new ArrayList<>();
			for (int j = 0; j < relatedArtists.get(i).size(); j++) {
				relatedArtistNames.add(relatedArtists.get(i).get(j).getName());
			}
			artistRelatedArtists.add(relatedArtistNames);
		}
	}
	
	/**
	 * getArtistRelatedArtists() gets the related artists' names
	 * of each artist.
	 * @return a double ArrayList of the names of each related artist
	 */
	public ArrayList<ArrayList<String>> getArtistRelatedArtists() {
		return artistRelatedArtists;
	}
	
	/**
	 * setArtistTopTracks() sets the top track of each artist into an
	 * ArrayList that contains just the name of each track.
	 * @param topTracks is a double ArrayList of Track objects
	 */
	private void setArtistTopTracks(ArrayList<ArrayList<Track>> topTracks) {
		for (int i = 0; i < maxInserts; i++) {
			// If there is no top tracks of an artist, add null to the ArrayList
			// Otherwise, add only the first top track
			if (!topTracks.get(i).isEmpty())
				artistTopTracks.add(topTracks.get(i).getFirst().getName());
			else
				artistTopTracks.add(null);
		}
	}
	
	/**
	 * getArtistTopTracks() gets the top track of each artist.
	 * @return an ArrayList of the names of the top track of each artist.
	 */
	public ArrayList<String> getArtistTopTracks() {
		return artistTopTracks;
	}

	/**
	 * setArtistImages() sets the first image of each artist that is
	 * obtained from the SearchResult object into an ArrayList
	 * of BufferedImage objects.
	 * @param data
	 */
	private void setArtistImages(SearchResult data) {
		for (int i = 0; i < maxInserts; i++) {
			try {
				if (data.getArtists().getItems()[i].getImages().length != 0) {
					// Convert the URL into a BufferedImage using the ImageIO read method
					URL imageUrl = new URL(data.getArtists().getItems()[i].getImages()[0].getUrl());
					BufferedImage image = ImageIO.read(imageUrl);
					artistImages.add(image);
				} else
					artistImages.add(null);	// Adds null to the ArrayList if there is no image of the artist
			} catch (IIOException e) {  // Caught if the image is of an unsupported format
				artistImages.add(null);
			} catch (IOException e) { // Caught if the image URL is an invalid one
				e.printStackTrace();
			}
		}
	}

	/**
	 * getArtistImages() gets the image of each artist.
	 * @return an ArrayList of BufferedImage objects
	 */
	public ArrayList<BufferedImage> getArtistImages() {
		return artistImages;
	}
	
}
