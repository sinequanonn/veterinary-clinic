package vet.vetclinic.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Vet {
    private static final int MAX_NAME_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vetId;

    @Column(nullable = false, length=MAX_NAME_LENGTH)
    private String vetName;

    protected Vet() {
    }

    public Vet(String vetName) {
        validateVetName(vetName);
        this.vetName = vetName;
    }

    private void validateVetName(String vetName) {
        if (vetName == null || vetName.isBlank()) {
            throw new IllegalArgumentException("수의사명은 필수 입력값입니다.");
        }


        if (vetName.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("수의사명은 1자 이상 20자 이하여야 합니다.");
        }
    }
}
