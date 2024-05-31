package carsharing.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "payments")
@Getter
@Setter
@SQLDelete(sql = "UPDATE payments SET is_deleted = TRUE WHERE id = ?")
@Where(clause = "is_deleted = FALSE")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "rental_id", nullable = false)
    private Rental rental;
    @Column(name = "payment_status", columnDefinition = "varchar", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Column(name = "payment_type", columnDefinition = "varchar", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;
    @Column(name = "session_url", columnDefinition = "varchar", nullable = false)
    private String sessionUrl;
    @Column(name = "session_id", nullable = false)
    private String sessionId;
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    public enum PaymentType {
        PAYMENT,
        FINE
    }

    public enum PaymentStatus {
        PENDING,
        PAID
    }
}
