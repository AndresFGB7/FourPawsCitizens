package edu.unbosque.fourpawscitizens.model;

import edu.unbosque.fourpawscitizens.model.dtos.Pet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerDAO {
    private Pet obj_pet;
    private ArrayList<Pet> arr_pet;
    private String f;
    public final String SEPARATOR = ";";
    private int number;

    public ManagerDAO(String f) {
        this.f = f;
        arr_pet = new ArrayList<Pet>();
        number = 3;
    }

    /**
     * Charge the archive CSV
     */
    public String uploadData() throws Exception {
        //LEE EL ARCHIVO CSV
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(f));
            String line = br.readLine();
            while (null != line) {
                boolean flag = true;
                String[] fields = line.split(SEPARATOR);
                long microchip = 0;
                try {
                    microchip = Long.parseLong(fields[0]);
                }catch (NumberFormatException e){
                    line = br.readLine();
                    continue;
                }
                try {
                    for (int i = 1; i <6;i++){
                        if (fields[i].isEmpty()){
                            throw new Exception("EmptyAttributeException");
                        }
                    }
                } catch (Exception EmptyAttributeException) {
                    line = br.readLine();
                    continue;
                }
                var potentDangerous = false;
                if (fields[4].equals("SI")) {
                    potentDangerous = true;
                }
                Pet p = new Pet("NO-ID", microchip, fields[1], fields[2], fields[3], potentDangerous, fields[5]);
                arr_pet.add(p);
                line = br.readLine();
            }
        } catch (Exception e) {
            return "Lo sentimos el archivo que intenta cargar no es valido recuerde que debe terminar .csv";
        } finally {
            if (null != br) {
                br.close();
            }
        }
        return "El proceso de carga del archivo ha finalizado";
    }

    /**
     * Assign an id to all animals
     */
    public String assignID() {
        for (int i = 0; i < arr_pet.size(); i++) {
            obj_pet = arr_pet.get(i);
            String microchip2 = (obj_pet.getMicrochip() + "").substring((obj_pet.getMicrochip() + "").length() - number);
            String bool = "F";
            if (obj_pet.isPotentDangerous()) {
                bool = "T";
            }
            String size = obj_pet.getSize().charAt(0)+"";

            if (obj_pet.getSize().equals("MINIATURA")) {
                size = obj_pet.getSize().substring(0, 2);
            }
            String id20 = microchip2 + "-" + obj_pet.getSpecies().charAt(0) + "" + obj_pet.getSex().charAt(0) + "" + size + "" + bool + "-" + obj_pet.getNeighborhood();
            if (i != 0) {
                for (int j = 0; j < i; j++) {
                    try {
                        if (arr_pet.get(j).getId().equals(id20)) {
                            number = number+1;
                            throw new Exception("IdentifierExistsException");
                        }
                    }catch (Exception IdentifierExistsException){
                        microchip2 = (obj_pet.getMicrochip() + "").substring((obj_pet.getMicrochip() + "").length() - number);
                        id20 = microchip2 + "-" + obj_pet.getSpecies().charAt(0) + "" + obj_pet.getSex().charAt(0) + "" + size + "" + bool + "-" + obj_pet.getNeighborhood();
                    }
                }
            }
            number = 3;
            obj_pet.setId(id20);
        }
        return "El proceso de asignación de ids ha finalizado";
    }

    /**
     * Return an animal for its microchip
     *
     * @param microchip long:
     * @return Object Pet with the attribute in String format
     */
    public Pet findByMicrochip(long microchip) {
        for (int i = 0; i < arr_pet.size(); i++) {
            if (arr_pet.get(i).getMicrochip() == microchip) {
                return arr_pet.get(i);
            }
        }
        return null;
    }

    /**
     * @param species
     * @return
     */
    public String countBySpecies(String species) {
        var contador = 0;
        for (int i = 0; i < arr_pet.size(); i++) {
            if (arr_pet.get(i).getSpecies().equals(species)) {
                contador++;
            }
        }
        return "“El número de animales de la especie " + species + " es: " + contador;
    }

    /**
     * Return a number of animals potentially dangerous of a locality, Can be the n first or the n last.
     *
     * @param n            - int: n-first or last animals
     * @param position     -Strinbg: (Top | Last)
     * @param neighborhood - String:
     * @return List of objects of pet class with the attribute in String format separated by a line break
     */
    public List findBypotentDangerousInNeighborhood(int n, String position, String neighborhood) {
        var contador = 0;
        List animales = new ArrayList();
        if (position.equals("TOP")) {
            for (int i = 0; i < arr_pet.size(); i++) {
                if (neighborhood.equals(arr_pet.get(i).getNeighborhood()) && arr_pet.get(i).isPotentDangerous()) {
                    animales.add(arr_pet.get(i));
                    contador++;
                    if (contador == n) {
                        return animales;
                    }
                }
            }
        } else if (position.equals("LAST")) {
            for (int i = arr_pet.size() - 1; i > n; i--) {
                if (arr_pet.get(i).getNeighborhood().equals(neighborhood)) {
                    animales.add(arr_pet.get(i));
                    contador++;
                    if (contador == n) {
                        return animales;
                    }
                }
            }
        } else {
            System.out.println("come monda");
        }
        return null;
    }

    /**
     * Return the animals ids that comply the search criteria
     *
     * @param sex
     * @param species
     * @param size
     * @param potentDangerous
     * @return List of animals ids that comply the search criteria
     */
    public List findByMultipleFields(String species, String sex, String size, String potentDangerous) {
        var flag = false;
        var lista = new ArrayList<String>();
        if (potentDangerous.equals("SI"))
            flag = true;
        for (int i = 0; i < arr_pet.size(); i++) {
            obj_pet = arr_pet.get(i);
            if (obj_pet.getSex().equals(sex) && obj_pet.getSpecies().equals(species) && obj_pet.getSize().equals(size) && obj_pet.isPotentDangerous() == flag) {
                lista.add(obj_pet.getId());
            }
        }
        return lista;
    }
}
