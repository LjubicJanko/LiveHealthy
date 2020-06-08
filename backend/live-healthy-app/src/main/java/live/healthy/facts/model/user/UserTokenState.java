package live.healthy.facts.model.user;

import live.healthy.facts.dto.UserDTO;
import live.healthy.facts.dto.UserWithAuthoritiesDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTokenState {

    private String accessToken;
    private int expiresIn;
    private UserWithAuthoritiesDTO userWithAuthoritiesDTO;

}