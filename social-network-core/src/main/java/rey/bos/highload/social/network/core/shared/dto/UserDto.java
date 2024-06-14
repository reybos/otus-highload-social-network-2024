package rey.bos.highload.social.network.core.shared.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

@Data
@Builder
public class UserDto {

    private String userId;

    private String firstName;

    private String secondName;

    private LocalDate birthdate;

    private String biography;

    private String city;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    private Collection<String> roles;

}
