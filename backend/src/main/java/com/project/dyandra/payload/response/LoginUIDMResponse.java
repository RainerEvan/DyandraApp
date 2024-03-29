package com.project.dyandra.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUIDMResponse {
    private String loginDetail;
    private String loggedUserDetail;
    private String loggedUserRoles;
}
