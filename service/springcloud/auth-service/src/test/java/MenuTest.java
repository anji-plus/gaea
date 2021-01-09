import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MenuTest extends BaseTest{

    private String token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIn0.KixnH7d5xlnVBJWlnpP3vdcYzo18LrPzTBsW5c6prstecLKcCozfI04o6foIwJrQMHdSzpHyv6bUZxfjwqai4A";


    @Test
    public void queryByPage(){
        String params = "{}";
        JSONObject response = post("/auth-service/menu/queryByPage", params, token);
        System.out.println(response.toJSONString());
    }
}
