
package gwopennlp.maventallerii.OpenNlp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class Nlp implements  Runnable{
    public String result = ""; 
    public boolean fin = false; 
    private List<String> dato = new ArrayList<String>(); 
    
    public Nlp(List<String> _dato) {
        dato = _dato; 
        Thread hilo = new Thread(this); 
        hilo.start();
    }

    public Nlp() {
    }
    

    @Override
    public void run() {
        //List<String> enviarLista = leerCsv(); 
        try { 
           result =  contarTiposVoz(dato);
           fin= true; 
        } catch (IOException ex) {
            Logger.getLogger(Nlp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *
     * @param rx
     * @return Retorna un String con la posibilidad de ser voz activa o pasiva
     * @throws FileNotFoundException
     * @throws IOException
     */
    public String validar(String rx) throws FileNotFoundException, IOException{
        
        InputStream tokenModelIn = null;
        InputStream posModelIn = null;
        boolean bandera1 = false;
        boolean bandera2 = false;
        boolean bandera3 = false;
        boolean bandera4 = false;

        
        String[] result = rx.split(",");
        if (result.length >1) {
            List<String> sentenceList = new ArrayList<>();
            for (String string : result) {
                sentenceList.add(string); 
            }
            return contarTiposVoz(sentenceList); 
        }
        
        
        tokenModelIn = new FileInputStream("E:\\GwOpenNlp\\src\\en-token.bin");
        TokenizerModel tokenModel = new TokenizerModel(tokenModelIn);
        Tokenizer tokenizer = new TokenizerME(tokenModel);
        String tokens[] = tokenizer.tokenize(rx);
        
        for (String token : tokens) {
            System.out.println(token);
        }
        
     
        posModelIn = new FileInputStream("E:\\GwOpenNlp\\src\\en-pos-maxent.bin");
        POSModel posModel = new POSModel(posModelIn);
        POSTaggerME posTagger = new POSTaggerME(posModel);
        String tags[] = posTagger.tag(tokens);
        double probs[] = posTagger.probs();
        
        System.out.println("Token\t:\tTag\t:\tProbability\n---------------------------------------------");
        for(int i=0;i<tokens.length;i++){
            System.out.println(tokens[i]+"\t:\t"+tags[i]+"\t:\t"+probs[i]);
        }
        for (int i = 0; i < tags.length; i++) {
            
            // Si lo primero que se encuentra es un sustantivo o un pronombre, hay probabilidades de ser voz activa.
            bandera1 = (tags[i].equals("PRP")  || tags[i].equals("NN")|| tags[i].equals("NNS") || (tags[i].equals("NNP") && tags[i+1].equals("VBD"))&& (probs[i]>=0.84) );
            // Si lo primero que encuentra es un verbo, puede que se trate de una voz pasiva.
            bandera2 = ((tags[i].equals("VBP") || tags[i].equals("VBZ") || tags[i].equals("VBD") || tags[i].equals("VBN")
                    ||tags[i].equals("VB") || tags[i].equals("PRP$") )&& (probs[i]>=0.84)
                    );
            if (bandera1 ) {
                // System.out.println("Probablemente activa");
                break;
            }
            if (bandera2 ) {
                // System.out.println("Probablemente pasiva");
                break;
            }
        }
        // Si es un pronombre propio o singular y le sigue un verbo en 3ra persona. pasivo
        for (int i = 0; i < tags.length; i++) {
            bandera3 =  (tags[i].equals("NNP") && tags[i+1].equals("VBZ") &&(probs[i]>=0.84)  && (probs[i+1]>=0.84) );
            if(bandera3){
                //System.out.println("Probablemente pasiva");
                break;
            }
        }
        // Si empieza con un determinante y luego un sustantivo... Pasivo.
        bandera4 =((tags[0].equals("DT") &&tags[1].equals("NN"))|| (tags[0].equals("DT") &&tags[1].equals("JJ")));

        
        //Puede que sea voz activa...
        boolean rAux1 = bandera1 && !bandera4 && !bandera3;
        // Puede que sea pasiva...
        boolean rAux2 = bandera4|| bandera3 || bandera2;
        
        if(rAux1 && !rAux2){
            return "Active voice";
        }
        else if( !rAux1 && rAux2){
            return "Passive voice";
        }
        else{
            return "Voie N.I";
        }
        
    }
    /**
     * @return Retorna lista de String con frases tanto de voz activa como de voz pasiva.
     * @throws FileNotFoundException
     * @throws IOException
     */
    
    
    /**
     *
     */
    public String contarTiposVoz(List<String> listaSentence) throws IOException{
        int contPasive = 0;
        int contActivo = 0;
        int conX = 0;
        for (String string : listaSentence) {
            String resultadoValidacion = validar(string);
            
            if (resultadoValidacion.equals("Active voice"))
                contActivo++;
            else if(resultadoValidacion.equals("Passive voice"))
                contPasive++;
            else{
                conX++;
            }
        }
           System.out.println("Activos "+contActivo+" Pasivos "+contPasive+ " otros "+conX);
           return "Active voice"+","+contActivo+","+"Passive voice"+","+contPasive+"," +"Voice N.I"+","+conX; 
    }
   
}