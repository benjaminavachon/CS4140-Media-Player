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

public class PlaylistSaver {

  private PlaylistCollection playlistCollection = new PlaylistCollection();
  private static File playlistFile = new File("playlist.xml");
  public static void main(String[] args) {

  }

  public static void savePlaylistCollection(PlaylistCollection playlistCollection){
    System.out.println("Saving: "+playlistCollection);
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(PlaylistCollection.class);
      Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
      jaxbMarshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
      jaxbMarshaller.marshal(playlistCollection, playlistFile);
    } catch (JAXBException e) {
      e.printStackTrace();
    }
  }
}
