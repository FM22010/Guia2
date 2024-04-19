import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class Userdata {
    public static void main(String[] args) {
        File file = new File("src/user_data.txt");
        try {
            FileReader fr = new FileReader(file, StandardCharsets.ISO_8859_1);
            BufferedReader br = new BufferedReader(fr); //para leer
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true)); //para modificar
            //se agrega el header si no esta
            List<String> Lineas;
            Lineas = Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1);
            String Headers = "id,first_name,last_name,email,gender,ip_address";
            String PrimeraLinea = br.readLine();
            if (PrimeraLinea.equals(Headers)) {
                JOptionPane.showMessageDialog(null, "El archivo ya tiene el header\nSe continuara con el resto de codigo");
            } else {
                //se agregarar el headers y solo se correra los datos una casilla hacia abajo
                Lineas.add(0, Headers);
                JOptionPane.showMessageDialog(null, "El archivo no tiene el header se agregara ......");
                Modificar(Lineas, file, bw);
                JOptionPane.showMessageDialog(null, "Se termino de agregar el headers :D");
            }

            /*
            - Buscar un registro pro `id`  Mostrar el registro encontrado o un mensaje indicando que
             el registro no fue encontrado.
            - Reemplazar el ID numérico por la primera letra del nombre, la primera letra del apellido
            y tres dígitos basados en la longitud del apellido
            * */
            Lineas = Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1); //se vuelve a leer el archivo para tener los datos actualizados
            String id = JOptionPane.showInputDialog(null, "Buscar registro\n" + "Ingrese el id a buscar");
            int IdUsuario = BuscarId(Lineas, id);
            String[] datos;
            boolean encontrado = IdUsuario != 0;
            /*            - Reemplazar el ID numérico por la primera letra del nombre, la primera letra del apellido
            y tres dígitos basados en la longitud del apellido
            * */
            if (encontrado) {
                JOptionPane.showMessageDialog(null, "Registro  encontrado \n Se procedera a reemplazar el id por la primera letra del nombre,\n" +
                        " la primera letra del apellido y tres digitos basados en la longitud del apellido");
                String[] dataTemp = Lineas.get(IdUsuario).split(",");
                String idReemplazo = GenaerarIdReemplazo(dataTemp);
                //Se remplaza el usuario con los datos actualizados
                Lineas.set(IdUsuario, idReemplazo + "," + dataTemp[1] + "," + dataTemp[2] + "," + dataTemp[3] + "," + dataTemp[4] + "," + dataTemp[5]);
                JOptionPane.showMessageDialog(null, "Se remplazo el id por " + idReemplazo);
                Modificar(Lineas, file, bw);
            } else {
                JOptionPane.showMessageDialog(null, "Registro no encontrado");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }




    }




    public static int BuscarId(List<String> Lineas, String id) {
        int IdUsuario = 0;
        for (int i = 0; i < Lineas.size(); i++) {
            String[] dataTemp = Lineas.get(i).split(",");
            if (dataTemp[0].equals(id)) {
                IdUsuario = i;
                break;
            }
        }
        return IdUsuario;
    }

    public static String GenaerarIdReemplazo(String[] dataTemp) {
        //Se prosede a modificar la cantida de digitos del apellido
        int apellido = dataTemp[2].length();
        String idReemplazo;
        if (apellido < 10) {
            idReemplazo = dataTemp[1].charAt(0) + "" + dataTemp[2].charAt(0) + "00" + apellido;
        } else if (apellido > 10 && apellido < 100) {
            idReemplazo = dataTemp[1].charAt(0) + "" + dataTemp[2].charAt(0) + "0" + apellido;
        } else {
            idReemplazo = dataTemp[1].charAt(0) + "" + dataTemp[2].charAt(0) + apellido;
        }
        return idReemplazo;
    }

    public static void Modificar(List<String> Lineas, File file, BufferedWriter bw) {
        try {

            FileWriter fw = new FileWriter(file);//para borrar el contenido del txt y despues agregar el header y el resto
            fw.close();
/** * Se agregan las lineas que ya tenia el archivo
 * con un metodo tipo for each recorriendo el array de lineas que se almaceno anterior mente en memortia
 */
            for (String linea : Lineas) {
                bw.write(linea);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
