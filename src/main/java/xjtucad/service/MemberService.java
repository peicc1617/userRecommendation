package xjtucad.service;

import org.springframework.stereotype.Service;
import xjtucad.model.MemberPO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MemberService {
    public static List<MemberPO> memberPOList=new ArrayList<>();
    static {
        List<String> memberNames= Arrays.asList("小明", "小王", "小东", "小红", "小乔", "小芳");
        int id=0;
        for (String memberName:memberNames) {
            memberPOList.add(new MemberPO(id++,memberName));
        }
    }

    /**
     * 根据名称获取用户信息
     * @param name
     * @return
     */
    public MemberPO getMemberByName(String name){
        return memberPOList.stream().filter(memberPO -> {
            return memberPO.getMemberName().equals(name);
        }).findFirst().get();
    }
}
