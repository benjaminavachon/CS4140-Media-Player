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
import java.util.concurrent.*;


/**
 * creates a Playlist of songs
 *
 * @author Eric St. Jean <es1187@plymouth.edu> Benjamin Vachon <bav1010@plymouth.edu>
 */
@XmlRootElement
public class Playlist extends Music{
  private String name;
  private List<Music> playlist;

  @XmlTransient
  private int iterator = 0;

  @XmlTransient
  private int playlistIterator;

  @XmlTransient
  List<Music> allNestedSongs = new ArrayList<Music>();

  /**
    * Constructor for Playlist. sets the Name and initializes the Arraylist.
    */
  public Playlist(String name){
    this.playlist = new ArrayList<Music>();
    this.name = name;
    //setPlaylistName();
  }

  /**
    * Empty constructor
    */
  public Playlist(){
    this("");
  }
  /**
  public String getPlaylistName(){
    return this.name;
  }

  public void setPlaylistName(String stringy){
    //this.name = this.name + " (" + this.getDuration() + ")";
    this.name = stringy;
  }
  **/

  /**
    * Setter for the playlist name
    */
  @XmlElement
  public void setName(String newName){
    this.name=newName;
  }
  /**
    * Getter for the playlist name
    */
  public String getName(){
    return this.name;
  }

  /**
    * Returns the text for the buttons in BestMusicPlayer. Combines the playlist name and duration.
    */
  public String getText(){
    return this.name + " (" + this.getDuration() + ")";

  }

  /**
    * Returns the ArrayList of the songs in the playlist.
    */
  public List<Music> getPlaylist(){
    return this.playlist;
  }

  /**
    * Sets the ArrayList of songs that are in the playlist.
    */
  @XmlElement
  public void setPlaylist(List<Music> playlist){
    this.playlist = playlist;
  }

  /**
    * Adds a song to the playlist
    */
  public void addToPlaylist(Music song){
    this.playlist.add(song);
  }

  /**
    * Removes a song from the playlist
    */
  public void removeFromPlaylist(int i){
    this.playlist.remove(iterator);
  }

  public MediaPlayer getMediaPlayer(){
    return this.playlist.get(iterator).getMediaPlayer();
  }

  public Media getMedia(){
    return this.playlist.get(iterator).getMedia();
  }

  public void init(){
    System.out.println("playlist");
  }

  public String toString(){
    String stringy=this.getText();
    for (Music song : this.playlist){
      stringy = stringy+": "+song.getText();
    }

    return stringy;
  }

  /**
    * Returns the duration of the playlist
    */
  public String getDuration(){
    for(Music song : this.playlist){
      //song.init();
      //System.out.println(song);
    }

    int totalMinutes = 0;
    for(Music song : this.playlist){

      totalMinutes += song.getMinutes();
    }
    double secondsDecimal = 0;
    for(Music song : this.playlist){
      secondsDecimal += song.getSeconds();
    }
    totalMinutes += (int)Math.floor(secondsDecimal);
    secondsDecimal -= (int)Math.floor(secondsDecimal);
    secondsDecimal *= 60;

    int seconds = (int)secondsDecimal;

    if(seconds < 10){
      return totalMinutes + ":0" + seconds;
    }

    return totalMinutes + ":" + seconds;
  }

  public double getMinutes(){
    return 1;
  }
  public double getSeconds(){
    return 1;
  }

  /**
    * Stops all songs currently being played, then plays the next song in the playlist.
    */
  public void skipSong(){
    //BestMusicPlayer.allStop();
    if(iterator != playlist.size()-1){
      iterator++;
      playMedia();
    }else{
      iterator = 0;
    }
  }

  /**
    * Stops all songs currently being played, then plays the previous song in the playlist.
    */
  public void previousSong(){
    //BestMusicPlayer.allStop();
    if(iterator != 0){
      iterator--;
      playMedia();
    }else{
      iterator = 0;
      playMedia();
    }
  }

  public void stopMedia(){
    this.playlist.get(iterator).stopMedia();
  }

  public boolean mediaPlaying(){
    return this.playlist.get(iterator).mediaPlaying();
  }

  public boolean mediaPaused(){
    return this.playlist.get(iterator).mediaPaused();
  }

  public void mediaPause(){}

  public void mediaStop(){}

  public void getSong(){
    for(Music song : playlist){
      song.getSong();
    }
  }

  public List<SongMedia> toFlatList(){
    List<SongMedia> flatList = new ArrayList<SongMedia>();
    for(Music song : this.playlist){
      List<SongMedia> importedList = new ArrayList<SongMedia>();
      importedList = song.toFlatList();
      for(SongMedia newSong : importedList){
        flatList.add(newSong);
      }
    }
    return flatList;
  }

  public void playPlaylist(){
    Music currentSong;
    if(playlistIterator == playlist.size()){
      playlistIterator = 0;
    }else{
      currentSong = this.playlist.get(playlistIterator);
      System.out.println("playlistIterator: " + playlistIterator);
      if(currentSong instanceof Playlist){
        currentSong.playPlaylist();
      }else{
        currentSong.playMedia();
        currentSong.getMediaPlayer().setOnEndOfMedia(()->{
          playlistIterator++;
          playPlaylist();
        });
      }
    }
  }

  public List<Music> getBigPlaylist(){
    for(Music song : this.playlist){
      allNestedSongs.addAll(song.toFlatList());
    }
    this.setPlaylist(allNestedSongs);
    System.out.println(this.playlist);
    return allNestedSongs;
  }

  /**
    * Plays the songs in the playlist in order
    */
  public void playMedia(){

    BestMusicPlayer.setActivePlaylist(this);
    //System.out.println("Hopefully this runs before it breaks when Iterator = 2");
    Music currentSong;
    String songName;
    int tempIterator;
    //ArrayList<Music> currentPlaylist = this.playlist;
    ////System.out.println("Current Playlist: "+this.playlist);
    ////System.out.println("Current Playlist Size: "+currentPlaylist.size());
    //System.out.println("is iterator the same as playlist size? "+(currentPlaylist.size() == iterator));
    //System.out.println("in the beginning of playMedia iterator is: "+iterator);
    /*for(Music song : this.playlist){
      if(iterator == this.playlist.size()){
        iterator = 0;
        System.out.println("END OF PLAYLIST REACHED");
      }else{
        currentSong = this.playlist.get(iterator);
        System.out.println("iterator: "+iterator);
        if(currentSong instanceof Playlist){
          System.out.println("GOING TO playPlaylist WHAT FUN");
          iterator++;
          currentSong.playPlaylist();
        }else{
          System.out.println("PLAYING "+currentSong+" HOW FUN");
          currentSong.playMedia();
          currentSong.getMediaPlayer().setOnEndOfMedia(()->{
            iterator++;
            System.out.println("in endOfMedia iterator: "+iterator);
            playMedia();
          });
        }
      }
    }*/

    


    if(iterator == this.playlist.size()){
      System.out.println("iterator = 0");
      iterator = 0;
    }else{
      currentSong = this.playlist.get(iterator);
      System.out.println("iterator: "+ iterator);
      //System.out.println("Playing: " + currentSong + "  iterator: " + iterator);

      if(currentSong instanceof Playlist){
        System.out.println("pingus");
        currentSong.playMedia();
      }
      else{
        currentSong.playMedia();
      }

      //this.playlist.get(iterator).playMedia();
      BestMusicPlayer.setLabel(this.playlist.get(iterator));


      currentSong.getMediaPlayer().setOnEndOfMedia(()->{
        iterator++;
        System.out.println("In setEndOfMedia.  iterator: " + iterator);
        playMedia();
      });
    }
  }
}
