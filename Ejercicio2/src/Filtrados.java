import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/***
 * - Leer el archivo, filtrar los registros por género y escribirlos en un nuevo archivo. `file_male.txt` & `file_female.txt`
 * - Buscar registros duplicados basados en la dirección IP. Si se encuentra un registro duplicado, eliminar todos los registros duplicados y dejar solo el primer registro encontrado.
 * - Contar y mostrar la cantidad de registros cuya dirección IP pertenece a un rango específico (por ejemplo, contar cuántos registros tienen una dirección IP que comienza con "170.").
 * - Agregar una nueva columna al registro original con la información encontrada en el siguiente archivo.
 */
public class Filtrados {
    public static void main(String[] args) {
        File file = new File("src/user_data.txt");
        try {
            FileReader fr = new FileReader(file, StandardCharsets.ISO_8859_1);
            BufferedReader br = new BufferedReader(fr); //para leer
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true)); //para modificar
            String nombreArchivos[] = {"file_male.txt", "file_female.txt"};
            List<String> Lineas;
            Lineas = Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1);
            filtrarPorGenero(Lineas, nombreArchivos);//Filtrar por genero

            List<String> LineasFiltradas = BuscarDuplicados(Lineas);
            CrearArchivo((ArrayList<String>) LineasFiltradas, "file_sin_duplicados.txt");

            ContarRegistrosPorRango(Lineas, "170.");

            AgregarColumna(Lineas, "user_names.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // * - Agregar una nueva columna al registro original con la información encontrada en el siguiente archivo.
    static void AgregarColumna(List<String> Lineas, String nombreArchivos) {
        try {
            File file = new File("src/" + nombreArchivos);
            List<String> LineasArchivo = Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1);
            for (int i = 0; i < Lineas.size(); i++) {
                Lineas.set(i, Lineas.get(i).split(",")[0]+","
                        + Lineas.get(i).split(",")[1] +","+
                        Lineas.get(i).split(",")[2] +
                        "," + LineasArchivo.get(i) + "," + Lineas.get(i).split(",")[3] + "," +
                        Lineas.get(i).split(",")[4] + "," + Lineas.get(i).split(",")[5]);

            }
            CrearArchivo((ArrayList<String>) Lineas, "file_con_columna.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*Contar y mostrar la cantidad de registros cuya dirección IP pertenece a un
    rango específico (por ejemplo, contar cuántos registros tienen una dirección IP que comienza con "170.").
     * */
    public static void ContarRegistrosPorRango(List<String> Lineas, String rango) {
        int contador = 0;
        for (String linea : Lineas) {
            String ip = linea.split(",")[5];
            if (ip.startsWith(rango)) {
                contador++;
            }
        }
        System.out.println("Cantidad de registros con IP que comienza con " + rango + ": " + contador);
    }


    /*Buscar registros duplicados basados en la dirección IP. Si se encuentra un registro duplicado,
    eliminar todos los registros duplicados y dejar solo el primer registro encontrado.*/
    public static List<String> BuscarDuplicados(List<String> Lineas) {
        Set<String> ipsVistas = new HashSet<>();
        List<String> LineasFiltradas = new ArrayList<>();

        for (String linea : Lineas) {
            String ip = linea.split(",")[5];
            if (!ipsVistas.contains(ip)) {
                ipsVistas.add(ip);
                LineasFiltradas.add(linea);
            } else {
                System.out.println("Duplicado encontrado: " + linea);
            }
        }
        return LineasFiltradas;
    }

    /***
     *
     * @param Lineas
     * @param nombreArchivos
     */
    public static void filtrarPorGenero(List<String> Lineas, String[] nombreArchivos) {
        ArrayList<String> LineasHombres = new ArrayList<>();
        ArrayList<String> LineasMujeres = new ArrayList<>();
        String Headers = "id,first_name,last_name,email,gender,ip_address";
        LineasMujeres.add(Headers);
        LineasHombres.add(Headers);
        for (String linea : Lineas) {
            String[] dataTemp = linea.split(",");
            //Female y Male
            if (dataTemp[4].equals("Male")) {//Empieza con Hombres
                LineasHombres.add(linea);//Guarde los datos
            } else if (dataTemp[4].equals("Female")) {//Filtra por mujeres
                LineasMujeres.add(linea);//Guarde los datos
            }
        }
        CrearArchivo(LineasHombres, nombreArchivos[0]);
        CrearArchivo(LineasMujeres, nombreArchivos[1]);
    }

    /**
     * @param Lineas
     * @param nombreArchivo
     */
    public static void CrearArchivo(ArrayList<String> Lineas, String nombreArchivo) {
        try {
            File file = new File("src/" + nombreArchivo);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            for (String linea : Lineas) {
                bw.write(linea + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
