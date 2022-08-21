import java.io.*;
import java.nio.file.Paths;
import java.lang.*;
import javafx.scene.media.*;
import javafx.embed.swing.JFXPanel;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.stage.*;
import java.util.*;


/**
 * Launches a music player that does all the things I want it to.  Command-line usage: java BestMusicPlayer
 *
 * @author Kyle Burke <paithanq@gmail.com> Eric St. Jean <es1187@plymouth.edu> Benjamin Vachon <bav1010@plymouth.edu>
 */
 public class BestMusicPlayer extends Application {
   private static Label songTitle = new Label("No song currently playing.");
   private static String songName;

   private static int rowNum = 3;
   private static int playlistRowNum = 1;

   //private static ArrayList<SongMedia> mediaArray = new ArrayList<SongMedia>();
   private static SongMediaCollection mediaCollection = new SongMediaCollection();
   private static PlaylistCollection playlistCollection = new PlaylistCollection();
   private static ArrayList<Playlist> playlistArray = new ArrayList<Playlist>();

   private static ArrayList<SongMedia> playlist = new ArrayList<SongMedia>();

   private static ArrayList<SongMedia> listOfPlaylistSongs = new ArrayList<SongMedia>();

   private static Playlist activePlaylist;

   private static Playlist testPlaylist = new Playlist("");

   private static ArrayList<Music> createPlaylist = new ArrayList<Music>();

   private static ListView<String> playlistContents = new ListView<String>();

   private static ListView<String> listOfPlaylists = new ListView<String>();

   private static ArrayList<Music> mediaArray= new ArrayList<Music>();

   private static PlaylistCollection playlistsToBeSaved = new PlaylistCollection();

   private static File songFile = new File("songs.xml");//XML file filled with SongMedia objects

   private static File playlistFile = new File("playlist.xml");//XML file filled with Playlist objects

   private static int selectedPlaylist;

   private static FileChooser fileChooser = new FileChooser();

   public static String getSongName() {
     return songName;
   }
   public static String TITLE = "The Best Music Player Ever!";


   /**
     * Searches all songMedia objects to see if one is currently playing or paused, and stops them.
     * This function is called whenever a songMedia starts playing to prevent multiple from playing at once.
     */
   public static void allStop(){
     for(int i = 0;i<mediaArray.size();i++){
       if(mediaArray.get(i).mediaPlaying()||mediaArray.get(i).mediaPaused()){
         mediaArray.get(i).stopMedia();
       }
     }
     for(int j = 0; j  < listOfPlaylistSongs.size();j++){
       if(listOfPlaylistSongs.get(j).mediaPlaying()||listOfPlaylistSongs.get(j).mediaPaused()){
         listOfPlaylistSongs.get(j).stopMedia();
       }
     }
     songTitle.setText("No song currently playing.");
   }

   /**
     * Searches all songMedia objects and returns the first active one that it finds.
     */
   public Music getActiveMedia(){
     for(int i = 0;i<mediaArray.size();i++){
       if(mediaArray.get(i).mediaPlaying()){
         return mediaArray.get(i);
       }
     }
     for(int j = 0; j < listOfPlaylistSongs.size();j++){
       if(listOfPlaylistSongs.get(j).mediaPlaying()){
         return listOfPlaylistSongs.get(j);
       }
     }
     return null;
   }

   /**
     * Returns the playlist that is currently playing
     */
   public Playlist getActivePlaylist(){
     return activePlaylist;
   }
     /**
     * Sets the current active playlist
     */
   public static void setActivePlaylist(Playlist currentPlaylist){
     activePlaylist=currentPlaylist;
   }

   /**
     * Returns a Button that plays the associated SongMedia object when pressed
     */
   public Button getButton(SongMedia songMedia){
     Button songButton = new Button();
     songMedia.getSong();
     MediaPlayer buttonMediaPlayer = new MediaPlayer(songMedia.getMedia());
     buttonMediaPlayer.setOnReady(()->{
       buttonMediaPlayer.play();
       buttonMediaPlayer.stop();
       songButton.setText("Play " + songMedia.getText());
     });
     songButton.setText(songMedia.getText());
     songButton.setOnAction(new EventHandler<ActionEvent>() {
       @Override
       public void handle(ActionEvent event) {
         //code to play a song modified from stackoverflow user jasonwaste's answer on https://stackoverflow.com/questions/6045384/playing-mp3-and-wav-in-java


         songButton.setText("Play " + songMedia.getText());
         allStop();
         songMedia.playMedia();
       }
     });

     return songButton;
   }

   public static void setLabel(Music song){
     songTitle.setText("Now Playing: " + song.getText());
   }

   /**
     * Returns a Button that plays the associated Playlist when pressed
     */
   public Button getPlaylistButton(Playlist playlist){
     Button playlistButton = new Button();
     //playlist.setPlaylistName();

     playlistButton.setOnAction(new EventHandler<ActionEvent>() {
       @Override
       public void handle(ActionEvent event) {
         //playlist.setPlaylistName();
         //playlistButton.setText(playlist.getPlaylistName());
         playlistButton.setText(playlist.getText());
         //code to play a song modified from stackoverflow user jasonwaste's answer on https://stackoverflow.com/questions/6045384/playing-mp3-and-wav-in-
         allStop();
         playlist.getBigPlaylist();
         playlist.playMedia();
       }
     });
     playlistButton.setText(playlist.getText());

     return playlistButton;
   }




 /**
  * Main method to launch the program.
  *
  * @param args  Command-line arguments for the program.  Currently unused.
  */
  public static void main(String[] args) {launch(args);}
    @Override
    public void start(Stage primaryStage){
      //solution from stackoverflow user Sagar Damani at: https://stackoverflow.com/questions/14025718/javafx-toolkit-not-initialized-when-trying-to-play-an-mp3-file-through-mediap
      final JFXPanel MusicPlayer = new JFXPanel();

      SongMediaCollection importedSongs = SongMediaRestorer.restoreSongMediaCollection(songFile);

      PlaylistCollection importedPlaylists = PlaylistRestorer.getPlaylistCollection(playlistFile);

      primaryStage.setTitle(this.TITLE);


      SongMedia wolverineMedia = new SongMedia("./songs/PrideOfTheWolverines.mp3");
      //mediaArray.add(wolverineMedia);


      SongMedia brassMonkeyMedia = new SongMedia("./songs/BrassMonkey.mp3");
      //mediaArray.add(brassMonkeyMedia);

      SongMedia danceMonkeyMedia = new SongMedia("./songs/DanceMonkey.mp3");
      //mediaArray.add(danceMonkeyMedia);

      testPlaylist.addToPlaylist(danceMonkeyMedia);
      testPlaylist.addToPlaylist(brassMonkeyMedia);
      testPlaylist.addToPlaylist(wolverineMedia);

      /*playlistsToBeSaved.addPlaylist(testPlaylist);
      System.out.println("testPlaylist Saved");
      for(Playlist playlist : playlistsToBeSaved.getPlaylistArray()){
        System.out.println(playlist);
      }*/

      //PlaylistSaver.savePlaylistCollection(playlistsToBeSaved);

      //CODE TO MAKE A PAUSE BUTTON AND PAUSE A SONG

      Button pauseButton = new Button();
      pauseButton.setText("Pause");
      pauseButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          for(int i = 0;i < mediaArray.size();i++){
            if(mediaArray.get(i).mediaPlaying() == true){
              pauseButton.setText("Play");
              mediaArray.get(i).mediaPause();
            }else if(mediaArray.get(i).mediaPaused() == true){
              pauseButton.setText("Pause");
              mediaArray.get(i).playMedia();
            }
          }
        }
      });

      //CODE TO GO TO NEXT SONG WITH A BUTTON CLICK

      Button nextSongButton = new Button();
      nextSongButton.setText("Next Song");
      nextSongButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          activePlaylist.skipSong();
        }
      });

      //CODE TO GO BACK TO PREVIOUS SONG WITH A BUTTON CLICK

      Button previousSongButton = new Button();
      previousSongButton.setText("Previous Song");
      previousSongButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          activePlaylist.previousSong();
        }
      });

      //CODE TO MAKE A STOP BUTTON

      Button stopButton = new Button();
      stopButton.setText("Stop");
      stopButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          pauseButton.setText("Pause");
          allStop();
        }
      });



      GridPane musicPlayerGrid = new GridPane();
      musicPlayerGrid.setAlignment(Pos.CENTER);
      musicPlayerGrid.setVgap(3);

      ScrollPane musicPlayerScroll = new ScrollPane();
      musicPlayerScroll.setContent(musicPlayerGrid);
      musicPlayerScroll.setFitToWidth(true);
      musicPlayerScroll.setPrefHeight(700);
      //musicPlayerGrid.add(getButton(danceMonkeyMedia), 0, 0);
      //musicPlayerGrid.add(getButton(brassMonkeyMedia), 0, 1);
      //musicPlayerGrid.add(getButton(wolverineMedia), 0, 2);

      for(SongMedia importedSong : importedSongs.getSongMedias()){
        musicPlayerGrid.add(getButton(importedSong),0,++rowNum);
        mediaCollection.addSongMedia(importedSong);
        mediaArray.add(importedSong);
      }

      for(Playlist importedPlaylist : importedPlaylists.getPlaylistArray()){
        //System.out.println(importedPlaylist.getName());
        for(Music song : importedPlaylist.getPlaylist()){
          song.getSong();
          //System.out.println(song);
          mediaArray.add(song);
        }
        listOfPlaylists.getItems().add(importedPlaylist.getName());
        musicPlayerGrid.add(getPlaylistButton(importedPlaylist),0,++rowNum);
        playlistCollection.addPlaylist(importedPlaylist);
        playlistArray.add(importedPlaylist);
        playlistsToBeSaved.addPlaylist(importedPlaylist);
      }

      musicPlayerGrid.add(listOfPlaylists,1,0);
      musicPlayerGrid.setRowSpan(listOfPlaylists,40);

      musicPlayerGrid.add(songTitle, 0, 42);

      musicPlayerGrid.add(pauseButton, 0, 44);
      musicPlayerGrid.add(stopButton, 0, 46);
      musicPlayerGrid.add(previousSongButton,0,48);
      musicPlayerGrid.add(nextSongButton,1,48);
      primaryStage.setScene(new Scene(musicPlayerScroll, 1600, 900));
      primaryStage.show();

      //CODE TO IMPORT NEW SONGS

      Button selectNewSongButton = new Button();
      selectNewSongButton.setText("Choose New Song");
      musicPlayerGrid.add(selectNewSongButton, 0, 40);
      selectNewSongButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {

          File songFile = fileChooser.showOpenDialog(primaryStage);

          fileChooser.setInitialDirectory(songFile.getParentFile());

          String songDir = songFile.getAbsolutePath();
          SongMedia newSongMedia = new SongMedia(songDir);
          //mediaArray.add(newSongMedia);

          mediaCollection.addSongMedia(newSongMedia);
          mediaArray.add(newSongMedia);

          SongMediaSaver.saveSongMediaCollection(mediaCollection);


          newSongMedia.getSong();
          musicPlayerGrid.add(getButton(newSongMedia), 0, ++rowNum);
        }
      });

      //CODE TO CREATE PLAYLISTS

      Button createPlaylistButton = new Button();
      createPlaylistButton.setText("Create New Playlist");
      musicPlayerGrid.add(createPlaylistButton,0,50);
      createPlaylistButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event){
          Stage stage = new Stage();
          GridPane playlistCreator = new GridPane();
          stage.setTitle("Create New Playlist");
          stage.setScene(new Scene(playlistCreator, 1000,600));
          playlistCreator.setAlignment(Pos.CENTER);
          playlistCreator.setVgap(5);
          stage.show();
          TextField playlistName = new TextField();
          playlistCreator.add(playlistName,0,0);
          playlistCreator.add(playlistContents,1,0);
          playlistCreator.setRowSpan(playlistContents, 10);

          for(SongMedia song : mediaCollection.getSongMedias()){
            //System.out.println(song.getText()+ song.getSongName());
            Button addSongToPlaylistButton = new Button();
            addSongToPlaylistButton.setText("add "+ song.getText());

            getButton(song);


            //System.out.println(song.getText()+ song);
            addSongToPlaylistButton.setOnAction(new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent event) {

                addSongToPlaylistButton.setText("add "+ song.getText());
                createPlaylist.add(song);
                playlistContents.getItems().add(song.getTitle() );
              }
            });
            playlistCreator.add(addSongToPlaylistButton,0,playlistRowNum);
            playlistRowNum++;
          }

          for(Playlist playlist : playlistsToBeSaved.getPlaylistArray()){
            Button addPlaylistToPlaylistButton = new Button();
            addPlaylistToPlaylistButton.setText("add "+ playlist.getText());
            getPlaylistButton(playlist);

            addPlaylistToPlaylistButton.setOnAction(new EventHandler<ActionEvent>(){
              @Override
              public void handle(ActionEvent event){
                addPlaylistToPlaylistButton.setText("add "+ playlist.getText());
                createPlaylist.add(playlist);
                playlistContents.getItems().add(playlist.getName());
              }
            });
            playlistCreator.add(addPlaylistToPlaylistButton,0,playlistRowNum);
            playlistRowNum++;
          }

          Button removeFromPlaylistButton = new Button();
          removeFromPlaylistButton.setText("remove selected song");
          removeFromPlaylistButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              //gets index of selected item in playlistContents
              int i = playlistContents.getSelectionModel().getSelectedIndex();

              playlistContents.getItems().remove(i);
              createPlaylist.remove(i);
            }
          });

          playlistCreator.add(removeFromPlaylistButton,1,50);

          Button finalizePlaylist = new Button();
          finalizePlaylist.setText("Create Playlist");
          playlistCreator.add(finalizePlaylist,0,50);
          finalizePlaylist.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
              ArrayList<Music> playlistCreator = new ArrayList<Music>();
              for(Music song : createPlaylist){
                playlistCreator.add(song);
              }
              Playlist newPlaylist = new Playlist(playlistName.getText());
              for(Music song : playlistCreator){
                newPlaylist.addToPlaylist(song);
              }
              playlistContents.getItems().clear();
              playlistRowNum = 1;
              createPlaylist.removeAll(createPlaylist);
              musicPlayerGrid.add(getPlaylistButton(newPlaylist),0,++rowNum);
              playlistsToBeSaved.addPlaylist(newPlaylist);
              System.out.println("Playlist Saved");
              //System.out.println(playlistCollection);
              PlaylistSaver.savePlaylistCollection(playlistsToBeSaved);
              for(Playlist playlist : playlistsToBeSaved.getPlaylistArray()){
                //System.out.println(playlist);
              }
            }
          });
        }
      });//end of create playlist
    }//end of start
 }//end of BestMusicPlayer.java
