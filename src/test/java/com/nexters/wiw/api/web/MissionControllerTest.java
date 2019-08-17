package com.nexters.wiw.api.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
public class MissionControllerTest {

    @Autowired
    private MockMvc mvc;

    //미션 저장 후 불러오기
    @Test
    public void missionSaveAndGetTest() throws Exception {

    }
}