package sistemahotel.control.local;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sistemahotel.control.ControleTelaPrincipal;
import sistemahotel.control.ControleTelas;
import sistemahotel.model.infraestrutura.Persistencia;
import sistemahotel.model.infraestrutura.RetornaListas;
import sistemahotel.model.local.Local;
import sistemahotel.model.produto.Produto;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Tatsunori on 04/10/2017.
 */
public class ControleTelaLocais implements Initializable {
    @FXML
    TableView tvLocais;
    @FXML
    TableColumn tcTipo;
    @FXML
    TableColumn tcNumero;
    @FXML
    JFXTextField tfFiltro;

    @FXML
    private JFXButton btHabitacao;

    @FXML
    private JFXButton btSalaoFestas;

    RetornaListas pegaListas;
    ObservableList list;

    ControleTelas window = new ControleTelas();

    public void btHabitacaoActionHandler(ActionEvent e) throws IOException {
        window.newWindow("/sistemahotel/view/locais/Habitacao.fxml", e);
    }

    public void btSalaoFestasActionHandler(ActionEvent e) throws IOException {
        window.newWindow("/sistemahotel/view/locais/SalaoFestas.fxml", e);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list = FXCollections.observableList(pegaListas.listLocais());
        tcTipo.setCellValueFactory( new PropertyValueFactory<>("tipo"));
        tcNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tvLocais.setItems(FXCollections.observableList(list));


        FilteredList<Local> filteredData = new FilteredList<>(list, p -> true);
        tfFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(local -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (local.getNumero().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Local> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvLocais.comparatorProperty());
        tvLocais.setItems(sortedData);

    }
}
