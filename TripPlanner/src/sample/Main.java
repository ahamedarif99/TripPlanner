package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.TextEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Optional;
import java.util.logging.Logger;

public class Main extends Application  {

    final String FILE_PROTOCOL = "file:";
    final String IMAGES_PATH = "./images/";
    final String MAP_URL = FILE_PROTOCOL + IMAGES_PATH + "5c9621778c78d9fefb6aa46d24df90bd.png";
    static ArrayList<Stops> possibleStops = new ArrayList<Stops>();
    ArrayList<Stops> tripStops = new ArrayList<Stops>();
    ListView<Stops> listview = new ListView<Stops>();
    ListView<Stops> tripListView = new ListView<Stops>();
    Image mapimage = loadImage(MAP_URL);
    String temp[];
    ImageView topImageView = new ImageView();
    BorderPane appPane = new BorderPane();
    HBox centerPane = new HBox();
    VBox leftbPane = new VBox();
    VBox rightbPane = new VBox();
    VBox righttPane = new VBox();
    HBox topPane = new HBox();
    Button b1 = new Button("+");
    Button b2 = new Button("-");
    Button b3 = new Button("+");
    Button b4 = new Button("-");
    HBox bottomPane = new HBox();
    Button New, Save, Load;
    Button update = new Button("Update");
    TextField tf1 = new TextField();
    TextField tf2 = new TextField();
    TextField tf3 = new TextField();
    TextField tf4 = new TextField();
    TextField tf5 = new TextField();
    TextField tf6 = new TextField();
    ObservableList<Stops> list1;
    ObservableList<Stops> list2;
    Text text;
    int counter = 0;
    final double EARTH_RADIUS = 6371;
    final double RADIAN_FACTOR = 180.0/Math.PI;

    Stage window;

    @Override
    public void start(Stage primaryStage){
        window = primaryStage;

        UpTownVibes();
        MiddleChild();
        DownLow();

        b1.setOnAction(e -> plusbottomright());
        b2.setOnAction(e -> minusbottomright());
        b3.setOnAction(e -> plusmiddleright());
        b4.setOnAction(e -> minusmiddleright());

        update.setOnAction(e -> updatebuutton());

        New.setOnAction(e -> newbutton());
        Save.setOnAction(e -> savebutton());
        Load.setOnAction(e -> loadbutton());

        appPane.setTop(topPane);
        appPane.setCenter(centerPane);
        appPane.setBottom(bottomPane);
        appPane.setPadding(new Insets(5));


        window.setTitle("Trip Planner - Criss Cross Tour");
        window.setScene(new Scene(appPane, 820, 757));
        window.show();
    }

    public void UpTownVibes()
    {
        topPane.setMaxWidth(818);
        topPane.setMinHeight(50);
        topPane.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
        topPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        New = new Button("New");
        New.setMaxHeight(25);
        New.setMinWidth(110);
        Save = new Button("Save");
        Save.setMaxHeight(25);
        Save.setMinWidth(110);
        Load = new Button("Load");
        Load.setMaxHeight(25);
        Load.setMinWidth(110);
        topPane.getChildren().addAll(New, Save, Load);
        topPane.setAlignment(Pos.CENTER_LEFT);
        topPane.setSpacing(10);
        topPane.setPadding(new Insets(10));


    }

    public void MiddleChild()
    {
        centerPane.setMaxHeight(320);
        centerPane.setMaxWidth(818);
        centerPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        centerPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        topImageView.setImage(mapimage);
        topImageView.setX(577);
        topImageView.setY(316);
        centerPane.getChildren().add(topImageView);
        b3.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        b4.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        Text text1 = new Text("Trip Stops");
        text1.setX(50);
        text1.setY(50);
        HBox h2 = new HBox(text1, b3, b4);
        righttPane.getChildren().addAll(h2);
        centerPane.getChildren().add(righttPane);
        righttPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        righttPane.setPadding(new Insets(5,5,5,5));
        righttPane.setMinWidth(475);

    }

    public void DownLow()
    {
        bottomPane.setMaxWidth(818);
        bottomPane.setMinHeight(368);
        bottomPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        bottomPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        bottomPane.setPadding(new Insets(5,5,5,5));
        writeToarraylistofPossibleStops();
        list1 = FXCollections.observableArrayList(possibleStops);
        listview.setItems(list1);
        Text text = new Text("Possible Stops");
        text.setX(50);
        text.setY(50);
        leftbPane.getChildren().addAll(text, listview);
        leftbPane.setMaxHeight(345);
        leftbPane.setMaxWidth(206);
        bottomPane.getChildren().add(leftbPane);
        leftbPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        leftbPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        leftbPane.setPadding(new Insets(5,5,5,5));
        HBox hb = new HBox();
        hb.setMinHeight(35);
        hb.setMaxWidth(70);
        hb.setPadding(new Insets(5,5,5,5));
        b1.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        b2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        hb.getChildren().addAll(b1,b2);
        hb.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.NONE, new CornerRadii(5), BorderWidths.DEFAULT)));
        rightbPane.getChildren().add(hb);
        rightbPane.setMaxHeight(200);
        rightbPane.setMinWidth(320);
        rightbPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        rightbPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        bottomPane.getChildren().add(rightbPane);
        GridPane gp = new GridPane();
        Text t1 = new Text("City:");
        Text t2 = new Text("State:");
        Text t3 = new Text("Latitude Degrees");
        Text t4 = new Text("Latitude Minute:");
        Text t5 = new Text("Longitude Degrees:");
        Text t6 = new Text("Longitude Minutes:");
        tf1.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        tf2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        tf3.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        tf4.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        tf5.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        tf6.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        update.setMinWidth(75);
        update.setMaxHeight(25);
        listview.setOnMouseClicked(e -> {
            if(e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 1) {
                whenclickedstopsinfo();
            } else if (e.getClickCount() == 2)
            {
                clickedagain();
            }
        });
        gp.setMinSize(270, 180);
        gp.setPadding(new Insets(5,5,5,5));
        gp.setVgap(3);
        gp.setHgap(3);
        gp.setAlignment(Pos.CENTER);
        gp.add(t1, 0,0);
        gp.add(tf1,1,0);
        gp.add(t2,0,1);
        gp.add(tf2,1,1);
        gp.add(t3,0,2);
        gp.add(tf3,1,2);
        gp.add(t4,0,3);
        gp.add(tf4,1,3);
        gp.add(t5,0,4);
        gp.add(tf5,1,4);
        gp.add(t6,0,5);
        gp.add(tf6,1,5);
        gp.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        rightbPane.getChildren().add(gp);
        rightbPane.getChildren().add(update);


    }

    private Image loadImage(String imageFileURL) {
        Image image = new Image(imageFileURL);
        if (!image.isError()) {
            return image;
        }
        else
            return null;
    }

    public void writeToarraylistofPossibleStops()
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(new File("PossibleStops.txt"))))
        {
            String line;
            while((line = reader.readLine()) != null)
            {
                String temp[] = line.replace(" ", "").trim().split(",");
                Stops stops = new Stops(temp[0], temp[1], Integer.parseInt(temp[2]),
                        Integer.parseInt(temp[3]), Integer.parseInt(temp[4]),
                        Integer.parseInt(temp[5]));
                possibleStops.add(stops);
            }

        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void whenclickedstopsinfo()
    {
        tf1.setText(listview.getSelectionModel().getSelectedItem().getCityname());
        tf2.setText(listview.getSelectionModel().getSelectedItem().getStatename());
        tf3.setText(listview.getSelectionModel().getSelectedItem().getLatdeg() + "");
        tf4.setText(listview.getSelectionModel().getSelectedItem().getLatmin() + "");
        tf5.setText(listview.getSelectionModel().getSelectedItem().getLongdeg() + "");
        tf6.setText(listview.getSelectionModel().getSelectedItem().getLongmin() + "");


    }

    public void clickedagain()
    {
        tf1.clear();
        tf2.clear();
        tf3.clear();
        tf4.clear();
        tf5.clear();
        tf6.clear();
        listview.getSelectionModel().clearSelection();

    }

    public void updatebuutton()
    {
        listview.getSelectionModel().getSelectedItem().setCityname(tf1.getText());
        listview.getSelectionModel().getSelectedItem().setStatename(tf2.getText());
        listview.getSelectionModel().getSelectedItem().setLatdeg(Integer.parseInt(tf3.getText().trim()));
        listview.getSelectionModel().getSelectedItem().setLatmin(Integer.parseInt(tf4.getText().trim()));
        listview.getSelectionModel().getSelectedItem().setLongdeg(Integer.parseInt(tf5.getText().trim()));
        listview.getSelectionModel().getSelectedItem().setLongmin(Integer.parseInt(tf6.getText().trim()));

    }

    public void plusbottomright()
    {
        int counter = 0;
        for (int i = 0; i < possibleStops.size(); i++)
        {
            if (tf1.getText().trim().equalsIgnoreCase(possibleStops.get(i).getCityname()))
            {
                counter++;
            }
        }

        if(counter == 1)
        {
            Alert a2 = new Alert(Alert.AlertType.WARNING);
            a2.setTitle("WARNING");
            a2.setHeaderText("A City with that name already exist");
            a2.setContentText("Please choose a different city to add");
            a2.showAndWait();
        } else
        {
            try
            {
                Stops stop = new Stops(tf1.getText(), tf2.getText(), Integer.parseInt(tf3.getText().trim()),
                        Integer.parseInt(tf4.getText().trim()), Integer.parseInt(tf5.getText()),
                        Integer.parseInt(tf6.getText().trim()));
                possibleStops.add(stop);
                Collections.sort(possibleStops);
                list1 = FXCollections.observableArrayList(possibleStops);
                listview.setItems(list1);
                leftbPane.getChildren().addAll(listview);
            }catch(IllegalArgumentException ex)
            {
                System.out.println();
            }
        }

    }

    public void minusbottomright()
    {
        Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);
        a1.setTitle("Confirmation");
        a1.setContentText("Do you want to delete this stop?");
        a1.setHeaderText(null);
        Optional<ButtonType> result = a1.showAndWait();
        if(result.get() == ButtonType.OK)
        {

            try {
                for (int i = 0; i < possibleStops.size(); i++) {
                    if (possibleStops.get(i).getCityname().equalsIgnoreCase(tf1.getText())) {
                        possibleStops.remove(i);
                    }
                }
                Collections.sort(possibleStops);
                list1 = FXCollections.observableArrayList(possibleStops);
                listview.setItems(list1);
                leftbPane.getChildren().addAll(listview);
                tf1.clear();
                tf2.clear();
                tf3.clear();
                tf4.clear();
                tf5.clear();
                tf6.clear();
            } catch (IllegalArgumentException ex) {
                System.out.println();
            }
        } else
        {
            a1.close();
        }
    }

    public void plusmiddleright()
    {
        int counter = 0;
        for (int i = 0; i < tripStops.size(); i++)
        {
            if (tf1.getText().trim().equalsIgnoreCase(tripStops.get(i).getCityname()))
            {
                counter++;
            }
        }

        if(counter == 1)
        {
            Alert a2 = new Alert(Alert.AlertType.WARNING);
            a2.setTitle("WARNING");
            a2.setHeaderText("A City with that name already exist");
            a2.setContentText("Please choose a different city to add");
            a2.showAndWait();
        } else
        {
            try
            {
                Stops stops = new Stops(tf1.getText(), tf2.getText(), Integer.parseInt(tf3.getText().trim()),
                        Integer.parseInt(tf4.getText().trim()), Integer.parseInt(tf5.getText()),
                        Integer.parseInt(tf6.getText().trim()));
                tripStops.add(stops);
                Collections.sort(tripStops);
                list2 = FXCollections.observableArrayList(tripStops);
                tripListView.setItems(list2);
                righttPane.getChildren().addAll(tripListView);
//
//            text = new Text("Total Mileage: ");
//            Text text1 = new Text();
//            text1.setText("" + this.calcDist());
//            HBox hb = new HBox(text, text1);
//
//            System.out.println(calcDist());
//            righttPane.getChildren().addAll(tripListView, hb);

            } catch (IllegalArgumentException ex) {
                System.out.println();
            }
        }
    }

    public void minusmiddleright()
    {
        try
        {
            Stops st = tripListView.getSelectionModel().getSelectedItem();
            for (int i = 0; i < tripStops.size(); i++) {
                tripStops.remove(st);
            }
            Collections.sort(tripStops);
            list2 = FXCollections.observableArrayList(tripStops);
            tripListView.setItems(list2);
            righttPane.getChildren().addAll(tripListView);
        }catch (IllegalArgumentException ex)
        {
            System.out.println();
        }
    }

    public void newbutton()
    {
        TextInputDialog tid = new TextInputDialog();
        tid.setTitle("Tittle");
        tid.setContentText("Please enter the nam of the trip");
        tid.setHeaderText(null);
        Optional<String> result = tid.showAndWait();
        if (result.isPresent())
        {
            try
            {
                window.setTitle("Trip Planner - " + result.get());
                tripStops.clear();
                list2 = FXCollections.observableArrayList(tripStops);
                tripListView.setItems(list2);
                righttPane.getChildren().addAll(tripListView);
            }catch (IllegalArgumentException e)
            {
                System.out.println();
            }
        }else
        {
            tid.close();
        }
    }

    public void savebutton()
    {
        try
        {
            ArrayList<String> stringtriplist = new ArrayList<>();
            for (int i = 0; i < tripStops.size(); i++) {
                stringtriplist.add(tripStops.get(i).getCityname() + ", " +
                        tripStops.get(i).getStatename() + ", " +
                        tripStops.get(i).getLatdeg() + ", " +
                        tripStops.get(i).getLatmin() + ", " +
                        tripStops.get(i).getLongdeg() + ", " +
                        tripStops.get(i).getLongmin() + "");
            }

            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showSaveDialog(window);

            if (file != null) {
                try {
                    FileWriter writer = new FileWriter(file);
                    for (String str : stringtriplist) {
                        writer.write(str + "\n");
                    }
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            writetofilepossiblestop();
        }catch (IllegalArgumentException e)
        {
            System.out.println();
        }

    }

    public void loadbutton()
    {

        try {
            tripStops.clear();
            FileChooser fc = new FileChooser();
            fc.setTitle("Open Resource File");
            File file = fc.showOpenDialog(window);
            if (file != null) {
                try (BufferedReader reader = new BufferedReader(new FileReader((file)))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String temp[] = line.replace(" ", "").trim().split(",");
                        Stops stops = new Stops(temp[0], temp[1], Integer.parseInt(temp[2]),
                                Integer.parseInt(temp[3]), Integer.parseInt(temp[4]),
                                Integer.parseInt(temp[5]));
                        tripStops.add(stops);
                    }
                    Collections.sort(tripStops);
                    list2 = FXCollections.observableArrayList(tripStops);
                    tripListView.setItems(list2);
                    righttPane.getChildren().addAll(tripListView);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }catch (IllegalArgumentException e)
        {
            System.out.println();
        }


    }

    public void writetofilepossiblestop()
    {
        ArrayList<String> stringlist = new ArrayList<>();
        for (int i = 0; i < possibleStops.size(); i++)
        {
            stringlist.add(possibleStops.get(i).getCityname() + ", " +
                    possibleStops.get(i).getStatename() + ", " +
                    possibleStops.get(i).getLatdeg() + ", " +
                    possibleStops.get(i).getLatmin() + ", " +
                    possibleStops.get(i).getLongdeg() + ", " +
                    possibleStops.get(i).getLongmin() + "");
        }
        try {
            Files.write(Paths.get("PossibleStops.txt"), stringlist);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double Distance(Stops s1, Stops s2)
    {
        double lat1 = (s1.getLatdeg());
        double long1 = (s1.getLongdeg());
        double lat2 = (s2.getLatdeg());
        double long2 = (s2.getLongdeg());
        double x = (Math.sin(lat1/RADIAN_FACTOR) * Math.sin(lat2/RADIAN_FACTOR))
                    + ((Math.cos(lat1/RADIAN_FACTOR))
                        * (Math.cos(lat2/RADIAN_FACTOR))
                        * (Math.cos((long2/RADIAN_FACTOR) - (long1/RADIAN_FACTOR))));
        double distance = EARTH_RADIUS * Math.atan((Math.sqrt(1 - Math.pow(x,2))) / x);
        return distance;
    }

    public double calcDist()
    {

        for (int i = 0; i < tripStops.size() - 1; i++) {
            int j = i + 1;

            counter += Distance(tripStops.get(i), tripStops.get(j));
        }

        return counter;
    }


    public static void main(String[] args) {
        launch(args);

    }
}
