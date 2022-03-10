package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.json.JSONObject;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private Text pressure;

    @FXML
    private Text temp_feels;

    @FXML
    private Text temp_info;

    @FXML
    private Text temp_max;

    @FXML
    private Text temp_min;

    @FXML
    private ImageView image;

    @FXML
    void initialize() {
        // Инициализация события по клику на кпонку
        getData.setOnAction( event -> {
            // Получение данных из текстового поля
            String getUserCity = city.getText().trim();
            // Получение данных с сайта OpenWeather
            String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=f9f673d16c50887c7dab6c6c035c0c81&units=metric");

            if (!output.isEmpty()) { // Нет ошибки и такой город есть
                JSONObject obj = new JSONObject(output);
                temp_info.setText("Температура: " + (int) obj.getJSONObject("main").getDouble("temp"));
                temp_feels.setText("Ощущается как: " + (int) obj.getJSONObject("main").getDouble("feels_like"));
                temp_max.setText("Максимум: " + obj.getJSONObject("main").getDouble("temp_max"));
                temp_min.setText("Минимум: " + obj.getJSONObject("main").getDouble("temp_min"));
                pressure.setText("Давление: " + (int) obj.getJSONObject("main").getDouble("pressure"));
            }

        } );
    }

    // Обращение к URL и получение данных
    private static String getUrlContent (String urlAdress){
        StringBuffer content = new StringBuffer();

        // Обращение по URL
        try{
           URL url = new URL(urlAdress);
           URLConnection urlConn = url.openConnection();

           // Открытие потока на чтение данных
           BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
           String line;

           // Записывание данных в строку
           while ((line = bufferedReader.readLine())!= null){
               content.append(line +"\n");
           }
           bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Неверно введенный город!");
        }
        return content.toString();
    }

}
