package vet.vetclinic.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class VetTest {
    @Test
    void 이름으로_수의사를_생성한다() {
        //given
        String vetName = "박진우";

        //when
        Vet vet = new Vet("박진우");

        //then
        assertThat(vet.getVetName()).isEqualTo(vetName);
    }

    @Test
    void 수의사명_길이가_정확히_20이면_수의사를_생성한다() {
        //given
        String vetName = "a".repeat(20);

        //when
        Vet vet = new Vet(vetName);

        //then
        assertThat(vet.getVetName()).isEqualTo(vetName);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  ", "\t", "\n"})
    void 수의사명이_null이거나_공백이면_예외가_발생한다(String vetName) {
        //when&then
        assertThatThrownBy(() -> new Vet(vetName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수의사명은 필수 입력값입니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {21, 100, 150})
    void 수의사명이_20자를_초과하면_예외가_발생한다(int repetition) {
        //given
        String vetName = "a".repeat(repetition);

        //when&then
        assertThatThrownBy(() -> new Vet(vetName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수의사명은 1자 이상 20자 이하여야 합니다.");
    }
}
