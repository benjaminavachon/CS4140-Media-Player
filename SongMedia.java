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
import javax.xml.bind.*;
import javax.xml.*;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.*;


/**
 * Creates an object called SongMedia that handles all media.
 *
 * @author Eric St. Jean <es1187@plymouth.edu> Benjamin Vachon <bav1010@plymouth.edu>
 */

@XmlRootElement
public class SongMedia extends Music{
  private String songName;
  private String songPath;

  @XmlTransient
  private Button songButton;

  @XmlTransient
  private Media media;

  @XmlTransient
  private MediaPlayer mediaPlayer;

  @XmlTransient
  private String text;


  /**
    * Empty constructor for SongMedia
    */
  public SongMedia(){
    this("");

  }

  /**
    * Constructor for songMedia, takes the file path of the song and stores it as this.songPath
    */
  public SongMedia(String song){
    this.songPath=song;

  }


  /**
    * Setter for the song name
    */
  @XmlElement
  public void setName(String songName) {
    this.songName = songName;
  }

  /**
    * Getter for the song name
    */
  public String getName() {
    return this.songName;
  }

  /**
    * Sets the file path with which to locate the actual mp3 file
    */
  @XmlElement
  public void setSongPath(String songPath) {
    this.songPath = songPath;
  }

  /**
    * Getter for the file path
    */
  public String getSongPath() {
    return this.songPath;
  }

  /**
    * Creates a MediaPlayer for the file specified when the SongMedia object was created
    */
  public void getSong() {
    this.media = new Media(new File(this.songPath).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(this.media);
    this.mediaPlayer = mediaPlayer;
    this.init();

  }

  public void init(){
    this.mediaPlayer.setOnReady(()->{
      this.mediaPlayer.play();
      this.mediaPlayer.stop();
    });
    this.getMedia().getMetadata();
  }

  /**
    * Returns the MediaPlayer object
    */
  public MediaPlayer getMediaPlayer(){
      return this.mediaPlayer;
  }

  /**
    * Returns the Media object generated from the MP3
    */
  public Media getMedia(){
    return this.media;
  }

  /**
    * Stops the media player
    */
  public void stopMedia(){
    this.mediaPlayer.stop();

  }

  /**
    * Returns the text for the button in BestMusicPlayer. Returns the name and duration of song.
    */
  public String getText(){
    this.setSongText();
    return this.text;
  }

  public String toString(){
    return this.getText();
  }

  public void setSongText(){
    this.text = getTitle() +" ("+ getDuration() + ")";
  }


  /**
    * Starts the MediaPlayer. It unpauses a song or stops any active SongMedia, and then plays the new song.
    */
  public void playMedia(){
    if (this.mediaPaused()){
      System.out.println("Media unpaused");
    }
    else{
      BestMusicPlayer.allStop();
    }

    this.mediaPlayer.play();
    BestMusicPlayer.setLabel(this);
  }

  /**
    * Pauses a song
    */
  public void mediaPause(){
    this.mediaPlayer.pause();
  }

  /**
    * Returns whether or not the song is currently playing
    */
  public boolean mediaPlaying(){
    return (this.mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING));
  }

  /**
    * Returns whether or not the song is currently paused
    */
  public boolean mediaPaused(){
    return (this.mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED));
  }

  public List<SongMedia> toFlatList(){
    List<SongMedia> flatList = new ArrayList<SongMedia>();
    flatList.add(this);
    return flatList;
  }

  public void playPlaylist(){}

  /**
    * Returns the duration of the song
    */
  public String getDuration(){
    double minutes = this.getMinutes();
    double secondsDecimal = this.getSeconds();
    secondsDecimal *= 60;
    secondsDecimal = Math.floor(secondsDecimal);
    int seconds = (int)secondsDecimal;
    if(seconds < 10){
      return minutes + ":0" + seconds;
    }
    return minutes + ":" + seconds;
  }

  public double getMinutes(){
    return Math.floor(this.media.getDuration().toMinutes());
  }
  public double getSeconds(){
    return this.media.getDuration().toMinutes() - Math.floor(this.media.getDuration().toMinutes());
  }

  /**
    * Finds and then returns the title of the song from the Media object.
    */
  public String getTitle(){
    String title = (String)media.getMetadata().get("title");
    return title;
  }
}
