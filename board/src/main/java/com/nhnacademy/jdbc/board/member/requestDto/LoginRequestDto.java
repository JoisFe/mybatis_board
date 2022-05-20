package com.nhnacademy.jdbc.board.member.requestDto;

import javax.validation.constraints.NotBlank;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

@Value
public class LoginRequestDto {
    @NotBlank @Length(max=3)
    String memberId;
    @NotBlank @Length(max=3)
    String memberPwd;
}
