/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package gwopennlp.maventallerii;

import gwopennlp.OpenFile.Load;
import gwopennlp.maventallerii.OpenNlp.Nlp;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;



public class FXMLDocumentController implements Initializable {
    
    @FXML
            TextField txtFrase;
    
    @FXML
    private Label label;
    
    @FXML
    private Label lnbNI;
    
    @FXML
    private Label lnbTime;
    
    @FXML
    private Label lbnVoiceActiva;
    
    @FXML
    private Label lnbVoicePasiva;
    
    @FXML
    private Pane controlPane;
    
    @FXML
    private PieChart pieChart;
    
    @FXML
    private ProgressIndicator barProgreso;
    
    @FXML
    private Label lbnBarProgreso;
    
    @FXML
    private Button btnFileLoad;
    
    @FXML
    private AnchorPane paneGrafica;
    
    // Variables globales
    boolean commutador = false;
    //
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        paneGrafica.visibleProperty().set(true);
        String sentenceIn = txtFrase.getText();
        Nlp ClaseProcesamientoTexto = new Nlp();
        String sentenceOut = ClaseProcesamientoTexto.validar(sentenceIn);
        
        String[] result = sentenceOut.split(",");
        
        if (result.length>1) {
            lbnVoiceActiva.setText(result[1]);
            lnbVoicePasiva.setText(result[3]);
            lnbNI.setText(result[5]);
            ObservableList<PieChart.Data> pierChartData = FXCollections.observableArrayList();
            for (int i = 0; i < result.length; i++) {
                pierChartData.add( new PieChart.Data(result[i], Integer.parseInt(result[i+1])) );
                i++;
                pieChart.setData(pierChartData);
//                pieChart.setLegendVisible(true);
//                pieChart.setStartAngle(90);
            }
        }else{
            ObservableList<PieChart.Data> pierChartData = FXCollections.observableArrayList(
                    new PieChart.Data(sentenceOut, 100)
            );
            pieChart.setData(pierChartData);
//            pieChart.setLegendVisible(true);
//            pieChart.setStartAngle(90);
        }
        paneGrafica.visibleProperty().set(true);
    }
    
    @FXML
                            void btnFileLoadClick(ActionEvent event) throws IOException, InterruptedException {

long star = System.nanoTime();

//                String resultPorcentage = "";
//                resultPorcentage =  ClaseProcesamientoTexto.contarTiposVoz(envirLista);
                Load LeerArchivo = new Load();
                List<String> file =  LeerArchivo.leerCsv();

                List<String>  lista1= file.subList(0, 200);
                List<String>  lista2= file.subList(200, 400);
                List<String>  lista3= file.subList(400, 600);
                 List<String>  lista4= file.subList(600, 800);
                List<String>  lista5= file.subList(800, 1000);


                Nlp hilo1 = new Nlp(lista1);
                Nlp hilo2 = new Nlp(lista2);
                Nlp hilo3 = new Nlp(lista3);
                Nlp hilo4 = new Nlp(lista4);
                Nlp hilo5 = new Nlp(lista5);
               
              
               while (!hilo1.fin || !hilo2.fin || !hilo3.fin || !hilo4.fin || !hilo5.fin) {            
            
        }    
                long lastTime = System.nanoTime() - star;
                float tiempo = lastTime / 1000000000.0f;
                String resultadoTiempo = String.format("%.2f", tiempo);
                lnbTime.setText(resultadoTiempo+ " Seg");
                
     
                String[] result = hilo1.result.split(",");
                String[] result2 = hilo2.result.split(",");
                String[] result3 = hilo3.result.split(",");
                String[] result4 = hilo4.result.split(",");
                String[] result5 = hilo5.result.split(",");
                
                int activasTotal = Integer.parseInt(result[1]) + Integer.parseInt(result2[1]) + Integer.parseInt(result3[1]) + Integer.parseInt(result4[1])+ Integer.parseInt(result5[1]);
                int pasivasTotal = Integer.parseInt(result[3]) + Integer.parseInt(result2[3]) + Integer.parseInt(result3[3]) + Integer.parseInt(result4[3])+ Integer.parseInt(result5[3]);
                int NiTotal = Integer.parseInt(result[5]) + Integer.parseInt(result2[5]) + Integer.parseInt(result3[5]) + Integer.parseInt(result4[5])+ Integer.parseInt(result5[5]);
                lbnVoiceActiva.setText(""+activasTotal);
                lnbVoicePasiva.setText(""+pasivasTotal);
                lnbNI.setText(""+NiTotal);
                ObservableList<PieChart.Data> pierChartData = FXCollections.observableArrayList();
                for (int i = 0; i < result.length; i++) {
                    pierChartData.add( new PieChart.Data(result[i], Integer.parseInt(result[i+1])));
                    i++;
                }
                paneGrafica.visibleProperty().set(true);

                pieChart.setData(pierChartData);
                pieChart.setLegendVisible(true);
                pieChart.setStartAngle(90);
//                barProgreso.visibleProperty().set(false);
//                lbnBarProgreso.visibleProperty().set(false);

            }

            @FXML
            void btnSeeMore(ActionEvent event) {
                commutador= !commutador;
                controlPane.visibleProperty().set(commutador);
            }
            @Override
            public void initialize(URL url, ResourceBundle rb) {

            }
    }
