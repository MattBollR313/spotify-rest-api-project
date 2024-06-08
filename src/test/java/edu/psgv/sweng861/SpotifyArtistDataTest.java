package edu.psgv.sweng861;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * SpotifyArtistDataTest contains the unit tests of the
 * SpotifyArtistData class. These tests test various methods
 * of the class to make sure data is obtained.
 * @author Matthew Bollinger
 */
class SpotifyArtistDataTest {

	// Can't test images due to unique id attached to every BufferedImage object

	// SpotifyRestRequester object that makes all API requests
	SpotifyRestRequester projectApi = new SpotifyRestRequester();
	SpotifyArtistData artistData;

	/**
	 * Initializes SpotifyArtistData object with "Penkin" acting as input
	 */
	@BeforeEach
	public void setUp() {
		artistData = projectApi.searchArtist("Penkin");
	}

	/**
	 * Tests the getArtistNames() method to ensure the correct
	 * names are obtained through the initialization of the object.
	 */
	@Test
	public void testArtistNames() {
		ArrayList<String> artistNames = artistData.getArtistNames();
		assertNotNull(artistNames);
	}

	/**
	 * Tests the getArtistGenres() method to ensure the correct
	 * genres are obtained through the initialization of the object.
	 */
	@Test
	public void testArtistGenres() {
		ArrayList<ArrayList<String>> artistGenres = artistData.getArtistGenres();
		assertNotNull(artistGenres);
	}

	/**
	 * Tests the getArtistFollowers() method to ensure the correct
	 * follower counts are obtained through the initialization of the object.
	 */
	@Test
	public void testArtistFollowers() {
		ArrayList<Integer> artistFollowers = artistData.getArtistFollowers();
		assertNotNull(artistFollowers);
	}
	
	/**
	 * Tests the getArtistPopularities() method to ensure the correct
	 * popularity ratings are obtained through the initialization of the object.
	 */
	@Test
	public void testArtistPopularities() {
		ArrayList<String> artistPopularities = artistData.getArtistPopularities();
		assertNotNull(artistPopularities);
	}

}
