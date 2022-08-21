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
 * creates and ArrayList of Playlists
 *
 * @author Eric St. Jean <es1187@plymouth.edu> Benjamin Vachon <bav1010@plymouth.edu>
 */

@XmlRootElement
public class PlaylistCollection{
  private String name; //official name
  private ArrayList<Playlist> playlistArray; // array of playlists to be saved and loaded

  /**
    * Constructor for PlaylistCollection
    */
  public PlaylistCollection(String name){
    this.name = name;
    this.playlistArray = new ArrayList<Playlist>();
  }

  /**
    * Adds a Playlist to the PlaylistCollection
    */
  public void addPlaylist(Playlist playlistToAdd){
    this.playlistArray.add(playlistToAdd);
  }

  /**
    * No arg constructor for JAXB
    */
  public PlaylistCollection(){
    this("");

  }

  /**
    * getter for the PlaylistCollection name
    */
  public String getName(){
    return this.name;
  }

  public String toString(){
    String stringy=new String();
    for (Playlist playlist : this.playlistArray){
      stringy = stringy+playlist+"\n ";
    }
    return stringy;
  }

  /**
    * setter for the PlaylistCollection name
    */
  @XmlElement
  public void setName(String name){
    this.name = name;
  }

  /**
    * Returns all of the Playlist objects in the collection
    */
  public ArrayList<Playlist> getPlaylistArray(){
    return this.playlistArray;
  }

  @XmlElementWrapper
  @XmlElement
  public void setPlaylistArray(ArrayList<Playlist> playlistArray){
    this.playlistArray = playlistArray;
  }


} //end of PlaylistCollection.java
