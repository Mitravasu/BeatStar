import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class GUI implements ActionListener, MouseListener, KeyListener {
    // Constants
    private final int FRAME_HEIGHT = 600;
    private final int FRAME_WIDTH = 712;
    private final int BUTTON_WIDTH = 150;
    private final int BUTTON_HEIGHT = 50;
    private final int TEXT_FIELD_WIDTH = 360;
    private final int TEXT_FIELD_HEIGHT = 50;

    // Dimensions
    private Dimension buttonDimensions = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
    private Dimension frameDimensions = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
    private Dimension textFieldDimensions = new Dimension(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
    private Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();

    // Frames
    private JFrame startMenu;
    private JFrame newGameMenu;
    private JFrame songSelectionMenu;
    private JFrame rulesFrame;
    private JFrame highScoreFrame;
    private MainGame mainGamePanel;
    private JFrame mainGameFrame;

    // Label
    private JLabel informationLabel;

    // Panels
    private ImagePanel startMenuLogoPanel;
    private JPanel startMenuButtonPanel;
    private JPanel textFieldAndButtonPanel;
    private JPanel informationLabelPanel;
    private JPanel songOptionPanel;
    private JPanel exitPanel;
    private JScrollPane tablePanel;

    // Buttons
    private JButton exitGame;
    private JButton highScore;
    private JButton newGame;
    private JButton rules;
    private JButton submit;
    private JButton[] song;
    private JButton back;
    private JButton mainMenu;

    // TextPane
    private JTextPane textPane;

    // TextField
    private JTextField textField;

    // String
    private String userName = "";

    // ArrayList
    private ArrayList<Integer> scores;
    private ArrayList<String> userNames;

    // Integer
    private int screenCenterX = screenDimension.width / 2 - FRAME_WIDTH / 2;
    private int screenCenterY = screenDimension.height / 2 - FRAME_HEIGHT / 2;
    private int frameNumber = 0;

    // Timer
    private Timer timer;

    // Writer
    private PrintWriter userNameWriter;


    public GUI() {
        createStartMenu();
        timer = new Timer(0, this);
    }

    //------------------------------------------------------START MENU--------------------------------------------------
    public void createStartMenu() {
        // Create Frame
        startMenu = new JFrame("Start Menu");

        // Set Frame Preferences
        startMenu.setPreferredSize(frameDimensions);
        startMenu.getContentPane().setBackground(Color.WHITE);

        // Create Frame Components
        createStartMenuButtonPanel();
        createStartMenuLogoPanel();
        // Button Panel
        // Logo Panel

        // Add Components to Frame
        startMenu.add(getStartMenuButtonPanel());
        startMenu.add(getStartMenuLogoPanel(), BorderLayout.PAGE_START);

        // Set screen to spawn in the centre
        finalizeFrameBehaviour(getStartMenu());
    }

    public void createStartMenuButtonPanel() {
        // Create Panel
        startMenuButtonPanel = new JPanel();

        // Set Panel Preferences
        startMenuButtonPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        startMenuButtonPanel.setBackground(Color.PINK);
        //startMenuButtonPanel.setLayout(new GridLayout(4,1));

        // Create Buttons
        createStartMenuButtons();

        // Add Buttons to Panel
        startMenuButtonPanel.add(getNewGame());
        startMenuButtonPanel.add(getRules());
        startMenuButtonPanel.add(getHighScore());
        startMenuButtonPanel.add(getExitGame());
    }

    public void createStartMenuButtons() {
        // New Game Button
        newGame = new JButton("New Game");
        newGame.setPreferredSize(buttonDimensions);
        newGame.setBackground(Color.WHITE);

        // Rules Button
        rules = new JButton("Game Rules");
        rules.setPreferredSize(buttonDimensions);
        rules.setBackground(Color.WHITE);

        // High Score Button
        highScore = new JButton("High Score");
        highScore.setPreferredSize(buttonDimensions);
        highScore.setBackground(Color.WHITE);

        // Exit Game Button
        exitGame = new JButton("Exit Game");
        exitGame.setPreferredSize(buttonDimensions);
        exitGame.setBackground(Color.WHITE);

        // Back Button
        back = new JButton("Back");
        back.setPreferredSize(buttonDimensions);
        back.setBackground(Color.WHITE);

        // Main Menu Button
        mainMenu = new JButton("Main Menu");
        mainMenu.setPreferredSize(buttonDimensions);
        mainMenu.setBackground(Color.WHITE);

        // Add Action Listener
        mainMenu.addActionListener(this);
        back.addActionListener(this);
        exitGame.addActionListener(this);
        highScore.addActionListener(this);
        newGame.addActionListener(this);
        rules.addActionListener(this);
    }

    public void createStartMenuLogoPanel() {
        String source = "Logo.png";
        startMenuLogoPanel = new ImagePanel(new ImageIcon(source).getImage());
    }

    //------------------------------------------------NEW GAME MENU-----------------------------------------------------

    public void createNewGameMenu() {
        // Create Frame
        newGameMenu = new JFrame("New Game");

        // Set Frame Preferences
        newGameMenu.setPreferredSize(frameDimensions);
        newGameMenu.getContentPane().setBackground(Color.WHITE);

        // Create Frame Components
        createTextFieldAndButtonPanel();
        createInformationLabelPanel();

        // Add Frame Components
        newGameMenu.add(getInformationLabelPanel(), BorderLayout.PAGE_START);
        newGameMenu.add(getTextFieldAndButtonPanel(), BorderLayout.PAGE_END);

        // Finalize Frame Behaviour
        newGameMenu.setVisible(true);
        finalizeFrameBehaviour(getNewGameMenu());

    }

    public void createInformationLabelPanel() {
        // Create Panel
        informationLabelPanel = new JPanel();

        // Set Panel Preferences
        informationLabelPanel.setPreferredSize(new Dimension(FRAME_WIDTH, 75));
        informationLabelPanel.setBackground(Color.PINK);

        // Create Panel Components
        informationLabel = new JLabel("Please Enter Your Username: ");
        informationLabel.setFont(new Font(informationLabel.getFont().getName(), Font.PLAIN, 18));

        // Add Panel Components
        informationLabelPanel.add(getInformationLabel(), BorderLayout.CENTER);
    }

    public void createTextFieldAndButtonPanel() {
        // Create Panel
        textFieldAndButtonPanel = new JPanel();

        // Set Panel Preferences
        textFieldAndButtonPanel.setPreferredSize(new Dimension(FRAME_WIDTH, 120));
        textFieldAndButtonPanel.setBackground(Color.PINK);

        // Create Panel Components
        createNewGameMenuComponents();

        // Add Panel Components
        textFieldAndButtonPanel.add(getTextField(), BorderLayout.CENTER);
        textFieldAndButtonPanel.add(getSubmit(), BorderLayout.CENTER);
        textFieldAndButtonPanel.add(getExitGame(), BorderLayout.CENTER);
        textFieldAndButtonPanel.add(getBack(), BorderLayout.CENTER);
        textFieldAndButtonPanel.add(getMainMenu(), BorderLayout.CENTER);
    }

    public void createNewGameMenuComponents() {
        submit = new JButton("Submit");
        submit.setPreferredSize(buttonDimensions);
        submit.setBackground(Color.WHITE);
        submit.addActionListener(this);

        textField = new JTextField("Enter Username");
        textField.setPreferredSize(textFieldDimensions);
        textField.addMouseListener(this);
        textField.addKeyListener(this);
    }

    //---------------------------------------------SONG SELECTION MENU--------------------------------------------------

    public void createSongSelectionMenu() {
        // Create Frame
        songSelectionMenu = new JFrame("Song Selection");

        // Set Frame Preferences
        songSelectionMenu.setPreferredSize(frameDimensions);
        songSelectionMenu.setBackground(Color.WHITE);

        // Create Frame Components
        getInformationLabel().setText("Please Select A Song");
        createSongOptionPanel();

        // Add Frame Components
        songSelectionMenu.add(getInformationLabelPanel(), BorderLayout.PAGE_START);
        songSelectionMenu.add(getSongOptionPanel(), BorderLayout.CENTER);

        // Finalize Frame Behaviour
        songSelectionMenu.setVisible(true);
        finalizeFrameBehaviour(getSongSelectionMenu());

    }

    public void createSongOptionPanel() {
        // Create Panel
        songOptionPanel = new JPanel();

        // Set Panel Preferences
        songOptionPanel.setPreferredSize(frameDimensions);
        songOptionPanel.setBackground(Color.PINK);
        songOptionPanel.setLayout(new GridLayout(4, 3));

        // Create Panel Components
        createSongOptionButtons();

        // Add Panel Components
        for (int i = 0; i < song.length; i++) {
            songOptionPanel.add(song[i]);
        }
        songOptionPanel.add(getExitGame(), BorderLayout.PAGE_END);
        songOptionPanel.add(getMainMenu(), BorderLayout.PAGE_END);
    }

    public void createSongOptionButtons() {
        song = new JButton[9];
        String[] songTitles = new String[9];
        File file = new File("song.txt");
        try {
            Scanner fileReader = new Scanner(file);

            for (int i = 0; i < songTitles.length; i++) {
                songTitles[i] = fileReader.nextLine();
                song[i] = new JButton(songTitles[i]);
                song[i].setPreferredSize(buttonDimensions);
                song[i].setBackground(Color.WHITE);
                song[i].addActionListener(this);
            }
        } catch (Exception exception) {
            System.out.println("File not Found!");
        }
    }

    //-----------------------------------------------MAIN GAME FRAME----------------------------------------------------
    public void createExitPanel() {
        exitPanel = new JPanel();

        exitPanel.setPreferredSize(new Dimension(FRAME_WIDTH, 60));
        exitPanel.setBackground(Color.PINK);

        exitPanel.add(getExitGame());
        exitPanel.add(getMainMenu());
    }

    //-------------------------------------------------RULES FRAME------------------------------------------------------
    public void createRulesFrame() {
        rulesFrame = new JFrame("Rules");

        rulesFrame.setPreferredSize(frameDimensions);
        rulesFrame.getContentPane().setBackground(Color.WHITE);

        createExitPanel();
        createInformationLabelPanel();

        getInformationLabelPanel().setPreferredSize(frameDimensions);
        getInformationLabel().setText("<html>Rules:<br>" +
                "- Click the circles before they run out. The quicker you tap, the more points.<br>" +
                "- Make sure to not click the black circles! Clicking three of them is an automatic loss!<br>" +
                "- The <span style=\"color:#0040FF\"> Blue </span> one is quicker than the rest so don’t be surprised!<br>" +
                "- Make sure to press <span style=\"color:#FF0000\"> Z </span> on your keyboard when a "
                + "<span style=\"color:#FF0000\"> Red </span> one appears.<br>" +
                "- Make sure to press <span style=\"color:#AEB404\"> X </span>  on your keyboard when a " +
                "<span style=\"color:#AEB404\"> Yellow </span> one appears.<br>" +
                "- Missing 5 colored circles (excluding the black circles) will cause you to lose!<br></html>");
        getInformationLabelPanel().setBackground(Color.WHITE);

        rulesFrame.add(getExitPanel(), BorderLayout.PAGE_END);
        rulesFrame.add(getInformationLabelPanel(), BorderLayout.PAGE_START);

        finalizeFrameBehaviour(rulesFrame);
        rulesFrame.setVisible(true);
    }


    //-----------------------------------------------HIGHSCORE FRAME----------------------------------------------------
    public void createHighScoreFrame() {
        highScoreFrame = new JFrame("High Score");

        highScoreFrame.setPreferredSize(frameDimensions);
        highScoreFrame.getContentPane().setBackground(Color.WHITE);

        createExitPanel();
        createInformationLabelPanel();

        sortScores();

        String text = "<table><tr><th><b>Player</b></th><th><b>Score</b></th></tr>";

        for (int i = 0; i < getScores().size(); i++) {
            text += "<tr>" + "<td>" + getUserNames().get(i) + "</td>"
                    + "<td>" + getScores().get(i) + "</td>" + "</tr>";
        }

        createTextPane();
        textPane.setContentType("text/html");
        textPane.setText("<html>" + text + "</html>");

        createTablePanel();

        highScoreFrame.add(getExitPanel(), BorderLayout.PAGE_END);
        highScoreFrame.getContentPane().add(getTablePanel(), BorderLayout.PAGE_START);

        finalizeFrameBehaviour(highScoreFrame);
        highScoreFrame.setVisible(true);
    }

    public void createTextPane() {
        textPane = new JTextPane();
        textPane.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT - 100));
        textPane.setEditable(false);
    }

    public void createTablePanel() {
        tablePanel = new JScrollPane(textPane);
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tablePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }


    //------------------------------------------------OTHER METHODS-----------------------------------------------------

    public void newGame() {
        startMenu.setVisible(false);
        setFrameNumber(1);
        createNewGameMenu();
    }

    public void checkRules() {
        startMenu.setVisible(false);
        setFrameNumber(4);
        createRulesFrame();
    }

    public void checkHighScores() {
        startMenu.setVisible(false);
        setFrameNumber(5);
        createHighScoreFrame();
    }

    public void proceedToSongSelection() {
        newGameMenu.setVisible(false);
        setFrameNumber(2);
        getTextField().setText(getUserName());
        createSongSelectionMenu();
    }

    public void sortScores() {
        scores = new ArrayList<>();
        scores = readFromScoreFile();

        userNames = new ArrayList<>();
        userNames = readFromUserNameFile();

        int arrayLength = scores.size();

        for (int i = 0; i < arrayLength - 1; i++) {
            for (int j = 0; j < arrayLength - i - 1; j++) {
                if (scores.get(j) < scores.get(j + 1)) {
                    // Sort score
                    int tempScore = scores.get(j);
                    scores.set(j, scores.get(j + 1));
                    scores.set(j + 1, tempScore);

                    // Shift the userName position such that it corresponds with the score.
                    String tempUserName = userNames.get(j);
                    userNames.set(j, userNames.get(j+1));
                    userNames.set(j+1, tempUserName);
                }
            }
        }
    }


    public ArrayList<String> readFromUserNameFile() {
        ArrayList<String> userNames = new ArrayList<>();

        File file = new File("userName.txt");
        try {
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                userNames.add(fileReader.nextLine());
            }
            fileReader.close();
        } catch (Exception exception) {
            System.out.println("Error occurred in reading file!");
        }

        return userNames;
    }

    public ArrayList<Integer> readFromScoreFile() {
        ArrayList<Integer> scores = new ArrayList<>();

        File file = new File("score.txt");
        try {
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNextInt()) {
                scores.add(fileReader.nextInt());
            }
            fileReader.close();
        } catch (Exception exception) {
            System.out.println("Error occurred in reading file!");
        }

        return scores;
    }

    public void songSelected(String song) {
        setFrameNumber(3);
        String songFile = song + ".wav";

        songSelectionMenu.setVisible(false);

        mainGameFrame = new JFrame("BeatStar");
        mainGameFrame.setPreferredSize(frameDimensions);
        mainGameFrame.getContentPane().setBackground(Color.WHITE);

        mainGamePanel = new MainGame(songFile, song);
        createExitPanel();

        mainGameFrame.add(mainGamePanel, BorderLayout.CENTER);
        mainGameFrame.add(exitPanel, BorderLayout.PAGE_END);
        finalizeFrameBehaviour(mainGameFrame);
        getMainGamePanel().setReturnToMainMenu(false);
        mainGameFrame.setVisible(true);
        timer.start();
    }

    public void gameOver() {
        mainGamePanel.getMusicPlayer().stopMusic();
        gameFinished();

        JOptionPane.showMessageDialog(null, "You Lost! \n Your score: "
                + mainGamePanel.getScore(), "Game Over", JOptionPane.INFORMATION_MESSAGE);
        playAgainDialog();
    }

    public void gameFinished() {
        writeToFile("score.txt", "" + mainGamePanel.getScore());
        System.out.println(mainGamePanel.getScore());
        mainGameFrame.setVisible(false);
        timer.stop();
    }

    public void playAgainDialog() {
        int input = JOptionPane.showConfirmDialog(null, "Play Again?", "Play Again",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (input == JOptionPane.YES_OPTION) {
            createStartMenu();
            startMenu.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Thank You for playing BeatStar!"
                    + "\nWe hope you enjoyed the experience!");
            System.exit(0);
        }
    }

    public void gameWon() {
        gameFinished();

        JOptionPane.showMessageDialog(null, "You Won! \n Your score: "
                + mainGamePanel.getScore(), "Game Won", JOptionPane.INFORMATION_MESSAGE);
        playAgainDialog();
    }

    public void finalizeFrameBehaviour(JFrame frame) {
        frame.setLocation(screenCenterX, screenCenterY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
    }

    public String readFromFile(String fileName) {
        String fullText = "";
        File file = new File(fileName);
        try {
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                fullText += fileReader.nextLine() + "\n";
            }
            fileReader.close();
        } catch (Exception exception) {
            System.out.println("Error occurred in reading file!");
        }

        return fullText;
    }

    public void writeToFile(String fileName, String text) {
        String fullText = "";

        fullText = readFromFile(fileName);

        fullText += text;

        try {
            userNameWriter = new PrintWriter(fileName, "UTF-8");
        } catch (Exception exception) {
            System.out.println("Error!");
        }
        userNameWriter.println(fullText);
        userNameWriter.close();
    }
    //------------------------------------------------OVERRIDDEN METHODS------------------------------------------------
    // Button Listener
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == timer) {
            if (mainGamePanel.didPlayerLose()) {
                gameOver();
            } else if (mainGamePanel.didPlayerWin()) {
                gameWon();
            }
        } else {
            if (source == getNewGame()) {
                newGame();
            } else if (source == getRules()) {
                checkRules();
            } else if (source == getHighScore()) {
                checkHighScores();
            } else if (source == getExitGame()) {
                if(getFrameNumber() == 2) {
                    writeToFile("score.txt", "0");
                }
                System.exit(0);
            } else if (source == getSubmit()) {
                userName = getTextField().getText();
                getTextField().setText("");
                writeToFile("userName.txt", userName);
                proceedToSongSelection();
            } else if (source == getBack()) {
                newGameMenu.setVisible(false);
                createStartMenu();
                startMenu.setVisible(true);
            } else if (source == getMainMenu()) {
                switch (getFrameNumber()) {
                    case 1:
                        getNewGameMenu().setVisible(false);
                        break;
                    case 2:
                        getSongSelectionMenu().setVisible(false);
                        writeToFile("score.txt", "0");
                        break;
                    case 3:
                        getMainGamePanel().setReturnToMainMenu(true);
                        getMainGamePanel().getMusicPlayer().stopMusic();
                        getMainGamePanel().setVisible(false);
                        break;
                    case 4:
                        getRulesFrame().setVisible(false);
                        break;
                    case 5:
                        getHighScoreFrame().setVisible(false);
                        break;
                }
                createStartMenu();
                getStartMenu().setVisible(true);
            } else {
                for (int i = 0; i < song.length; i++) {
                    if (source == getSong()[i]) {
                        songSelected(getSong()[i].getText());
                    }
                }
            }
        }
    }

    // Key Listener
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_ENTER) {
            userName = getTextField().getText();
            getTextField().setText("");
            writeToFile("userName.txt", userName);
            proceedToSongSelection();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    // Mouse Listener
    @Override
    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();
        if (source == textField) {
            getTextField().setText("");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    //----------------------------------------------------GETTERS-------------------------------------------------------

    public JFrame getStartMenu() {
        return startMenu;
    }

    public JPanel getStartMenuLogoPanel() {
        return startMenuLogoPanel;
    }

    public JPanel getStartMenuButtonPanel() {
        return startMenuButtonPanel;
    }

    public JButton getExitGame() {
        return exitGame;
    }

    public JButton getHighScore() {
        return highScore;
    }

    public JButton getNewGame() {
        return newGame;
    }

    public JButton getRules() {
        return rules;
    }

    public JFrame getNewGameMenu() {
        return newGameMenu;
    }

    public JPanel getTextFieldAndButtonPanel() {
        return textFieldAndButtonPanel;
    }

    public JButton getSubmit() {
        return submit;
    }

    public JTextField getTextField() {
        return textField;
    }

    public JFrame getSongSelectionMenu() {
        return songSelectionMenu;
    }

    public JLabel getInformationLabel() {
        return informationLabel;
    }

    public JPanel getInformationLabelPanel() {
        return informationLabelPanel;
    }

    public String getUserName() {
        return userName;
    }

    public JPanel getSongOptionPanel() {
        return songOptionPanel;
    }

    public JButton[] getSong() {
        return song;
    }

    public JButton getBack() {
        return back;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public JButton getMainMenu() {
        return mainMenu;
    }

    public MainGame getMainGamePanel() {
        return mainGamePanel;
    }

    public JPanel getExitPanel() {
        return exitPanel;
    }

    public JFrame getRulesFrame() {
        return rulesFrame;
    }

    public JFrame getHighScoreFrame() {
        return highScoreFrame;
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public ArrayList<String> getUserNames() {
        return userNames;
    }

    public JScrollPane getTablePanel() {
        return tablePanel;
    }

    public void setFrameNumber(int frameNumber) {
        this.frameNumber = frameNumber;
    }
}
