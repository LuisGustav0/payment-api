package com.ead.model;

import com.ead.enums.PaymentStatusE;
import com.ead.enums.UserStatusE;
import com.ead.enums.UserTypeE;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserModel implements Serializable {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 150)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatusE statusE;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private UserTypeE typeE;

    @Column(nullable = false, length = 20)
    private String cpf;

    @Column(length = 200)
    private String imageUrl;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatusE paymentStatusE;

    @Column
    private OffsetDateTime paymentExpirationDate;

    @Column
    private OffsetDateTime firstPaymentDate;

    @Column
    private OffsetDateTime lastPaymentDate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<PaymentModel> listPayment;

    @Transient
    public boolean isBlocked() {
        return UserStatusE.BLOCKED.equals(this.statusE);
    }
}
