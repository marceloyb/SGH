package sistemahotel.control.pessoa;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sistemahotel.control.ControleTelas;
import sistemahotel.model.infraestrutura.Persistencia;
import sistemahotel.model.infraestrutura.RetornaListas;
import sistemahotel.model.pessoa.Cliente;
import sistemahotel.model.pessoa.ClienteDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class ControleTelaClientes implements Initializable {
    @FXML
    TableView tvClientes;
    @FXML
    TableColumn tcNome;
    @FXML
    TableColumn tcRG;
    @FXML
    JFXTextField tfFiltro;
    @FXML
    private JFXTextField tfNome;
    @FXML
    private JFXTextField tfRG;
    @FXML
    private JFXTextField tfCPF;
    @FXML
    private DatePicker dtDataDeNascimento;
    @FXML
    private JFXTextField tfTelefone;
    @FXML
    private JFXTextField tfEndereco;
    @FXML
    private JFXTextField tfEmail;
    @FXML
    private JFXTextField tfCidade;
    @FXML
    private JFXTextField tfNacionalidade;
    @FXML
    private JFXTextField tfPlacaDoCarro;
    @FXML
    private JFXTextField tfInformacoesAdicionais;

    Cliente clienteMain;
    ClienteDAO clienteDAO = ClienteDAO.getInstancia();
    RetornaListas pegaListas;
    ObservableList list;
    ControleTelas controleTelas = ControleTelas.getInstancia();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list = FXCollections.observableList(pegaListas.listClientes2());
        tcNome.setCellValueFactory( new PropertyValueFactory<>("nome"));
        tcRG.setCellValueFactory(new PropertyValueFactory<>("RG"));
        tvClientes.setItems(FXCollections.observableList(list));

        tvClientes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldvalue, newValue) -> selecaoDeItens((Cliente) newValue)
        );
        FilteredList<Cliente> filteredData = new FilteredList<>(list, p -> true);
        tfFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(cliente -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (cliente.getNome().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Cliente> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvClientes.comparatorProperty());
        tvClientes.setItems(sortedData);
    }

    public void btNovoClienteActionHandler(ActionEvent event) throws IOException {
        controleTelas.newWindow("/sistemahotel/view/pessoa/NovoCliente.fxml",event);
    }

    public void btAlterarClienteActionHandler(ActionEvent event) throws IOException {
        if (tfNome.getText().isEmpty() || tfRG.getText().isEmpty()) {
            controleTelas.popupAviso("Campos inválidos", "Campos com * são obrigatórios");
        } else {
            list.add(clienteDAO.Alterar(tfNome.getText(), tfEndereco.getText(), tfEmail.getText(),
                    tfCidade.getText(), tfNacionalidade.getText(), tfPlacaDoCarro.getText(),
                    tfInformacoesAdicionais.getText(), tfCPF.getText(), tfRG.getText(),
                    dtDataDeNascimento.getValue(), clienteMain.getId()));
            list.remove(clienteMain);
            controleTelas.notificacao("Alteração efetuada", "Alteração do cliente concluída no banco de dados");
            tvClientes.refresh();
        }
    }

    public void btDeletarClienteActionHandler(ActionEvent event) throws IOException {
        boolean conf;

        conf = controleTelas.continuarOuCancelar("Menssagem de confirmação",
                "Você está excluindo um cliente!",
                "Você realmente deseja excluir o cliente?");
        if (conf) {
            clienteDAO.Deletar(clienteMain);
            list.remove(clienteMain);
            tvClientes.refresh();
        }
    }

    public void selecaoDeItens(Cliente cliente){
        clienteMain = cliente;
        tfNome.setText(cliente.getNome());
        tfRG.setText(cliente.getRG());
        tfCPF.setText(cliente.getCPF());
        dtDataDeNascimento.setValue(cliente.getDataDeNascimento());
        tfTelefone.setText(cliente.getTelefone());
        tfEndereco.setText(cliente.getEndereco());
        tfEmail.setText(cliente.getEmail());
        tfCidade.setText(cliente.getCidade());
        tfNacionalidade.setText(cliente.getNacionalidade());
        tfPlacaDoCarro.setText(cliente.getPlacaDoCarro());
        tfInformacoesAdicionais.setText(cliente.getInformacoesAdicionais());
    }
}
