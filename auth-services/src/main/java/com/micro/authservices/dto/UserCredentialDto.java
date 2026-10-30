package com.micro.authservices.dto;


import com.micro.authservices.model.UserCredential;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialDto {
    private String name;
    private String email;
    private String password;

    public UserCredential convertUserCredentialDtoToUserCredential() {
        UserCredential credential = new UserCredential();
        credential.setName(this.name);
        credential.setEmail(this.email);
        credential.setPassword(this.password);
        return credential;
    }

    public static UserCredentialDto convertUserCredentialToUserCredentialDto(UserCredential credential) {
        if (credential == null) {
            return null;
        }
        UserCredentialDto dto = new UserCredentialDto();
        dto.setName(credential.getName());
        dto.setEmail(credential.getEmail());
        dto.setPassword(credential.getPassword());
        return dto;
    }

}
