package edu.unbosque.fourpawscitizens.model.dtos;

public class Pet {
    private String id;
    private long microchip;
    private String species;
    private String sex;
    private String size;
    private boolean potentDangerous;
    private String neighborhood;

    /**
     * This method is the controller of pet.
     * @param id - Sring: id que se debe crear
     * @param microchip - long:
     * @param species - Sring:
     * @param sex - Sring:
     * @param size - Sring:
     * @param potentDangerous - Boolean:
     * @param neighborhood - Sring:
     */
    public Pet(String id, long microchip, String species, String sex, String size, boolean potentDangerous, String neighborhood) {
        this.id = id;
        this.microchip = microchip;
        this.species = species;
        this.sex = sex;
        this.size = size;
        this.potentDangerous = potentDangerous;
        this.neighborhood = neighborhood;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMicrochip(long microchip) {
        this.microchip = microchip;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setPotentDangerous(boolean potentDangerous) {
        this.potentDangerous = potentDangerous;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getId() {
        return id;
    }

    public long getMicrochip() {
        return microchip;
    }

    public String getSpecies() {
        return species;
    }

    public String getSex() {
        return sex;
    }

    public String getSize() {
        return size;
    }
    public String getNeighborhood() {
        return neighborhood;
    }

    public boolean isPotentDangerous() {
        return potentDangerous;
    }

    @Override
    public String toString() {
        return  "ID: " + id + '\n' +
                "Species: " + species + '\n' +
                "Gender: " + sex + '\n' +
                "Size: " + size + '\n' +
                "Potentially: " + potentDangerous +"\n"+
                "Neighborhood: " + neighborhood + '\n'+ '\n';
    }
}
