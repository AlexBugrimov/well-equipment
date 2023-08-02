package dev.bug.console;

import dev.bug.domain.Count;
import dev.bug.domain.WellName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SetupEquipmentParameterExtractorTest {

    private SetupEquipmentParameterExtractor parameterExtractor;

    @BeforeEach
    void setUp() {
        parameterExtractor = new SetupEquipmentParameterExtractor();
    }

    @Test
    void shouldReturnSetupLineWithCountAndWellName() {
        var count = new Count(1000);
        var wellName = new WellName("Some_well_name");
        var inputLine = parameterExtractor.extract("%s %s".formatted(count.value(), wellName.value()));

        assertThat(inputLine.count()).isEqualTo(count);
        assertThat(inputLine.wellName()).isEqualTo(wellName);
    }

    @Test
    void shouldErrorIfInputIsNull() {
        assertThatThrownBy(() -> parameterExtractor.extract(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Command line 'null' is not correct");
    }
}
