package com.zebrunner.reporting.domain.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvitationListType {

    @Valid
    @NotEmpty(message = "{error.emailList.notEmpty}")
    private List<InvitationType> invitationTypes;

}
