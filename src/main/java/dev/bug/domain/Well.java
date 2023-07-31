package dev.bug.domain;

import java.util.List;

public record Well(WellId wellId,
                   WellName name,
                   List<Equipment> equipments) implements AgreementRoot {

    public static Well of(WellId wellId, WellName name, List<Equipment> equipments) {
        return new Well(wellId, name, equipments);
    }
}
