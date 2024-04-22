package com.ideau.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
public class ApiApplication extends Application{

	// Variável para manter o contexto da aplicação Spring
	public static ConfigurableApplicationContext context;

	// Método principal da aplicação
	public static void main(String[] args) {
		// Inicializa a aplicação JavaFX e Spring Boot
		launch(args);
		SpringApplication.run(ApiApplication.class, args);
	}

	// Método start da aplicação JavaFX
	@Override
	public void start(Stage stage) throws Exception {
		// Inicializa o contexto da aplicação Spring
		context = SpringApplication.run(ApiApplication.class);

		// Carrega a interface gráfica (FXML) e define o controlador
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("/com/java/fx/main.fxml"));

		// Cria e configura a cena (Scene) com a interface gráfica carregada
		fxml.setControllerFactory(context::getBean);

		// Cria e configura a cena (Scene) com a interface gráfica carregada
		Scene scene = new Scene(fxml.load());
		
		// Configura o título da janela
		stage.setTitle("Cadastro de Pessoa");

		// Define a cena (Scene) na janela (Stage) e exibe a janela
		stage.setScene(scene);
		stage.show();
	}

}


