package sistemahotel.control.reserva;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import sistemahotel.control.ControleTelas;
import sistemahotel.model.infraestrutura.RetornaListas;
import sistemahotel.model.reserva.Reserva;
import sistemahotel.model.reserva.ReservaDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sistemahotel.model.infraestrutura.Util.setUpFilter;

public class ControleTelaReserva implements Initializable{

    @FXML
    public TableView tvReservas;
    @FXML
    TableColumn tcStatus;
    @FXML
    TableColumn tcCliente;
    @FXML
    TableColumn tcLocal;
    @FXML
    TableColumn tcQtdhospede;
    @FXML
    TableColumn tcDataCheckIn;
    @FXML
    TableColumn tcDataCheckOut;
    @FXML
    JFXTextField tfFiltro;

    Reserva reservaMain;

    private ObservableList olReservas;
    private ReservaDAO InstanciaReservaDAO = ReservaDAO.getInstancia();
    private ControleTelas janela = ControleTelas.getInstancia();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        olReservas = FXCollections.observableList(RetornaListas.listReserva());
        tcStatus.setCellValueFactory( new PropertyValueFactory<>("status"));
        tcCliente.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Reserva, String>, ObservableValue<String>>) r -> new SimpleStringProperty(r.getValue().getCliente().getNome()));
        tcLocal.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Reserva, String>, ObservableValue<String>>) r -> new SimpleStringProperty(r.getValue().getLocal().getNumero()));
        tcQtdhospede.setCellValueFactory( new PropertyValueFactory<>("qtdhospede"));
        tcDataCheckIn.setCellValueFactory(new PropertyValueFactory<>("dataCheckIn"));
        tcDataCheckOut.setCellValueFactory(new PropertyValueFactory<>("dataCheckOut"));
        tvReservas.setItems(FXCollections.observableList(olReservas));
        tvReservas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldvalue, newValue) -> selecaoReserva((Reserva) newValue)
        );
        setUpFilter(olReservas, tfFiltro, tvReservas);

    }

    public void btNovaReservaActionHandler(ActionEvent event) throws IOException{
        janela.novaJanelaSobreposta("/sistemahotel/view/reserva/TelaNovaReserva.fxml",event);
    }

    public void btNovaReservaSalaoActionHandler(ActionEvent event) throws IOException{
        janela.novaJanelaSobreposta("/sistemahotel/view/reserva/TelaNovaReservaSalao.fxml",event);
    }

    public void btCancelaReservaActionHandler(ActionEvent event) throws IOException{
        boolean msg = janela.continuarOuCancelar("Menssagem de confirmação",
                "Você está cancelando uma reserva!",
                "Você realmente deseja cancelar esta reserva?");
        if (msg) {
            InstanciaReservaDAO.cancelarReserva(reservaMain);
            olReservas.remove(reservaMain);
            tvReservas.refresh();
        }
    }

    public void selecaoReserva(Reserva reserva){
        reservaMain = reserva;
    }

}
