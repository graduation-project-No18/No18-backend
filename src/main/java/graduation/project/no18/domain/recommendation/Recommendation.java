package graduation.project.no18.domain.recommendation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import graduation.project.no18.domain.recording.Recording;
import graduation.project.no18.domain.song.Song;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Recommendation {

    @Id
    @Column(name = "recommendation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "recording_id")
    private Recording recording;

    @OneToMany(mappedBy = "recommendation")
    private List<Song> songList = new ArrayList<>();

}
