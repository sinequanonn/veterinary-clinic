package vet.vetclinic.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Vet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vetId;

    @Column(nullable = false)
    private String vetName;

    protected Vet() {
    }

    public Vet(String vetName) {
        this.vetName = vetName;
    }
}
