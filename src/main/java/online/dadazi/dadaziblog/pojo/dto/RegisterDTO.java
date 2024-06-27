package online.dadazi.dadaziblog.pojo.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注册传输
 *
 * @author lqk
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
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
     * 邮箱
     */
    @NotBlank(message = "请输入邮箱")
    @Pattern(message = "请输入正确的邮箱", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;

    /**
     * 昵称
     */
    @NotBlank(message = "请输入昵称")
    @Pattern(message = "请输入正确格式的昵称", regexp = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]{2,16}$")
    private String nickname;

    /**
     * 头像
     */
    @NotBlank(message = "请上传头像")
    @Size(message = "文件路径长度过长" ,max = 200)
    private String userPic;

}
