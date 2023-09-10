import java.awt.Color;		//For background color in JFrame
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;	//For JFrame
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Quiz 
{
	JFrame window = new JFrame("Quiz!");
	JPanel titleNamePanel, startButtonPanel, mainTextPanel, choiceButtonPanel, playerPanel, buttonPanel, musicPanel, picPanel;	//Canvas for text, buttons, etc.
	JLabel titleNameLabel, playerName, qNum, score, picLabel;	//Displays text
	JSlider soundVolumeSlider, musicVolumeSlider;
	Font titleFont = new Font("Arial", Font.PLAIN, 70);	//Font for the title text
	Font headerFont = new Font("Arial", Font.BOLD, 25);
	Font normalFont = new Font("Arial", Font.PLAIN, 30);
	JButton startButton, choice1, choice2, choice3, choice4, usernameButton, musicButton, soundButton;
	JTextArea mainTextArea;
	int quest, questionCounter, points, correctChoice, reset, musicSwitch = 1, soundSwitch = 1;
    boolean usernameInputStarted; // Flag to track if inputUsername has been invoked
	String position, username = ": ---", correctSound, wrongSound, introSound, musicOnOff;
	URL correctSoundURL, wrongSoundURL, introSoundURL, winnerSoundURL;
	
	
	Random rand = new Random();
	
	TitleScreenHandler tsHandler = new TitleScreenHandler();
	ChoiceHandler choiceHandler = new ChoiceHandler();
	SoundHandler soundHandler = new SoundHandler();
	SoundEffect se = new SoundEffect();
	Music mu = new Music();
	ImageIcon image;
	
	private static final double MIN_VOLUME_DB = -40.0; // Minimum volume in decibels
	private static final double MAX_VOLUME_DB = 6.0;   // Maximum volume in decibels
	private static final double RANGE_DB = MAX_VOLUME_DB - MIN_VOLUME_DB;
	
	Question[] questions = new Question[]{
	        new Question("How many states are in the United States?", new String[]{"13", "50", "100", "51"}, 1),
	        new Question("How many countries are in the world today?", new String[]{"151", "211", "178", "195"}, 3),
	        new Question("What year did the One Piece anime first air?", new String[]{"1999", "1997", "1991", "2000"}, 0),
	        new Question("How many members are in Love Live's 'Aqours' group?", new String[]{"6", "7", "12", "9"}, 3),
	        new Question("Which of these is a fruit?", new String[]{"Broccoli", "Carrot", "Asparagus", "Corn"}, 3),
	        new Question("How big is the US in square miles (in millions)?", new String[]{"3.8", "7.2", "9.8", "2.6"}, 0),
	        new Question("What month did the Nintendo Switch release in 2017?", new String[]{"November", "March", "June", "February"}, 1),
	        new Question("What year was the discord server \"Tom's Fan Club\" created?", new String[]{"2015", "2017", "2018", "2020"}, 1),
	        new Question("Which college/university is the best?", new String[]{"UGA", "Flagler", "GT", "KSU"}, 0),
	        new Question("What anime continues to rule as #1 in the all anime category on MyAnimeList?", new String[]{"LiW", "AOT", "FMAB", "One Piece"}, 2),
	        
	        new Question("Which college/university is the best?", new String[]{"UGA", "Flagler", "GT", "KSU"}, 0),
	        new Question("Which video game publisher started the 'Call of Duty' series?", new String[]{"Treyarch", "Sledgehammer", "Bungie", "Activision"}, 3),
	        new Question("What was Mario's original profession?", new String[]{"Plumber", "Carpenter", "Electrician", "Delivery"}, 1),
	        new Question("When did the empire-building game Civilization 1 release?", new String[]{"1985", "2004", "1998", "1991"}, 3),
	        new Question("Which studio began the 'Halo' series?", new String[]{"343", "Bungie", "Activision", "Blizzard"}, 1),
	        new Question("What is the highest-grossing video game of all time?", new String[]{"Pac-Man", "Minecraft", "Tetris", "Mario Bros."}, 0),
	        new Question("What coding language was Minecraft first created in?", new String[]{"C++", "Java", "Python", "C#"}, 1),
	        new Question("Who is the GOAT?", new String[]{"Kobe", "Lebron", "Magic", "MJ"}, 3),
	        new Question("How much does a legendary skin on OW2 cost?", new String[]{"$1", "$5", "$10", "$20"}, 3),
	        new Question("What is Fanta?", new String[]{"Soda", "Cat", "Dog", "Human"}, 1),
	        
	        new Question("Which Forza Horizon song is the most popular on Spotify?", new String[]{"All the Stars", "Levitating", "Industry Baby", "X Gonna Give it to Ya"}, 2),
	        new Question("Who is the author of One Piece?", new String[]{"Tite Kubo", "Eiichiro Oda", "J.K. Rowling", "Hayao Miyazaki"}, 1),
	        new Question("What anime studio adapted the manga Death Note?", new String[]{"Wit", "Cloverworks", "Bones", "Madhouse"}, 3),
	        new Question("How many parts are in Jojo's Bizarre Adventure?", new String[]{"9", "7", "3", "5"}, 0),
	        new Question("Who didn't disappear in Marvel's Infinity War?", new String[]{"Spiderman", "Dr. Strange", "Thor", "Black Panther"}, 2),
	        new Question("When did Blockbuster close?", new String[]{"2009", "2014", "2016", "2006"}, 1),
	        new Question("What is the best selling graphic novel of all time (2023)?", new String[]{"One Piece", "Superman", "Batman", "Spider-Man"}, 1),
	        new Question("What is the best selling video game console of all time (2023)?", new String[]{"PlayStation 2", "Nintendo Switch", "Xbox 360", "Wii"}, 0),
	        new Question("What is the name of Google's conversational AI?", new String[]{"Cortana", "LaMDA", "ChatGPT", "DALL-E"}, 1),
	        new Question("If someone were born in 1941, what generation would they belong to?", new String[]{"Millenial", "Gen X", "Silent Gen", "Baby Boomer"}, 2),
	        
	        new Question("What soccer team did Lionel Messi transfer to in 2023?", new String[]{"PSG", "ATL United", "Barcelona", "Inter Miami"}, 3),
	        new Question("What is the standard adult soccer ball size?", new String[]{"Size 3", "Size 5", "Size 7", "Size 9"}, 1),
	        new Question("Which of these songs is NOT an Eminem song?", new String[]{"Cold Wind Blows", "Slim Shady", "Beautiful", "Cursed"}, 3),
	        new Question("What band was Michael Jackson in with his 4 brothers when he was a child?", new String[]{"The Crew", "Jackson 5", "Five Time", "ABC's"}, 1),
	        new Question("Which email service is the oldest?", new String[]{"Gmail", "Yahoo! Mail", "Outlook", "Proton Mail"}, 1),
	        new Question("What is the capital of Algeria?", new String[]{"Algiers", "Oran", "Batna", "Constantine"}, 0),
	        new Question("Which is the newest Coca-Cola flavor?", new String[]{"Diet Coke w/Lime", "Coca-Cola Cherry", "Coca-Cola Vanilla", "Coca-Cola Orange"}, 3),
	        new Question("What is the most viewed YouTube video?", new String[]{"Sorry", "Baby Shark Dance", "Despacito", "Gangnam Style"}, 1),
	        new Question("What is the third most spoken language in the world?", new String[]{"Hindi", "English", "Mandarin", "French"}, 0),
	        new Question("Which of these countries has the largest population?", new String[]{"Italy", "Spain", "France", "Germany"}, 3),
	        
	        new Question("In which biome does the greatest amount of photosynthesis occur?", new String[]{"Ocean", "Mountains", "Desert", "Tundra"}, 0),
	        new Question("What is the world's highest mountain above sea level?", new String[]{"Mount Chimborazo", "Mount Everest", "Mount Chimborazo", "K2"}, 1),
	        new Question("The general precipitation and temperatures over a long period of time is called ___.", new String[]{"Weather", "Biome", "Biotic Factors", "Climate"}, 3),
	        /*new Question("", new String[]{"", "", "", ""}, 3),
	        new Question("", new String[]{"", "", "", ""}, 3),
	        new Question("", new String[]{"", "", "", ""}, 3),
	        new Question("", new String[]{"", "", "", ""}, 3),
	        new Question("", new String[]{"", "", "", ""}, 3),
	        new Question("", new String[]{"", "", "", ""}, 3),
	        new Question("", new String[]{"", "", "", ""}, 3),*/
	        };
	
	public void shuffleArray(Question[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Question temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
	
	public void displayQuestionByIndex(int index) {
	    Question questionToDisplay = questions[index];

	    mainTextArea.setText(questionToDisplay.getQuestion());

	    String[] choices = questionToDisplay.getChoices();
	    choice1.setText(choices[0]);
	    choice2.setText(choices[1]);
	    choice3.setText(choices[2]);
	    choice4.setText(choices[3]);
	}

	public static void main(String[] args) 
	{
		
		new Quiz();

	}
	
	public Quiz()
	{
		window = new JFrame();
		window.setSize(800, 600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setBackground(Color.black);
		window.setLayout(null);
		window.setTitle("Trivia Game");
		
		titleNamePanel = new JPanel();
		titleNamePanel.setBounds(100, 100, 600, 150); 	//Starts from top left: x, y, width, height
		titleNamePanel.setBackground(Color.black);	//Becomes transparent rather than just same color
		titleNameLabel = new JLabel("Trivia");
		titleNameLabel.setForeground(Color.white);
		titleNameLabel.setFont(titleFont);
		
		startButtonPanel = new JPanel();
		startButtonPanel.setBounds(300, 400, 200, 100);
		startButtonPanel.setBackground(Color.black);
		
		startButton = new JButton("START");
		startButton.setBackground(Color.black);	//Color of button
		startButton.setForeground(Color.white); //Color of text
		startButton.setFont(normalFont);		//Add normal font to start button text
		startButton.addActionListener(tsHandler);	//Pressing start button calls onto TitleScreenHandler class result 
		startButton.setFocusPainted(false);
		startButtonPanel.add(startButton);
		
		
		//Getting sounds from res folder
		//correctSound = ".//res//answerCorrect_2.wav";
		correctSoundURL = getClass().getResource("answerCorrect_2.wav");
		//wrongSound = ".//res//answerWrong.wav";
		wrongSoundURL = getClass().getResource("answerWrong.wav");
		//introSound = ".//res//backgroundCasualPiano.wav";
		introSoundURL = getClass().getResource("backgroundCasualPiano.wav");
		
		winnerSoundURL = getClass().getResource("winner.wav");
		
		musicOnOff = "off";
		
	    usernameInputStarted = false; // Flag to track if inputUsername has been invoked
		
		titleNamePanel.add(titleNameLabel);				//Adds title text from JLabel onto the JPanel
		startButtonPanel.add(startButton);				//Adds start button text to start button
		
		window.add(titleNamePanel);
		window.add(startButtonPanel);	
		
		
		
		window.setVisible(true);
	}
	
	public void createGameScreen()	//For buttons at the bottom of screen
	{
		titleNamePanel.setVisible(false);
		startButtonPanel.setVisible(false);
		
		mainTextPanel = new JPanel();
		mainTextPanel.setBounds(100, 100, 600, 275);
		mainTextPanel.setBackground(Color.black);
		window.add(mainTextPanel);
		
		mainTextArea = new JTextArea("");
		mainTextArea.setBounds(100, 100, 600, 260);
		mainTextArea.setBackground(Color.black);
		mainTextArea.setForeground(Color.white);
		mainTextArea.setFont(normalFont);
		mainTextArea.setLineWrap(true);
		mainTextArea.setWrapStyleWord(true);
		mainTextPanel.add(mainTextArea);
		
		picPanel = new JPanel();
		picPanel.setBounds(50, 200, 100, 100);
		picPanel.setBackground(Color.blue);
		window.add(picPanel);
		
		picLabel = new JLabel();
		
		image = new ImageIcon("");
		
		choiceButtonPanel = new JPanel();
		choiceButtonPanel.setBounds(250, 375, 300, 150);
		choiceButtonPanel.setBackground(Color.black);
		choiceButtonPanel.setLayout(new GridLayout(4,1));
		window.add(choiceButtonPanel);
		
		usernameButton = new JButton("Submit");
		usernameButton.setBackground(Color.black);
		usernameButton.setForeground(Color.white);
		usernameButton.setFont(normalFont);
		usernameButton.setFocusPainted(false);
		usernameButton.addActionListener(choiceHandler);
		usernameButton.setActionCommand("usernameInput"); // Set the action command for username input
		
		choice1 = new JButton("");
		choice1.setBackground(Color.black);
		choice1.setForeground(Color.white);
		choice1.setFont(normalFont);
		choice1.setFocusPainted(false);
		choice1.addActionListener(choiceHandler);
		choice1.setActionCommand("c1");
		choiceButtonPanel.add(choice1);
		choice2 = new JButton("");
		choice2.setBackground(Color.black);
		choice2.setForeground(Color.white);
		choice2.setFont(normalFont);
		choice2.setFocusPainted(false);
		choice2.addActionListener(choiceHandler);
		choice2.setActionCommand("c2");
		choiceButtonPanel.add(choice2);
		choice3 = new JButton("");
		choice3.setBackground(Color.black);
		choice3.setForeground(Color.white);
		choice3.setFont(normalFont);
		choice3.setFocusPainted(false);
		choice3.addActionListener(choiceHandler);
		choice3.setActionCommand("c3");
		choiceButtonPanel.add(choice3);
		choice4 = new JButton("");
		choice4.setBackground(Color.black);
		choice4.setForeground(Color.white);
		choice4.setFont(normalFont);
		choice4.setFocusPainted(false);
		choice4.addActionListener(choiceHandler);
		choice4.setActionCommand("c4");
		choiceButtonPanel.add(choice4);
		
		playerPanel = new JPanel();
		playerPanel.setBounds(100, 15, 600, 50);
		playerPanel.setBackground(Color.black);
		playerPanel.setLayout(new GridLayout(1 , 3));
		window.add(playerPanel);
		playerName = new JLabel("Name: ");
		playerName.setFont(headerFont);
		playerName.setForeground(Color.gray);
		playerPanel.add(playerName);
		score = new JLabel("Score: ");
		score.setFont(headerFont);
		score.setForeground(Color.gray);
		playerPanel.add(score);
		score.setVisible(false);
		qNum = new JLabel("Q 0 of 10");
		qNum.setFont(headerFont);
		qNum.setForeground(Color.gray);
		playerPanel.add(qNum);
		qNum.setVisible(false);
		
		musicPanel = new JPanel();
		musicPanel.setBounds(600, 450, 115, 50);
		musicPanel.setLayout(new GridLayout(2 , 1));
		musicPanel.setBackground(Color.black);
		
		soundButton = new JButton("Sound Effects");
		soundButton.setFocusPainted(false);
		soundButton.addActionListener(soundHandler);
		soundButton.setActionCommand("soundB");
		musicPanel.add(soundButton); 
		
		musicButton = new JButton("Music");
		musicButton.setFocusPainted(false);
		musicButton.addActionListener(soundHandler);
		musicButton.setActionCommand("musicB");
		musicPanel.add(musicButton);
		
		
		
        window.add(musicPanel);
		musicPanel.setVisible(false);

		playerSetup();
		
	}
	public void playerSetup()
	{
		quest = 1;
		playerName.setText("Name" + username);
		score.setText("Score: 0");
		
		inputUsername();
	}
	public void inputUsername() {
	    position = "inputUsername";
	    
	    if (!usernameInputStarted) { // Check if inputUsername has been invoked
            usernameInputStarted = true; // Set the flag to true
            // Remove the welcome screen components
            mainTextPanel.remove(titleNamePanel);
            mainTextPanel.remove(startButtonPanel);
            mainTextPanel.revalidate();
            mainTextPanel.repaint();
        }
	    
	 // Create a JLabel to display the instruction text
	    mainTextArea.setText("Please enter your username here: ");

	    // Create a JTextField for the user to input their username
	    JTextField usernameTextField = new JTextField(10);
	    usernameTextField.setFont(normalFont);
	    usernameTextField.setBackground(Color.black);
	    usernameTextField.setForeground(Color.white);
	    mainTextPanel.add(usernameTextField); // Add the text field to the mainTextPanel

	    // Create a submit button to process the username input
	    JButton submitButton = new JButton("Submit");
	    submitButton.setBackground(Color.black);
	    submitButton.setForeground(Color.white);
	    submitButton.setFont(normalFont);
	    submitButton.setFocusPainted(false);
	    submitButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            username = usernameTextField.getText(); // Get the entered username from the text field
	            mainTextPanel.remove(usernameTextField); // Remove the username text field
	            mainTextPanel.remove(submitButton); // Remove the submit button
	            mainTextPanel.revalidate(); // Revalidate the panel to reflect the changes
	            mainTextPanel.repaint(); // Repaint the panel to update the display

	            intro(); // Transition to the question phase
	        }
	    });

	    mainTextPanel.add(submitButton); // Add the submit button to the mainTextPanel

	    choice1.setVisible(false);
	    choice2.setVisible(false);
	    choice3.setVisible(false);
	    choice4.setVisible(false);
	}
	public void intro()
	{
		position = "intro";
		if(musicOnOff == "off") {
			mu.setURL(introSoundURL);
			mu.play();
			mu.loop();
	    	musicOnOff = "on";
	    }
	   
		qNum.setText("Q " + questionCounter + " of 10");
	    score.setText("Score: " + points);
		playerName.setText("Name: " + username);
		shuffleArray(questions);
		mainTextArea.setText("Welcome to Trivia! Answer as many of these non-boomer questions to see if you can win!");
		choice1.setVisible(true);
		choice1.setText("Start Game"); 
		choice2.setVisible(false);
	    choice3.setVisible(false);
	    choice4.setVisible(false);
	    
		musicPanel.setVisible(true);
	}
	public void question() {
	    position = "question";
	    qNum.setVisible(true);
	    score.setVisible(true);
	    choiceButtonPanel.remove(usernameButton);
	    choiceButtonPanel.revalidate();
	    choiceButtonPanel.repaint();
	    choice1.setVisible(true);
	    choice2.setVisible(true);
	    choice3.setVisible(true);
	    choice4.setVisible(true);
	    
	    playerName.setText("Name: " + username);
	    questionCounter++;
	    qNum.setText("Q " + questionCounter + " of 15");
	    score.setText("Score: " + points);
	    
	    int questionIndex = quest - 1; // Adjust index since arrays are 0-based
	    displayQuestionByIndex(questionIndex);
	    
	    
	}
	
	public void loser()
	{
		position = "loser";
		Question currentQuestion = questions[quest - 1]; // Get the current question
	    String correctChoice = currentQuestion.getChoices()[currentQuestion.getCorrectChoice()]; // Get the correct choice text
	    String questionText = currentQuestion.getQuestion(); // Get the question text
		mainTextArea.setText("Wrong answer! The correct answer for: \n\n\"" + questionText + "\"\nwas  \"" + correctChoice + "\". \n\nYou answered " + (quest-1) + " questions correctly.");
		choice1.setText("Try Again");
		choice2.setText("Close");
		choice3.setText("");
		choice4.setText("");
		choice3.setVisible(false);
		choice4.setVisible(false);
	}
	public void winner()
	{

		position = "winner";
		score.setText("Score: " + points);
		mainTextArea.setText("CONGRATS " + username + ", YOU WIN!!! \n\n<WINNER>");
		choice1.setText("Play Again");
		choice2.setText("Close");
		choice3.setText("");
		choice4.setText("");
		choice3.setVisible(false);
		choice4.setVisible(false);
	}
	public void resetGame() {
        // Reset the game's state here
        questionCounter = 0;
        quest = 1;
        points = 0;
        // ... other reset logic ...

        // Reset the UI components as needed
        mainTextArea.setText(""); // Clear the text area
        choice1.setText("");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");

        // Show necessary buttons or panels
        choice1.setVisible(true);
        choice2.setVisible(true);
        choice3.setVisible(true);
        choice4.setVisible(true);

        // Go back to the appropriate phase of the game
        intro(); // For example, go back to the introduction screen
    }
	
	public class TitleScreenHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			createGameScreen(); //This is called whenever this class/action is used
			
		}
	}
	
	public class SoundHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String clickedButton = event.getActionCommand();
			
			switch(clickedButton) {
			case "soundB" :
				if(soundSwitch == 1) {
					soundSwitch *= -1;
					se.setURL(wrongSoundURL);
					se.play();
					correctSound = "";
					wrongSound = "";
					break;
				}
				else {
					soundSwitch *= -1;
					correctSound = ".//res//answerCorrect_2.wav";
					wrongSound = ".//res//answerWrong.wav";
					se.setURL(correctSoundURL);
					se.play();
					break;
				}
			case "musicB" :
				if(musicSwitch == 1) {
					musicSwitch *= -1;
					introSound = "";
					mu.stop();
					break;
				}
				else {
					musicSwitch *= -1;
					introSound = ".//res//backgroundCasualPiano.wav";
					mu.setURL(introSoundURL);
					mu.play();
					mu.loop();
					break;
				}
			
			}
			
		}
	}
	
	public class ChoiceHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String yourChoice = event.getActionCommand();
            
            String clickedButton = event.getActionCommand();
            switch(clickedButton) {
            	case "soundB":
            }

            switch (position) {
            case "intro":
                switch (yourChoice) {
                    case "c1":
                        question();
                        break;
                }
                break;
                
            case "inputUsername":
            	if (yourChoice.equals("usernameInput")) {
                    username = mainTextArea.getText(); // Get the entered username from the mainTextArea
                    question(); // Transition to the question phase
                }
                break;
                
            case "loser":
            	switch(yourChoice){
				case "c1": resetGame(); break;
				case "c2": window.dispose(); break;
				}
				break;
				
            case "winner":
            	switch(yourChoice){
				case "c1": resetGame(); break;
				case "c2": window.dispose(); break;
				}
				break;

            case "question":
                int selectedChoice = -1;
                switch (yourChoice) {
                    case "c1":
                        selectedChoice = 0;
                        break;
                    case "c2":
                        selectedChoice = 1;
                        break;
                    case "c3":
                        selectedChoice = 2;
                        break;
                    case "c4":
                        selectedChoice = 3;
                        break;
                }

                Question currentQuestion = questions[quest - 1]; // Get the current question
                if (selectedChoice == currentQuestion.getCorrectChoice()) {
                    // Handle correct choice logic here
                	quest++;
                	points++;
                	
                	if(soundSwitch == 1 && points != 1000000) {
                		se.setURL(correctSoundURL);
                        se.play();
                	}
                	
                	if(points == 1){
                		points = 100;
                	}
                	if(points == 101){
                		points = 200;
                	}
                	if(points == 201){
                		points = 300;
                	}
                	if(points == 301){
                		points = 500;
                	}
                	if(points == 501){
                		points = 1000;
                	}
                	if(points == 1001){
                		points = 2000;
                	}
                	if(points == 2001){
                		points = 4000;
                	}
                	if(points == 4001){
                		points = 8000;
                	}
                	if(points == 8001){
                		points = 16000;
                	}
                	if(points == 16001){
                		points = 32000;
                	}
                	if(points == 32001){
                		points = 64000;
                	}
                	if(points == 64001){
                		points = 125000;
                	}
                	if(points == 125001){
                		points = 250000;
                	}
                	if(points == 250001){
                		points = 500000;
                	}
                	if(points == 500001){
                		points = 1000000;
                	}
                	
                	if(quest == 16) {
                		if(soundSwitch == 1) {
                    		se.setURL(winnerSoundURL);
                            se.play();
                            mu.setURL(introSoundURL);
                			mu.stop();
                    	}
                		winner();
                	}
                	else {
                		
                    question();
                	}
                } 
                else {
                    // Handle incorrect choice logic here
                	
                	if(soundSwitch == 1) {
                		se.setURL(wrongSoundURL);
                        se.play();
                	}
                    loser(); // Trigger the "loser" method
                }
                break;

            // Handle other positions and choices here
        }

            // Handle other positions and choices here
        }
    }
	
}
