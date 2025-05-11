package br.com.luizgmelo.desafiocadastro.models;

import java.util.List;

public class Pet {
    private String name;
    private PetType type;
    private PetSex sex;
    private Address address;
    private Float age;
    private Float weight;
    private String breed;
    private final String NOT_INFORMED = "NÃO INFORMADO";

    public Pet(Address address) {
        this.address = address;
    }

    public Pet(String name, PetType type, PetSex sex, Address address, Float age, Float weight, String breed) {
        this.name = name;
        this.type = type;
        this.sex = sex;
        this.address = address;
        this.age = age;
        this.weight = weight;
        this.breed = breed;
    }

    public Pet(List<String> petData) {
        this.name = petData.get(0);
        this.type = PetType.valueOf(petData.get(1));
        this.sex = PetSex.valueOf(petData.get(2));
        this.address = new Address(petData.get(3), Integer.parseInt(petData.get(4)), petData.get(5));
        this.age = petData.get(6).equals(NOT_INFORMED) ?  null : Float.parseFloat(petData.get(6).replace(" anos", ""));
        this.weight = petData.get(7).equals(NOT_INFORMED) ? null : Float.parseFloat(petData.get(7).replace("kg", ""));
        this.breed = petData.get(8);
    }

    @Override
    public String toString() {
        return  "1 - " + name + "\n" +
                "2 - " + capitalize(type.name()) + "\n" +
                "3 - " + capitalize(sex.name()) + "\n" +
                "4 - " + address.getStreet() + ", " + address.getHouseNumber() + ", " + address.getCity() + "\n" +
                "5 - " + age + " anos\n" +
                "6 - " + weight + "kg\n" +
                "7 - " + breed + "\n";
    }

    public String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public PetSex getSex() {
        return sex;
    }

    public void setSex(PetSex sex) {
        this.sex = sex;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
}
