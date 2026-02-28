package com.BookMyShow.Payment_service.entity;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentTransaction {

    @Id
    private UUID id;

    @ManyToOne
    private Payment payment;

    private String gatewayTxnId;
    private String status;
    private LocalDateTime createdAt;
}
