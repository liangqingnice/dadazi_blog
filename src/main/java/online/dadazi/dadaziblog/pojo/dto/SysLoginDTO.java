package online.dadazi.dadaziblog.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录dto
 * @author lqk
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysLoginDTO {
    /**
     * 账号
     */
    @NotBlank(message = "请输入账号")
    @Pattern(message = "请输入正确的账号", regexp = "^[a-zA-Z0-9_]{4,16}$")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "请输入密码")
    @Pattern(message = "请输入正确的密码", regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$")
    private String password;
    /**
     * 验证码
     */
    private String code;
    /**
     * 验证码Key
     */
    private String codeKey;
}
