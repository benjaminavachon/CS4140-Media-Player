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
 * Saves a SongMediaCollection to an xml file.
 *
 * @author Eric St. Jean <es1187@plymouth.edu> Benjamin Vachon <bav1010@plymouth.edu>
 */

public class SongMediaSaver {

  private SongMediaCollection songMediaArray = new SongMediaCollection();
  private static File file = new File("songs.xml");
  public static void main(String[] args) {
    SongMedia peepers = new SongMedia("/songs/PrideOfTheWolverines.mp3");
    SongMedia brassMonkeyButton = new SongMedia("./songs/BrassMonkey.mp3");
    SongMedia danceMonkeyButton = new SongMedia("./songs/DanceMonkey.mp3");

  }

  /**
    * Saves all of the songs that have been added to songs.xml. Includes Exception handling for when songs.xml does not exist.
    */
  public static void saveSongMediaCollection(SongMediaCollection songMediaCollection){

    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(SongMediaCollection.class);
      Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
      jaxbMarshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
      jaxbMarshaller.marshal(songMediaCollection, file);
    } catch (JAXBException e) {
      //e.printStackTrace();
      System.out.println("FIle doesnt exist");
    }
  }
}
