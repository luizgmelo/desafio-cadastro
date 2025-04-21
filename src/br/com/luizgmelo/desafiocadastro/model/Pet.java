package br.com.luizgmelo.desafiocadastro.model;

import br.com.luizgmelo.desafiocadastro.dto.PetDto;

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

    public Pet(PetDto petDto) {
        this.name = petDto.getName();
        this.type = PetType.valueOf(petDto.getType());
        this.sex = PetSex.valueOf(petDto.getSex());
        this.streetName = petDto.getStreetName();
        this.houseNumber = petDto.getHouseNumber();
        this.city = petDto.getCity();
        this.age = petDto.getAge();
        this.weight = petDto.getWeight();
        this.breed = petDto.getBreed();
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
                "2 - " + type + "\n" +
                "3 - " + sex + "\n" +
                "4 - " + streetName + ", " + houseNumber + ", " + city + "\n" +
                "5 - " + age + "\n" +
                "6 - " + weight + "\n" +
                "7 - " + breed + "\n";
    }
}
