package dev.bug.usecase;

import dev.bug.domain.Count;
import dev.bug.domain.WellName;

public interface SetupEquipmentAtWell {

    void execute(Count count, WellName wellName);
}
