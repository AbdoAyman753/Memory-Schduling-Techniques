package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.lang.*;
public class Main extends Application {

    //Algorithms Variables
    public static Memory [] memory;
    public static int holesNum=0;
    public static int processNum=0;
    public static Processe [] proc =new Processe[10];

    public static Hole [] hole =new Hole[100];

    //GUI variables
    Stage window;
    Scene scene1;
    VBox mainBox;
    HBox topBox;
    HBox bottomBox;
    static TableView<Memory>tableView;
    static TableView<Segment>segtable;
    Label MemSize;
    Label allocMethod;
    TextField memSize;
    Button addHole;
    Button addProcess;
    Button GenerateMem;
    Button deallocate;
    static ComboBox<String> comboBox;
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Program window
        window=primaryStage;
        window.setTitle("Memory Allocation");
        window.setMinWidth(650);
        window.setMinHeight(580);
//********************** Memory Table GUI decliration *****************************
        //Address column
        TableColumn<Memory, Integer> addressColumn = new TableColumn<>("Address");
        addressColumn.setMinWidth(200);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        //Data status column
        TableColumn<Memory, String> dataColumn = new TableColumn<>("Data Status");
        dataColumn.setMinWidth(300);
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));

        //Memory table
        tableView = new TableView<>();
        tableView.setItems(getMemory());
        tableView.getColumns().addAll(addressColumn,dataColumn);

//**********************Segments Table GUI decleration*****************************
        //Name column
        TableColumn<Segment, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Base status column
        TableColumn<Segment, Integer> baseColumn = new TableColumn<>("Base");
        baseColumn.setMinWidth(200);
        baseColumn.setCellValueFactory(new PropertyValueFactory<>("base"));

        //Limit column
        TableColumn<Segment, Integer> limitColumn = new TableColumn<>("Limit");
        limitColumn.setMinWidth(200);
        limitColumn.setCellValueFactory(new PropertyValueFactory<>("size"));

        //Segments table
        segtable =new TableView<>();
        segtable.setItems(getSegments());
        segtable.getColumns().addAll(nameColumn,baseColumn,limitColumn);
        Scene neu=new Scene(segtable);

//***************************** Mouse clicks handling *******************************
        tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //show deallocation button on single click and deallocate processes if deallocation button clicked
                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                    String temp=tableView.getSelectionModel().getSelectedItem().getData();
                    if(temp.charAt(0) =='P'){
                        deallocate.setVisible(true);
                        temp=temp.substring(9,temp.indexOf(','));
                        int pro=Integer.parseInt(temp);
                        deallocate.setOnAction(e->{
                            for(int lo=0;lo<proc[pro].getSegNum();lo++){
                                addHole(proc[pro].getSegments(lo).getBase(),proc[pro].getSegments(lo).getSize());
                                for(int i=0 ;i<holesNum;i++)
                                {
                                    for(int j=i+1 ;j<holesNum;j++)
                                    {
                                        if(hole[i].getBase()>hole[j].getBase())
                                        {
                                            swap(hole,i,j);
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
                //show segments table on double click
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    segtable.getItems().clear();
                    Stage win=new Stage();
                    win.initModality(Modality.APPLICATION_MODAL);

                    win.setMinWidth(250);
                    String temp=tableView.getSelectionModel().getSelectedItem().getData();
                    temp=temp.substring(9,temp.indexOf(','));
                    int pro=Integer.parseInt(temp);
                    win.setTitle("Process: "+pro+" Segment Table");
                    for(int lo=0;lo<proc[pro].getSegNum();lo++){
                        segtable.getItems().add(proc[pro].getSegments(lo));
                    }
                    segtable.setPadding(new Insets(10, 10, 10, 10));
                    win.setScene(neu);
                    win.showAndWait();
                }
            }
        });




//***************************** Main Window GUI *******************************
        //Top view of main window
        MemSize=new Label("Total Memory Size:");
        memSize=new TextField();
        memSize.setPromptText("Memory Size");
        GenerateMem=new Button("Generate...");
        GenerateMem.setOnAction(e -> generateMem());
        deallocate=new Button("Deallocate Process");
        deallocate.setVisible(false);
        //**************

        //Bottom view of main window
        addHole =new Button("Add Hole");
        addProcess=new Button("Add Process");

        //Add Hole button
        addHole.setOnAction(e ->PopWindow.displayHWin());
        addProcess.setOnAction(e ->PopWindow.displayPWin());

        //Allocation Method
        allocMethod=new Label("Allocation Method:");
        comboBox=new ComboBox<>();
        comboBox.setPromptText("Choose Allocation Method...");
        comboBox.getItems().addAll("First-Fit", "Best-Fit");

        //layout
        topBox=new HBox(20);
        topBox.getChildren().addAll(MemSize,memSize,GenerateMem,deallocate);
        topBox.setPadding(new Insets(5,5,5,5));
        bottomBox=new HBox(90);
        bottomBox.setPadding(new Insets(5,5,5,5));
        bottomBox.getChildren().addAll(addHole,comboBox,addProcess);
        mainBox=new VBox(10);
        mainBox.setPadding(new Insets(5,5,5,5));
        mainBox.getChildren().addAll(topBox,tableView,bottomBox);

        //Window
        scene1=new Scene(mainBox,580, 350);
        window.setScene(scene1);
        window.show();
    }

//************************ Controlling Methods *****************************
    public void generateMem(){
        holesNum=0;
        processNum=0;
        hole =new Hole[100];
        proc =new Processe[100];
        int size=Integer.parseInt(memSize.getText());
        memory=new Memory[size];
        tableView.getItems().clear();
        for(int i=0;i<size;i++){
            memory[i]=new Memory();
            memory[i].setAddress(i);
            memory[i].setData("Uninitialized");
            tableView.getItems().add(memory[i]);

        }

    }

    public static final <T> void swap (T[] a, int i, int j) {
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void addHole(int base,int limit){
        if(holesNum !=0){
            int index=0  ;
            int field =0;
            int flag = 0;
            for(int loop=0;loop<holesNum;loop++) {
                //new hole under old,new base =old limit
                if (base == hole[loop].getBase() + hole[loop].getLimit()) {
                    if(flag==0) {
                        field = loop;
                    flag=1 ;
                    }
                    hole[field].setLimit(hole[loop].getLimit() + limit);
                    for (int i = hole[loop].getBase(); i < hole[loop].getLimit()+hole[loop].getBase(); i++) {
                        memory[i].setData("Hole ");
                        tableView.getItems().add(null);
                    }
                    base = hole[field].getBase();
                    limit = hole[field].getLimit();
                    index++;
                }
                //new hole at the top of old,non-overlapping
                else if (base + limit == hole[loop].getBase()) {
                    if(flag==0) {
                        field = loop;
                        flag=1 ;
                    }
                    hole[field].setLimit(hole[loop].getLimit() + limit);
                    hole[field].setBase(base);
                    for (int i = base; i < hole[field].getLimit()+base; i++) {
                        memory[i].setData("Hole " );
                        tableView.getItems().add(null);
                    }
                    base = hole[field].getBase();
                    limit = hole[field].getLimit();
                    index++;

                }
                if(index==2)
                {
                    hole[loop].setLimit(0);
                    for(int c= 0 ; c<holesNum;c++)
                    {
                        if(hole[c].getLimit()==0)
                        {
                            swap(hole, c,c+1);
                        }
                    }
                    break ;
                }

            }
                if(index<1)
                {
                    hole[holesNum] = new Hole(base, limit);
                    for (int i = base; i < base + limit; i++) {
                        memory[i].setData("Hole " );
                        tableView.getItems().add(null);
                    }
                    holesNum++;
                }
                if(index>1)
                {
                    holesNum=holesNum-index+1;
                }
            }
        else {
            hole[holesNum] = new Hole(base, limit);
            for (int i = base; i < base + limit; i++) {
                memory[i].setData("Hole " );
                tableView.getItems().add(null);
            }
            holesNum++;
        }
        for(int i=0 ;i<holesNum;i++)
        {
            for(int j=i+1 ;j<holesNum;j++)
            {
                if(hole[i].getBase()>hole[j].getBase())
                {
                    swap(hole,i,j);
                }
            }
        }

    }

    public static void addProcess(int Segments){
        proc[processNum]=new Processe(Segments);
        if(comboBox.getValue()=="First-Fit"){
            proc[processNum].setFirstFited(true);
           // processNum++;
            PopWindow.displaySWin(Segments);

        }
        else if(comboBox.getValue()=="Best-Fit"){
            proc[processNum].setFirstFited(false);
            for(int i=0 ;i<holesNum;i++)
            {
                for(int j=i+1 ;j<holesNum;j++)
                {
                    if(hole[i].getLimit()>hole[j].getLimit())
                    {
                        swap(hole,i,j);
                    }
                }
            }
            PopWindow.displaySWin(Segments);
        }

    }

    public static void addSegment(int segNum,String name,int Size){

        proc[processNum].segmentsTable[segNum]=new Segment(name,Size);

    }

    public static int [] basee=new int[100] ;

    public static int [] limitt=new int[100] ;

    public static void allocate(Processe [] process){
        int c = processNum+1 ;
        for(int i=0;i<c;i++){
            if(!process[i].isAllocated()){
                boolean flag1=false,flag2=false;

                    for(int d=0;d<holesNum;d++)
                    {
                        basee[d]=hole[d].getBase();
                        limitt[d]=hole[d].getLimit();
                    }
                for(int y=0;y<process[i].getSegNum();y++){
                    for(int z=0;z<holesNum;z++){
                        if(process[i].getSegments(y).getSize()<=hole[z].getLimit()){
                            hole[z].setLimit(hole[z].getLimit()-process[i].getSegments(y).getSize());
                            flag1=true;
                            break;
                        }else
                            flag1=false;
                    }
                    if(flag1)
                        flag2=true;
                    else{
                        flag2=false;
                        break;
                    }
                }
                     for(int d=0;d<holesNum;d++)
                     {
                        hole[d].setBase(basee[d]);
                        hole[d].setLimit(limitt[d]);
                     }
                if(flag2){
                    for(int y=0;y<process[i].getSegNum();y++){
                        for(int z=0;z<holesNum;z++){
                            if(process[i].getSegments(y).getSize()<=hole[z].getLimit()) {
                                hole[z].setLimit(hole[z].getLimit() - process[i].getSegments(y).getSize());
                                process[i].getSegments(y).setBase(hole[z].getBase());
                                for (int loop = hole[z].getBase(); loop < hole[z].getBase()+process[i].getSegments(y).getSize(); loop++) {
                                    memory[loop].setData("Process: " + i +", Segment: "+process[i].getSegments(y).getName());
                                    tableView.getItems().add(null);
                                }
                                hole[z].setBase(hole[z].getBase()+process[i].getSegments(y).getSize());
                                break;
                            }
                        }
                    }
                    process[i].setAllocated(true);
                    processNum++ ;
                }
                else{
                    AlertBox.display("Error!!!","Process couldn't be allocated. \n(Suitable space can't be found in Memory)");
                }

            }
        }
        for(int cc= 0 ; cc<holesNum;cc++)
        {
            if(hole[cc].getLimit()==0)
            {

                swap(hole, cc,cc+1);
                holesNum--;
            }
        }
    }

    public ObservableList<Memory> getMemory(){
        ObservableList<Memory> memory = FXCollections.observableArrayList();
        return memory;
    }

    public ObservableList<Segment> getSegments(){
        ObservableList<Segment> seg = FXCollections.observableArrayList();
        return seg;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
