package com.zhaolq.mars.service.sys.controller;

import com.zhaolq.mars.service.sys.entity.UserEntity;
import com.zhaolq.mars.tool.core.jackson.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockCookie;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

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
        userEntity.setBirthday(new Date());
        userEntity.setEmail("test@qq.com");
        userEntity.setMobile("13566667777");
        userEntity.setCountryCode("156");
        userEntity.setNationCode("01");
        userEntity.setPoliticalStatusCode("01");
        userEntity.setDeptId("1");
        userEntity.setCreateBy("JUnit");
        userEntity.setCreateTime(LocalDateTime.now());
        userEntity.setStatus(Byte.valueOf("1"));
        userEntity.setDelFlag(Byte.valueOf("0"));

        session = new MockHttpSession();
        cookie = new MockCookie("session_id", session.getId());
    }

    /**
     * 无法启动应用程序上下文，该测试将失败
     */

    @Order(0)
    @Test
    @DisplayName("contextLoads()")
    public void contextLoads() {
        log.debug("测试使用的随机端口：" + port);
    }

    @Order(1)
    @Test
    @DisplayName("post()")
    public void post() throws Exception {
        // 构造请求
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user")
                .header("user-agent", "Chrome")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
                .cookie(cookie)
                .content(JacksonUtil.objectToJson(userEntity));
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
                .header("user-agent", "Chrome")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
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
    }

    @Order(3)
    @Test
    @DisplayName("update()")
    public void update() throws Exception {
        UserEntity userTemp = userEntity;
        userTemp.setAccount("testUpdate");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/user")
                .header("user-agent", "Chrome")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
                .cookie(cookie)
                .content(JacksonUtil.objectToJson(userEntity))
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
                .header("user-agent", "Chrome")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
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
