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

public class PlaylistRestorer {

  private static File playlistFile = new File("playlist.xml");
  //private static ArrayList<SongMedia> buttonArray;
  public static void main(String[] args) {
    //SongMedia danceMonkeyButton = new SongMedia("./songs/DanceMonkey.mp3");
    //SongMediaSaver.saveSongMedia(danceMonkeyButton);
    //restoreSongMediaCollection(file);
  }
  
  @XmlElement
  public static PlaylistCollection getPlaylistCollection(File playlistFile){
    //System.out.println("monkeys");
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(PlaylistCollection.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      PlaylistCollection playlistCollection = (PlaylistCollection)
      jaxbUnmarshaller.unmarshal(playlistFile);

      /*for(SongMedia newSongMedia : mediaButtonCollection.getSongMedias()){
        System.out.println(newSongMedia.getSongPath());
      }*/

      return playlistCollection;
    } catch (JAXBException e) {
      //e.printStackTrace();
      System.out.println("playlist.xml doesn't exist yet.");
      return new PlaylistCollection();
    }
  }
}
