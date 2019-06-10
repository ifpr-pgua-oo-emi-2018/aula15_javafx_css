package sample.control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import java.awt.*;
import java.net.URI;
import java.util.function.Predicate;


//Para maiores detalhes das propriedades css dos componentes
//https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html


public class Controller {

    @FXML
    private RadioButton rbBase;

    @FXML
    private RadioButton rbBase1;

    @FXML
    private Parent root;

    @FXML
    private ListView<String> ltvItems;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfBusca;

    @FXML
    private CheckBox chbAjuda;

    @FXML
    private WebView wbAjuda;

    private ObservableList<String> lista ;


    @FXML
    private void initialize(){
        lista = FXCollections.observableArrayList();
        ltvItems.setItems(lista);

        tfNome.setStyle("-fx-text-fill: green");



        ltvItems.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                ListCell<String> celula = new ListCell<String>(){
                    @Override
                    protected void updateItem(String str, boolean vazio) {
                        super.updateItem(str, vazio);

                        getStyleClass().remove("normal");
                        getStyleClass().remove("media");
                        getStyleClass().remove("grande");

                        if(vazio){

                            setText(null);
                        }
                        else {
                            setText(str);
                            if (str.length() < 3) {
                                getStyleClass().add("normal");
                            } else if (str.length() >= 7) {
                                getStyleClass().add("grande");
                            } else {
                                getStyleClass().add("media");
                            }
                        }

                    }
                };

                return celula;
            }
        });

    }


    @FXML
    private void mudaStyleSheet(ActionEvent e){

        root.getStylesheets().remove("/sample/css/adicional1.css");
        root.getStylesheets().remove("/sample/css/adicional2.css");

        if(e.getSource().equals(rbBase)){
            root.getStylesheets().add("/sample/css/adicional1.css");
        }else{
            root.getStylesheets().add("/sample/css/adicional2.css");
        }

    }

    @FXML
    private void addNome(){
        System.out.println(tfNome.getText());
        lista.add(tfNome.getText());
    }

    @FXML
    private void acaoTecla(KeyEvent e){

        System.out.println(e.getCode());

        if(e.isControlDown() && e.getCode() == KeyCode.Z){
            ltvItems.setItems(lista);
            tfBusca.clear();
        }



        String filtro = tfBusca.getText()+e.getText();

        if(filtro.length()>=3){
            ltvItems.setItems(filtra(filtro));
        }


    }

    private ObservableList<String> filtra(String filtro){
        ObservableList<String> temp = FXCollections.observableArrayList();

        temp.addAll(lista.filtered(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.startsWith(filtro);

            }
        }));

        return temp;

    }



}
