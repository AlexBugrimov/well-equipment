package dev.bug.usecase;

import dev.bug.domain.Equipment;
import dev.bug.domain.WellName;

import java.util.List;

public interface GetEquipmentsByWells {

    List<Equipment> getEquipments(WellName... wellNames);
}
