package dev.bug.console;

import dev.bug.domain.Count;
import dev.bug.domain.WellName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SetupLineTest {

    @Test
    void shouldInitializeCountAndWellName() {
        var count = new Count(100);
        var wellName = new WellName("North-well");

        var setupLine = SetupLine.of("%s %s".formatted(count.value(), wellName.value()));

        assertThat(setupLine.count()).isEqualTo(count);
        assertThat(setupLine.wellName()).isEqualTo(wellName);
    }

    @ParameterizedTest
    @ValueSource(strings = {"99", "", "some input line"})
    void shouldReturnErrorIfInputLineHoldsLessThanOrGreaterThanTwoArguments(String input) {
        assertThatThrownBy(() -> SetupLine.of(input))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("The input string must consist of 2 arguments");
    }

    @Test
    void shouldReturnErrorIfInputLineIsNull() {
        assertThatThrownBy(() -> SetupLine.of(null))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("The input string must non null");
    }
}
