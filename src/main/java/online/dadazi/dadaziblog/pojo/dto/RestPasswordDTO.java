package online.dadazi.dadaziblog.pojo.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 重置密码dto
 * @author lqk
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestPasswordDTO {
    /**
     * 原始密码
     */
    @NotBlank(message = "请输入旧密码")
    @Pattern(message = "请输入正确的密码格式", regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$")
    private String oldPassword;
    /**
     * 新密码
     */
    @NotBlank(message = "请输入新密码")
    @Pattern(message = "请输入正确的密码格式", regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$")
    private String newPassword;
    /**
     * 确认密码
     */
    @NotBlank(message = "请输入确认密码")
    @Pattern(message = "请输入正确的密码格式", regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$")
    private String restPassword;

}
