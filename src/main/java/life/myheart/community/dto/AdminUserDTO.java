package life.myheart.community.dto;

import lombok.Data;

@Data
public class AdminUserDTO {
    private Long id;
    private String name;
    private String sex;
    private String phone;
    private String type;
}
