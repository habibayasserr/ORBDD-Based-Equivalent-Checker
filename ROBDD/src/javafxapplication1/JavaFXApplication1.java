import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.scene.control.Label;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import static javafx.application.Application.launch;

/**
 *
 */
public class JavaFXApplication1 extends Application {
	private Stage primaryStage;
	private Scene primaryScene;
	private BorderPane primaryLayout;
	
	private GridPane promptLayout;
	private Button drawButton;
	private Button clearButton;
	private TextField inputEqtn;
	private TextField inputOrder;
	//EDITS
        private Button drawButton2;
        private Button check;
        private Label equiv;
	private Button clearButton2;
	private TextField inputEqtn2;
	private TextField inputOrder2;
       
        //////////////////////////////
	private MenuBar mainMenu;
	private Menu fileMenu;
	private Menu helpMenu;
	private MenuItem save;
	private MenuItem help;
	
	private String equation;
        //edits
        private String equation2;

	private String order;
        // EDITS
        private String order2;
	private char[] charEquation;
        //EDITS
        private char[] charEquation2;
        
	private char[] varOrder;
        //EDITS
        private char[] varOrder2;
	private char[] reservedChars;
	private Operators ops;
        //EDITS
        private Operators ops2;
	private Robdd robdd;
        //EDITS
        private Robdd robdd2;
	
	private Canvas placeholderCanvas;
   
	private Group canvasGroup;
	private ScrollPane canvasScrollPane;
        
        //EDITS
        private Canvas placeholderCanvas2;
   
	private Group canvasGroup2;
	private ScrollPane canvasScrollPane2;
        ///////////////////////
	private Canvas nodeCanvas;
        
        //EDITS
        private Canvas nodeCanvas2;
	
	private int WIDTH;
	private int HEIGHT;
	
	private String fileName;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		// Initialize the primaryStage
		initialize();
		// Set actions for all buttons/menu items
		setActions();
		
		primaryStage.show();
	}
	
	/** Method used to group code where GUI elements are initialized.
	 */
	private void initialize() {
		WIDTH = 600;
		HEIGHT = 600;
		
		// Calc widths and heights from base width and height
		int canvasScrollPaneWidth = 300;
		int canvasScrollPaneHeight = HEIGHT - 10;
		int promptLayoutWidth = (WIDTH - canvasScrollPaneWidth);
		int promptLayoutHeight = HEIGHT;
                
                ///EDITS
                int canvasScrollPaneWidth2 = 400;
		int canvasScrollPaneHeight2 = HEIGHT - 10;
		int promptLayoutWidth2 = (WIDTH - canvasScrollPaneWidth2);
		int promptLayoutHeight2 = HEIGHT;
		
		// Initialize currently unused fields to null
		equation = null;
		order = null;
		varOrder = null;
		charEquation = null;
		robdd = null;
		ops = new Operators();
		fileName = null;
                
                // EDITS
		equation2 = null;
		order2 = null;
		varOrder2 = null;
		charEquation2 = null;
		robdd2 = null;
                check = null;
		ops2 = new Operators();
		///////////////////////
		
		// These are characters reserved for use by the program
		reservedChars = new char[]{'0', '1'};
		
		// Creating primary layout
		primaryLayout = new BorderPane();
		
		// Creating prompt sublayout
		promptLayout = new GridPane();
		promptLayout.setPadding(new Insets(5, 5, 5, 5));
		promptLayout.setVgap(5);
		promptLayout.setHgap(5);
		
		// Creating user input textfields and draw button
		inputEqtn = new TextField();
		inputEqtn.setPromptText("Enter the 1st Boolean Equation");
		inputOrder = new TextField();
		inputOrder.setPromptText("Enter the 1st Variable Order");
		drawButton = new Button("Draw the 1st ROBDD");
		clearButton = new Button("Clear the 1st Input");
                
                //EDITS
                inputEqtn2 = new TextField();
		inputEqtn2.setPromptText("Enter the 2nd Boolean Equation");
		inputOrder2 = new TextField();
		inputOrder2.setPromptText("Enter the 2nd Variable Order");
		drawButton2 = new Button("Draw the 2nd ROBDD");
		clearButton2 = new Button("Clear the 2nd Input");
                check = new Button("Equivalence");
                equiv= new Label("Check");
                
                ////////////////////////////////////////
        
		
		// Adding to promptLayout
		promptLayout.setConstraints(inputEqtn, 1, 1);
		promptLayout.setConstraints(inputOrder, 1, 2);
		promptLayout.setConstraints(drawButton, 1, 3);
		promptLayout.setConstraints(clearButton, 1, 5);
		promptLayout.getChildren().addAll(inputEqtn, inputOrder, drawButton, clearButton);
		
                // Adding to promptLayout
		promptLayout.setConstraints(inputEqtn2, 1, 12);
		promptLayout.setConstraints(inputOrder2, 1, 13);
		promptLayout.setConstraints(drawButton2, 1, 14);
		promptLayout.setConstraints(clearButton2, 1, 16);
                promptLayout.setConstraints(check, 1, 22);
                promptLayout.setConstraints(equiv, 1, 23);
		promptLayout.getChildren().addAll(inputEqtn2, inputOrder2, drawButton2, clearButton2,check, equiv);
		/////////////////////////////////////////////////////////////////
                
		// Setting promptLayout sizing constraints
		promptLayout.setPrefWidth(promptLayoutWidth);
		promptLayout.setPrefHeight(promptLayoutHeight);
                
                promptLayout.setPrefWidth(promptLayoutWidth2);
		promptLayout.setPrefHeight(promptLayoutHeight2);
		
		// Add promptLayout to primaryLayout
		primaryLayout.setLeft(promptLayout);
		
		// Creating menu and adding to primaryLayout
		mainMenu = new MenuBar();
		fileMenu = new Menu("File");
		save = new MenuItem("Save Robdd Image");
		fileMenu.getItems().add(save);
		helpMenu = new Menu("Help");
		help = new MenuItem("Program Information");
		helpMenu.getItems().add(help);
		mainMenu.getMenus().addAll(fileMenu, helpMenu);
		
		// Adding mainMenu to primaryLayout
		primaryLayout.setTop(mainMenu);
		
		// Creating canvas layout, canvas group, and canvas scrollpane
		canvasGroup = new Group();
		canvasScrollPane = new ScrollPane();
		canvasScrollPane.setContent(canvasGroup);
		canvasScrollPane.setFitToHeight(true);
		canvasScrollPane.setFitToWidth(true);
                
                //EDITS
                // Creating canvas layout, canvas group, and canvas scrollpane
		canvasGroup2 = new Group();
		canvasScrollPane2 = new ScrollPane();
		canvasScrollPane2.setContent(canvasGroup2);
		canvasScrollPane2.setFitToHeight(true);
		canvasScrollPane2.setFitToWidth(true);
		
		// Creating placeholder white canvas
		placeholderCanvas = DrawRobdd.getWhiteCanvas(canvasScrollPaneWidth, canvasScrollPaneHeight);
		canvasGroup.getChildren().add(placeholderCanvas);
		canvasScrollPane.setContent(canvasGroup);
                // EDITS
		placeholderCanvas2 = DrawRobdd.getWhiteCanvas(canvasScrollPaneWidth2, canvasScrollPaneHeight2);
                canvasGroup2.getChildren().add(placeholderCanvas2);
                canvasScrollPane2.setContent(canvasGroup2);
		
		//Adding canvas to primaryLayout
		primaryLayout.setCenter(placeholderCanvas);
                /////
                
                primaryLayout.setCenter(placeholderCanvas2);
		
		// Setting primaryScene and Stage
		primaryScene = new Scene(primaryLayout, WIDTH, HEIGHT);
		primaryStage.setTitle("RobddProgram");
		primaryStage.setScene(primaryScene);
	}
	
	/** Method used t group sections of code where buttons' actions are defined.
	 */ 
	private void setActions() {
		drawButton.setOnAction(e -> {
			equation = inputEqtn.getText();
			order = inputOrder.getText();
                       
                        
			
			try {
				checkString(equation, reservedChars);
				checkString(order, reservedChars);
				varOrder = order.toCharArray();
				charEquation = ShuntingYardAlgorithm.infixToPostfix(equation, varOrder, ops);
				robdd = Robdd.RobddFactory(charEquation, varOrder, ops);
				nodeCanvas = DrawRobdd.drawRobddNodes(robdd, varOrder);
			
				canvasGroup.getChildren().clear();
				canvasGroup.getChildren().add(nodeCanvas);
                                canvasScrollPane.setContent(canvasGroup);
                                primaryLayout.setCenter(canvasScrollPane);
                              
                                
			} catch(ExpressionError err) {
				errorDisplay(err);
			}
		});
                drawButton2.setOnAction(e -> {
			
                        equation2 = inputEqtn2.getText();
			order2 = inputOrder2.getText();
                        
			
			try {
				
                                //EDITS
                                
                                checkString(equation2, reservedChars);
				checkString(order2, reservedChars);
				varOrder2 = order2.toCharArray();
				charEquation2 = ShuntingYardAlgorithm.infixToPostfix(equation2, varOrder2, ops2);
				robdd2 = Robdd.RobddFactory(charEquation2, varOrder2, ops2);
				nodeCanvas2 = DrawRobdd.drawRobddNodes(robdd2, varOrder2);
                                //////////////////////////
                               
                                //EDITS
                                canvasGroup2.getChildren().clear();
                                canvasGroup2.getChildren().add(nodeCanvas2);
                                canvasScrollPane2.setContent(canvasGroup2);
                                primaryLayout.setBottom(canvasScrollPane2);
                                ////////////////////////
                                
			} catch(ExpressionError err2) {
				errorDisplay(err2);
			}
		});
                
                check.setOnAction ( e ->{
                    
                    /* if (varOrder.length != varOrder2.length)
                    {
                        equiv.setText("NOT EQUAL");
                    }
                    */
                    if(DrawRobdd.checkEquivalence(robdd, robdd2) ==  true)
                   {
                       equiv.setText("EQUIVALENT");
                       
                   }
                   
                    else 
                   
                   {
                       equiv.setText("NOT EQUIVALENT");
                   }
                    
                
                });
                
		
		clearButton.setOnAction(e -> {
				inputEqtn.clear();
				inputOrder.clear();
				canvasGroup.getChildren().clear();
				canvasGroup.getChildren().add(placeholderCanvas);
				canvasScrollPane.setContent(canvasGroup);
				primaryLayout.setCenter(canvasScrollPane);
		});
                
                //EDITS
                clearButton2.setOnAction(e -> {
				inputEqtn2.clear();
				inputOrder2.clear();
				canvasGroup2.getChildren().clear();
				canvasGroup2.getChildren().add(placeholderCanvas2);
				canvasScrollPane2.setContent(canvasGroup2);
				primaryLayout.setCenter(canvasScrollPane2);
		});
                //////////////////////////////////
		
		save.setOnAction(e -> {
			savePromptDisplay();
		});
		
		help.setOnAction(e -> {
			readmeDisplay();
		});
	}
	
	/** Creates a graphical display for a passed error.
	 * @param e The exception whose message will be displayed.
	 */
	private void errorDisplay(Exception e) {
		Stage errorMsg = new Stage();
		errorMsg.initModality(Modality.WINDOW_MODAL);
		VBox err = new VBox(10.0);
		err.setAlignment(Pos.CENTER);
		Button b = new Button("Close");
		b.setOnAction(n -> {
			errorMsg.close();
		});
		Label errMsg = new Label(e.getMessage());
		err.getChildren().addAll(errMsg, b);
		errorMsg.setScene(new Scene(err, 200.0, 200.0));
		errorMsg.show();
	}
        
        
        
        private void errorDisplay2(Exception e2) {
		Stage errorMsg2 = new Stage();
		errorMsg2.initModality(Modality.WINDOW_MODAL);
		VBox err2 = new VBox(10.0);
		err2.setAlignment(Pos.CENTER);
		Button b2 = new Button("Close");
		b2.setOnAction(n -> {
			errorMsg2.close();
		});
		Label errMsg2 = new Label(e2.getMessage());
		err2.getChildren().addAll(errMsg2, b2);
		errorMsg2.setScene(new Scene(err2, 200.0, 200.0));
		errorMsg2.show();
	}
	
	/** Displays the README file for the program, or an error if the file is not found.
	 */
	private void readmeDisplay() {
		Stage helpStage = new Stage();
		helpStage.initModality(Modality.WINDOW_MODAL);
		ScrollPane s = new ScrollPane();
		VBox v = new VBox(2);
		
		try{
			FileReader f = new FileReader("README.txt");
			BufferedReader b = new BufferedReader(f);
			
			String reader = b.readLine();
			while(reader != null) {
				v.getChildren().add(new Label(reader));
				reader = b.readLine();
			}
			s.setContent(v);
		}catch(FileNotFoundException e){
			errorDisplay(new Exception("Error: README.txt not found."));
		}catch(IOException e){
			errorDisplay(new Exception("Error: File Read Error."));
		}
		
		helpStage.setScene(new Scene(s, 600, 600));
		helpStage.show();
	}
	
	
	/** Creates a save prompt dialog box for saving an image.
	 * @return the name to use when saving an image.
	 */
	private void savePromptDisplay() {
		Stage savePrompt = new Stage();
		savePrompt.initModality(Modality.WINDOW_MODAL);
		VBox saveBox = new VBox(10.0);
		saveBox.setAlignment(Pos.CENTER);
		Label l = new Label("Enter the Image name below:");
		TextField name = new TextField();
		name.setPromptText("Enter File Name");
		Button s = new Button("Save");
		s.setOnAction(n -> {
			fileName = name.getText();
			fileName += ".png";
			
			WritableImage image = new WritableImage((int)nodeCanvas.getWidth(), (int)nodeCanvas.getHeight());
			nodeCanvas.snapshot(null, image);
			BufferedImage im = SwingFXUtils.fromFXImage(image, null);
			
			try {
				File f = new File(fileName);
				ImageIO.write(im, "png", f);
			} catch(Exception err) {
				errorDisplay(err);
			}
			savePrompt.close();
		});
		saveBox.getChildren().addAll(l, name, s);
		saveBox.setMargin(name, new Insets(10.0));
		savePrompt.setScene(new Scene(saveBox, 200.0,150.0));
		savePrompt.show();
	}
	
	/** Method that checks if a string contains reserved characters; throws an error if it does.
	 * @param s The String to check.
	 * @param reserved An array of reserved characters.
	 */
	private void checkString(String s, char[] reserved) throws ExpressionError {
		for(int i = 0; i < s.length(); i++) {
			for(int j = 0; j < reserved.length; j++) {
				if(reserved[j] == s.charAt(i)) {
					throw new ExpressionError("Error: Character " + reserved[j] + " is reserved" +
												" for program use and cannot be used in an equation.");
				}
			}
		}
		return;
	}
}