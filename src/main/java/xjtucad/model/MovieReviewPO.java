package xjtucad.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieReviewPO {
    private int movieId;
    private int memberId;
    private int reviewScore;
}
