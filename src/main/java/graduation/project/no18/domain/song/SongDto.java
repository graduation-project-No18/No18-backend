package graduation.project.no18.domain.song;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SongDto {
    private String title;
    private String singer;
    private String octave;
    private String youtubeLink;



}
