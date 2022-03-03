package hu.novin.greenfox.domain;

import com.sun.istack.NotNull;
import hu.novin.greenfox.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private String comment;

    @NotNull
    private BigDecimal price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public String toString() {
        return name;
    }
}
