package vet.vetclinic.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VetTest {
    @Test
    void 이름으로_수의사를_생성한다() {
        //given
        String vetName = "박진우";

        //when
        Vet vet = new Vet("박진우");

        //then
        Assertions.assertThat(vet.getVetName()).isEqualTo(vetName);
    }
}
