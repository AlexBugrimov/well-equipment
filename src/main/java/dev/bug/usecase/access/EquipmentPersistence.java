package dev.bug.usecase.access;

import dev.bug.domain.Count;
import dev.bug.domain.WellName;

public interface EquipmentPersistence {

    void setupEquipment(Count count, WellName wellName);
}
