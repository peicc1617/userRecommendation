package xjtucad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xjtucad.model.MemberPO;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendService {
    @Autowired
    private MovieReviewService movieReviewService;

    /**
     * 计算两个用户的相似度
     * @param currentMemberId
     * @param compareMemberId
     * @return double 相似度
     */
    public double calculateSimilarity(int currentMemberId,int compareMemberId){
        List<Integer> currentIndexList=movieReviewService.getScoreList(currentMemberId);
        List<Integer> compareIndexList=movieReviewService.getScoreList(compareMemberId);
        //检查两个人的的评分列表是否一样(向量维度)
        if(currentIndexList.size()==compareIndexList.size()){
            int numberOfMovie=MovieService.moviePOList.size();
            int result=0;
            //计算向量的欧氏距离
            for (int i = 0; i <numberOfMovie ; i++) {
                int x1=currentIndexList.get(i);
                int x2=compareIndexList.get(i);
                result+=(int)Math.pow((x1-x2),2);
            }
            double degree=Math.sqrt(result);
            return degree;
        }
        return 0;
    }

    /**
     * 计算当前用户与其他用户的匹配程度，从高到低存储到list中
     * @param currentMemberId
     * @return list存储当前用户与其他用户的匹配程度
     */
    public List calculateSimilarityList(int currentMemberId){
        List<Integer> idList=MemberService.memberPOList
                .stream()
                .filter(memberPO -> memberPO.getId()!=currentMemberId)
                .map(MemberPO::getId)
                .collect(Collectors.toList());
        //存储当前用户与其他用户的匹配度，key为用户id,value为匹配度
        Map<Integer,Double> similarityMap=new HashMap<>();
        for (Integer memberId:idList) {
            double degree=calculateSimilarity(currentMemberId,memberId);
            similarityMap.put(memberId,degree);
        }
        //将map.entrySet()转换成list
        List<Map.Entry<Integer,Double>> list=new ArrayList<>(similarityMap.entrySet());
        //通过比较器来实现排序
        Collections.sort(list,new Comparator<Map.Entry<Integer,Double>>(){
            //降序排序
            @Override
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        return list;
    }
}
