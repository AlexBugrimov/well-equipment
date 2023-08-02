package dev.bug.console;

import dev.bug.domain.Count;
import dev.bug.domain.WellName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SetupLineTest {

    @Test
    void shouldReturnCountAndWellName() {
        var setupLine = SetupLine.of("1", "Well_Name");

        assertThat(setupLine.count()).isEqualTo(Count.one());
        assertThat(setupLine.wellName()).isEqualTo(WellName.of("Well_Name"));
    }

    @Test
    void shouldReturnErrorIfCountIsNull() {
        assertThatThrownBy(() -> SetupLine.of(null, "Well_Name"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("Параметр является обязательным и не должен быть пустым");
    }

    @Test
    void shouldReturnErrorIfWellNameIsNull() {
        assertThatThrownBy(() -> SetupLine.of("10", null))
                .isInstanceOf(AssertionError.class)
                .hasMessage("Параметр является обязательным и не должен быть пустым");
    }

    @Test
    void shouldReturnErrorIfNumberOfParamsAreNotTwo() {
        assertThatThrownBy(() -> SetupLine.of(new String[]{"1"}))
                .isInstanceOf(AssertionError.class)
                .hasMessage("Размер массива должен быть равен 2-м");
    }
}
