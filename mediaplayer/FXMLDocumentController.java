/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaplayer;

import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 *
 * @author Dan
 */
public class FXMLDocumentController implements Initializable {
    
    private String filePath;
    private MediaPlayer mediaPlayer;
    
    @FXML
    private MediaView mediaView;
    
    @FXML
    private Slider sliderVolume;
    @FXML
    private Slider sliderVideo;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        FileChooser filechooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file (*.mp4)", "*.mp4");
            filechooser.getExtensionFilters().add(filter);
            File file = filechooser.showOpenDialog(null);
            filePath = file.toURI().toString();
            
            if(filePath != null)
            {
                Media media = new Media(filePath);
                mediaPlayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaPlayer);
                
                DoubleProperty width = mediaView.fitWidthProperty();
                DoubleProperty height = mediaView.fitHeightProperty();
                width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
                
                sliderVolume.setValue(mediaPlayer.getVolume() * 100);
                sliderVolume.valueProperty().addListener(new InvalidationListener(){

                    @Override
                    public void invalidated(javafx.beans.Observable observable) {
                         mediaPlayer.setVolume(sliderVolume.getValue() / 100);
                    }
                    
                });

                mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                    @Override
                    public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                        sliderVideo.setValue(newValue.toSeconds());
                    }
                });
                
                sliderVideo.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        mediaPlayer.seek(Duration.seconds(sliderVideo.getValue()));
                    }
                });
                
                mediaPlayer.play();
            }
    }
    
    @FXML
    private void pauseVideo(ActionEvent event)
    {
        mediaPlayer.pause();
    }
    
    @FXML
    private void playVideo(ActionEvent event)
    {
        mediaPlayer.play();
        mediaPlayer.setRate(1.0);
    }
    
    @FXML
    private void stopVideo(ActionEvent event)
    {
        mediaPlayer.stop();
    }
    
    @FXML
    private void fastVideo(ActionEvent event)
    {
        mediaPlayer.setRate(1.5);
    }
    
    @FXML
    private void ffastVideo(ActionEvent event)
    {
        mediaPlayer.setRate(2.0);
    }
    
    @FXML
    private void rewindVideo(ActionEvent event)
    {
        mediaPlayer.setRate(0.75);
    }
    
    @FXML
    private void rrewindVideo(ActionEvent event)
    {
        mediaPlayer.setRate(0.5);
    }
    
    @FXML
    private void exitVideo(ActionEvent event)
    {
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    private static class ChangeListenerImpl implements ChangeListener<Duration> {

        public ChangeListenerImpl() {
        }

        @Override
        public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
            
        }
    }
    
}
