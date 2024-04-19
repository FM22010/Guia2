import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ContadorPalabra {
    public static void main(String[] args) {
        File file = new File("src/BIBLIA_COMPLETA.txt");
        double contadorPro = 0;
        ArrayList<String> vector=new ArrayList<>();
        try {
            FileReader fr = new FileReader(file, java.nio.charset.StandardCharsets.ISO_8859_1);
            BufferedReader br = new BufferedReader(fr);
            String lineaData;
            String[] vectorPalbras = new String[5000];
            while ((lineaData = br.readLine()) != null){
                lineaData = lineaData;
                vectorPalbras = lineaData.split(" ");

                   for (int i = 0; i < vectorPalbras.length; i++) {
                       /*filtraje para quitar , . - o signos*/
                       vectorPalbras[i] = vectorPalbras[i].replace(",", "");
                    if (vectorPalbras[i].equalsIgnoreCase("dios")) {
                        contadorPro++;
                        vector.add(vectorPalbras[i]);
                    }
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("La cantidad de veces es de "+contadorPro);
        for (int i = 0; i < vector.size(); i++) {
            System.out.print(vector.get(i)+" ");
        }
    }
}