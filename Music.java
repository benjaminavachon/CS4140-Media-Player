import java.io.*;
import java.util.*;
import java.nio.file.Paths;
import java.lang.*;
import javafx.scene.media.*;
import javafx.embed.swing.JFXPanel;
import javax.xml.bind.*;
import javax.xml.*;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.*;

@XmlSeeAlso({Playlist.class,SongMedia.class})
@XmlType
public abstract class Music{
  public abstract void playMedia();

  public abstract MediaPlayer getMediaPlayer();

  public abstract Media getMedia();

  public abstract void stopMedia();

  public abstract String getDuration();

  public abstract boolean mediaPlaying();

  public abstract boolean mediaPaused();

  public abstract void mediaPause();

  public abstract void getSong();

  public abstract void playPlaylist();

  public abstract String getText();

  public abstract void init();

  public abstract double getMinutes();

  public abstract double getSeconds();

  public abstract List<SongMedia> toFlatList();
}
