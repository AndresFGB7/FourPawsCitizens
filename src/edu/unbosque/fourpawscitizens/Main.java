package edu.unbosque.fourpawscitizens;

import edu.unbosque.fourpawscitizens.model.ManagerDAO;

import java.util.List;
import java.util.Scanner;
/**
 * @author AndresGB
 */
public class Main {
    // CREAR EL SCANNER Y LLAMAR LA CLASE MANAGER
    private Scanner scanner;
    private ManagerDAO daomanager;

    public Main() {

        //SUBIR EL ARCHIVO CSV

        scanner = new Scanner(System.in);
        System.out.println("Por favor ingresa la ruta del archivo csv");
        var file = scanner.nextLine();
        daomanager = new ManagerDAO(file);
        try {
            var message = daomanager.uploadData();
            System.out.println(message);
            if (message.equals("Lo sentimos el archivo que intenta cargar no es valido recuerde que debe terminar .csv")){
                System.exit(0);
            }
            menu1();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Este muestra el menu para los diversos requerimentos del programa
     */
    public void menu1() {
        System.out.println("\nPor favor escoge que deseas hacer " +
                "\n\n1.Asignar los respectivos ids " +
                "\n2 Encontrar por Microship " +
                "\n3 Contar el numero de especies " +
                "\n4 Animales potencialmente peligrosos de una localidad. Puede ser los n primeros (TOP) o los n últimos (Last)." +
                "\n5 ids de los animales que coincidan con los parametros de busqueda ");
        try {
            //VERIFICA QUE EL NUMERO SEA VALIDO
            var n = scanner.nextInt();
            scanner.nextLine();
            if (n < 1 && n > 5) {
                System.out.println("Ingresa un número del 1 al 5");
                menu1();
            } else {
                realizar(n);
            }
        } catch (Exception e) {
            System.out.println("Ingresa un numero valido!");
        }
    }

    /**
     * Aqui se ejecutan los metodos en base al numero escogido
     * @param n int: Numero escogido
     */
    private void realizar(int n) {

        switch (n) {
            case 1:
                //ASIGNAR ID
                try {
                    System.out.println(daomanager.assignID());
                }catch (Exception e) {
                    e.printStackTrace();
                }
                preguntar();
                break;
            case 2:
                //BUSCAR POR MICROSHIP
                System.out.println("Ingresa el número del microship");
                long c = Long.parseLong(scanner.nextLine());
                System.out.println(daomanager.findByMicrochip(c).toString());
                preguntar();
                break;
            case 3:
                //CONTAR ESPECIES
                System.out.println("Ingresa la especie que deseas contar");
                String specie = scanner.nextLine();
                System.out.println(daomanager.countBySpecies(specie));
                preguntar();
                break;
            case 4:
                //ANIMALES POTENCIALMENTE PELIGROSOS
                System.out.println("Ingresa el numero, Ingresa(TOP|LAST),Ingresa Localidad");
                String info = scanner.nextLine().replace("”", "").replace("\"","");
                info = info.replace("“","");
                String parts[] = info.split(", ");
                System.out.println(daomanager.findBypotentDangerousInNeighborhood(Integer.parseInt(parts[0]), parts[1], parts[2]).toString().replace("[","").replace("]","").replace(", ",""));
                preguntar();
                break;
            case 5:
                //ID DEL ANIMAL EN BASE A LOS PARAMETROS DE BUSQUEDA
                System.out.println("Ingresa La especie,Ingresa el sexo,Ingresa el tamaño,Ingresa el peligro potente");
                String info2 = scanner.nextLine().replace("”","").replace("\"","");
                info2 = info2.replace("“","");
                String[] parts2 = info2.split(",");
                System.out.println(parts2[0]+" "+ parts2[1]+" "+ parts2[2]+" "+ parts2[3]);
                List<String> v = daomanager.findByMultipleFields(parts2[0], parts2[1], parts2[2], parts2[3]);
                for (int i = 0;i<v.size();i++){
                    System.out.println(v.get(i));
                }
                preguntar();
                break;
        }

    }

    /**
     * Pregunta si desea seguir con las opciones que brinda el programa
     */
    private void preguntar(){
        System.out.println("¿Desea continuar solicitando informacion?(SI/NO)");
        if(scanner.next().compareToIgnoreCase("SI")==0){
            menu1();
        }
    }

    /**
     * Se lanza el programa
     * @param args
     */
    public static void main(String[] args) {
        Main c = new Main();
    }
}
