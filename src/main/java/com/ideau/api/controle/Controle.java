package com.ideau.api.controle;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ideau.api.modelo.Pessoa;
import com.ideau.api.repositorio.Repositorio;
import com.ideau.api.servico.Servico;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

@RestController
public class Controle implements Initializable {

     // Declaração de variáveis e injeção de dependências
    private Pessoa item;

    private int id;

    @Autowired
    private Repositorio acao; // Repositório para acesso ao banco de dados

    @Autowired
    private Servico servico; // Serviço para lógica de negócio

    // Componentes da interface gráfica (FXML)
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField txtId, txtNome, txtIdade;

    @FXML
    private Button btnSalvar, btnAtualizar, btnDeletar, btnCancelar;

    @FXML
    private ListView<Pessoa> list;

    // Método para salvar uma pessoa
    @FXML
    public void salvar() {
        if (!txtNome.getText().trim().isEmpty()) {
            Pessoa pessoa = new Pessoa();
            pessoa.setNome(txtNome.getText());
            pessoa.setIdade(Integer.valueOf(txtIdade.getText()));
            acao.save(pessoa);
            limpar();
            listar();
        }
    }

    // Método para listar todas as pessoas
    public void listar() {
        list.setItems(FXCollections.observableArrayList(acao.findAll()));

    }

    // Método para deletar uma pessoa
    public void deletar(){
        acao.delete(item);
        limpar();
        listar();
    }

    // Método para editar uma pessoa
    @FXML
    public void editar(){
        item.setNome(txtNome.getText());
        item.setIdade(Integer.valueOf(txtIdade.getText()));
        acao.save(item);
        limpar();
        listar();
    }
    
    // Método para limpar os campos e redefinir os botões
    @FXML
    public void limpar() {
        txtId.clear();
        txtNome.clear();
        txtIdade.clear();

        btnSalvar.setVisible(true);
        btnCancelar.setVisible(false);
        btnAtualizar.setVisible(false);
        btnDeletar.setVisible(false);

        btnSalvar.setVisible(true);
        btnCancelar.setVisible(false);
        btnAtualizar.setVisible(false);
        btnDeletar.setVisible(false);
    }

     // ** Endpoints da API REST **

    // Endpoint para cadastrar uma pessoa
    @PostMapping("/api")
    public ResponseEntity<?> cadastrar(@RequestBody Pessoa obj) {
        return servico.cadastrar(obj);
    }

    // Endpoint para selecionar todas as pessoas
    @GetMapping("/api")
    public ResponseEntity<?> selecionar() {
        return servico.selecionar();
    }

    // Endpoint para selecionar uma pessoa pelo código
    @GetMapping("/api/{codigo}")
    public ResponseEntity<?> selecionarPeloCodigo(@PathVariable int codigo) {
        return servico.selecionarPeloCodigo(codigo);
    }

    // Endpoint para editar uma pessoa
    @PutMapping("/api")
    public ResponseEntity<?> editar(@RequestBody Pessoa obj) {
        return servico.editar(obj);
    }

    // Endpoint para remover uma pessoa pelo código
    @DeleteMapping("/api/{codigo}")
    public ResponseEntity<?> remover(@PathVariable int codigo) {
        return servico.remover(codigo);
    }

    // @PostMapping("/api")
    // public Pessoa cadastrar(@RequestBody Pessoa obj){
    // return acao.save(obj);
    // }

    // @GetMapping("/api")
    // public List<Pessoa> selecionar(){
    // return acao.findAll();
    // }

    @GetMapping("")
    public String mensagem() {
        return "Hello World!";
    }

    @GetMapping("/boasVindas/{nome}")
    public String boasVindas(@PathVariable String nome) {
        return "Seja Bem-vindo! " + nome;
    }

    @PostMapping("/pessoa")
    public Pessoa pessoa(@RequestBody Pessoa p) {
        return p;
    }

    // @GetMapping("/api/{codigo}")
    // public Pessoa selecionarPeloCodigo(@PathVariable int codigo){
    // return acao.findByCodigo(codigo);
    // }

    // @PutMapping("/api")
    // public Pessoa editar(@RequestBody Pessoa obj){
    // return acao.save(obj);
    // }

    // @DeleteMapping("/api/{codigo}")
    // public void remover(@PathVariable int codigo){
    // Pessoa obj = selecionarPeloCodigo(codigo);
    // acao.delete(obj);
    // }

    @GetMapping("/api/contador")
    public long contador() {
        return acao.count();
    }

    @GetMapping("/api/ordenarNomes")
    public List<Pessoa> ordenarNomes() {
        return acao.findByOrderByNome();
    }

    @GetMapping("/api/nomeContem")
    public List<Pessoa> nomeContem() {
        return acao.findByNomeContaining("m");
    }

    @GetMapping("/api/somaIdades")
    public int somaIdades() {
        return acao.somaIdades();
    }

    @GetMapping("/api/idadeMaiorIgual")
    public List<Pessoa> idadeMaiorIgual() {
        return acao.idadeMaiorIgual(18);
    }

    @GetMapping("/status")
    public ResponseEntity<?> status() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Método para obter um item pelo ID
    public Pessoa item(){
        return acao.findById(id).get();
    }

    // Método inicializador
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listar();
        list.getSelectionModel().selectedItemProperty().addListener((obs, old, newValue) -> {
            if (newValue != null){
                id = newValue.getCodigo();
                item = item();
                txtId.setText(String.valueOf(item.getCodigo()));
                txtNome.setText(item.getNome());
                txtIdade.setText(String.valueOf(item.getIdade()));

                // Mostra os botões de edição e exclusão
                btnSalvar.setVisible(false);
                btnCancelar.setVisible(true);
                btnDeletar.setVisible(true);
                btnAtualizar.setVisible(true);
                
            }
        });
    }

}
