package edu.psgv.sweng861;

import javax.swing.*;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * SpotifyRestApiProject is the interface that interacts
 * with the other various classes to produce output about
 * a particular song or artist that is inquired. The user
 * enters an input title of an artist or song and then
 * clicks a button which brings up five resulting names that
 * can then be clicked on for information about said result.
 * Included in the information is a picture of either the
 * artist clicked on or the album that the song belongs to.
 * @author Matthew Bollinger
 *
 */
public class SpotifyRestApiProject {

	// The frame that holds all elements of the GUI
	private JFrame spotifyFrame;
	// The button to click submit upon entering a search request
	private JButton submitInputButton;
	// The search request input in by a user
	private JTextField userInputText;
	// The list model and list containing the names of the artists/tracks
	private DefaultListModel<String> listModel;
	private JList<String> listOfNames;
	// The information obtained from clicking on one of the names in the list
	private JTextArea resultInfo;
	// The picture obtained from clicking on one of the names in the list
	private JLabel resultPicture;
	// The artist radio button
	private JRadioButton chooseArtist;
	// The track radio button
	private JRadioButton chooseTrack;
	// The button group that combines the two radio buttons together
	private final ButtonGroup chooseTypeOfInput = new ButtonGroup();

	// The SpotifyRestRequester object that handles API requests
	private final SpotifyRestRequester apiDataHandler = new SpotifyRestRequester();
	// Contains both the artist and track data information from the API requests
	private SpotifyArtistData artistsInfo;
	private SpotifyTrackData tracksInfo;
	// Scroll panes used for the list and text area
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpotifyRestApiProject window = new SpotifyRestApiProject();
					window.spotifyFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SpotifyRestApiProject() {
		initialize();
	}

	/**
	 * ButtonAction implements ActionListener and is used
	 * to provide functionality to the clicking of the submit
	 * button. It deals with if the user doesn't input any text
	 * by displaying a message dialog and exiting the method. It
	 * also deals with if the input doesn't have any results
	 * by asking the user to try another request. If no problems
	 * arise, either the track names or artist names will be
	 * displayed in the list for further action.
	 * @author Matthew Bollinger
	 *
	 */
	class ButtonAction implements ActionListener {

		// Type of input (artist/track) and text input into text field
		String inputType, inputText;

		/**
		 * actionPerformed() is called upon when the button is pressed.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			inputType = chooseTypeOfInput.getSelection().getActionCommand();
			inputText = userInputText.getText();
			if (inputText.isEmpty()) {
				// Asks user to enter some query
				JOptionPane.showMessageDialog(spotifyFrame, "Please enter a name of a song/artist");
				return;
			}
			// Clears the list in order to allow for new names to fill it
			listModel.clear();
			if (inputType.equals("Artist")) {
				// Calls the searchArtist() method to make an API request about that specific artist
				artistsInfo = apiDataHandler.searchArtist(inputText);
				if (artistsInfo.getArtistNames().isEmpty()) {
					// Asks user to enter another query as there were no results
					JOptionPane.showMessageDialog(spotifyFrame, "Name yielded no results.\nPlease enter in another one.");
					return;
				}
				// Lists all names of the artists from the result
				for (String artistNames : artistsInfo.getArtistNames())
					listModel.addElement(artistNames);
			} else {
				tracksInfo = apiDataHandler.searchSong(inputText);
				if (tracksInfo.getTrackNames().isEmpty()) {
					// Asks user to enter another query as there were no results
					JOptionPane.showMessageDialog(spotifyFrame, "Name yielded no results.\nPlease enter in another one.");
					return;
				}
				// Lists all names of the tracks from the result
				for (String trackNames : tracksInfo.getTrackNames())
					listModel.addElement(trackNames);
			}
		}

	}

	/**
	 * ListAction implements ListSelectionListener and is used
	 * to provide functionality to the list by having the text area
	 * and label display information about a selection by the user.
	 * Only one selection at a time is allowed by the list.
	 * @author Matthew Bollinger
	 *
	 */
	class ListAction implements ListSelectionListener {
		
		/**
		 * convertFromMs() converts from milliseconds to
		 * hours, minutes, and seconds in a nice string format
		 * that will be displayed by the GUI.
		 * @param lengthMs is the length of a song in milliseconds
		 * @return the formatted string with the completed conversion
		 */
		private String convertFromMs(int lengthMs) {
			// Uses the TimeUnit package to convert milliseconds
			// Into the various units
			return String.format("%02d:%02d:%02d",
					TimeUnit.MILLISECONDS.toHours(lengthMs),
					TimeUnit.MILLISECONDS.toMinutes(lengthMs) -  
					TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(lengthMs)),
					TimeUnit.MILLISECONDS.toSeconds(lengthMs) - 
					TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(lengthMs)));   
		}
		
		/**
		 * setCorrectImageDimen() converts a BufferedImage object
		 * into an ImageIcon with the dimensions of the label that
		 * is holding the image.
		 * @param img is the BufferedImage object
		 * @return the ImageIcon with the proper size
		 */
		private ImageIcon setCorrectImageDimen(BufferedImage img) {
			Image correctDimen = new ImageIcon(img).getImage().getScaledInstance(252, 252, Image.SCALE_DEFAULT);
			return new ImageIcon(correctDimen);
		}
		
		/**
		 * insertArtistInfo() inserts all information about the artist
		 * selected into the text area and label.
		 * @param name is the name of the selection in the list by the user
		 * @param index is the index of the selection in the list by the user
		 */
		private void insertArtistInfo(String name, int index) {
			resultInfo.append("Artist Name: " + name + "\n");
			if (!artistsInfo.getArtistGenres().get(index).isEmpty())
				resultInfo.append("Genres: " + String.join(", ", artistsInfo.getArtistGenres().get(index)) + "\n");
			String topTrack = artistsInfo.getArtistTopTracks().get(index);
			if (topTrack != null)
				resultInfo.append("Top Song: " + topTrack + "\n");
			ArrayList<String> relatedArtists = artistsInfo.getArtistRelatedArtists().get(index);
			if (!relatedArtists.isEmpty()) {
				// Display the first three related artists to the selected artist
				int maxArtists = Math.min(relatedArtists.size(), 3);
				ArrayList<String> artistRelatedArtists = new ArrayList<>();
				for (int i = 0; i < maxArtists; i++)
					artistRelatedArtists.add(artistsInfo.getArtistRelatedArtists().get(index).get(i));
				resultInfo.append("Related Artists: " + String.join(", ", artistRelatedArtists) + "\n");
			}
			resultInfo.append("Followers: " + artistsInfo.getArtistFollowers().get(index) + "\n");
			resultInfo.append("Artist Popularity: " + artistsInfo.getArtistPopularities().get(index) + "\n");
			// Display the ImageIcon in the label
			BufferedImage artistImg = artistsInfo.getArtistImages().get(index);
			if (artistImg != null)
				resultPicture.setIcon(setCorrectImageDimen(artistImg));
		}
		
		/**
		 * insertTrackInfo() inserts all information about the track
		 * selected into the text area and label.
		 * @param name is the name of the selection in the list by the user
		 * @param index is the index of the selection in the list by the user
		 */
		private void insertTrackInfo(String name, int index) {
			resultInfo.append("Song Name: " + name + "\n");
			resultInfo.append("Album Name: " + tracksInfo.getTrackAlbums().get(index) + "\n");
			resultInfo.append("Album Release Date: " + tracksInfo.getTrackAlbumReleaseDates().get(index) + "\n");
			if (!tracksInfo.getTrackArtists().get(index).isEmpty())
				resultInfo.append("Artists Involved: " + String.join(", ", tracksInfo.getTrackArtists().get(index)) + "\n");
			String trackLength = convertFromMs(tracksInfo.getTrackLengths().get(index));
			resultInfo.append("Length: " + trackLength + "\n");
			resultInfo.append("Explicit?: " + tracksInfo.getTrackExplicits().get(index) + "\n");
			resultInfo.append("Disc Number: " + tracksInfo.getTrackDiscNumbers().get(index) + "\n");
			resultInfo.append("Track Number: " + tracksInfo.getTrackTrkNumbers().get(index) + "\n");
			resultInfo.append("Track Popularity: " + tracksInfo.getTrackPopularities().get(index) + "\n");
			BufferedImage trackImg = tracksInfo.getTrackAlbumImages().get(index);
			if (trackImg != null)
				resultPicture.setIcon(setCorrectImageDimen(trackImg));
		}

		/**
		 * valueChanged() method is called when a selection in the list
		 * is changed from what was previous. When a selection is made,
		 * the index and name of the selection are obtained and the correct
		 * method displays the information of the selection.
		 */
		@Override
		public void valueChanged(ListSelectionEvent e) {
			// Resets the text area and label for new information to be displayed
			resultInfo.setText("");
			resultPicture.setIcon(null);
			// Gets the list in order to obtain the index and name of the selection
			@SuppressWarnings("unchecked")
			JList<String> sourceList = (JList<String>) e.getSource();
			int index = sourceList.getSelectedIndex();
			// If statement is ignored if no selection is made
			if (index != -1) {
				String selectedName = listModel.getElementAt(index);
				String inputType = chooseTypeOfInput.getSelection().getActionCommand();
				// Calls the right method depending on the selection of the radio buttons
				if (inputType.equals("Artist")) {
					insertArtistInfo(selectedName, index);
				} else {
					insertTrackInfo(selectedName, index);
				}
			}
		}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Initialize the frame for all contents to be displayed
		spotifyFrame = new JFrame();
		spotifyFrame.setTitle("Spotify Retrieve Information");
		spotifyFrame.setBounds(100, 100, 700, 500);
		spotifyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		spotifyFrame.getContentPane().setLayout(null);
		// Initialize the submit button using the ButtonAction class above
		submitInputButton = new JButton("Get Results");
		submitInputButton.addActionListener(new ButtonAction());
        assert submitInputButton != null;
        submitInputButton.setBounds(195, 73, 112, 30);
        assert spotifyFrame != null;
        spotifyFrame.getContentPane().add(submitInputButton);
		// Initialize the input text field using a KeyAdapter object
		// Which allows for clicking enter to act the same as the submit button
		userInputText = new JTextField(); 
		userInputText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					submitInputButton.doClick();
	            }
			}
		});
		// Helpful tool tip for the user to explain what to do
		userInputText.setToolTipText("Enter in an artist or song");
		userInputText.setBounds(27, 21, 280, 39);
		spotifyFrame.getContentPane().add(userInputText);
		userInputText.setColumns(10);
		// Initialize the artist radio button (default selection)
		chooseArtist = new JRadioButton("Artist");
		chooseArtist.setSelected(true);
		chooseArtist.setActionCommand("Artist");
		chooseTypeOfInput.add(chooseArtist);
		chooseArtist.setHorizontalAlignment(SwingConstants.CENTER);
		chooseArtist.setBounds(27, 77, 80, 23);
		spotifyFrame.getContentPane().add(chooseArtist);
		// Initialize the track radio button
		chooseTrack = new JRadioButton("Song");
		chooseTrack.setActionCommand("Song");
		chooseTypeOfInput.add(chooseTrack);
		chooseTrack.setHorizontalAlignment(SwingConstants.CENTER);
		chooseTrack.setBounds(109, 77, 80, 23);
		spotifyFrame.getContentPane().add(chooseTrack);
		// Initialize the list with a scroll bar if the text overflows
		// Also uses the ListAction object from the class above
		listModel = new DefaultListModel<>();
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(338, 11, 336, 124);
		spotifyFrame.getContentPane().add(scrollPane_1);
		listOfNames = new JList<>(listModel);
		scrollPane_1.setViewportView(listOfNames);
		listOfNames.setFont(new Font("Tahoma", Font.PLAIN, 12));
		listOfNames.addListSelectionListener(new ListAction());
        assert listOfNames != null;
        listOfNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// Initializes the text area with a scroll bar if the text overflows underneath
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(338, 170, 336, 280);
        assert spotifyFrame != null;
        spotifyFrame.getContentPane().add(scrollPane);
		resultInfo = new JTextArea();
		scrollPane.setViewportView(resultInfo);
		resultInfo.setFont(new Font("Monospaced", Font.PLAIN, 14));
		resultInfo.setWrapStyleWord(true);
		resultInfo.setLineWrap(true);
		resultInfo.setEditable(false);
		// Initializes the label that displays the image of the artist/album
		resultPicture = new JLabel("");
		resultPicture.setBounds(28, 170, 252, 252);
		spotifyFrame.getContentPane().add(resultPicture);
	}
}
