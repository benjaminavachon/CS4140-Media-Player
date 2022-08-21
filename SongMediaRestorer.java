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
 * Loads a SongMediaCollection from an xml file
 *
 * @author Eric St. Jean <es1187@plymouth.edu> Benjamin Vachon <bav1010@plymouth.edu>
 */

public class SongMediaRestorer {

  private static File file = new File("songs.xml");
  //private static ArrayList<SongMedia> buttonArray;
  public static void main(String[] args) {

  }
  /**
    * Marshals the SongMediaCollection from songs.xml, creates a new SongMediaCollection, and returns it. Includes Exception handling for when songs.xml does not exist.
    */
    public static SongMediaCollection restoreSongMediaCollection(File file){
    System.out.println("monkeys");
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(SongMediaCollection.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      SongMediaCollection songMediaCollection = (SongMediaCollection)
      jaxbUnmarshaller.unmarshal(file);
      songMediaCollection.prune();

      return songMediaCollection;
    } catch (JAXBException e) {
      //e.printStackTrace();
      System.out.println("songs.xml doesn't exist yet.");

      SongMediaCollection newSongMediaCollection = new SongMediaCollection();

      SongMedia peepers = new SongMedia("./songs/PrideOfTheWolverines.mp3");
      newSongMediaCollection.addSongMedia(peepers);
      SongMedia creepers = new SongMedia("./songs/BrassMonkey.mp3");
      newSongMediaCollection.addSongMedia(creepers);
      SongMedia meepers = new SongMedia("./songs/DanceMonkey.mp3");
      newSongMediaCollection.addSongMedia(meepers);

      return newSongMediaCollection;
    }
  }
}
