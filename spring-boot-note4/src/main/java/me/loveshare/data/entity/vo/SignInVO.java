package me.loveshare.data.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import me.loveshare.data.definition.RegExpDefinition;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

public class SignInVO {

    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "${username.empty}")
    @Pattern(regexp = RegExpDefinition.USERNAME, message = "${username.format.error}")
    private String username; //用户名格式错误，英文大小写、数字、@_.-，长度4-25位
    @ApiModelProperty(value = "用户密码", required = true)
    @NotBlank(message = "${password.empty}")
    @Pattern(regexp = RegExpDefinition.PASSWORD, message = "${password.format.error}")
    private String password; //为了保证您注册账户安全，密码必须为大写字母、小写字母、数字、特殊字符中至少三种组合,长度6-15位

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
