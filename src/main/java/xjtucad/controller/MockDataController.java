package xjtucad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xjtucad.model.MemberPO;
import xjtucad.model.MoviePO;
import xjtucad.service.MemberService;
import xjtucad.service.MovieReviewService;
import xjtucad.service.MovieService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 模拟向数据库插入数据
 */
@RestController
public class MockDataController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieReviewService movieReviewService;
    @GetMapping(value="/mockData")
    public String mockData(){
        List<String> list=MovieService.moviePOList
                .stream()
                .map(moviePO -> moviePO.getMovieName())
                .collect(Collectors.toList());
        List<Integer> score= Arrays.asList(-1, 4, -1, 3, 3, 5, 5, -1, 4);
        String name="小芳";
        int index=0;
        //对同一用户对不同电影的评分插入redis数据库
        for (String movieName:list) {
            this.mockData(name,movieName,score.get(index));
            index++;
        }
        return "success";
    }
    private void mockData(String memberName,String movieName,int score){
        MemberPO memberPO=memberService.getMemberByName(memberName);
        MoviePO moviePO=movieService.getMovieByName(movieName);
        movieReviewService.mockData(memberPO,moviePO,score);
        System.out.println(memberPO.toString()+" "+moviePO.toString());
    }
}
