package xjtucad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xjtucad.service.RecommendService;

import java.util.List;

/**
 * 根据当前用户，返回其他用户与当前的推荐列表
 */
@RestController
public class RecommendController {
    @Autowired
    private RecommendService recommendService;
    @GetMapping(value = "calculate")
    public List calculateDegree(int currentMemberId){
        return recommendService.calculateSimilarityList(currentMemberId);
    }
}
