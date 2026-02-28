package Com.BookMyShow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @Column(unique = true)
    private String username;

    private String password;

    private String role;

    @NotBlank
    @Pattern(
            regexp = "^\\+[1-9]\\d{1,14}$",
            message = "Phone number must be in international format (E.164), e.g., +14155552671"
    )
    private String phoneNumber;

    @NotBlank
    @Email(message = "Invalid email format")
    private String emailId;}





