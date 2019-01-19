import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class MusicPlayer {
    private String filePath;
    private Clip clip;

    public MusicPlayer(String filePath) {
        this.filePath = filePath;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(getFilePath()).getAbsoluteFile());
            clip = AudioSystem.getClip();
            getClip().open(audioInputStream);
        } catch (Exception exception) {
            System.out.println("Error occurred in opening file!");
        }
    }

    public void playMusic() {
        try {
            System.out.println("Playing test file: " + getFilePath());
            getClip().start();

        } catch (Exception exception) {
            System.out.println("Error! Could not play sound file!");
        }
    }

    public void stopMusic() {
        try {
            getClip().stop();
            getClip().close();
        } catch (Exception ex) {
            System.out.println("Error!");
        }
    }

    public boolean clipFinished() {
        return !getClip().isRunning();
    }

    public String getFilePath() {
        return filePath;
    }

    public Clip getClip() {
        return clip;
    }
}
