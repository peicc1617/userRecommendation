package xjtucad.service;

import xjtucad.model.MoviePO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieService {
    public static List<MoviePO> moviePOList=new ArrayList<>();
    static {
        List<String> movieNames = Arrays.asList("绿皮书", "复仇者联盟", "月光男孩", "海边的曼彻斯特",
                "盗梦空间", "记忆碎片", "致命魔术", "流浪地球", "正义联盟");
        int id = 0;
        for (String movieName : movieNames) {
            moviePOList.add(new MoviePO(id++, movieName));
        }
    }

    /**
     * 根据名称获取电影信息
     * @param name
     * @return
     */
    public MoviePO getMovieByName(String name){
        return moviePOList.stream().filter(moviePO -> {
            return moviePO.getMovieName().equals(name);
        }).findFirst().get();
    }
}
