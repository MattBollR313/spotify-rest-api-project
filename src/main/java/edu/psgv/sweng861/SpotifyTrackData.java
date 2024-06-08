package edu.psgv.sweng861;

import se.michaelthelin.spotify.model_objects.special.SearchResult;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;

/**
 * SpotifyTrackData stores the data from the API requests
 * meant to be displayed later on by the JFrame window
 * in the GUI. It stores each track's name, the name of
 * the album it belongs to, the album release date, the album
 * image, the artists that are involved in the track, the
 * length of the track, whether the track is explicit or not,
 * the disc number, the track number, and the popularity of
 * the track.
 * @author Matthew Bollinger
 */
public class SpotifyTrackData {
	
	// Max number to be inserted into array list
	private int maxInserts = 5;
	
	// Contains the name of each track
	private ArrayList<String> trackNames = new ArrayList<>();
	// Contains the album that each track belongs to
	private ArrayList<String> trackAlbums = new ArrayList<>();
	// Contains each album's release date
	private ArrayList<String> trackAlbumReleaseDates = new ArrayList<>();
	// Contains the artists involved in each track
	private ArrayList<ArrayList<String>> trackArtists = new ArrayList<ArrayList<String>>();
	// Contains the length of each track
	private ArrayList<Integer> trackLengths = new ArrayList<>();
	// Contains whether each track is explicit or not
	private ArrayList<String> trackExplicits = new ArrayList<>();
	// Contains the disc number of each track
	private ArrayList<Integer> trackDiscNumbers = new ArrayList<>();
	// Contains the track number of each track
	private ArrayList<Integer> trackTrkNumbers = new ArrayList<>();
	// Contains the simplified popularity of each track
	private ArrayList<String> trackPopularities = new ArrayList<>();
	// Contains the image of each album that a track belongs to
	private ArrayList<BufferedImage> trackAlbumImages = new ArrayList<>(); 

	/**
	 * SpotifyTrackData() constructor takes one argument and
	 * sets the number of maximum number of tracks to get 
	 * information about. Afterwards, it sets the various values
	 * of each track in different ArrayLists and double ArrayLists.
	 * @param data is a SearchResult and is obtained from SpotifyRestRequester
	 * in order to get the data to be put into the ArrayLists.
	 */
	public SpotifyTrackData(SearchResult data) {
		setMaxInserts(data);
		setTrackValues(data);
	}

	/**
	 * setMaxInserts() sets the maximum number of tracks
	 * to obtain their information in order to be displayed
	 * by the GUI.
	 * @param data
	 */
	private void setMaxInserts(SearchResult data) {
		int size = data.getTracks().getTotal();
		// Max number of tracks that will be used is five
		maxInserts = (size >= 5) ? 5 : size;
	}

	/**
	 * setTrackValues() sets the various values of each track
	 * into multiple ArrayLists as well as double ArrayLists.
	 * @param data
	 */
	private void setTrackValues(SearchResult data) {
		setTrackNames(data);
		setTrackAlbums(data);
		setTrackAlbumReleaseDates(data);
		setTrackArtists(data);
		setTrackLengths(data);
		setTrackExplicits(data);
		setTrackDiscNumbers(data);
		setTrackTrkNumbers(data);
		setTrackPopularities(data);
		setTrackAlbumImages(data);
	}

	/**
	 * setTrackNames() sets the names of the tracks
	 * using the getName() method from the Track class.
	 * @param data
	 */
	private void setTrackNames(SearchResult data) {
		for (int i = 0; i < maxInserts; i++) {
			trackNames.add(data.getTracks().getItems()[i].getName());
		}
	}

	/**
	 * getTrackNames() gets the names of the tracks.
	 * @return an ArrayList of the names of the tracks
	 */
	public ArrayList<String> getTrackNames() {
		return trackNames;
	}

	/**
	 * setTrackAlbums() sets the name of the album that
	 * each track belongs to using the getAlbum().getName()
	 * methods from the Track class.
	 * @param data
	 */
	private void setTrackAlbums(SearchResult data) {
		for (int i = 0; i < maxInserts; i++) {
			trackAlbums.add(data.getTracks().getItems()[i].getAlbum().getName());
		}
	}

	/**
	 * getTrackAlbums() gets the name of the album that
	 * each track belongs to.
	 * @return an ArrayList of the names of each album
	 */
	public ArrayList<String> getTrackAlbums() {
		return trackAlbums;
	}

	/**
	 * setTrackAlbumReleaseDates() sets the release date of
	 * the album that each track belongs to using the 
	 * getAlbum().getReleaseDate() methods from the Track class.
	 * @param data
	 */
	private void setTrackAlbumReleaseDates(SearchResult data) {
		for (int i = 0; i < maxInserts; i++) {
			trackAlbumReleaseDates.add(data.getTracks().getItems()[i].getAlbum().getReleaseDate());
		}
	}

	/**
	 * getTrackAlbumReleaseDates() gets the release date of
	 * the album that each track belongs to.
	 * @return an ArrayList of the release dates
	 */
	public ArrayList<String> getTrackAlbumReleaseDates() {
		return trackAlbumReleaseDates;
	}

	/**
	 * setTrackArtists() sets the names of the artists involved 
	 * in each track using the getArtists() method from the Track class.
	 * @param data
	 */
	private void setTrackArtists(SearchResult data) {
		for (int i = 0; i < maxInserts; i++) {
			// Get an array of ArtistSimplified objects that represents the
			// artists that are involved in a track
			ArtistSimplified[] artists = data.getTracks().getItems()[i].getArtists();
			ArrayList<String> singleTrackArtists = new ArrayList<>();
			// Copies the array into an ArrayList
			for (ArtistSimplified a : artists) {
				singleTrackArtists.add(a.getName());
			}
			trackArtists.add(singleTrackArtists);
		}
	}
	
	/**
	 * getTrackArtists() gets the names of the artists involved
	 * in each track.
	 * @return an double ArrayList of the names of the artists
	 */
	public ArrayList<ArrayList<String>> getTrackArtists() {
		return trackArtists;
	}

	/**
	 * setTrackLengths() sets the length of each track using
	 * the getDurationMs() method from the Track class.
	 * @param data
	 */
	private void setTrackLengths(SearchResult data) {
		for (int i = 0; i < maxInserts; i++) {
			trackLengths.add(data.getTracks().getItems()[i].getDurationMs());
		}
	}

	/**
	 * getTrackLengths() gets the length of each track.
	 * @return an ArrayList of ints that represent the length of each track.
	 */
	public ArrayList<Integer> getTrackLengths() {
		return trackLengths;
	}

	/**
	 * setTrackExplicits() sets whether each track is explicit or not
	 * using the getIsExplicit() method from the Track class.
	 * @param data
	 */
	private void setTrackExplicits(SearchResult data) {
		for (int i = 0; i < maxInserts; i++) {
			// Adds "Yes" to the ArrayList if the track is explicit, "No" otherwise
			boolean isExplicit = data.getTracks().getItems()[i].getIsExplicit();
			trackExplicits.add((isExplicit) ? "Yes" : "No");
		}
	}

	/**
	 * getTrackExplicits() gets whether each track is explicit or not.
	 * @return an ArrayList of strings declaring yes or not to each song being explicit
	 */
	public ArrayList<String> getTrackExplicits() {
		return trackExplicits;
	}

	/**
	 * setTrackDiscNumbers() sets the disc number of each track
	 * using the getDiscNumber() method from the Track class.
	 * @param data
	 */
	private void setTrackDiscNumbers(SearchResult data) {
		for (int i = 0; i < maxInserts; i++) {
			trackDiscNumbers.add(data.getTracks().getItems()[i].getDiscNumber());
		}
	}

	/**
	 * getTrackDiscNumbers() gets the disc number of each track.
	 * @return an ArrayList of the disc number of each track
	 */
	public ArrayList<Integer> getTrackDiscNumbers() {
		return trackDiscNumbers;
	}

	/**
	 * setTrackTrkNumbers() sets the track number of each track
	 * using the getTrackNumber() method from the Track class.
	 * @param data
	 */
	private void setTrackTrkNumbers(SearchResult data) {
		for (int i = 0; i < maxInserts; i++) {
			trackTrkNumbers.add(data.getTracks().getItems()[i].getTrackNumber());
		}
	}

	/**
	 * getTrackTrkNumbers() gets the track number of each track.
	 * @return an ArrayList of the track number of each track
	 */
	public ArrayList<Integer> getTrackTrkNumbers() {
		return trackTrkNumbers;
	}
	
	/**
	 * setTrackPopularities() sets the popularity of each track
	 * based on the number obtained from the getPopularity() method
	 * from the Track class. The popularity number is used to
	 * specify one of five different strings that simplifies
	 * the number to something more readable by a user.
	 * @param data
	 */
	private void setTrackPopularities(SearchResult data) {
		for (int i = 0; i < maxInserts; i++) {
			// Obtains the popularity number (0..100)
			int pop = data.getTracks().getItems()[i].getPopularity();
			// Uses popularity number to split into five different
			// Possible popularity ratings
			if (pop <= 20)
				trackPopularities.add("Not popular");
			else if (pop <= 40)
				trackPopularities.add("Not very popular");
			else if (pop <= 60)
				trackPopularities.add("Somewhat popular");
			else if (pop <= 80)
				trackPopularities.add("Popular");
			else
				trackPopularities.add("Very popular");
		}
	}
	
	/**
	 * getTrackPopularities() gets the popularity of each track.
	 * @return an ArrayList of the popularity of each track
	 */
	public ArrayList<String> getTrackPopularities() {
		return trackPopularities;
	}

	/**
	 * setTrackAlbumImages() sets the image of the album that each 
	 * track belongs to into an ArrayList of BufferedImage objects.
	 * @param data
	 */
	private void setTrackAlbumImages(SearchResult data) {
		for (int i = 0; i < maxInserts; i++) {
			try {
				if (data.getTracks().getItems()[i].getAlbum().getImages().length != 0) {
					// Convert the URL into a BufferedImage using the ImageIO read method
					URL imageUrl = new URL(data.getTracks().getItems()[i].getAlbum().getImages()[0].getUrl());
					BufferedImage image = ImageIO.read(imageUrl);
					trackAlbumImages.add(image);
				} else
					trackAlbumImages.add(null); // Adds null to the ArrayList if there is no album image
			} catch (IIOException e) { // Caught if the image is of an unsupported format
				trackAlbumImages.add(null);
			} catch (IOException e) { // Caught if the image URL is an invalid one
				e.printStackTrace();
			}
		}
	}

	/**
	 * getTrackAlbumImages() gets the image of the album that each
	 * track belongs to.
	 * @return an ArrayList of BufferedImage objects
	 */
	public ArrayList<BufferedImage> getTrackAlbumImages() {
		return trackAlbumImages;
	}

}
