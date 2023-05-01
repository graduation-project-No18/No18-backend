package graduation.project.no18.domain.member;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDto {

    private String nickname;
    private String profileImg;
    private String introduction;
    private String Octave;
}
