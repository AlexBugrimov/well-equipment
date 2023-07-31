package dev.bug.usecase;

import dev.bug.domain.Count;
import dev.bug.domain.WellName;

public interface SetupEquipmentAtWell {

    void setUpEquipment(Count count, WellName wellName);
}
