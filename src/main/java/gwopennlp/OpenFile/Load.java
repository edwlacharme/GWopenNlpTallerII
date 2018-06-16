/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gwopennlp.OpenFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Leonardo-PC
 */
public class Load {
    
    
    public List<String> leerCsv(){
        String rutaCsv = "src/main/java/prueba.csv";
        BufferedReader br = null;
        String line = "";
        List<String> sentenceList = new ArrayList<>();
        try {
            
            br = new BufferedReader(new FileReader(rutaCsv));
            while ((line = br.readLine()) != null) {
                sentenceList.add(line);
                System.out.println(line);
                
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se  encontro el archivo "+ e.toString());
        } catch (IOException e) {
            System.out.println("Error tipo IO "+ e.toString());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Error al cerrar el archivo "+ e.toString());
                }
            }
        }
        return sentenceList;
    }
    
}
