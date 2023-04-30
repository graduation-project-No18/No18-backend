package graduation.project.no18.domain.song;

import com.fasterxml.jackson.annotation.JsonIgnore;
import graduation.project.no18.domain.recommendation.Recommendation;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
public class Song {

    @Id
    @Column(name = "song_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommendation_id")
    private Recommendation recommendation;

    private String title;
    private String singer;
    private String octave; //노래의 최고음 옥타브
    private String youtubeLink; //노래 공식 유튜브 링크 (금영 유튜브일지도 모름)

}
