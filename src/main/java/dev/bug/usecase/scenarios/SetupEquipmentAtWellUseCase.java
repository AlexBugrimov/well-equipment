package dev.bug.usecase.scenarios;

import dev.bug.domain.Count;
import dev.bug.domain.WellName;
import dev.bug.usecase.SetupEquipmentAtWell;
import dev.bug.usecase.access.EquipmentPersistence;

public record SetupEquipmentAtWellUseCase(EquipmentPersistence equipmentPersistence) implements SetupEquipmentAtWell {

    @Override
    public void setUpEquipment(Count count, WellName wellName) {
        equipmentPersistence.setupEquipment(count, wellName);
    }
}
