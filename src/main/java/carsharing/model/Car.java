package carsharing.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = "id")
@SQLDelete(sql = "UPDATE cars SET is_deleted = TRUE WHERE id = ?")
@Where(clause = "is_deleted = FALSE")
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "model")
    private String model;
    @Column(nullable = false, name = "brand")
    private String brand;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "type")
    private Type type;
    @Column(nullable = false, name = "inventory_number")
    private int inventoryNumber;
    @Column(nullable = false, name = "fee")
    private BigDecimal fee;
    @Column(nullable = false)
    private boolean isDeleted = false;
}
