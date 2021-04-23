package com.zhaolq.mars.service.sys.controller;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhaolq.mars.service.sys.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 *
 * @author zhaolq
 * @date 2020/10/20 21:13
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    /**
     * 无法启动应用程序上下文，该测试将失败
     */
    @Test
    public void contextLoads() {
        log.debug("测试使用的随机端口：" + port);
    }

    @Test
    @DisplayName("post()")
    public void post() throws Exception {
        UserEntity userEntity = new UserEntity();

        userEntity.setAccount("test");
        userEntity.setPassword("21218CCA77804D2BA1922C33E0151105");
        userEntity.setName("测试");
        userEntity.setNickName("昵称");
        userEntity.setSalt("YzcmCZNvbXocrsz9dm8e");
        userEntity.setSex(Byte.valueOf("1"));
        userEntity.setBirthday(new Date());
        userEntity.setEmail("test@qq.com");
        userEntity.setMobile("13566667777");
        userEntity.setCountryCode("156");
        userEntity.setNationCode("01");
        userEntity.setPoliticalStatusCode("01");
        userEntity.setDeptId(BigDecimal.valueOf(1L));
        userEntity.setCreateBy("JUnit");
        userEntity.setCreateTime(LocalDateTime.now());
        userEntity.setStatus(Byte.valueOf("1"));
        userEntity.setDelFlag(Byte.valueOf("0"));

        System.out.println(JSONUtil.toJsonStr(userEntity));
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(userEntity));
        String json = "{\"account\":\"test\",\"password\":\"21218CCA77804D2BA1922C33E0151105\",\"name\":\"测试\",\"nickName\":\"昵称\"," +
                "\"salt\":\"YzcmCZNvbXocrsz9dm8e\",\"sex\":1,\"birthday\":\"2021-04-20 13:49:37\",\"age\":null,\"idNumber\":null,\"address\":null," +
                "\"email\":\"test@qq.com\",\"mobile\":\"13566667777\",\"entryTime\":null,\"departureTime\":null,\"countryCode\":\"156\"," +
                "\"nationCode\":\"01\",\"politicalStatusCode\":\"01\",\"userType\":null,\"identityCode\":null,\"deptId\":1,\"createBy\":\"JUnit\"," +
                "\"createTime\":\"2021-04-20 13:49:37\"," +
                "\"lastUpdateBy\":null,\"lastUpdateTime\":null,\"status\":1,\"delFlag\":0,\"flag\":null,\"role\":null,\"remarkTest\":null," +
                "\"remarkTest3\":null,\"id\":null}";
        System.out.println(json);


        // 构造请求
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user")
                .header("user-agent", "Chrome")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .session(new MockHttpSession())
                .content(json);
        // 执行一个请求并返回一个类型，该类型允许对结果链接进一步的操作，例如：打印MvcResult详细信息、断言期望。
        ResultActions resultActions = mockMvc.perform(request);
        MvcResult mvcResult = resultActions
                .andDo(print()) // 打印MvcResult详细信息
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(content().string(containsString("请求成功")))
                .andExpect(content().json("{\"code\":1,\"data\":null,\"msgEn\":\"success\",\"msgCh\":\"请求成功\",\"success\":true}"))
                .andReturn();
        log.debug("HTTP响应状态码" + mvcResult.getResponse().getStatus());

        // get(userEntity);

        // update(userEntity);

        // delete();
    }

    @Test
    @DisplayName("get()")
    public void get(UserEntity userEntity) throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .param("account", userEntity.getAccount());
        ResultActions resultActions = mockMvc.perform(request);
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andReturn();
        log.debug("HTTP响应状态码" + mvcResult.getResponse().getStatus());
    }

    @Test
    @DisplayName("update()")
    public void update(UserEntity userEntity) throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/user")
                .header("user-agent", "Chrome")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .session(new MockHttpSession())
                .content(JSONUtil.toJsonStr(userEntity))
                .param("account", userEntity.getAccount());
        ResultActions resultActions = mockMvc.perform(request);
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andReturn();
        log.debug("HTTP响应状态码" + mvcResult.getResponse().getStatus());
    }

    @Test
    @DisplayName("delete()")
    public void delete(String id) throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/user/1382969808408481794"));

    }


}
