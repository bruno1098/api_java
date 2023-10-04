package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class PrimaryController implements Initializable {



    public FlowPane carregar(){

        try {
            var url = new URL("https://bymykel.github.io/CSGO-API/api/en/skins.json");
            var con = url.openConnection();
            con.connect();
            var is = con.getInputStream();

            var reader = new BufferedReader(new InputStreamReader(is));
            var json = reader.readLine();

            var lista = jsonParaLista(json);

            return mostrarSkin(lista);
            
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensagem("Erro. " + e.getMessage());
            
        }

        return null;

    }

    private FlowPane mostrarSkin(List<Skin> lista) {
        var flow = new FlowPane();
        flow.setVgap(20);
        flow.setHgap(20);

        lista.forEach(Skin ->{
            var image = new ImageView(new Image(Skin.getImage()));
            image.setFitHeight(150);
            image.setFitWidth(150);
            var labelName = new Label(Skin.getName());
            var labelDescription = new Label(Skin.getDescription());
            flow.getChildren().add(new VBox(image, labelName, labelDescription));
        }); 
        return flow;
    }

    private List<Skin> jsonParaLista(String json) throws JsonMappingException, JsonProcessingException {
        var mapper = new ObjectMapper();
        var results = mapper.readTree(json).get("results");
        List<Skin> lista = new ArrayList<>();

        results.forEach(Skin -> {
            try {
                lista.add(mapper.readValue(Skin.toString(), Skin.class));
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        return lista;
    }

    private void mostrarMensagem(String mensagem){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(mensagem);
        alert.show();
        
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }
 
}
