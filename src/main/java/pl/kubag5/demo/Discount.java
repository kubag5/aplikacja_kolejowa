package pl.kubag5.demo;

public enum Discount {
    CHILD_UNDER_4(1.0),
    MEN_OVER_65(.30),
    WOMEN_OVER_60(.30),
    ARMY_DISABLED_MEN(.37),
    LERNER(.37),
    BLIND_PERSON(.37),
    PERSON_INCAPABILITY_TO_EXIST_INDEPENDENTLY(.37),
    PARENT_WITH_BIG_FAMILY_CARD(.37),
    SPOUSE_OF_PARENT_WITH_BIG_FAMILY_CARD(.37),
    VETERAN_CLASS_1(.37),
    VETERAN_CLASS_2(.37),
    CONTRACT(.5),
    ANTICOMMUNIST_OPPOSITION_ACTIVIST(.51),
    COMBATANT(.51),
    STUDENT(.51),
    BLIND_ARMY_DISABLED_MEN(.78),
    DISABLED_STUDENTS_AND_LERNERS(.78),
    INVALID_COMBATANT(.78),
    ARMY_MAN(.78),
    GUIDE(.95);


    private double discount;
    Discount(double v) {
        this.discount = v;
    }

    public double applyDiscount(double price) {
        return price - (price * discount);
    }

}
