package es.uvigo.esei.daa.entities;

public class Pet {
    private int id;
    private String name;
    private int owner;


    // Constructor needed for the JSON conversion
    Pet() {}

    /**
     * Constructs a new instance of {@link Pet}
     *
     * @param id idintefier of the pet
     * @param name name of the pet
     * @param owner identifier of the pet ownet
     */
    public Pet(int id, int owner, String name) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Pet)) return false;
        if (!super.equals(object)) return false;
        Pet pet = (Pet) object;
        return getId() == pet.getId() &&
                getOwner() == pet.getOwner() &&
                java.util.Objects.equals(getName(), pet.getName());
    }

    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), getId(), getName(), getOwner());
    }
}


