package edu.psgv.sweng861;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * SpotifyTrackDataTest contains the unit tests of the
 * SpotifyTrackData class. These tests test various methods
 * of the class to make sure data is obtained.
 * @author Matthew Bollinger
 */
class SpotifyTrackDataTest {

	// Can't test images due to unique id attached to every BufferedImage object

	// SpotifyRestRequester object that makes all API requests
	SpotifyRestRequester projectApi = new SpotifyRestRequester();
	SpotifyTrackData trackData;

	/**
	 * Initializes SpotifyTrackData object with "Avid" acting as input
	 */
	@BeforeEach
	void setUp() {
		trackData = projectApi.searchSong("Avid");
	}

	/**
	 * Tests the getTrackNames() method to ensure the correct
	 * names are obtained through the initialization of the object.
	 */
	@Test
	public void testTrackNames() {
		ArrayList<String> trackNames = trackData.getTrackNames();
		assertNotNull(trackNames);
	}

	/**
	 * Tests the getTrackAlbums() method to ensure the correct
	 * album names are obtained through the initialization of the object.
	 */
	@Test
	public void testTrackAlbums() {
		ArrayList<String> trackAlbums = trackData.getTrackAlbums();
		assertNotNull(trackAlbums);
	}

	/**
	 * Tests the getTrackAlbumReleaseDates() method to ensure the correct
	 * album release dates are obtained through the initialization of the object.
	 */
	@Test
	public void testTrackAlbumReleaseDates() {
		ArrayList<String> trackAlbumReleaseDates = trackData.getTrackAlbumReleaseDates();
		assertNotNull(trackAlbumReleaseDates);
	}

	/**
	 * Tests the getTrackArtists() method to ensure the correct
	 * artist names are obtained through the initialization of the object.
	 */
	@Test
	public void testTrackArtist() {
		ArrayList<ArrayList<String>> trackArtists = trackData.getTrackArtists();
		assertNotNull(trackArtists);
	}

	/**
	 * Tests the getTrackLengths() method to ensure the correct
	 * lengths are obtained through the initialization of the object.
	 */
	@Test
	public void testTrackLengths() {
		ArrayList<Integer> trackLengths = trackData.getTrackLengths();
		assertNotNull(trackLengths);
	}

	/**
	 * Tests the getTrackExplicits() method to ensure the correct
	 * explicit tracks are obtained through the initialization of the object.
	 */
	@Test
	public void testTrackExplicits() {
		ArrayList<String> trackExplicits = trackData.getTrackExplicits();
		assertNotNull(trackExplicits);
	}

	/**
	 * Tests the getTrackDiscNumbers() method to ensure the correct
	 * disc numbers are obtained through the initialization of the object.
	 */
	@Test
	public void testTrackDiscNumbers() {
		ArrayList<Integer> trackDiscNumbers = trackData.getTrackDiscNumbers();
		assertNotNull(trackDiscNumbers);
	}

	/**
	 * Tests the getTrackTrkNumbers() method to ensure the correct
	 * track numbers are obtained through the initialization of the object.
	 */
	@Test
	public void testTrackTrkNumbers() {
		ArrayList<Integer> trackTrkNumbers = trackData.getTrackTrkNumbers();
		assertNotNull(trackTrkNumbers);
	}

}
