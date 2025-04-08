package br.com.luizgmelo.desafiocadastro.model;

public class Pet {
    private String name;
    private PetType type;
    private PetSex sex;
    private Address address;
    private String age;
    private String weight;
    private String breed;

    public Pet(String name, PetType type, PetSex sex, Address address, String age, String weight, String breed) {
        this.name = name;
        this.type = type;
        this.sex = sex;
        this.address = address;
        this.age = age;
        this.weight = weight;
        this.breed = breed;
    }

    public String getName() {
        return name;
    }

    public PetType getType() {
        return type;
    }

    public PetSex getSex() {
        return sex;
    }

    public Address getAddress() {
        return address;
    }

    public String getAge() {
        return age;
    }

    public String getWeight() {
        return weight;
    }

    public String getBreed() {
        return breed;
    }

    @Override
    public String toString() {
        return  "1 - " + name + "\n" +
                "2 - " + type + "\n" +
                "3 - " + sex + "\n" +
                "4 - " + address.toString() + "\n" +
                "5 - " + age + "\n" +
                "6 - " + weight + "\n" +
                "7 - " + breed + "\n";
    }
}
