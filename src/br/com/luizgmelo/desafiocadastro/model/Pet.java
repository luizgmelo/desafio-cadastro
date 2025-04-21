package br.com.luizgmelo.desafiocadastro.model;

public class Pet {
    private String name;
    private PetType type;
    private PetSex sex;
    private String streetName;
    private String houseNumber;
    private String city;
    private String age;
    private String weight;
    private String breed;

    public Pet(String name, PetType type, PetSex sex, String streetName, String houseNumber, String city, String age, String weight, String breed) {
        this.name = name;
        this.type = type;
        this.sex = sex;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.city = city;
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

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
                "2 - " + capitalize(type.name()) + "\n" +
                "3 - " + capitalize(sex.name()) + "\n" +
                "4 - " + streetName + ", " + houseNumber + ", " + city + "\n" +
                "5 - " + age + "\n" +
                "6 - " + weight + "\n" +
                "7 - " + breed + "\n";
    }

    public String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
}
