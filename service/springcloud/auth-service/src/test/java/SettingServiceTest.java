import com.alibaba.fastjson.JSONObject;
import com.anji.mirror.auth.AuthApplication;
import com.anji.mirror.auth.domain.po.MenuActionPO;
import com.anji.mirror.auth.domain.po.MenuPO;
import com.anji.mirror.auth.domain.vo.SettingVO;
import com.anji.mirror.auth.mapper.MenuActionMapper;
import com.anji.mirror.auth.mapper.MenuMapper;
import com.anji.mirror.auth.service.SettingService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = AuthApplication.class)
@RunWith(SpringRunner.class)
public class SettingServiceTest {

    @Autowired
    private SettingService settingService;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private MenuActionMapper menuActionMapper;

    @Test
    public void queryBySettingName(){
        SettingVO settingVO = settingService.queryBySettingName("demo_config");
        Integer integer = settingVO.getIntSettingValue();
        Map map = settingVO.getSettingValueJson();
        System.out.println(JSONObject.toJSONString(settingVO));
    }

    @Test
    public void initMenuAction(){
        long[] actions = new long[]{1,2,3,4};
        QueryWrapper<MenuPO> menuWrapper = new QueryWrapper<MenuPO>();
        menuWrapper.gt("menu_id", 10);
        List<MenuPO> menuPOS = menuMapper.selectList(menuWrapper);
        menuPOS.stream().forEach(menuPO -> {
            MenuActionPO menuActionPO = new MenuActionPO();
            menuActionPO.setMenuId(menuPO.getMenuId());
            Arrays.stream(actions).forEach(actionId ->{
                menuActionPO.setActionId(actionId);
                menuActionMapper.insert(menuActionPO);
            });
        });
    }
}
