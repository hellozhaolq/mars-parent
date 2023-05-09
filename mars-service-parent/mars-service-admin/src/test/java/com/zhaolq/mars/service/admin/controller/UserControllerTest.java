package com.zhaolq.mars.service.admin.controller;

import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockCookie;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.alibaba.fastjson2.JSON;

import com.zhaolq.mars.api.sys.entity.UserEntity;

import lombok.extern.slf4j.Slf4j;

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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    HttpHeaders httpHeaders;

    MockHttpSession session;

    MockCookie cookie;

    private UserEntity userEntity;

    @BeforeEach
    public void before() {
        userEntity = new UserEntity();
        userEntity.setId("123456789");
        userEntity.setAccount("test");
        userEntity.setPassword("21218CCA77804D2BA1922C33E0151105");
        userEntity.setName("测试");
        userEntity.setNickName("昵称");
        userEntity.setSalt("YzcmCZNvbXocrsz9dm8e");
        userEntity.setSex(Byte.valueOf("1"));
        userEntity.setEmail("test@qq.com");
        userEntity.setMobile("13566667777");
        userEntity.setCountryCode("156");
        userEntity.setNationCode("01");
        userEntity.setPoliticalStatusCode("01");
        userEntity.setDeptId("1");
        userEntity.setCreateBy("JUnit");
        userEntity.setStatus(Byte.valueOf("1"));
        userEntity.setDelFlag(Byte.valueOf("0"));

        session = new MockHttpSession();
        cookie = new MockCookie("session_id", session.getId());

        httpHeaders = new HttpHeaders();
        httpHeaders.add("user-agent", "Chrome");
        httpHeaders.add("Content-Type", "application/json;charset=UTF-8");
        httpHeaders.add("Accept", "application/json;charset=UTF-8");
    }

    /**
     * 启动contextLoads(应用程序上下文)，检测该单元测试是否可用
     */
    @Order(0)
    @Test
    @DisplayName("contextLoads()")
    public void contextLoads() {
        log.debug("测试使用的随机端口：" + port);
        Properties properties = System.getProperties();
        for (String key : properties.stringPropertyNames()) {
            System.out.println(key + "=" + properties.getProperty(key));
        }
    }

    @Order(1)
    @Test
    @DisplayName("post()")
    public void post() throws Exception {
        // 构造请求
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user")
                .headers(httpHeaders)
                .session(session)
                .cookie(cookie)
                .content(JSON.toJSONString(userEntity));
        // 执行一个请求并返回一个类型，该类型允许对结果链接进一步的操作，例如：打印MvcResult详细信息、断言期望。
        ResultActions resultActions = mockMvc.perform(request);
        MvcResult mvcResult = resultActions
                .andDo(print()) // 打印MvcResult详细信息
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(content().string(containsString("\"success\":true")))
                .andExpect(content().json("{\"code\":1,\"data\":null,\"msgEn\":\"success\",\"msgCh\":\"操作成功\",\"success\":true}"))
                .andReturn();

        log.debug("HTTP响应状态码" + mvcResult.getResponse().getStatus());
    }

    @Order(2)
    @Test
    @DisplayName("get()")
    public void get() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/user")
                .headers(httpHeaders)
                .session(session)
                .cookie(cookie)
                .param("account", userEntity.getAccount());
        ResultActions resultActions = mockMvc.perform(request);
        MvcResult mvcResult = resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andReturn();
        log.debug("HTTP响应状态码" + mvcResult.getResponse().getStatus());
        System.out.println(mvcResult.getResponse().getContentAsString());
        log.debug(mvcResult.getResponse().getContentAsString());
    }

    @Order(3)
    @Test
    @DisplayName("update()")
    public void update() throws Exception {
        UserEntity userTemp = userEntity;
        userTemp.setAccount("testUpdate");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/user")
                .headers(httpHeaders)
                .session(session)
                .cookie(cookie)
                .content(JSON.toJSONString(userEntity))
                .param("account", userTemp.getAccount());
        ResultActions resultActions = mockMvc.perform(request);

        MvcResult mvcResult = resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andReturn();
        log.debug("HTTP响应状态码" + mvcResult.getResponse().getStatus());
    }

    @Order(4)
    @Test
    @DisplayName("delete()")
    public void delete() throws Exception {
        String userId = userEntity.getId();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/user/" + userId)
                .headers(httpHeaders)
                .session(session)
                .cookie(cookie);
        ResultActions resultActions = mockMvc.perform(request);
        MvcResult mvcResult = resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andReturn();
        log.debug("HTTP响应状态码" + mvcResult.getResponse().getStatus());
    }


}
