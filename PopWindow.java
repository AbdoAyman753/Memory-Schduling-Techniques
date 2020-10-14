package sample;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
public class PopWindow {



    public static void displayPWin() {

        Stage window = new Stage();
        window.setTitle("Add Process");
        window.setMinWidth(350);
        window.setMinHeight(200);

        Label label1 = new Label();
        label1.setText("No. OF Segments:");
        TextField text1 = new TextField();
        text1.setPromptText("No. OF Segments");

        Button add = new Button("Add");
        add.setOnAction(e -> Main.addProcess(Integer.parseInt(text1.getText())));
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        HBox base = new HBox(10);
        base.getChildren().addAll(label1, text1);
        HBox button = new HBox(10);
        button.getChildren().add(add);
        button.setAlignment(Pos.BASELINE_RIGHT);
        layout.getChildren().addAll(base, button);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();


    }

    public static void displayHWin(){
        Stage window = new Stage();
        window.setTitle("Add Hole");
        window.setMinWidth(350);
        window.setMinHeight(200);

        Label label1 =new Label();
        label1.setText("Base:");
        TextField text1=new TextField();
        text1.setPromptText("Base");
        Label label2 =new Label();
        label2.setText("Size:");
        TextField text2=new TextField();
        text2.setPromptText("Limit");
        Button add=new Button("Add");
        add.setOnAction(e -> Main.addHole(Integer.parseInt(text1.getText()),Integer.parseInt(text2.getText())));
        VBox layout=new VBox(10);
        layout.setPadding(new Insets(10,10,10,10));
        HBox base=new HBox(10);
        base.getChildren().addAll(label1,text1);
        HBox size=new HBox(10);
        size.getChildren().addAll(label2,text2);
        HBox buttonBox=new HBox(10);
        buttonBox.getChildren().add(add);
        buttonBox.setAlignment(Pos.BASELINE_RIGHT);
        layout.getChildren().addAll(base,size,buttonBox);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }


    public static void displaySWin(int win){
        Stage window=new Stage();
        for(int i=0;i<win;i++){
            window.setTitle("Segment "+i);
            window.setMinWidth(350);
            window.setMinHeight(200);

            Label label1 =new Label();
            label1.setText("Name:");
            TextField text1=new TextField();
            text1.setPromptText("Segment Name");
            Label label2 =new Label();
            label2.setText("Size:");
            TextField text2=new TextField();
            text2.setPromptText("Segment Size");
            Button add=new Button("Add");
            int finalI = i;
            add.setOnAction(e -> {
                Main.addSegment(finalI,text1.getText(),Integer.parseInt(text2.getText()));
                if(finalI==win-1)
                    Main.allocate(Main.proc);
                window.close();
            });
            VBox layout=new VBox(10);
            layout.setPadding(new Insets(10,10,10,10));
            HBox base=new HBox(10);
            base.getChildren().addAll(label1,text1);
            HBox size=new HBox(10);
            size.getChildren().addAll(label2,text2);
            HBox button=new HBox(10);
            button.getChildren().add(add);
            button.setAlignment(Pos.BASELINE_RIGHT);
            layout.getChildren().addAll(base,size,button);
            layout.setAlignment(Pos.CENTER);
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        }
    }

}
