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
 * creates and ArrayList of SongMedias
 *
 * @author Eric St. Jean <es1187@plymouth.edu> Benjamin Vachon <bav1010@plymouth.edu>
 */

@XmlRootElement
public class SongMediaCollection {

  private String name; //official name
  private ArrayList<SongMedia> songMediaArray;

  /**
    * Constructor for SongMediaCollection. takes a name and initializes the ArrayList.
    */
  public SongMediaCollection(String name) {
    this.name = name;
    this.songMediaArray = new ArrayList<SongMedia>();
  }

  /**
    * Adds a SongMedia object to the songMediaCollection
    */
  public void addSongMedia(SongMedia m) {
    this.songMediaArray.add(m);
  }

  /**
    * No-arg contsructor for Jaxb
    */
  public SongMediaCollection() {
    this(""); //call the other one
  }

  /**
    * Returns the Name
    */
  public String getName() {
    return this.name;
  }

  /**
    * Sets the name
    */
  @XmlElement
  public void setName(String name) {
    this.name = name;
  }

  /**
    * Returns all of the SongMedia objects in the collection
    */
  public ArrayList<SongMedia> getSongMedias() {
    return this.songMediaArray;
  }

  /**
    * Populates songMediaArray
    */
  @XmlElementWrapper
  @XmlElement
  public void setSongMedias(ArrayList<SongMedia>songMediaArray) {
    this.songMediaArray = songMediaArray;
  }


  /**
    * Removes duplicates from songMediaArray
    */
  public void prune(){
    HashSet<SongMedia> newArray= new HashSet<SongMedia>(songMediaArray);
    songMediaArray.clear();
    songMediaArray.addAll(newArray);
  }
}
