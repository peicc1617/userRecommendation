package xjtucad.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xjtucad.model.MemberPO;
import xjtucad.model.MoviePO;
import xjtucad.model.MovieReviewPO;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class MovieReviewService {
    @Resource
    private RedisTemplate<String, MovieReviewPO> template;
    public void mockData(MemberPO memberPO, MoviePO moviePO,Integer score){
        Map<Object,Object> scoreMap=template.opsForHash().entries(String.valueOf(memberPO.getId()));
        if(scoreMap==null){
            scoreMap=new HashMap<>();
        }
        scoreMap.put(moviePO.getId(),score);
        template.opsForHash().putAll(String.valueOf(memberPO.getId()),scoreMap);
        log.info("[MovieReviewService]保存成功");
    }

    /**
     * 根据用户id获取用户评分列表
     * @param memberId
     * @return
     */
    public List<Integer> getScoreList(int memberId){
        Map<Object,Object> scoreMap=template.opsForHash().entries(String.valueOf(memberId));
        List<Integer> result=new ArrayList<>();
        Map<Integer,Integer>sortMap=new TreeMap<Integer, Integer>(
                new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2.compareTo(o1);
                    }
                }
        );
        for (Object key:scoreMap.keySet()) {
            Integer movieIndex=(Integer) key;
            Integer score=(Integer)scoreMap.get(key);
            sortMap.put(movieIndex,score);
        }
        for (Object key:sortMap.keySet()) {
            result.add(sortMap.get(key));
        }
        return result;
    }
}
