package dev.bug.console;

import dev.bug.domain.Count;
import dev.bug.domain.WellName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SetupEquipmentParameterExtractorTest {

    private SetupEquipmentParameterExtractor parameterExtractor;

    @BeforeEach
    void setUp() {
        parameterExtractor = new SetupEquipmentParameterExtractor();
    }

    @ParameterizedTest
    @CsvSource({"2 , Command line '2' is not correct", " Some_Name, Command line 'Some_Name' is not correct"})
    void shouldErrorIfParamIsOne(String inputLine, String errorMessage) {
        assertThatThrownBy(() -> parameterExtractor.extract(inputLine))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining(errorMessage);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldErrorIfParamIsNullOrEmpty(String inputLine) {
        assertThatThrownBy(() -> parameterExtractor.extract(inputLine))
                .isInstanceOf(AssertionError.class);
    }
}
