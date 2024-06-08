package edu.psgv.sweng861;

import com.neovisionaries.i18n.CountryCode;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.special.SearchResult;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.artists.GetArtistsRelatedArtistsRequest;
import se.michaelthelin.spotify.requests.data.artists.GetArtistsTopTracksRequest;
import se.michaelthelin.spotify.requests.data.search.SearchItemRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * SpotifyRestRequester sets up a Spotify REST API object
 * that sends requests to the Spotify server about track
 * and artist information. The object is first built through
 * the client id and client secret obtained from the spotify
 * developer website and then requests an access token that
 * is valid for one hour in order to make any API requests.
 * @author Matthew Bollinger
 */
public class SpotifyRestRequester {

	// Unique client id and secret obtained from the Spotify developer website
	private static final String clientId = System.getenv("SPOTIFY_ID");
	private static final String clientSecret = System.getenv("SPOTIFY_PASS");

	// Initial build of the SpotifyApi object using the id and secret
	private final SpotifyApi spotifyApi = new SpotifyApi.Builder()
			.setClientId(clientId).setClientSecret(clientSecret)
			.build();

	/**
	 * SpotifyRestRequester constructor requests a
	 * client credentials access token through the proper
	 * client id and client secret in order to make API
	 * requests for exactly one hour (which in that case
	 * a new access token must be obtained).
	 */
	public SpotifyRestRequester() {
		try {
			// Use Client Credentials method to obtaining an access token using the
			// Valid client secret and client id
			final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
			final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

			String accessToken = clientCredentials.getAccessToken();
			
			// Set access token for further "spotifyApi" object usage
			spotifyApi.setAccessToken(accessToken);
		} catch (IOException | SpotifyWebApiException | ParseException e) { // Catches necessary exceptions
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * searchArtist() uses the input to request up to five artists'
	 * information such as name, genre, or number of followers. It
	 * also uses the artists' unique IDs to get the artists that are 
	 * related to them as well as their top tracks.
	 * @param name is the name of the artist that will be put into
	 * the search request.
	 * @return a SpotifyArtistData object which contains all relevant
	 * data from the search result to be used by the GUI.
	 */
	public SpotifyArtistData searchArtist(String name) {
		try {
			final String type = ModelObjectType.ARTIST.getType();
			// Search using name and type with a limit of five results
			final SearchItemRequest searchArtistsRequest = spotifyApi.searchItem(name, type).limit(5).build();

			final SearchResult searchResult = searchArtistsRequest.execute();
			
			// Obtains the IDs of each artist from the initial result
			String[] searchIDs = new String[searchResult.getArtists().getItems().length];
			for (int i = 0; i < searchIDs.length; i++) {
				searchIDs[i] = searchResult.getArtists().getItems()[i].getId();
			}
			
			// Uses the IDs of the artists to make additional API requests that retrieves
			// Data about the related artists and top tracks of each artist
			ArrayList<ArrayList<Artist>> relatedArtists = new ArrayList<>();
			ArrayList<ArrayList<Track>> topTracks = new ArrayList<>();
			final CountryCode countryCode = CountryCode.SE;
			for (String id : searchIDs) {
				final GetArtistsRelatedArtistsRequest artistRelatedArtists = spotifyApi.getArtistsRelatedArtists(id).build();
				final GetArtistsTopTracksRequest artistsTopTracks = spotifyApi.getArtistsTopTracks(id, countryCode).build();
				final Artist[] artists = artistRelatedArtists.execute();
				final Track[] tracks = artistsTopTracks.execute();
				relatedArtists.add(new ArrayList<>(Arrays.asList(artists)));
				topTracks.add(new ArrayList<>(Arrays.asList(tracks)));
			}

			// Creates the SpotifyArtistData object using the search results before returning it
			return new SpotifyArtistData(searchResult, relatedArtists, topTracks);
		} catch (IOException | SpotifyWebApiException | ParseException e) { // Catches necessary exceptions
			System.out.println("Error: " + e.getMessage());
			return null;
		}
	}

	/**
	 * searchSong() uses the input to request up to five tracks'
	 * information such as name, album, or artists involved.
	 * @param name is the name of the song that will be put into
	 * the search request.
	 * @return a SpotifyTrackData object which contains all relevant
	 * data from the search result to be used by the GUI.
	 */
	public SpotifyTrackData searchSong(String name) {
		try {
			final String type = ModelObjectType.TRACK.getType();
			// Search using name and type with a limit of five results
			final SearchItemRequest searchArtistsRequest = spotifyApi.searchItem(name, type).limit(5).build();

			final SearchResult searchResult = searchArtistsRequest.execute();

			// Creates the SpotifyTrackData object using the search result before returning it
			return new SpotifyTrackData(searchResult);
		} catch (IOException | SpotifyWebApiException | ParseException e) { // Catches necessary exceptions
			System.out.println("Error: " + e.getMessage());
			return null;
		}
	}

}
