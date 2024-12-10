package pl.kubag5.demo;

public enum Discount {
    CHILD_UNDER_4(1.0, "Darmowy wstęp dla dzieci poniżej 4 lat"),
    MEN_OVER_65(0.30, "30% zniżki dla mężczyzn powyżej 65 roku życia"),
    WOMEN_OVER_60(0.30, "30% zniżki dla kobiet powyżej 60 roku życia"),
    ARMY_DISABLED_MEN(0.37, "37% zniżki dla niepełnosprawnych weteranów wojskowych"),
    LERNER(0.37, "37% zniżki dla uczniów"),
    BLIND_PERSON(0.37, "37% zniżki dla osób niewidomych"),
    PERSON_INCAPABILITY_TO_EXIST_INDEPENDENTLY(0.37, "37% zniżki dla osób niezdolnych do samodzielnej egzystencji"),
    PARENT_WITH_BIG_FAMILY_CARD(0.37, "37% zniżki dla rodziców z Kartą Dużej Rodziny"),
    SPOUSE_OF_PARENT_WITH_BIG_FAMILY_CARD(0.37, "37% zniżki dla małżonka rodzica z Kartą Dużej Rodziny"),
    VETERAN_CLASS_1(0.37, "37% zniżki dla weteranów klasy 1"),
    VETERAN_CLASS_2(0.37, "37% zniżki dla weteranów klasy 2"),
    CONTRACT(0.50, "50% zniżki na podstawie umowy"),
    ANTICOMMUNIST_OPPOSITION_ACTIVIST(0.51, "51% zniżki dla działaczy opozycji antykomunistycznej"),
    COMBATANT(0.51, "51% zniżki dla kombatantów"),
    STUDENT(0.51, "51% zniżki dla studentów"),
    BLIND_ARMY_DISABLED_MEN(0.78, "78% zniżki dla niewidomych niepełnosprawnych wojskowych"),
    DISABLED_STUDENTS_AND_LERNERS(0.78, "78% zniżki dla niepełnosprawnych uczniów i studentów"),
    INVALID_COMBATANT(0.78, "78% zniżki dla niepełnosprawnych kombatantów"),
    ARMY_MAN(0.78, "78% zniżki dla wojskowych"),
    GUIDE(0.95, "95% zniżki dla przewodników");

    private final double discount;
    private final String description;

    Discount(double discount, String description) {
        this.discount = discount;
        this.description = description;
    }

    public double applyDiscount(double price) {
        return price - (price * discount);
    }

    public String getDescription() {
        return description;
    }

    public double getDiscount() {
        return discount;
    }
}
